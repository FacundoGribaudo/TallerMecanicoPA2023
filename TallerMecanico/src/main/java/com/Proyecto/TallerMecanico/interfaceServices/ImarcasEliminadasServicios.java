package com.Proyecto.TallerMecanico.interfaceServices;

import java.util.List;
import java.util.Optional;

import com.Proyecto.TallerMecanico.domain.MarcasEliminadas;

public interface ImarcasEliminadasServicios {
    public List<MarcasEliminadas> listarMarcasEliminadas();
    public Optional<MarcasEliminadas> listarIdMarcasEliminadas(int id);
    public int saveMarcaEliminada (MarcasEliminadas me);
    public void deleteMarcaEliminada(int id);
} 
