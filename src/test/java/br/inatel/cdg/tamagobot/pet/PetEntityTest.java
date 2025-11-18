package br.inatel.cdg.tamagobot.pet;

import br.inatel.cdg.tamagobot.esr.pet.PetEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Clock;

public class PetEntityTest
{
    @Mock
    Clock clock;

    private final PetEntity pet = new PetEntity("123ABC", "Paciente 0", "https://", clock);
}
