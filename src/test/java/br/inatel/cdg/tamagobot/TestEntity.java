package br.inatel.cdg.tamagobot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEntity
{
    @Test
    public void TestEntityInstanceSucess()
    {
        Entity entity = new Entity("123", "Gravattack supremo", "https://");
        assertEquals("123", entity.getGuildId());
        assertEquals("Gravattack supremo", entity.getName());
        assertEquals("https://", entity.getImg_url());
        assertEquals(100, entity.getEnergy());
    }

    @Test
    public void TestEntitySetterSucess()
    {
        Entity entity = new Entity("123","Gravattack supremo", "https://");
        entity.setName("Fantasmatico");
        entity.setImg_url("https://teste");
        entity.setEnergy(50);
        assertEquals("123", entity.getGuildId());
        assertEquals("Fantasmatico", entity.getName());
        assertEquals("https://teste", entity.getImg_url());
        assertEquals(50, entity.getEnergy());
    }
}
