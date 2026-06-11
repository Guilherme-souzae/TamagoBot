"""
Testes unitários para UserService.
O UserRepository é sempre mockado.
"""
import pytest
from unittest.mock import patch, call
from model.user.user_entity import User
from model.user.user_service import UserService


def make_user(**kwargs):
    defaults = dict(id=1, guild_id=100, balance=0, xp=0)
    defaults.update(kwargs)
    return User(**defaults)


class TestCreateUser:
    @patch("model.user.user_service.UserRepository.save_user")
    def test_create_user_calls_save(self, mock_save):
        UserService.create_user(1, 100)
        mock_save.assert_called_once()

    @patch("model.user.user_service.UserRepository.save_user")
    def test_created_user_has_correct_id(self, mock_save):
        UserService.create_user(42, 100)
        saved = mock_save.call_args[0][0]
        assert saved.id == 42

    @patch("model.user.user_service.UserRepository.save_user")
    def test_created_user_has_correct_guild_id(self, mock_save):
        UserService.create_user(1, 999)
        saved = mock_save.call_args[0][0]
        assert saved.guild_id == 999

    @patch("model.user.user_service.UserRepository.save_user")
    def test_created_user_starts_with_zero_balance(self, mock_save):
        UserService.create_user(1, 100)
        saved = mock_save.call_args[0][0]
        assert saved.balance == 0

    @patch("model.user.user_service.UserRepository.save_user")
    def test_created_user_starts_with_zero_xp(self, mock_save):
        UserService.create_user(1, 100)
        saved = mock_save.call_args[0][0]
        assert saved.xp == 0


class TestIncreaseWealth:
    @patch("model.user.user_service.UserRepository.save_user")
    @patch("model.user.user_service.UserRepository.get_user", return_value=make_user(balance=10))
    def test_increase_wealth_default_value_is_1(self, mock_get, mock_save):
        UserService.increase_wealth(1, 100)
        saved = mock_save.call_args[0][0]
        assert saved.balance == 11

    @patch("model.user.user_service.UserRepository.save_user")
    @patch("model.user.user_service.UserRepository.get_user", return_value=make_user(balance=0))
    def test_increase_wealth_custom_value(self, mock_get, mock_save):
        UserService.increase_wealth(1, 100, value=50)
        saved = mock_save.call_args[0][0]
        assert saved.balance == 50

    @patch("model.user.user_service.UserRepository.save_user")
    @patch("model.user.user_service.UserRepository.get_user", return_value=None)
    def test_increase_wealth_creates_user_if_not_exists(self, mock_get, mock_save):
        """Se o usuário não existe, cria um novo com balance inicial."""
        UserService.increase_wealth(99, 200, value=5)
        saved = mock_save.call_args[0][0]
        assert saved.balance == 5
        assert saved.id == 99
        assert saved.guild_id == 200

    @patch("model.user.user_service.UserRepository.save_user")
    @patch("model.user.user_service.UserRepository.get_user", return_value=make_user(balance=100))
    def test_increase_wealth_calls_save(self, mock_get, mock_save):
        UserService.increase_wealth(1, 100)
        mock_save.assert_called_once()

    @patch("model.user.user_service.UserRepository.save_user")
    @patch("model.user.user_service.UserRepository.get_user", return_value=make_user(balance=50))
    def test_increase_wealth_large_value(self, mock_get, mock_save):
        UserService.increase_wealth(1, 100, value=10_000)
        saved = mock_save.call_args[0][0]
        assert saved.balance == 10_050

    @patch("model.user.user_service.UserRepository.save_user")
    @patch("model.user.user_service.UserRepository.get_user", return_value=make_user(balance=10))
    def test_increase_wealth_does_not_modify_xp(self, mock_get, mock_save):
        UserService.increase_wealth(1, 100)
        saved = mock_save.call_args[0][0]
        assert saved.xp == 0


class TestGetUserCallback:
    @patch("model.user.user_service.UserRepository.get_user", return_value=make_user(balance=99))
    def test_returns_user_when_exists(self, mock_get):
        user = UserService.get_user_callback(1, 100)
        assert user is not None
        assert user.balance == 99

    @patch("model.user.user_service.UserRepository.get_user", return_value=None)
    def test_returns_none_when_user_does_not_exist(self, mock_get):
        user = UserService.get_user_callback(1, 100)
        assert user is None

    @patch("model.user.user_service.UserRepository.get_user", return_value=make_user(id=7, guild_id=42))
    def test_passes_correct_id_and_guild(self, mock_get):
        UserService.get_user_callback(7, 42)
        mock_get.assert_called_once_with(7, 42)

    @patch("model.user.user_service.UserRepository.get_user", return_value=make_user(xp=500))
    def test_returned_user_has_correct_xp(self, mock_get):
        user = UserService.get_user_callback(1, 100)
        assert user.xp == 500
