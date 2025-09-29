package br.inatel.cdg.tamagobot;

import java.util.HashMap;

public class Repository
{
    private final HashMap<String, Entity> entities = new HashMap<>();

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
            throw  new IllegalStateException("Entity not exists!");
        }
        else
        {
            return entities.get(guildId);
        }
    }

    public String getName(String guildId) throws IllegalStateException
    {
        if (entities.get(guildId) == null)
        {
            throw  new IllegalStateException("Entity not exists!");
        }
        else
        {
            return entities.get(guildId).getName();
        }
    }

    public String getImgUrl(String guildId) throws IllegalStateException
    {
        if (entities.get(guildId) == null)
        {
            throw  new IllegalStateException("Entity not exists!");
        }
        else
        {
            return entities.get(guildId).getImg_url();
        }
    }

    public void updateName(String guildId, String newName)
    {
        if (entities.get(guildId) == null)
        {
            throw  new IllegalStateException("Entity not exists!");
        }
        else
        {
            entities.get(guildId).setName(newName);
        }
    }

    public void updateUrl(String guildId, String newUrl)
    {
        if (entities.get(guildId) == null)
        {
            throw  new IllegalStateException("Entity not exists!");
        }
        else
        {
            entities.get(guildId).setImg_url(newUrl);
        }
    }

    public void delete(String guildId)
    {
        if (entities.get(guildId) == null)
        {
            throw new IllegalStateException("Entity not exists!");
        }
        else
        {
            entities.remove(guildId);
        }
    }
}
