package com.Proyecto.TallerMecanico.interfaceServices;

import java.util.List;
import java.util.Optional;

import com.Proyecto.TallerMecanico.domain.OrdenTrabajo;

public interface IordenTrabajoService {
    public List<OrdenTrabajo> listarOrdenTrabajo();
    public Optional<OrdenTrabajo> listarIdOrdenTrabajo(int id);
    public int save (OrdenTrabajo ot);
    public void delete(int id);
}
