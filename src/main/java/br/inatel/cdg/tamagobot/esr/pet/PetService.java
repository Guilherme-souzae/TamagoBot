package br.inatel.cdg.tamagobot.esr.pet;

import br.inatel.cdg.tamagobot.esr.Repository;
import br.inatel.cdg.tamagobot.esr.Service;
import br.inatel.cdg.tamagobot.exceptions.DatabaseStateException;
import br.inatel.cdg.tamagobot.exceptions.IllegalStringException;

import java.time.Clock;

public class PetService extends Service
{

    public PetService(Repository repo)
    {
        super(repo);
    }

    public void create(String id, String content) throws DatabaseStateException, IllegalStringException
    {
        String name = getName(content);
        String url = getUrl(content);
        PetEntity pet = new PetEntity(id, name, url, Clock.systemDefaultZone());
        repo.create(pet);
    }

    public PetEntity get(String id) throws DatabaseStateException
    {
        return (PetEntity) repo.get(id);
    }

    public String getName(String content) throws IllegalStringException
    {
        checkValidString(content);
        int index = content.indexOf("https://");
        return content.substring(0, index).trim();
    }

    public String getUrl(String content) throws IllegalStringException
    {
        checkValidString(content);
        int index = content.indexOf("https://");
        return content.substring(index).trim();
    }

    public void delete(String id) throws DatabaseStateException
    {
        repo.delete(id);
    }

    private void checkValidString(String content) throws IllegalStringException {
        if (content == null || content.isEmpty())
        {
            throw new IllegalStringException("Mensagem vazia ou nula");
        }

        int index = content.indexOf("https://");
        if (index == -1)
        {
            throw new IllegalStringException("URL não encontrada na mensagem");
        }

        String name = content.substring(0, index).trim();
        String url = content.substring(index).trim();

        if (name.isEmpty() || url.isEmpty())
        {
            throw new IllegalStringException("Nome ou URL estão vazios");
        }
    }
}