package br.inatel.cdg.tamagobot.esr.pet;

import java.time.Clock;
import java.time.Duration;
import java.time.OffsetDateTime;

public class PetEntity
{
    private String guildId;
    private String name;
    private String img_url;

    private static final int denergy = 10;
    private int energy;
    private static final int dhunger = 10;
    private int hunger;

    private boolean sleeping;

    private OffsetDateTime dateTime;
    private Clock clock;

    public PetEntity(String guildId, String name, String img_url, Clock clock)
    {
        this.guildId = guildId;
        this.name = name;
        this.img_url = img_url;
        this.clock = clock;
        this.energy = 100;
        this.hunger = 100;
        this.dateTime = OffsetDateTime.now();
        this.sleeping = false;
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

    public int getHunger()
    {
        return hunger;
    }

    public void setSleeping(Boolean sleeping)
    {
        this.sleeping = sleeping;
    }

    public void setDateTime(OffsetDateTime dateTime)
    {
        this.dateTime = dateTime;
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

    public void setHunger(int hunger)
    {
        this.hunger = hunger;
    }

    public void feed(int hunger)
    {
        this.hunger = Math.min(this.hunger + hunger, 100);
    }

    public void calculateEnergy()
    {
        OffsetDateTime jetzt = OffsetDateTime.now(clock);
        Duration duration = Duration.between(dateTime, jetzt);
        long minutes = duration.toMinutes();
        int change = (int) (minutes / denergy);

        if (change >= 1)
        {
            dateTime = jetzt;
            energy = (sleeping) ? Math.min(100, energy + change) : Math.max(0, energy - change);
        }
    }

    public void calculateHunger()
    {
        OffsetDateTime jetzt = OffsetDateTime.now(clock);
        Duration duration = Duration.between(dateTime, jetzt);
        long minutes = duration.toMinutes();
        int change = (int) (minutes / dhunger);

        if (change >= 1)
        {
            dateTime = jetzt;
            hunger = Math.max(0, hunger - change);
        }
    }

    public void calculateAll()
    {
        calculateEnergy();
        calculateHunger();
    }
}
