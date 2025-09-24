package br.inatel.cdg.tamagobot;

public class Service
{
    private Repository repo;

    public void setRepo(Repository repo)
    {
        this.repo = repo;
    }

    public void createEntity(String guildId, String msg)
    {
        int index = msg.indexOf("https://");
        String name = msg.substring(0, index).trim();
        String url = msg.substring(index).trim();
        repo.create(new Entity(guildId, name, url));
    }

    public Entity getEntity(String guildId)
    {
        return repo.get(guildId);
    }
}
