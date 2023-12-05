package com.Proyecto.TallerMecanico.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Proyecto.TallerMecanico.domain.MarcasEliminadas;

@Repository 
public interface IMarcasEliminadas extends CrudRepository<MarcasEliminadas, Integer> {

    
} 
