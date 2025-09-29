package br.inatel.cdg.tamagobot;

public class Service
{
    private Repository repo;

    public Service()
    {
        this.repo = new Repository();
    }

    public void setRepo(Repository repo)
    {
        this.repo = repo;
    }

    public void createEntity(String guildId, String msg) throws IllegalStateException, IllegalArgumentException
    {
        if (msg == null || msg.isEmpty())
        {
            throw new IllegalArgumentException("Mensagem vazia ou nula");
        }

        int index = msg.indexOf("https://");
        if (index == -1)
        {
            throw new IllegalArgumentException("URL não encontrada na mensagem");
        }

        String name = msg.substring(0, index).trim();
        String url = msg.substring(index).trim();

        if (name.isEmpty() || url.isEmpty())
        {
            throw new IllegalArgumentException("Nome ou URL estão vazios");
        }

        repo.create(new Entity(guildId, name, url));
    }

    public Entity getEntity(String guildId) throws IllegalStateException
    {
        return repo.get(guildId);
    }

    public void renameEntity(String guildId, String msg) throws IllegalStateException, IllegalArgumentException
    {
        if (msg == null || msg.isEmpty())
        {
            throw new IllegalArgumentException("Mensagem vazia ou nula");
        }

        repo.updateName(guildId, msg);
    }

    public void changeImgUrl(String guildId, String msg) throws IllegalStateException, IllegalArgumentException
    {
        if (msg == null || msg.isEmpty())
        {
            throw new IllegalArgumentException("Mensagem vazia ou nula");
        }

        repo.updateUrl(guildId, msg);
    }

    public void deleteEntity(String guildId) throws IllegalStateException
    {
        repo.delete(guildId);
    }
}
