package com.Proyecto.TallerMecanico.interfaceServices;

import java.util.List;
import java.util.Optional;
import com.Proyecto.TallerMecanico.domain.ServiciosTaller;

public interface IserviciosTallerService {
    public List<ServiciosTaller> listarServicios();
    public Optional<ServiciosTaller> listarIdServicios(int id);
    public int save (ServiciosTaller s);
    public void delete(int id);

}
