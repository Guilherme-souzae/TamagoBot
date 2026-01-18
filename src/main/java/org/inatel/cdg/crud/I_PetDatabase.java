package org.inatel.cdg.crud;

public interface I_PetDatabase
{
    public void Create(PetEntity pet);
    public PetEntity Read(String petId);
    public void Update(PetEntity pet);
    public void Delete(String petId);
}
