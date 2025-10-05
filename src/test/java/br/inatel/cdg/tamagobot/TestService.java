package br.inatel.cdg.tamagobot;

import br.inatel.cdg.tamagobot.esr.Repository;
import br.inatel.cdg.tamagobot.esr.Service;
import org.junit.jupiter.api.BeforeEach;
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
    private String guildId;
    private String content;

    @Mock
    Repository repository;

    @InjectMocks
    Service service;

    @BeforeEach
    public void setup()
    {
        guildId = "1";
        content = "Test test https://teste";
    }

    @Test
    public void testServiceStringToEntitySucess()
    {
        assertDoesNotThrow(() -> service.createEntity(guildId, content));
    }

    @Test
    public void testIllegalUrlFail()
    {
        content = "Teste teste http://test";
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.createEntity(guildId, content));
    }

    @Test
    public void testIllegalNameFail()
    {
        content = " https://test";
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.createEntity(guildId, content));
    }

    @Test
    public void testRenameSucess()
    {
        content = "Paciente Zero";
        assertDoesNotThrow(() -> service.renameEntity(guildId, content));
        when(repository.getName(guildId)).thenReturn(content);
        assertEquals(content,repository.getName(guildId));
    }

    @Test
    public void testRenameIllegalNameFail()
    {
        content = "";
        Exception e = assertThrows(IllegalArgumentException.class, () -> service.renameEntity(guildId, content));
    }

    @Test
    public void testChangeUrlSucess()
    {
        content = "https://salvekk";
        assertDoesNotThrow(() -> service.changeImgUrl(guildId, content));
        when(repository.getImgUrl(guildId)).thenReturn(content);
        assertEquals(content,repository.getImgUrl(guildId));
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
