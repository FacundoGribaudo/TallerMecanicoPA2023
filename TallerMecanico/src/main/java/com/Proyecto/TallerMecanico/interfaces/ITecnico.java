package com.Proyecto.TallerMecanico.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.Proyecto.TallerMecanico.domain.Tecnico;

@Repository
public interface ITecnico extends CrudRepository<Tecnico, Integer>{
    
}
