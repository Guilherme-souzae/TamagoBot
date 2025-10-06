package br.inatel.cdg.tamagobot.esr;

import br.inatel.cdg.tamagobot.esr.pet.PetEntity;
import br.inatel.cdg.tamagobot.esr.pet.PetService;
import br.inatel.cdg.tamagobot.exceptions.DatabaseStateException;
import br.inatel.cdg.tamagobot.exceptions.IllegalStringException;

public class ServiceFacade
{
    private PetService petService;

    public ServiceFacade(PetService petService) {this.petService = petService;}

    public void createPet(String id, String content) throws DatabaseStateException, IllegalStringException
    {
        petService.create(id, content);
    }

    public PetEntity getPet(String id) throws DatabaseStateException
    {
        return petService.get(id);
    }

    public void renamePet(String id, String name) throws DatabaseStateException
    {

    }

    public void changePetImgUrl(String id, String url) throws DatabaseStateException
    {

    }

    public void deletePet(String id) throws DatabaseStateException
    {

    }
}
