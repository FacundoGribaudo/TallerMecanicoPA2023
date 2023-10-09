package com.Proyecto.TallerMecanico.interfaces;

import org.springframework.data.repository.CrudRepository;
import com.Proyecto.TallerMecanico.domain.OrdenTrabajo;

public interface IOrdenTrabajo extends CrudRepository<OrdenTrabajo, Integer> {
    
}
