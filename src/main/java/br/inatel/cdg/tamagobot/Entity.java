package br.inatel.cdg.tamagobot;

public class Entity
{
    private String guildId;
    private String name;
    private String img_url;
    private int energy;

    public Entity(String guildId, String name, String img_url)
    {
        this.guildId = guildId;
        this.name = name;
        this.img_url = img_url;
        this.energy = 100;
    }

    public String getGuildId()
    {
        return guildId;
    }

    public String getName()
    {
        return name;
    }

    public String getImg_url()
    {
        return img_url;
    }

    public int getEnergy()
    {
        return energy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
