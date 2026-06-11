from .pet_entity import Pet
from .pet_repository import PetRepository
from exceptions import UnregisteredPetError, AlreadyAddoptedPetError
from utils import validate_name, validate_image_url

class PetService:
    @classmethod
    def create_pet(cls, id, time, name, url):
        PetService._check_existing_pet(id)
        validate_name(name)
        validate_image_url(url)
        
        pet = Pet(id, time, name, url)
        PetRepository.save_pet(pet)

    @classmethod
    def apply_time(cls, id, time):
        PetService._check_unexisting_pet(id)

        dhunger = 10  # minutes to change hunger
        denergy = 20  # minutes to change energy

        pet = PetRepository.get_pet(id)

        delta = time - pet.time
        minutes = delta.total_seconds() / 60

        deltahunger = minutes // dhunger
        deltaenergy = minutes // denergy

        if deltahunger == 0 and deltaenergy == 0:
            return

        if pet.sleeping:
            pet.hunger -= deltahunger * 2
            pet.energy += deltaenergy * 2
        else:
            pet.hunger -= deltahunger
            pet.energy -= deltaenergy

        pet.hunger = int(max(0, min(pet.hunger, 100)))
        pet.energy = int(max(0, min(pet.energy, 100)))
        pet.time = time

        PetRepository.save_pet(pet)

    @classmethod
    def get_pet_callback(cls, id):
        PetService._check_unexisting_pet(id)

        pet = PetRepository.get_pet(id)
        return

    @classmethod
    def delete_pet_callback(cls, id):
        PetService._check_unexisting_pet(id)

        PetRepository.delete_pet(id)

    @classmethod
    def set_sleeping(cls, id, value):
        PetService._check_unexisting_pet(id)

        pet = PetRepository.get_pet(id)
        pet.sleeping = value
        PetRepository.save_pet(pet)

    @classmethod
    def increase_energy(cls, id, value):
        PetService._check_unexisting_pet(id)

        pet = PetRepository.get_pet(id)
        pet.energy += value
        PetRepository.save_pet(pet)

    @classmethod
    def increase_hunger(cls, id, value):
        PetService._check_unexisting_pet(id)

        pet = PetRepository.get_pet(id)
        pet.hunger += value
        PetRepository.save_pet(pet)

    @classmethod
    def _check_existing_pet(id):
        pet = PetRepository.get_pet(id)
        if pet != None:
            raise AlreadyAddoptedPetError
        
    @classmethod
    def _check_unexisting_pet(id):
        pet = PetRepository.get_pet(id)
        if pet == None:
            raise UnregisteredPetError