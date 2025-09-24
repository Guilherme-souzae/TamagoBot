package br.inatel.cdg.tamagobot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestService
{
    @Mock
    Repository repository;

    @InjectMocks
    Service service;

    @Test
    public void testServiceStringToEntitySucess()
    {
        String guildId = "1";
        String content = "Roboute Guilliman https://ablubluble";
        assertDoesNotThrow(() -> service.createEntity(guildId, content));
    }

    @Test
    public void testServiceStringToEntityFail()
    {
        String guildId = "1";
        String content = "Roboute Guilliman http://ablubluble";
        Exception e = assertThrows(Exception.class, () -> service.createEntity(guildId, content));
        assertTrue(e instanceof IllegalArgumentException || e instanceof IllegalStateException, "Exceção lançada deve ser IllegalArgumentException ou IllegalStateException");
    }
}
