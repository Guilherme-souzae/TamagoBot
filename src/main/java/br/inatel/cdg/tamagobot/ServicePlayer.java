package br.inatel.cdg.tamagobot;

public class ServicePlayer
{
    private RepositoryPlayer repo;

    public ServicePlayer()
    {
        this.repo = new RepositoryPlayer();
    }

    public void createEntityPlayer(String userId)
    {
        repo.create(userId);
    }
}
