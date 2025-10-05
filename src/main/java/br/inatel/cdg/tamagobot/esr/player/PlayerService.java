package br.inatel.cdg.tamagobot.esr.player;

public class PlayerService
{
    private final IPlayerRepository repo;

    public PlayerService(IPlayerRepository repo)
    {
        this.repo = repo;
    }

    public void createEntity(String userId)
    {
        PlayerEntity player = new PlayerEntity(userId);
        repo.create(player);
    }
}
