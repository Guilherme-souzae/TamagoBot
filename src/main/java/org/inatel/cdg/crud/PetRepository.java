package org.inatel.cdg.crud;

import java.util.HashMap;

public enum PetRepository
{
    INSTANCE;

    private static I_PetDatabase database;

    public void SetDatabase(I_PetDatabase database)
    {
        this.database = database;
    }

    public static void CreatePet(PetEntity petEntity)
    {
        database.Create(petEntity);
    }

    public static PetEntity ReadPet(String petId)
    {
        return database.Read(petId);
    }

    public static void UpdatePet(PetEntity petEntity)
    {
        database.Update(petEntity);
    }

    public static void DeletePet(String petId)
    {
        database.Delete(petId);
    }
}
