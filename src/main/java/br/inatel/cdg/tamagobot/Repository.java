package br.inatel.cdg.tamagobot;

import java.util.HashMap;

public class Repository
{
    private static final HashMap<String, Entity> entities = new HashMap<>();

    public void create(Entity entity)
    {
        entities.put(entity.getGuildId(),entity);
    }

    public Entity get(String guildId)
    {
        return entities.get(guildId);
    }
}
