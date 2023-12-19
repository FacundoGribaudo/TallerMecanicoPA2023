package com.Proyecto.TallerMecanico.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Proyecto.TallerMecanico.domain.Cliente;
import com.Proyecto.TallerMecanico.interfaceServices.IclienteServices;

@RestController
public class ClienteRestController {
    

    @Autowired
    private IclienteServices servicesCliente;

    /*
     * LLEVAMOS A CABO LA IMPLEMENTACION DE END-POINT PARA PODER HACER UTILIZACION DE POSTMAN
     */

    @GetMapping("/api/clientes/listar")
    public List<Cliente> clientesPostman(){
        
        List<Cliente> context = new ArrayList<>();
        context = servicesCliente.listarClientes();
        return context;
    }

    @GetMapping("/api/clientes/buscar/{id_buscar}")
    public List<Cliente> clientesPostmanID(@PathVariable int id_buscar){
        
        List<Cliente> clientes = servicesCliente.listarClientes();
        List<Cliente> clientesBuscados = new ArrayList<>();

        for(Cliente c:clientes){
            if (c.getId_cliente().equals(id_buscar)){
                clientesBuscados.add(c);
            }
        }

        return clientesBuscados;

        
    }

    @PostMapping("/api/clientes/guardar")
    public String clientePostmanSave(@RequestBody Cliente datos){

        Cliente nuevoCliente = new Cliente(datos.getNombre(), datos.getApellido(), datos.getDni(),
        datos.getTelefono(), datos.getLocalidad(), datos.getNroLicencia(), datos.getVtoLicencia());

        servicesCliente.save(nuevoCliente);

        return "SAVE";
    }

    @DeleteMapping("/api/clientes/borrar/{id_borrar}")
    public String clientePostmanDelete(@PathVariable int id_borrar){
        
        List<Cliente> clientes = servicesCliente.listarClientes();
        String mensaje = "";


        for(Cliente c:clientes){
            if (c.getId_cliente() != id_borrar){
                mensaje = "No existe el cliente con ID: " + id_borrar;
            } else if (c.getId_cliente().equals(id_borrar)){
                servicesCliente.delete(id_borrar);

                mensaje = "Eliminado exitosamente";
                break;
            }
        }
        
        return mensaje;
    }

    @PutMapping("/api/clientes/modificar/{id_modificar}")
    public String clientesPostmanModificado(@RequestBody Cliente datos, @PathVariable int id_modificar){

        List<Cliente> clientes = servicesCliente.listarClientes();
        String mensaje = ""; 

        for(Cliente c:clientes){
            if(c.getId_cliente() != id_modificar){
                mensaje = "No existe un cliente con el ID: " + id_modificar;
            } else if (c.getId_cliente().equals(id_modificar)){
                c.setId_cliente(id_modificar);    
                c.setApellido(datos.getApellido());
                c.setNombre(datos.getNombre());
                c.setDni(datos.getDni());
                c.setLocalidad(datos.getLocalidad());
                c.setNroLicencia(datos.getNroLicencia());
                c.setTelefono(datos.getTelefono());
                c.setVtoLicencia(datos.getVtoLicencia());
                servicesCliente.save(c);

                mensaje = ""+"Cliente modificado correctamente - ID:"+ id_modificar;
                break;
            }
        }

        return mensaje;
        
    }

}
