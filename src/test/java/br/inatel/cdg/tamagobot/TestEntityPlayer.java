package br.inatel.cdg.tamagobot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEntityPlayer
{
    private EntityPlayer player;

    @BeforeEach
    public void setup()
    {
        player = new EntityPlayer("123ABC");
    }

    @Test
    public void testPlayerInstanceSucess()
    {
        assertEquals("123ABC", player.getUserId());
        assertEquals(100, player.getBalance());
    }
}
