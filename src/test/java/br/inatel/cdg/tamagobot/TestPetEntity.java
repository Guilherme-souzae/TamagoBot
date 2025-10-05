package br.inatel.cdg.tamagobot;

import br.inatel.cdg.tamagobot.esr.pet.PetEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestPetEntity
{
    PetEntity petEntity;

    @Mock
    Clock clock;

    @BeforeEach
    public void setUp()
    {
        petEntity = new PetEntity("123", "Paciente Zero", "https://", clock);
    }

    @Test
    public void TestEntityInstanceSucess()
    {
        assertEquals("123", petEntity.getGuildId());
        assertEquals("Paciente Zero", petEntity.getName());
        assertEquals("https://", petEntity.getImg_url());
        assertEquals(100, petEntity.getEnergy());
    }

    @Test
    public void TestEntitySetterSucess()
    {
        petEntity.setName("Paciente Um");
        petEntity.setImg_url("https://teste");
        petEntity.setEnergy(50);
        assertEquals("123", petEntity.getGuildId());
        assertEquals("Paciente Um", petEntity.getName());
        assertEquals("https://teste", petEntity.getImg_url());
        assertEquals(50, petEntity.getEnergy());
    }

    @Test
    void testCalculateEnergyWhenSleeping()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        petEntity.setSleeping(true);
        petEntity.setDateTime(start);
        petEntity.setEnergy(40);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        petEntity.calculateEnergy();

        assertEquals(42, petEntity.getEnergy());
    }

    @Test
    void testCalculateEnergyWhenAwake()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        petEntity.setSleeping(false);
        petEntity.setDateTime(start);
        petEntity.setEnergy(44);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        petEntity.calculateEnergy();

        assertEquals(42, petEntity.getEnergy());
    }

    @Test
    void testCalculateHunger()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        petEntity.setDateTime(start);
        petEntity.setHunger(44);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        petEntity.calculateHunger();

        assertEquals(42, petEntity.getHunger());
    }

    @Test
    void testNormalizeEnergyUp()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        petEntity.setSleeping(true);
        petEntity.setDateTime(start);
        petEntity.setEnergy(100);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        petEntity.calculateEnergy();

        assertEquals(100, petEntity.getEnergy());
    }

    @Test
    void testNormalizeEnergyDown()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        petEntity.setSleeping(false);
        petEntity.setDateTime(start);
        petEntity.setEnergy(0);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        petEntity.calculateEnergy();

        assertEquals(0, petEntity.getEnergy());
    }

    @Test
    void testNormalizeHungerUp()
    {
        petEntity.setHunger(100);
        petEntity.feed(1);
        assertEquals(100, petEntity.getHunger());
    }

    @Test
    void normalizeHungerDown()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        petEntity.setDateTime(start);
        petEntity.setHunger(0);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        petEntity.calculateHunger();

        assertEquals(0, petEntity.getHunger());
    }
}
