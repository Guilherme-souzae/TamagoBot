package br.inatel.cdg.tamagobot;

import br.inatel.cdg.tamagobot.esr.pet.PetRepository;
import br.inatel.cdg.tamagobot.esr.pet.PetService;
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
public class TestPetService
{
    private String guildId;
    private String content;

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetService petService;

    @BeforeEach
    public void setup()
    {
        guildId = "1";
        content = "Test test https://teste";
    }

    @Test
    public void testServiceStringToEntitySucess()
    {
        assertDoesNotThrow(() -> petService.createEntity(guildId, content));
    }

    @Test
    public void testIllegalUrlFail()
    {
        content = "Teste teste http://test";
        Exception e = assertThrows(IllegalArgumentException.class, () -> petService.createEntity(guildId, content));
    }

    @Test
    public void testIllegalNameFail()
    {
        content = " https://test";
        Exception e = assertThrows(IllegalArgumentException.class, () -> petService.createEntity(guildId, content));
    }

    @Test
    public void testRenameSucess()
    {
        content = "Paciente Zero";
        assertDoesNotThrow(() -> petService.renameEntity(guildId, content));
        when(petRepository.getName(guildId)).thenReturn(content);
        assertEquals(content, petRepository.getName(guildId));
    }

    @Test
    public void testRenameIllegalNameFail()
    {
        content = "";
        Exception e = assertThrows(IllegalArgumentException.class, () -> petService.renameEntity(guildId, content));
    }

    @Test
    public void testChangeUrlSucess()
    {
        content = "https://salvekk";
        assertDoesNotThrow(() -> petService.changeImgUrl(guildId, content));
        when(petRepository.getImgUrl(guildId)).thenReturn(content);
        assertEquals(content, petRepository.getImgUrl(guildId));
    }

    @Test
    public void testGetErrorPropagation()
    {
        when(petRepository.get("error")).thenThrow(new IllegalStateException("Pet inexistente"));
        assertThrows(IllegalStateException.class, () -> petService.getEntity("error"));
    }

    @Test
    public void testDeleteErrorPropagation()
    {
        doThrow(new IllegalStateException("Pet inexistente")).when(petRepository).delete("error");
        assertThrows(IllegalStateException.class, () -> petService.deleteEntity("error"));
    }
}
