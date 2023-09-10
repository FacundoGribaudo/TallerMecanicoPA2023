package com.Proyecto.TallerMecanico.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.Proyecto.TallerMecanico.domain.Cliente;

@Repository
public interface ICliente extends CrudRepository<Cliente, Integer>{
    
}
