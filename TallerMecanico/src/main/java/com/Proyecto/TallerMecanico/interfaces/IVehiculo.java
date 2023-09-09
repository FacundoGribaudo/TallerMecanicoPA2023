package com.Proyecto.TallerMecanico.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Proyecto.TallerMecanico.domain.Vehiculo;

@Repository 
public interface IVehiculo extends CrudRepository<Vehiculo, Integer> {
    
}
