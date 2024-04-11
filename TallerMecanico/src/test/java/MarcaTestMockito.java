import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.cdi.Eager;

import com.Proyecto.TallerMecanico.domain.Marca;
import com.Proyecto.TallerMecanico.interfaces.IMarca;
import com.Proyecto.TallerMecanico.services.MarcaServices;

@ExtendWith(MockitoExtension.class)
public class MarcaTestMockito {
    @Mock
    private IMarca marcaRepositorio;

    @InjectMocks
    private MarcaServices marcaServices;

    private Marca marca;

    @BeforeEach
    void setup() {
        marca = new Marca("Ford", "Activo");
    }

    @Test
    void saveTest() {
        when(marcaRepositorio.save(marca)).thenReturn(marca);
        int number = marcaServices.save(marca);
        assert (number == 1);

    }

    @Test
    void saveTestError() {
        when(marcaRepositorio.save(marca)).thenThrow(Exception.class);
        int number = marcaServices.save(marca);
        assertThrows(Exception.class, number);
    }

}
