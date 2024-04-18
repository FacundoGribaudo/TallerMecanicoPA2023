import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private List<Marca> marcas = new ArrayList<Marca>();

    @BeforeEach
    void setup() {
        marca = new Marca("Ford", "Activo");
        Marca marca = new Marca("Toyota", "Activo");
        Marca marca2 = new Marca("Nissan", "Activo");
        Marca marca3 = new Marca("Chevrolet", "Activo");
        Marca marca4 = new Marca("Renault", "Activo");
        marcas.add(marca);
        marcas.add(marca2);
        marcas.add(marca3);
        marcas.add(marca4);
    }

    @Test
    void saveTest() {
        when(marcaRepositorio.save(marca)).thenReturn(marca);
        int number = marcaServices.save(marca);
        assert (number == 1);
    }

    @Test
    void saveTestError() {
        when(marcaRepositorio.save(marca)).thenReturn(null);
        int number = marcaServices.save(marca);
        assert (number == 0);
    }

    @Test
    void listMarcasTest() {
        when(marcaRepositorio.findAll()).thenReturn(marcas);
        List<Marca> newMarcas = marcaServices.listarMarcas();
        assert (newMarcas.size() == 4);
    }

}
