package com.Proyecto.TallerMecanico.interfaceServices;

import com.Proyecto.TallerMecanico.domain.Cliente;
import java.util.List;
import java.util.Optional;


public interface IclienteServices {
    
    public List<Cliente> listarClientes();
    public Optional<Cliente> listarIdCliente(int id);
    public int save (Cliente c);
    public void delete(int id);

}
