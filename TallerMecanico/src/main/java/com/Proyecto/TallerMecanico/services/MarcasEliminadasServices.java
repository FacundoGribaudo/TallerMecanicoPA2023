package com.Proyecto.TallerMecanico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.TallerMecanico.domain.MarcasEliminadas;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcasEliminadasServicios;
import com.Proyecto.TallerMecanico.interfaces.IMarcasEliminadas;

@Service
public class MarcasEliminadasServices implements ImarcasEliminadasServicios  {

    @Autowired
    private IMarcasEliminadas data;


    @Override
    public List<MarcasEliminadas> listarMarcasEliminadas() {
        return (List<MarcasEliminadas>)data.findAll();
    }

    @Override
    public Optional<MarcasEliminadas> listarIdMarcasEliminadas(int id) {
        return data.findById(id); 
    }

    @Override
    public int saveMarcaEliminada(MarcasEliminadas me) {
        int res=0;
        MarcasEliminadas marcaEliminada = data.save(me);
        if(!marcaEliminada.equals(null)){
            return 1; 
        }
        return res;
    }

    @Override
    public void deleteMarcaEliminada(int id) {
        data.deleteById(id);
    }
    
}
