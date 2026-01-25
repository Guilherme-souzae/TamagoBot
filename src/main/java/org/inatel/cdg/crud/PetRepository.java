package org.inatel.cdg.crud;

import java.util.HashMap;

public class PetRepository
{
    private final I_PetDatabase database;

    public PetRepository(I_PetDatabase database)
    {
        this.database = database;
    }

    public void CreatePet(PetEntity petEntity)
    {
        database.Create(petEntity);
    }

    public PetEntity ReadPet(String petId)
    {
        return database.Read(petId);
    }

    public void UpdatePet(PetEntity petEntity)
    {
        database.Update(petEntity);
    }

    public void DeletePet(String petId)
    {
        database.Delete(petId);
    }
}
