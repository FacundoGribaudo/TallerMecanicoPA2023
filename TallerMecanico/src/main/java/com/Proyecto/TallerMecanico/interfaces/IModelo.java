package com.Proyecto.TallerMecanico.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Proyecto.TallerMecanico.domain.Modelo;

@Repository 
public interface IModelo extends CrudRepository<Modelo, Integer> {
    
}
