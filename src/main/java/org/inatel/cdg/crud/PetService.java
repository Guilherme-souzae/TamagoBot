package org.inatel.cdg.crud;

import static java.lang.Math.round;

public class PetService
{
    private final PetRepository petRepository;
    
    private final long energyRate = 1; // minutes
    
    public PetService(PetRepository petRepository)
    {
        this.petRepository = petRepository;
    }
    
    public void CreatePet(String petId, String petName, String petUrl, long time)
    {
        PetEntity petEntity = new PetEntity();

        petEntity.setPetName(petName);
        petEntity.setPetUrl(petUrl);
        petEntity.setPetId(petId);
        petEntity.setSleeping(false);
        petEntity.setPetEnergy(100);
        petEntity.setFistTime(time);
        petEntity.setLastTime(time);

        petRepository.CreatePet(petEntity);
    }

    public void SetSleep(PetEntity pet, boolean sleeping)
    {
        pet.setSleeping(sleeping);
    }

    public PetEntity GetPet(String petId)
    {
        return petRepository.ReadPet(petId);
    }

    public void DeletePet(String petId)
    {
        petRepository.DeletePet(petId);
    }

    public void AgePet(String petId, long time)
    {
        PetEntity pet = petRepository.ReadPet(petId);
        long deltaTime = (time - pet.getLastTime()) / 1; // minutes
        int deltaEnergy = (int) Math.round((double) deltaTime / energyRate);
        deltaEnergy *= (pet.getSleeping() ? 1 : -1);
        int newEnergy = pet.getPetEnergy() + deltaEnergy;
        newEnergy = Math.max(0, Math.min(100, newEnergy));
        pet.setPetEnergy(newEnergy);
        petRepository.UpdatePet(pet);
    }
}
