package br.inatel.cdg.tamagobot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEntity
{
    @Test
    public void TestEntityInstanceSucess()
    {
        Entity entity = new Entity("123", "Gravattack supremo", "https://");
        assertEquals(entity.getGuildId(), "123");
        assertEquals(entity.getName(), "Gravattack supremo");
        assertEquals(entity.getImg_url(), "https://");
        assertEquals(entity.getEnergy(), 100);
    }

    @Test
    public void TestEntitySetterSucess()
    {
        Entity entity = new Entity("123","Gravattack supremo", "https://");
        entity.setName("Fantasmatico");
        entity.setImg_url("https://teste");
        entity.setEnergy(50);
        assertEquals(entity.getGuildId(), "123");
        assertEquals(entity.getName(), "Fantasmatico");
        assertEquals(entity.getImg_url(), "https://teste");
        assertEquals(entity.getEnergy(), 50);
    }
}
