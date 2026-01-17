package org.inatel.cdg.crud;

import static java.lang.Math.round;

public enum PetService
{
    INSTANCE;

    private static long energyRate = 1; // minutes

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

        PetRepository.INSTANCE.CreatePet(petEntity);
    }

    public void SetSleep(PetEntity pet, boolean sleeping)
    {
        pet.setSleeping(sleeping);
    }

    public PetEntity GetPet(String petId)
    {
        return PetRepository.INSTANCE.ReadPet(petId);
    }

    public void DeletePet(String petId)
    {
        PetRepository.INSTANCE.DeletePet(petId);
    }

    public void AgePet(String petId, long time)
    {
        PetEntity pet = PetRepository.INSTANCE.ReadPet(petId);
        long deltaTime = (time - pet.getLastTime()) / 60000; // minutes
        int deltaEnergy = round(deltaTime / energyRate);
        pet.setPetEnergy(pet.getPetEnergy() - deltaEnergy);
    }
}
