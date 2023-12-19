package com.Proyecto.TallerMecanico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.TallerMecanico.domain.OrdenTrabajo;
import com.Proyecto.TallerMecanico.domain.ServiciosTaller;
import com.Proyecto.TallerMecanico.interfaceServices.IordenTrabajoService;
import com.Proyecto.TallerMecanico.interfaces.IOrdenTrabajo;

@Service
public class OrdenTrabajoService implements IordenTrabajoService{

    @Autowired
    private IOrdenTrabajo data;

    @Override
    public List<OrdenTrabajo> listarOrdenTrabajo() {
        return (List<OrdenTrabajo>)data.findAll();
    }

    @Override
    public Optional<OrdenTrabajo> listarIdOrdenTrabajo(int id) {
        return data.findById(id); 
    }

    @Override
    public OrdenTrabajo save(OrdenTrabajo ot) {
        try {
            return data.save(ot);
        } catch (Exception e) {
            // Manejar la excepción según tus necesidades
            throw new RuntimeException("Error al guardar la orden de trabajo", e);
        }
    }

    @Override
    public void delete(int id) {
       data.deleteById(id); 
    }

    @Override
    public void agregarHorasYMinutosParaServicio(OrdenTrabajo ordenTrabajo, ServiciosTaller servicio, int horas,
            int minutos) {
        // Asegúrate de que la orden de trabajo y el servicio no sean nulos
        if (ordenTrabajo != null && servicio != null) {
            // Agregar horas y minutos al servicio en la orden de trabajo
            ordenTrabajo.agregarHorasYMinutosParaServicio(servicio.getId_servicio(), horas, minutos);
            // Guardar la OrdenTrabajo actualizada en la base de datos
            save(ordenTrabajo);
        } else {
            // Manejar el caso donde la orden de trabajo o el servicio son nulos
            throw new IllegalArgumentException("Orden de trabajo o servicio nulos");
        }
    }

    
    
}
