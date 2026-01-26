package org.inatel.cdg.main;

public class FoodEntity
{
    private String name;
    private String rarity;
    private int points;

    public FoodEntity(String s, Rarity rarity)
    {
        name = s;
        rarity = rarity;
    }

    public String getName()
    {
        return name;
    }

    public String getRarity()
    {
        return rarity;
    }

    public int getPoints()
    {
        return points;
    }
}
