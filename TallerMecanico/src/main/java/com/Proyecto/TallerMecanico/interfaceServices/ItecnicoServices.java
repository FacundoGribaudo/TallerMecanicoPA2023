package com.Proyecto.TallerMecanico.interfaceServices;

import java.util.List;
import java.util.Optional;
import com.Proyecto.TallerMecanico.domain.Tecnico;

public interface ItecnicoServices  {
    
    public List<Tecnico> listarTecnico();
    public Optional<Tecnico> listarIdTecnico(int id);
    public int save (Tecnico t);
    public void delete(int id);
}
