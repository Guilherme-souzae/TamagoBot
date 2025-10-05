package br.inatel.cdg.tamagobot.esr.player;

public class PlayerEntity
{
    private String userId;
    private int balance;

    public PlayerEntity(String userId)
    {
        this.userId = userId;
        this.balance = 100;
    }

    public String getUserId()
    {
        return userId;
    }
}
