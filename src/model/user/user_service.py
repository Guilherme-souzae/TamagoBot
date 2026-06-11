from .user_entity import User
from .user_repository import UserRepository

class UserService:
    @classmethod
    def create_user(cls, user_id, guild_id):
        user = User(user_id, guild_id)
        UserRepository.save_user(user)

    @classmethod
    def increase_wealth(cls, user_id, guild_id, value=1):
        user = UserRepository.get_user(user_id, guild_id)

        if user is None:
            user = User(user_id, guild_id)

        user.balance += value
        UserRepository.save_user(user)

    @classmethod
    def get_user_callback(cls, user_id, guild_id):
        return UserRepository.get_user(user_id, guild_id)