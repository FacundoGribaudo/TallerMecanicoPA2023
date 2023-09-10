package com.Proyecto.TallerMecanico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.TallerMecanico.domain.Cliente;
import com.Proyecto.TallerMecanico.interfaceServices.IclienteServices;
import com.Proyecto.TallerMecanico.interfaces.ICliente;

@Service
public class ClienteServices implements IclienteServices {

    @Autowired
    private ICliente data;

    @Override
    public List<Cliente> listarClientes() {
        return (List<Cliente>)data.findAll();
    }

    @Override
    public Optional<Cliente> listarIdCliente(int id) {
        return data.findById(id); 
    }

    @Override
    public int save(Cliente c) {
        int res=0;
        Cliente cliente = data.save(c);
        if(!cliente.equals(null)){
            return 1; 
       }
       
       return res;
    }

    @Override
    public void delete(int id) {
        data.deleteById(id);
    }
    
}
