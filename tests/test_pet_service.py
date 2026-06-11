"""
Testes unitários para PetService.
O PetRepository é sempre mockado — nenhum arquivo de banco é criado.
"""
import pytest
from datetime import datetime, timedelta, timezone
from unittest.mock import patch, MagicMock

from model.pet.pet_entity import Pet
from model.pet.pet_service import PetService
from exceptions import UnregisteredPetError, AlreadyAddoptedPetError, InvalidUrlError

BASE_TIME = datetime(2024, 6, 1, 12, 0, 0, tzinfo=timezone.utc)
VALID_URL = "https://cdn.discordapp.com/attachments/111/222/pet.png"
INVALID_URL = "https://imgur.com/abc.png"

def make_pet(**kwargs):
    defaults = dict(id=1, time=BASE_TIME, name="Rex", url=VALID_URL)
    defaults.update(kwargs)
    return Pet(**defaults)


# ──────────────────────────────────────────────
# create_pet
# ──────────────────────────────────────────────

class TestCreatePet:
    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=None)
    def test_create_pet_success(self, mock_get, mock_save):
        PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)
        mock_save.assert_called_once()
        saved_pet = mock_save.call_args[0][0]
        assert saved_pet.name == "Rex"
        assert saved_pet.url == VALID_URL
        assert saved_pet.id == 1

    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=make_pet())
    def test_create_pet_when_already_exists_raises(self, mock_get):
        with pytest.raises(AlreadyAddoptedPetError):
            PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=None)
    def test_create_pet_with_invalid_url_raises(self, mock_get, mock_save):
        with pytest.raises(InvalidUrlError):
            PetService.create_pet(1, BASE_TIME, "Rex", INVALID_URL)
        mock_save.assert_not_called()

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=None)
    def test_create_pet_sets_default_energy_100(self, mock_get, mock_save):
        PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)
        saved_pet = mock_save.call_args[0][0]
        assert saved_pet.energy == 100

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=None)
    def test_create_pet_sets_default_hunger_100(self, mock_get, mock_save):
        PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)
        saved_pet = mock_save.call_args[0][0]
        assert saved_pet.hunger == 100

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=None)
    def test_create_pet_sleeping_is_false(self, mock_get, mock_save):
        PetService.create_pet(1, BASE_TIME, "Rex", VALID_URL)
        saved_pet = mock_save.call_args[0][0]
        assert saved_pet.sleeping is False


# ──────────────────────────────────────────────
# get_pet_callback
# ──────────────────────────────────────────────

class TestGetPetCallback:
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=make_pet())
    def test_returns_pet_when_exists(self, mock_get):
        pet = PetService.get_pet_callback(1)
        assert pet is not None
        assert pet.name == "Rex"

    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=None)
    def test_raises_when_pet_does_not_exist(self, mock_get):
        with pytest.raises(UnregisteredPetError):
            PetService.get_pet_callback(1)

    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=make_pet(energy=42))
    def test_returns_pet_with_correct_energy(self, mock_get):
        pet = PetService.get_pet_callback(1)
        assert pet.energy == 42


# ──────────────────────────────────────────────
# delete_pet_callback
# ──────────────────────────────────────────────

class TestDeletePetCallback:
    @patch("model.pet.pet_service.PetRepository.delete_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=make_pet())
    def test_delete_existing_pet(self, mock_get, mock_delete):
        PetService.delete_pet_callback(1)
        mock_delete.assert_called_once_with(1)

    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=None)
    def test_delete_nonexistent_pet_raises(self, mock_get):
        with pytest.raises(UnregisteredPetError):
            PetService.delete_pet_callback(1)


# ──────────────────────────────────────────────
# set_sleeping
# ──────────────────────────────────────────────

class TestSetSleeping:
    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=make_pet(sleeping=False))
    def test_set_sleeping_true(self, mock_get, mock_save):
        PetService.set_sleeping(1, True)
        saved = mock_save.call_args[0][0]
        assert saved.sleeping is True

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=make_pet(sleeping=True))
    def test_set_sleeping_false(self, mock_get, mock_save):
        PetService.set_sleeping(1, False)
        saved = mock_save.call_args[0][0]
        assert saved.sleeping is False

    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=None)
    def test_set_sleeping_nonexistent_pet_raises(self, mock_get):
        with pytest.raises(UnregisteredPetError):
            PetService.set_sleeping(1, True)


# ──────────────────────────────────────────────
# increase_energy
# ──────────────────────────────────────────────

class TestIncreaseEnergy:
    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=make_pet(energy=50))
    def test_increase_energy_positive(self, mock_get, mock_save):
        PetService.increase_energy(1, 30)
        saved = mock_save.call_args[0][0]
        assert saved.energy == 80

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=make_pet(energy=50))
    def test_increase_energy_negative(self, mock_get, mock_save):
        PetService.increase_energy(1, -20)
        saved = mock_save.call_args[0][0]
        assert saved.energy == 30

    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=None)
    def test_increase_energy_nonexistent_pet_raises(self, mock_get):
        with pytest.raises(UnregisteredPetError):
            PetService.increase_energy(1, 10)

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=make_pet(energy=100))
    def test_increase_energy_above_100_not_clamped_by_service(self, mock_get, mock_save):
        """O service não faz clamp — isso é responsabilidade do apply_time."""
        PetService.increase_energy(1, 50)
        saved = mock_save.call_args[0][0]
        assert saved.energy == 150


# ──────────────────────────────────────────────
# increase_hunger
# ──────────────────────────────────────────────

