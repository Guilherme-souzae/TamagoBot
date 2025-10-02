package br.inatel.cdg.tamagobot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;

public class TestRepository
{
    private Repository repo;
    private Entity entity;

    @BeforeEach
    public void setup()
    {
        repo = new Repository();
        entity = new Entity("0", "Paciente Zero", "https://", Clock.systemDefaultZone());
    }

    @Test
    public void testCreateSuccess()
    {
        assertDoesNotThrow(() -> repo.create(entity));
    }

    @Test
    public void testCreateFailure()
    {
        repo.create(entity);
        Entity entity1 = new Entity("0", "Paciente Um", "https://", Clock.systemDefaultZone());
        assertThrows(IllegalStateException.class, () -> repo.create(entity1));
    }

    @Test
    public void testGetSuccess()
    {
        repo.create(entity);
        Entity result = repo.get("0");
        assertEquals(entity, result);
    }

    @Test
    public void testGetFailure()
    {
        repo.create(entity);
        assertThrows(IllegalStateException.class, () -> repo.get("1"));
    }

    @Test
    public void testUpdateNameSuccess()
    {
        repo.create(entity);
        repo.updateName("0", "Paciente Um");
        assertEquals("Paciente Um", repo.get("0").getName());
    }

    @Test
    public void testUpdateNameFailure()
    {
        repo.create(entity);
        assertThrows(IllegalStateException.class, () -> repo.updateName("1", "Paciente Dois"));
    }

    @Test
    public void testUpdateImgUrlSuccess()
    {
        repo.create(entity);
        repo.updateUrl("0", "https://0");
        assertEquals("https://0", repo.get("0").getImg_url());
    }

    @Test
    public void testUpdateImgUrlFailure()
    {
        repo.create(entity);
        assertThrows(IllegalStateException.class, () -> repo.updateUrl("1", "https://2"));
    }

    @Test
    public void testDeleteSuccess()
    {
        repo.create(entity);
        assertDoesNotThrow(() -> repo.delete("0"));
    }

    @Test
    public void testDeleteFailure()
    {
        repo.create(entity);
        assertThrows(IllegalStateException.class, () -> repo.delete("19"));
    }
}