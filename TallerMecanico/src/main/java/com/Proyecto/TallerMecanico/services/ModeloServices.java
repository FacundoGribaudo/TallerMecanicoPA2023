package com.Proyecto.TallerMecanico.services;

import com.Proyecto.TallerMecanico.domain.Modelo;
import com.Proyecto.TallerMecanico.interfaceServices.IModeloServices;
import com.Proyecto.TallerMecanico.interfaces.IModelo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeloServices implements IModeloServices {
    
    @Autowired 
    private IModelo data;
    
    
    @Override
    public List<Modelo> listarModelos() {
        return (List<Modelo>)data.findAll();
    }

    @Override
    public Optional<Modelo> listarIdModelo(int id) {
        return data.findById(id); 
    }

    @Override
    public int save(Modelo m) {
       int res=0;
       Modelo modelo = data.save(m);
       if(!modelo.equals(null)){
           return 1; 
       }
       
       return res;
       
    }

    @Override
    public void delete(int id) {
        data.deleteById(id);
    }
    
}
