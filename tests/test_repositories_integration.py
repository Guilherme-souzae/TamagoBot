"""
Testes de integração para PetRepository e UserRepository.
Usam shelve real em diretórios temporários — nenhum dado persiste entre testes.
"""
import pytest
import os
import tempfile
from datetime import datetime, timezone
from unittest.mock import patch

from model.pet.pet_entity import Pet
from model.pet.pet_repository import PetRepository
from model.user.user_entity import User
from model.user.user_repository import UserRepository

BASE_TIME = datetime(2024, 6, 1, 12, 0, 0, tzinfo=timezone.utc)
VALID_URL = "https://cdn.discordapp.com/attachments/111/222/pet.png"


@pytest.fixture(autouse=True)
def isolated_db(tmp_path, monkeypatch):
    """
    Redireciona shelve para um diretório temporário isolado por teste.
    Garante que cada teste parte de um banco limpo.
    """
    db_path = str(tmp_path / "pets")
    user_db_path = str(tmp_path / "users")

    original_open = __import__("shelve").open

    def patched_open(filename, *args, **kwargs):
        if "pets" in filename:
            return original_open(db_path, *args, **kwargs)
        if "users" in filename:
            return original_open(user_db_path, *args, **kwargs)
        return original_open(filename, *args, **kwargs)

    monkeypatch.setattr("shelve.open", patched_open)
    yield


def make_pet(**kwargs):
    defaults = dict(id=1, time=BASE_TIME, name="Rex", url=VALID_URL)
    defaults.update(kwargs)
    return Pet(**defaults)

def make_user(**kwargs):
    defaults = dict(id=1, guild_id=100, balance=0, xp=0)
    defaults.update(kwargs)
    return User(**defaults)


# ──────────────────────────────────────────────
# PetRepository
# ──────────────────────────────────────────────

class TestPetRepository:
    def test_get_nonexistent_pet_returns_none(self):
        assert PetRepository.get_pet(999) is None

    def test_save_and_get_pet(self):
        pet = make_pet(id=1, name="Rex", energy=100, hunger=100)
        PetRepository.save_pet(pet)
        loaded = PetRepository.get_pet(1)
        assert loaded is not None
        assert loaded.name == "Rex"
        assert loaded.energy == 100

    def test_saved_pet_has_correct_url(self):
        pet = make_pet(id=2)
        PetRepository.save_pet(pet)
        loaded = PetRepository.get_pet(2)
        assert loaded.url == VALID_URL

    def test_saved_pet_has_correct_time(self):
        pet = make_pet(id=3)
        PetRepository.save_pet(pet)
        loaded = PetRepository.get_pet(3)
        assert loaded.time == BASE_TIME

    def test_update_pet_overwrites_previous(self):
        pet = make_pet(id=1, energy=100)
        PetRepository.save_pet(pet)
        pet.energy = 42
        PetRepository.save_pet(pet)
        loaded = PetRepository.get_pet(1)
        assert loaded.energy == 42

    def test_delete_pet_makes_get_return_none(self):
        pet = make_pet(id=1)
        PetRepository.save_pet(pet)
        PetRepository.delete_pet(1)
        assert PetRepository.get_pet(1) is None

    def test_multiple_pets_isolated_by_id(self):
        pet1 = make_pet(id=1, name="Rex")
        pet2 = make_pet(id=2, name="Bolt")
        PetRepository.save_pet(pet1)
        PetRepository.save_pet(pet2)
        assert PetRepository.get_pet(1).name == "Rex"
        assert PetRepository.get_pet(2).name == "Bolt"

    def test_pet_sleeping_flag_persisted(self):
        pet = make_pet(id=1, sleeping=True)
        PetRepository.save_pet(pet)
        loaded = PetRepository.get_pet(1)
        assert loaded.sleeping is True

    def test_pet_with_low_stats_persisted(self):
        pet = make_pet(id=1, energy=5, hunger=0)
        PetRepository.save_pet(pet)
        loaded = PetRepository.get_pet(1)
        assert loaded.energy == 5
        assert loaded.hunger == 0

    def test_get_pet_after_delete_returns_none(self):
        pet = make_pet(id=10)
        PetRepository.save_pet(pet)
        PetRepository.delete_pet(10)
        assert PetRepository.get_pet(10) is None

    def test_delete_nonexistent_pet_does_not_raise(self):
        """Deletar um pet que nunca existiu apenas seta None no db."""
        PetRepository.delete_pet(9999)  # não deve lançar

    def test_loaded_pet_is_pet_instance(self):
        pet = make_pet(id=1)
        PetRepository.save_pet(pet)
        loaded = PetRepository.get_pet(1)
        assert isinstance(loaded, Pet)


# ──────────────────────────────────────────────
# UserRepository
# ──────────────────────────────────────────────

