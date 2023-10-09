package com.Proyecto.TallerMecanico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.TallerMecanico.domain.OrdenTrabajo;
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
    public int save(OrdenTrabajo ot) {
        int res=0;
        OrdenTrabajo orden_trabajo = data.save(ot);
        if(!orden_trabajo.equals(null)){
            return 1; 
        }
       
        return res;
    }

    @Override
    public void delete(int id) {
       data.deleteById(id); 
    }

    
    
}
