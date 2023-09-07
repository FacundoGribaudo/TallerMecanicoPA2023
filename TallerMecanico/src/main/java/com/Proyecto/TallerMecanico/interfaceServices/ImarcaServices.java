package com.Proyecto.TallerMecanico.interfaceServices;

import com.Proyecto.TallerMecanico.domain.Marca;
import java.util.List;
import java.util.Optional;

public interface ImarcaServices {
    
    public List<Marca> listarMarcas();
    public Optional<Marca> listarIdMarca(int id);
    public int save (Marca m);
    public void delete(int id);
}
