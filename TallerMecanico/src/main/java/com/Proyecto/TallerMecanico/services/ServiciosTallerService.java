package com.Proyecto.TallerMecanico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.TallerMecanico.domain.ServiciosTaller;
import com.Proyecto.TallerMecanico.interfaceServices.IserviciosTallerService;
import com.Proyecto.TallerMecanico.interfaces.IServicioTaller;

@Service
public class ServiciosTallerService implements IserviciosTallerService {

    @Autowired
    private IServicioTaller data; 

    @Override
    public List<ServiciosTaller> listarServicios() {
        return (List<ServiciosTaller>)data.findAll();
    }

    @Override
    public Optional<ServiciosTaller> listarIdServicios(int id) {
        return data.findById(id); 
    }

    @Override
    public int save(ServiciosTaller s) {
        
        int res=0;
        ServiciosTaller servicio = data.save(s);
        if(!servicio.equals(null)){
            return 1; 
        }
       
       return res;
    }

    @Override
    public void delete(int id) {
        data.deleteById(id);
    }
    
}
