package br.inatel.cdg.tamagobot.esr.pet;

import java.util.HashMap;

public class PetRepository implements IPetRepository
{
    private final HashMap<String, PetEntity> entities = new HashMap<>();

    @Override
    public void create(PetEntity petEntity) throws IllegalStateException
    {
        if (entities.get(petEntity.getGuildId()) != null)
        {
            throw  new IllegalStateException("Entity already exists!");
        }
        else
        {
            entities.put(petEntity.getGuildId(), petEntity);
        }
    }

    @Override
    public PetEntity get(String guildId) throws IllegalStateException
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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
