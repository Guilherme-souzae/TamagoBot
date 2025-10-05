package br.inatel.cdg.tamagobot;

import br.inatel.cdg.tamagobot.esr.Entity;
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
public class TestEntity
{
    Entity entity;

    @Mock
    Clock clock;

    @BeforeEach
    public void setUp()
    {
        entity = new Entity("123", "Paciente Zero", "https://", clock);
    }

    @Test
    public void TestEntityInstanceSucess()
    {
        assertEquals("123", entity.getGuildId());
        assertEquals("Paciente Zero", entity.getName());
        assertEquals("https://", entity.getImg_url());
        assertEquals(100, entity.getEnergy());
    }

    @Test
    public void TestEntitySetterSucess()
    {
        entity.setName("Paciente Um");
        entity.setImg_url("https://teste");
        entity.setEnergy(50);
        assertEquals("123", entity.getGuildId());
        assertEquals("Paciente Um", entity.getName());
        assertEquals("https://teste", entity.getImg_url());
        assertEquals(50, entity.getEnergy());
    }

    @Test
    void testCalculateEnergyWhenSleeping()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        entity.setSleeping(true);
        entity.setDateTime(start);
        entity.setEnergy(40);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        entity.calculateEnergy();

        assertEquals(42, entity.getEnergy());
    }

    @Test
    void testCalculateEnergyWhenAwake()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        entity.setSleeping(false);
        entity.setDateTime(start);
        entity.setEnergy(44);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        entity.calculateEnergy();

        assertEquals(42, entity.getEnergy());
    }

    @Test
    void testCalculateHunger()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        entity.setDateTime(start);
        entity.setHunger(44);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        entity.calculateHunger();

        assertEquals(42, entity.getHunger());
    }

    @Test
    void testNormalizeEnergyUp()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        entity.setSleeping(true);
        entity.setDateTime(start);
        entity.setEnergy(100);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        entity.calculateEnergy();

        assertEquals(100, entity.getEnergy());
    }

    @Test
    void testNormalizeEnergyDown()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        entity.setSleeping(false);
        entity.setDateTime(start);
        entity.setEnergy(0);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        entity.calculateEnergy();

        assertEquals(0, entity.getEnergy());
    }

    @Test
    void testNormalizeHungerUp()
    {
        entity.setHunger(100);
        entity.feed(1);
        assertEquals(100, entity.getHunger());
    }

    @Test
    void normalizeHungerDown()
    {
        OffsetDateTime start = OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        when(clock.instant()).thenReturn(start.toInstant());

        entity.setDateTime(start);
        entity.setHunger(0);

        OffsetDateTime later = start.plusMinutes(20);
        when(clock.instant()).thenReturn(later.toInstant());
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        entity.calculateHunger();

        assertEquals(0, entity.getHunger());
    }
}
