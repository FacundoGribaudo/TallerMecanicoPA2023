package com.Proyecto.TallerMecanico.interfaceServices;

import java.util.List;
import java.util.Optional;

import com.Proyecto.TallerMecanico.domain.Vehiculo;

public interface IvehiculoServices {
    
    public List<Vehiculo> listarVehiculos();
    public Optional<Vehiculo> listarIdVehiculo(int id);
    public int save (Vehiculo v);
    public void delete(int id);
}
