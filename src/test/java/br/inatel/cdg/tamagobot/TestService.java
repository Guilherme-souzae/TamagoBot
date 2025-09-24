package br.inatel.cdg.tamagobot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestService
{
    @Test
    public void TestStringToEntitySucess()
    {
        String msg = "Eco-eco supremo https://localhost";
        Entity entity = Service.createEntity(msg);
        assertEquals(entity.getName(),"Eco-eco supremo");
        assertEquals(entity.getImg_url(),"https://localhost");
    }
}
