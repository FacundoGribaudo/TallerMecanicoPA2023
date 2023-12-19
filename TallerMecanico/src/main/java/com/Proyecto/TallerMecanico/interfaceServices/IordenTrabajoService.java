package com.Proyecto.TallerMecanico.interfaceServices;

import java.util.List;
import java.util.Optional;

import com.Proyecto.TallerMecanico.domain.OrdenTrabajo;
import com.Proyecto.TallerMecanico.domain.ServiciosTaller;

public interface IordenTrabajoService {
    public List<OrdenTrabajo> listarOrdenTrabajo();
    public Optional<OrdenTrabajo> listarIdOrdenTrabajo(int id);
    public OrdenTrabajo save (OrdenTrabajo ot);
    public void delete(int id);

    void agregarHorasYMinutosParaServicio(OrdenTrabajo ordenTrabajo, ServiciosTaller servicio, int horas, int minutos);

}
