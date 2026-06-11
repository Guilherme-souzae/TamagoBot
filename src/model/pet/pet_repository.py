import shelve

from .pet_entity import Pet

class PetRepository:
    @classmethod
    def save_pet(cls, pet):
        with shelve.open("pets.db") as db:
            db[str(pet.id)] = pet.__dict__.copy()

    @classmethod
    def get_pet(cls, id):
        with shelve.open("pets.db") as db:
            petdict = db.get(str(id))
        if petdict is None:
            return None
        return Pet(**petdict)

    @classmethod
    def delete_pet(cls, id):
        with shelve.open("pets.db") as db:
            db[str(id)] = None