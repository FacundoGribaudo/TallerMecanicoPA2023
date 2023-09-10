package com.Proyecto.TallerMecanico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.TallerMecanico.domain.Tecnico;
import com.Proyecto.TallerMecanico.interfaceServices.ItecnicoServices;
import com.Proyecto.TallerMecanico.interfaces.ITecnico;

@Service
public class TecnicoServices implements ItecnicoServices {

    @Autowired
    private ITecnico data;

    @Override
    public List<Tecnico> listarTecnico() {
        return (List<Tecnico>)data.findAll(); 
    }

    @Override
    public Optional<Tecnico> listarIdTecnico(int id) {
        return data.findById(id); 
    }

    @Override
    public int save(Tecnico t) {
        int res=0;
        Tecnico tecnico = data.save(t);
        if(!tecnico.equals(null)){
           return 1; 
        }
       
       return res;
    }

    @Override
    public void delete(int id) {
        data.deleteById(id);
    }
    
}