class TestUserRepository:
    def test_get_nonexistent_user_returns_none(self):
        assert UserRepository.get_user(999, 100) is None

    def test_save_and_get_user(self):
        user = make_user(id=1, guild_id=100, balance=50)
        UserRepository.save_user(user)
        loaded = UserRepository.get_user(1, 100)
        assert loaded is not None
        assert loaded.balance == 50

    def test_user_key_includes_guild(self):
        """Mesmo user_id em guilds diferentes deve ser independente."""
        u1 = make_user(id=1, guild_id=100, balance=10)
        u2 = make_user(id=1, guild_id=200, balance=99)
        UserRepository.save_user(u1)
        UserRepository.save_user(u2)
        assert UserRepository.get_user(1, 100).balance == 10
        assert UserRepository.get_user(1, 200).balance == 99

    def test_update_user_overwrites_balance(self):
        user = make_user(id=1, guild_id=100, balance=0)
        UserRepository.save_user(user)
        user.balance = 500
        UserRepository.save_user(user)
        loaded = UserRepository.get_user(1, 100)
        assert loaded.balance == 500

    def test_xp_persisted(self):
        user = make_user(id=1, guild_id=100, xp=250)
        UserRepository.save_user(user)
        loaded = UserRepository.get_user(1, 100)
        assert loaded.xp == 250

    def test_delete_user(self):
        user = make_user(id=1, guild_id=100)
        UserRepository.save_user(user)
        UserRepository.delete_user(1, 100)
        assert UserRepository.get_user(1, 100) is None

    def test_loaded_user_is_user_instance(self):
        user = make_user(id=1, guild_id=100)
        UserRepository.save_user(user)
        loaded = UserRepository.get_user(1, 100)
        assert isinstance(loaded, User)

    def test_multiple_users_same_guild(self):
        u1 = make_user(id=1, guild_id=100, balance=10)
        u2 = make_user(id=2, guild_id=100, balance=20)
        UserRepository.save_user(u1)
        UserRepository.save_user(u2)
        assert UserRepository.get_user(1, 100).balance == 10
        assert UserRepository.get_user(2, 100).balance == 20

    def test_get_user_wrong_guild_returns_none(self):
        user = make_user(id=1, guild_id=100)
        UserRepository.save_user(user)
        assert UserRepository.get_user(1, 999) is None


# ──────────────────────────────────────────────
# Integração PetService → PetRepository (real shelve)
# ──────────────────────────────────────────────

class TestPetServiceIntegration:
    """Testa o fluxo completo sem mocks, usando banco temporário."""

    def test_create_and_retrieve_pet(self):
        from model.pet.pet_service import PetService
        PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)
        pet = PetService.get_pet_callback(1)
        assert pet.name == "Rex"
        assert pet.energy == 100
        assert pet.hunger == 100

    def test_create_and_delete_pet(self):
        from model.pet.pet_service import PetService
        from exceptions import UnregisteredPetError
        PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)
        PetService.delete_pet_callback(1)
        with pytest.raises(UnregisteredPetError):
            PetService.get_pet_callback(1)

    def test_create_duplicate_pet_raises(self):
        from model.pet.pet_service import PetService
        from exceptions import AlreadyAddoptedPetError
        PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)
        with pytest.raises(AlreadyAddoptedPetError):
            PetService.create_pet(1, BASE_TIME, "Bolt", VALID_URL)

    def test_set_sleeping_persists(self):
        from model.pet.pet_service import PetService
        PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)
        PetService.set_sleeping(1, True)
        pet = PetService.get_pet_callback(1)
        assert pet.sleeping is True

    def test_apply_time_persists_changes(self):
        from model.pet.pet_service import PetService
        from datetime import timedelta
        PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)
        new_time = BASE_TIME + timedelta(minutes=20)
        PetService.apply_time(1, new_time)
        pet = PetService.get_pet_callback(1)
        # 20 min: deltahunger=2, deltaenergy=1
        assert pet.hunger == 98
        assert pet.energy == 99

    def test_increase_hunger_persists(self):
        from model.pet.pet_service import PetService
        PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)
        PetService.increase_hunger(1, -50)
        pet = PetService.get_pet_callback(1)
        assert pet.hunger == 50

    def test_increase_energy_persists(self):
        from model.pet.pet_service import PetService
        PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)
        PetService.increase_energy(1, -30)
        pet = PetService.get_pet_callback(1)
        assert pet.energy == 70


# ──────────────────────────────────────────────
# Integração UserService → UserRepository (real shelve)
# ──────────────────────────────────────────────

class TestUserServiceIntegration:
    def test_create_and_get_user(self):
        from model.user.user_service import UserService
        UserService.create_user(1, 100)
        user = UserService.get_user_callback(1, 100)
        assert user is not None
        assert user.id == 1
        assert user.guild_id == 100

    def test_increase_wealth_accumulates(self):
        from model.user.user_service import UserService
        UserService.increase_wealth(1, 100, value=10)
        UserService.increase_wealth(1, 100, value=5)
        user = UserService.get_user_callback(1, 100)
        assert user.balance == 15

    def test_increase_wealth_auto_creates_user(self):
        from model.user.user_service import UserService
        UserService.increase_wealth(77, 200)
        user = UserService.get_user_callback(77, 200)
        assert user is not None
        assert user.balance == 1

    def test_users_in_different_guilds_are_independent(self):
        from model.user.user_service import UserService
        UserService.increase_wealth(1, 100, value=100)
        UserService.increase_wealth(1, 200, value=5)
        u1 = UserService.get_user_callback(1, 100)
        u2 = UserService.get_user_callback(1, 200)
        assert u1.balance == 100
        assert u2.balance == 5

    def test_get_nonexistent_user_returns_none(self):
        from model.user.user_service import UserService
        user = UserService.get_user_callback(9999, 9999)
        assert user is None
