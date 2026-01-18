package org.inatel.cdg.crud;

import java.util.HashMap;

public class HashMapDatabase implements I_PetDatabase
{
    private HashMap<String, PetEntity> pets;

    @Override
    public void Create(PetEntity pet) {
        pets.put(pet.getPetId(), pet);
    }

    @Override
    public PetEntity Read(String petId) {
        return pets.get(petId);
    }

    @Override
    public void Update(PetEntity pet) {
        pets.put(pet.getPetId(), pet);
    }

    @Override
    public void Delete(String petId) {
        pets.remove(petId);
    }
}
