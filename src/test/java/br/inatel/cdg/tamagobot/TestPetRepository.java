package br.inatel.cdg.tamagobot;

import br.inatel.cdg.tamagobot.esr.pet.PetEntity;
import br.inatel.cdg.tamagobot.esr.pet.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;

public class TestPetRepository
{
    private PetRepository repo;
    private PetEntity petEntity;

    @BeforeEach
    public void setup()
    {
        repo = new PetRepository();
        petEntity = new PetEntity("0", "Paciente Zero", "https://", Clock.systemDefaultZone());
    }

    @Test
    public void testCreateSuccess()
    {
        assertDoesNotThrow(() -> repo.create(petEntity));
    }

    @Test
    public void testCreateFailure()
    {
        repo.create(petEntity);
        PetEntity petEntity1 = new PetEntity("0", "Paciente Um", "https://", Clock.systemDefaultZone());
        assertThrows(IllegalStateException.class, () -> repo.create(petEntity1));
    }

    @Test
    public void testGetSuccess()
    {
        repo.create(petEntity);
        PetEntity result = repo.get("0");
        assertEquals(petEntity, result);
    }

    @Test
    public void testGetFailure()
    {
        repo.create(petEntity);
        assertThrows(IllegalStateException.class, () -> repo.get("1"));
    }

    @Test
    public void testUpdateNameSuccess()
    {
        repo.create(petEntity);
        repo.updateName("0", "Paciente Um");
        assertEquals("Paciente Um", repo.get("0").getName());
    }

    @Test
    public void testUpdateNameFailure()
    {
        repo.create(petEntity);
        assertThrows(IllegalStateException.class, () -> repo.updateName("1", "Paciente Dois"));
    }

    @Test
    public void testUpdateImgUrlSuccess()
    {
        repo.create(petEntity);
        repo.updateUrl("0", "https://0");
        assertEquals("https://0", repo.get("0").getImg_url());
    }

    @Test
    public void testUpdateImgUrlFailure()
    {
        repo.create(petEntity);
        assertThrows(IllegalStateException.class, () -> repo.updateUrl("1", "https://2"));
    }

    @Test
    public void testDeleteSuccess()
    {
        repo.create(petEntity);
        assertDoesNotThrow(() -> repo.delete("0"));
    }

    @Test
    public void testDeleteFailure()
    {
        repo.create(petEntity);
        assertThrows(IllegalStateException.class, () -> repo.delete("19"));
    }
}