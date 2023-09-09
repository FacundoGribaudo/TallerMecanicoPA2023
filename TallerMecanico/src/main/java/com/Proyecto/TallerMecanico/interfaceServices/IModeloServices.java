package com.Proyecto.TallerMecanico.interfaceServices;

import java.util.List;
import java.util.Optional;

import com.Proyecto.TallerMecanico.domain.Marca;
import com.Proyecto.TallerMecanico.domain.Modelo;

public interface IModeloServices {
    public List<Modelo> listarModelos();
    public Optional<Modelo> listarIdModelo(int id);
    public int save (Modelo mo);
    public void delete(int id);
}