class TestIncreaseHunger:
    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=make_pet(hunger=40))
    def test_increase_hunger_positive(self, mock_get, mock_save):
        PetService.increase_hunger(1, 20)
        saved = mock_save.call_args[0][0]
        assert saved.hunger == 60

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=make_pet(hunger=40))
    def test_increase_hunger_negative(self, mock_get, mock_save):
        PetService.increase_hunger(1, -10)
        saved = mock_save.call_args[0][0]
        assert saved.hunger == 30

    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=None)
    def test_increase_hunger_nonexistent_pet_raises(self, mock_get):
        with pytest.raises(UnregisteredPetError):
            PetService.increase_hunger(1, 10)


# ──────────────────────────────────────────────
# apply_time
# ──────────────────────────────────────────────

class TestApplyTime:
    def _pet(self, **kwargs):
        defaults = dict(id=1, time=BASE_TIME, name="Rex",
                        url=VALID_URL, energy=100, hunger=100, sleeping=False)
        defaults.update(kwargs)
        return Pet(**defaults)

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet")
    def test_no_change_under_thresholds(self, mock_get, mock_save):
        """Menos de 10 min: hunger e energy não mudam."""
        mock_get.return_value = self._pet()
        new_time = BASE_TIME + timedelta(minutes=5)
        PetService.apply_time(1, new_time)
        mock_save.assert_not_called()

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet")
    def test_hunger_decreases_after_10_minutes(self, mock_get, mock_save):
        mock_get.return_value = self._pet(hunger=100)
        new_time = BASE_TIME + timedelta(minutes=10)
        PetService.apply_time(1, new_time)
        saved = mock_save.call_args[0][0]
        assert saved.hunger == 99

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet")
    def test_energy_decreases_after_20_minutes(self, mock_get, mock_save):
        mock_get.return_value = self._pet(energy=100)
        new_time = BASE_TIME + timedelta(minutes=20)
        PetService.apply_time(1, new_time)
        saved = mock_save.call_args[0][0]
        assert saved.energy == 99

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet")
    def test_sleeping_recovers_energy_twice_as_fast(self, mock_get, mock_save):
        mock_get.return_value = self._pet(energy=50, sleeping=True)
        new_time = BASE_TIME + timedelta(minutes=20)
        PetService.apply_time(1, new_time)
        saved = mock_save.call_args[0][0]
        # deltaenergy = 1, sleeping → +2
        assert saved.energy == 52

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet")
    def test_sleeping_hunger_decreases_twice_as_fast(self, mock_get, mock_save):
        mock_get.return_value = self._pet(hunger=100, sleeping=True)
        new_time = BASE_TIME + timedelta(minutes=10)
        PetService.apply_time(1, new_time)
        saved = mock_save.call_args[0][0]
        # deltahunger = 1, sleeping → -2
        assert saved.hunger == 98

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet")
    def test_hunger_clamped_at_zero(self, mock_get, mock_save):
        mock_get.return_value = self._pet(hunger=5)
        new_time = BASE_TIME + timedelta(minutes=200)  # grande delta
        PetService.apply_time(1, new_time)
        saved = mock_save.call_args[0][0]
        assert saved.hunger == 0

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet")
    def test_energy_clamped_at_zero(self, mock_get, mock_save):
        mock_get.return_value = self._pet(energy=5)
        new_time = BASE_TIME + timedelta(minutes=400)
        PetService.apply_time(1, new_time)
        saved = mock_save.call_args[0][0]
        assert saved.energy == 0

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet")
    def test_energy_clamped_at_100_while_sleeping(self, mock_get, mock_save):
        mock_get.return_value = self._pet(energy=99, sleeping=True)
        # 20 min → deltaenergy = 1 × 2 = +2, mas estava em 99 → clamp em 100
        new_time = BASE_TIME + timedelta(minutes=20)
        PetService.apply_time(1, new_time)
        saved = mock_save.call_args[0][0]
        assert saved.energy == 100

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet")
    def test_time_is_updated_after_apply(self, mock_get, mock_save):
        mock_get.return_value = self._pet()
        new_time = BASE_TIME + timedelta(minutes=30)
        PetService.apply_time(1, new_time)
        saved = mock_save.call_args[0][0]
        assert saved.time == new_time

    @patch("model.pet.pet_service.PetRepository.get_pet", return_value=None)
    def test_apply_time_nonexistent_pet_raises(self, mock_get):
        with pytest.raises(UnregisteredPetError):
            PetService.apply_time(1, BASE_TIME + timedelta(minutes=30))

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet")
    def test_multiple_hunger_decrements(self, mock_get, mock_save):
        mock_get.return_value = self._pet(hunger=100)
        new_time = BASE_TIME + timedelta(minutes=30)  # deltahunger = 3
        PetService.apply_time(1, new_time)
        saved = mock_save.call_args[0][0]
        assert saved.hunger == 97

    @patch("model.pet.pet_service.PetRepository.save_pet")
    @patch("model.pet.pet_service.PetRepository.get_pet")
    def test_hunger_and_energy_both_decrease_awake(self, mock_get, mock_save):
        mock_get.return_value = self._pet(hunger=100, energy=100, sleeping=False)
        # 20 min → deltahunger=2, deltaenergy=1
        new_time = BASE_TIME + timedelta(minutes=20)
        PetService.apply_time(1, new_time)
        saved = mock_save.call_args[0][0]
        assert saved.hunger == 98
        assert saved.energy == 99
