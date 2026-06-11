import shelve

from .user_entity import User

class UserRepository:
    @classmethod
    def save_user(cls, user: User):
        with shelve.open("users.db") as db:
            chave = f"{user.id}:{user.guild_id}"
            db[chave] = user.__dict__.copy()

    @classmethod
    def get_user(cls, id, guild_id):
        with shelve.open("users.db") as db:
            chave = f"{id}:{guild_id}"
            userdict = db.get(chave)
        if userdict is None:
            return None
        return User(**userdict)

    @classmethod
    def delete_user(cls, id, guild_id):
        with shelve.open("users.db") as db:
            chave = f"{id}:{guild_id}"
            db[chave] = None