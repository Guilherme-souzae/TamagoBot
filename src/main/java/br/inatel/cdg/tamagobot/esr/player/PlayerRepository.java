package br.inatel.cdg.tamagobot.esr.player;

import br.inatel.cdg.tamagobot.esr.pet.PetEntity;

import java.util.HashMap;

public class PlayerRepository implements IPlayerRepository
{
    private final HashMap<String, PlayerEntity> entities = new HashMap<>();

    @Override
    public void create(PlayerEntity player)
    {
        entities.put(player.getUserId(), player);
    }

    @Override
    public PlayerEntity get(String userId)
    {
        return entities.get(userId);
    }

    @Override
    public int getBalance(String guildId)
    {
        return 0;
    }

    @Override
    public void delete(String guildId)
    {

    }
}
