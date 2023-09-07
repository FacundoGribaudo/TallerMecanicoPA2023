package com.Proyecto.TallerMecanico.services;

import com.Proyecto.TallerMecanico.domain.Marca;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcaServices;
import com.Proyecto.TallerMecanico.interfaces.IMarca;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarcaServices implements ImarcaServices {
    
    @Autowired 
    private IMarca data;
    
    
    @Override
    public List<Marca> listarMarcas() {
        return (List<Marca>)data.findAll();
    }

    @Override
    public Optional<Marca> listarIdMarca(int id) {
        return data.findById(id); 
    }

    @Override
    public int save(Marca m) {
       int res=0;
       Marca marca = data.save(m);
       if(!marca.equals(null)){
           return 1; 
       }
       
       return res;
       
    }

    @Override
    public void delete(int id) {
        data.deleteById(id);
    }
    
}
