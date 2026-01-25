import org.inatel.cdg.crud.PetRepository;
import org.inatel.cdg.crud.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest
{
    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Test
    public void CreatePetTest()
    {
        petService.CreatePet("pet-123", "Doraemon", "placeholder", 1234);
        verify(petRepository).CreatePet(any());
    }
}
