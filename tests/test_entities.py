"""
Testes unitários para Pet e User entities.
Validam os valores padrão, atribuição de atributos e mutabilidade dos campos.
"""
import pytest
from datetime import datetime, timezone
from model.pet.pet_entity import Pet
from model.user.user_entity import User


# ──────────────────────────────────────────────
# Pet entity
# ──────────────────────────────────────────────

class TestPetEntity:
    BASE_TIME = datetime(2024, 1, 1, tzinfo=timezone.utc)

    def test_create_pet_with_required_fields(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png")
        assert pet.id == 1
        assert pet.name == "Rex"
        assert pet.url == "http://img.com/rex.png"
        assert pet.time == self.BASE_TIME

    def test_pet_default_energy_is_100(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png")
        assert pet.energy == 100

    def test_pet_default_hunger_is_100(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png")
        assert pet.hunger == 100

    def test_pet_default_sleeping_is_false(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png")
        assert pet.sleeping is False

    def test_pet_custom_energy(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png", energy=50)
        assert pet.energy == 50

    def test_pet_custom_hunger(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png", hunger=30)
        assert pet.hunger == 30

    def test_pet_custom_sleeping_true(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png", sleeping=True)
        assert pet.sleeping is True

    def test_pet_energy_is_mutable(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png")
        pet.energy = 42
        assert pet.energy == 42

    def test_pet_hunger_is_mutable(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png")
        pet.hunger = 10
        assert pet.hunger == 10

    def test_pet_sleeping_is_mutable(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png")
        pet.sleeping = True
        assert pet.sleeping is True

    def test_pet_energy_can_be_zero(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png", energy=0)
        assert pet.energy == 0

    def test_pet_hunger_can_be_zero(self):
        pet = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png", hunger=0)
        assert pet.hunger == 0

    def test_pet_id_accepts_large_guild_id(self):
        big_id = 123456789012345678
        pet = Pet(id=big_id, time=self.BASE_TIME, name="Rex", url="http://img.com/rex.png")
        assert pet.id == big_id

    def test_two_pets_are_independent(self):
        pet1 = Pet(id=1, time=self.BASE_TIME, name="Rex", url="http://img.com/a.png")
        pet2 = Pet(id=2, time=self.BASE_TIME, name="Bolt", url="http://img.com/b.png")
        pet1.energy = 10
        assert pet2.energy == 100  # não afetado


# ──────────────────────────────────────────────
# User entity
# ──────────────────────────────────────────────

class TestUserEntity:
    def test_create_user_with_required_fields(self):
        user = User(id=1, guild_id=100)
        assert user.id == 1
        assert user.guild_id == 100

    def test_user_default_balance_is_zero(self):
        user = User(id=1, guild_id=100)
        assert user.balance == 0

    def test_user_default_xp_is_zero(self):
        user = User(id=1, guild_id=100)
        assert user.xp == 0

    def test_user_custom_balance(self):
        user = User(id=1, guild_id=100, balance=500)
        assert user.balance == 500

    def test_user_custom_xp(self):
        user = User(id=1, guild_id=100, xp=250)
        assert user.xp == 250

    def test_user_balance_is_mutable(self):
        user = User(id=1, guild_id=100)
        user.balance += 100
        assert user.balance == 100

    def test_user_xp_is_mutable(self):
        user = User(id=1, guild_id=100)
        user.xp += 50
        assert user.xp == 50

    def test_two_users_different_guilds_are_independent(self):
        u1 = User(id=1, guild_id=10)
        u2 = User(id=1, guild_id=20)
        u1.balance = 999
        assert u2.balance == 0

    def test_user_id_accepts_large_discord_snowflake(self):
        big_id = 987654321098765432
        user = User(id=big_id, guild_id=100)
        assert user.id == big_id
