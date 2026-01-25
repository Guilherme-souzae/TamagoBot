import org.inatel.cdg.crud.PetEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PetEntityTest
{
    @Test
    public void PetSetGetTest()
    {
        PetEntity petEntity = new PetEntity();

        String name = "Doraemon";
        String url = "placeholder";
        String id = "pet-123";
        int energy = 80;
        boolean sleeping = true;
        long firstTime = 1_700_000_000_000L;
        long lastTime  = 1_700_000_060_000L;

        petEntity.setPetName(name);
        petEntity.setPetUrl(url);
        petEntity.setPetId(id);
        petEntity.setPetEnergy(energy);
        petEntity.setSleeping(sleeping);
        petEntity.setFistTime(firstTime);
        petEntity.setLastTime(lastTime);

        assertEquals(name, petEntity.getPetName());
        assertEquals(url, petEntity.getPetUrl());
        assertEquals(id, petEntity.getPetId());
        assertEquals(energy, petEntity.getPetEnergy());
        assertTrue(petEntity.getSleeping());
        assertEquals(firstTime, petEntity.getFistTime());
        assertEquals(lastTime, petEntity.getLastTime());
    }
}
