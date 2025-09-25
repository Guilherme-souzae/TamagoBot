package br.inatel.cdg.tamagobot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRepository
{
    @Test
    public void TestCreateSucess()
    {
        Repository repo = new Repository();
        Entity entity = new Entity("123", "Minos Prime", "https://");
        assertDoesNotThrow(() -> repo.create(entity));
    }

    @Test
    public void TestCreateFailure()
    {
        Repository repo = new Repository();
        Entity entity1 = new Entity("777", "Great Grey Wolf Sif", "https://");
        assertDoesNotThrow(() -> repo.create(entity1));
        Entity entity2 = new Entity("777", "Seath The Scaleless", "https://");
        assertThrows(IllegalStateException.class, () -> repo.create(entity2));
    }

    @Test
    public void TestGetSucess()
    {
        Repository repo = new Repository();
        Entity entity = new Entity("456", "Sisyphus prime", "https://");
        repo.create(entity);
        Entity result = repo.get("456");
        assertEquals(entity, result);
    }

    @Test
    public void TestGetFailure()
    {
        Repository repo = new Repository();
        Entity entity = new Entity("0", "Hollow Knight", "https://");
        repo.create(entity);
        assertThrows(IllegalStateException.class, () -> repo.get("1"));
    }
}
