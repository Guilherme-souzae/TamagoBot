package org.inatel.cdg.crud;

import java.util.HashMap;

public enum PetRepository
{
    INSTANCE;

    private static HashMap<String,PetEntity> database = new HashMap<>();

    public static void CreatePet(PetEntity petEntity)
    {
        database.put(petEntity.getPetId(), petEntity);
    }

    public static PetEntity ReadPet(String petId)
    {
        return database.get(petId);
    }

    public static void UpdatePet(PetEntity petEntity)
    {
        database.put(petEntity.getPetId(), petEntity);
    }

    public static void DeletePet(String petId)
    {
        database.remove(petId);
    }
}
