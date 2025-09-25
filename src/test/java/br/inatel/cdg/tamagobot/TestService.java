package br.inatel.cdg.tamagobot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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
    public void testIllegalUrlFail()
    {
        String guildId = "1";
        String content = "Roboute Guilliman http://ablubluble";
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.createEntity(guildId, content));
    }

    @Test
    public void testIllegalNameFail()
    {
        String guildId = "1";
        String content = "https://ablubluble";
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.createEntity(guildId, content));
    }

    @Test
    public void testGetErrorPropagation()
    {
        when(repository.get("error")).thenThrow(new IllegalStateException("Pet inexistente"));
        assertThrows(IllegalStateException.class, () -> service.getEntity("error"));
    }

    @Test
    public void testDeleteErrorPropagation()
    {
        doThrow(new IllegalStateException("Pet inexistente")).when(repository).delete("error");
        assertThrows(IllegalStateException.class, () -> service.deleteEntity("error"));
    }
}
