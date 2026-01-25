package org.inatel.cdg.main;

import org.inatel.cdg.crud.PetService;

public enum PetServiceFacade
{
    INSTANCE;

    private static PetService petService;

    public void SetPetService(PetService petS)
    {
        petService = petS;
    }

    public static PetService GetPetService()
    {
        return petService;
    }
}
