package br.inatel.cdg.tamagobot.esr;

import br.inatel.cdg.tamagobot.esr.pet.PetService;
import br.inatel.cdg.tamagobot.esr.pet.PetEntity;
import br.inatel.cdg.tamagobot.esr.player.PlayerService;

public class ServiceFacade {

    private final PetService petService;
    private final PlayerService playerService;

    public ServiceFacade(PetService petService, PlayerService playerService)
    {
        this.petService = petService;
        this.playerService = playerService;
    }

    public void createPet(String guildId, String msg) throws IllegalStateException, IllegalArgumentException
    {
        petService.createEntity(guildId, msg);
    }

    public PetEntity getPet(String guildId) throws IllegalStateException
    {
        return petService.getEntity(guildId);
    }

    public void renamePet(String guildId, String msg) throws IllegalStateException, IllegalArgumentException
    {
        petService.renameEntity(guildId, msg);
    }

    public void changePetImgUrl(String guildId, String msg) throws IllegalStateException, IllegalArgumentException
    {
        petService.changeImgUrl(guildId, msg);
    }

    public void deletePet(String guildId) throws IllegalStateException
    {
        petService.deleteEntity(guildId);
    }

    public void createPlayer(String userId)
    {
        playerService.createEntity(userId);
    }
}
