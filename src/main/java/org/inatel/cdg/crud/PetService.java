package org.inatel.cdg.crud;

import static java.lang.Math.round;

public class PetService
{
    private final PetRepository petRepository;
    
    private final int energyRate = 1; // minutes
    private final int hungerRate = 1; // minutes
    private final int moneyRate = 10; // messages
    private final int maxMoney = 1000;

    private int moneyBuffer = 0;

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
        petEntity.setPetHunger(100);
        petEntity.setPetBalance(0);
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
        // Time
        PetEntity pet = petRepository.ReadPet(petId);
        long deltaTime = (time - pet.getLastTime()) / (energyRate * 60000); // minutes

        // Energy
        int deltaEnergy = (int) Math.round((double) deltaTime / energyRate);
        deltaEnergy *= (pet.getSleeping() ? 1 : -1);
        int newEnergy = pet.getPetEnergy() + deltaEnergy;
        newEnergy = Math.max(0, Math.min(100, newEnergy));
        pet.setPetEnergy(newEnergy);

        // Hunger
        int deltaHunger = (int) Math.round((double) deltaTime / hungerRate);
        int newHunger = pet.getPetHunger() + deltaHunger;
        newHunger = Math.max(0, Math.min(100, newHunger));
        pet.setPetHunger(newHunger);

        // Update
        petRepository.UpdatePet(pet);
    }

    public void Working(String petId)
    {
        moneyBuffer += 1;

        if (moneyBuffer >= moneyRate)
        {
            moneyBuffer = 0;
            PetEntity pet = petRepository.ReadPet(petId);
            int newMoney = pet.getPetBalance();
            newMoney += (newMoney >= maxMoney) ? 0 : 1;
            pet.setPetBalance(newMoney);
            petRepository.UpdatePet(pet);
        }
    }
}
