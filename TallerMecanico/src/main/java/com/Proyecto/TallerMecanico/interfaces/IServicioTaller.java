package com.Proyecto.TallerMecanico.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Proyecto.TallerMecanico.domain.ServiciosTaller;

@Repository 
public interface IServicioTaller extends CrudRepository<ServiciosTaller, Integer>  {
    
}
