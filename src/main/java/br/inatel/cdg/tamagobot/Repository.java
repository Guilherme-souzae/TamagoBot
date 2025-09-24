package br.inatel.cdg.tamagobot;

import java.util.HashMap;

public final class Repository
{
    private static final HashMap<String, Entity> entities = new HashMap<>();

    private Repository() { throw new UnsupportedOperationException("Esta classe não pode ser instanciada."); }

    public static void create(Entity entity)
    {
        entities.put(entity.getGuildId(),entity);
    }

    public static Entity get(String guildId)
    {
        return entities.get(guildId);
    }
}
