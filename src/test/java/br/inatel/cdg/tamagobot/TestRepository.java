package br.inatel.cdg.tamagobot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestRepository
{
    @Test
    public void TestCreateSucess()
    {
        Repository repo = new Repository();
        Entity entity = new Entity("123", "Minos Prime", "https://");
        assertDoesNotThrow(() -> repo.create(entity));
    }
}
