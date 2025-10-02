package br.inatel.cdg.tamagobot;

import java.util.HashMap;

public class RepositoryPlayer
{
    private final HashMap<String, EntityPlayer> players = new HashMap<>();

    public void create(String userId) throws IllegalStateException
    {
        if (players.get(userId) != null)
        {
            throw new IllegalStateException();
        }
        else
        {
            EntityPlayer player = new EntityPlayer(userId);
            players.put(player.getUserId(), player);
        }
    }

    public EntityPlayer get(String userId) throws IllegalStateException
    {
        if (players.get(userId) == null)
        {
            throw new IllegalStateException();
        }
        else
        {
            return players.get(userId);
        }
    }
}
