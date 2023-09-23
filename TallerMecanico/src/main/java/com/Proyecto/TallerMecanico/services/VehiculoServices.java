package com.Proyecto.TallerMecanico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.interfaceServices.IvehiculoServices;
import com.Proyecto.TallerMecanico.interfaces.IVehiculo;

@Service
public class VehiculoServices implements IvehiculoServices {

    @Autowired
    private IVehiculo data;

    @Override
    public List<Vehiculo> listarVehiculos() {
        return (List<Vehiculo>)data.findAll();
    }

    @Override
    public Optional<Vehiculo> listarIdVehiculo(int id) {
        return data.findById(id); 
    }

    @Override
    public int save(Vehiculo v) {
        int res=0;
       Vehiculo vehiculo = data.save(v);
       if(!vehiculo.equals(null)){
           return 1; 
       }
       
       return res;
    }

    @Override
    public void delete(int id) {
        data.deleteById(id);;
    }
    
}
