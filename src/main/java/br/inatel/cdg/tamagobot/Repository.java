package br.inatel.cdg.tamagobot;

import java.util.HashMap;

public class Repository
{
    private static final HashMap<String, Entity> entities = new HashMap<>();

    public void create(Entity entity) throws IllegalStateException
    {
        if (entities.get(entity.getGuildId()) != null)
        {
            throw  new IllegalStateException("Entity already exists!");
        }
        else
        {
            entities.put(entity.getGuildId(),entity);
        }
    }

    public Entity get(String guildId) throws IllegalStateException
    {
        if (entities.get(guildId) == null)
        {
            throw  new IllegalStateException("Entity already exists!");
        }
        else
        {
            return entities.get(guildId);
        }
    }
}
