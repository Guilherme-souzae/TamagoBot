package org.inatel.cdg.crud;

public class PetEntity
{
    private String petName;
    private String petUrl;
    private String petId;
    private int petEnergy;
    private boolean sleeping;
    private long fistTime;
    private long lastTime;

    public void setPetName(String petName)
    {
        this.petName = petName;
    }

    public void setPetUrl(String petUrl)
    {
        this.petUrl = petUrl;
    }

    public void setPetId(String petId)
    {
        this.petId = petId;
    }

    public void setPetEnergy(int petEnergy)
    {
        this.petEnergy = petEnergy;
    }

    public void setSleeping(boolean sleeping)
    {
        this.sleeping = sleeping;
    }

    public void setFistTime(long fistTime)
    {
        this.fistTime = fistTime;
    }

    public void setLastTime(long lastTime)
    {
        this.lastTime = lastTime;
    }

    public String getPetName()
    {
        return petName;
    }

    public String getPetUrl()
    {
        return petUrl;
    }

    public String getPetId()
    {
        return petId;
    }

    public int getPetEnergy()
    {
        return petEnergy;
    }

    public boolean getSleeping()
    {
        return sleeping;
    }

    public long getFistTime()
    {
        return fistTime;
    }

    public long getLastTime()
    {
        return lastTime;
    }
}
