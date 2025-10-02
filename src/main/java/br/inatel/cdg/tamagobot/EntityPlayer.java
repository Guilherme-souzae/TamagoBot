package br.inatel.cdg.tamagobot;

public class EntityPlayer
{
    private String userId;
    private int balance;

    EntityPlayer(String userId)
    {
        this.userId = userId;
        this.balance = 100;
    }

    public String getUserId()
    {
        return userId;
    }

    public int getBalance()
    {
        return balance;
    }
}
