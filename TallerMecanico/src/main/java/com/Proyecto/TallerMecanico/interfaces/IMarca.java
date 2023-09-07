package com.Proyecto.TallerMecanico.interfaces;

import com.Proyecto.TallerMecanico.domain.Marca;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository 
public interface IMarca extends CrudRepository<Marca, Integer>{
    
}
