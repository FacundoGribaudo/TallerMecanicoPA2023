package com.Proyecto.TallerMecanico.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Proyecto.TallerMecanico.domain.Cliente;
import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.interfaceServices.IclienteServices;
import com.Proyecto.TallerMecanico.interfaceServices.IvehiculoServices;

@Controller
@RequestMapping
public class ClienteController {

    @Autowired
    private IvehiculoServices servicesVehiculo;
    @Autowired
    private IclienteServices servicesCliente;


    @GetMapping("/clientes")
    public String listarClientes(Model model){
        List<Cliente> clientes = servicesCliente.listarClientes();
        model.addAttribute("clientes", clientes);
        return "clientes"; 
    }
    
    @PostMapping("/saveCliente")
    public String agregarCliente(Cliente c, Model model){
        // List<Cliente> clientes = servicesCliente.listarClientes();
        // Boolean cargarCliente = true;

        // for(Cliente cliente:clientes){
        //     if(cliente.getDni().equals(c.getDni())){
        //         cargarCliente = false;
        //     }
        // }
        
        // if(cargarCliente == true){
        //     servicesCliente.save(c);
        // }
        servicesCliente.save(c);
        return "redirect:/clientes"; 
    }

    @GetMapping("/eliminarCliente/{id_cliente}")
    public String eliminarCliente(Model model, @PathVariable int id_cliente){
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();
        Boolean eliminarCliente = true;

        for(Vehiculo v:vehiculos){
            if(v.getCliente().getId_cliente().equals(id_cliente)){
                eliminarCliente = false;
            }
        }
        
        if(eliminarCliente == true){
            System.out.println("si PODES ELIMINAR, NO ESTA RELACIONADO");
            servicesCliente.delete(id_cliente);
        }else{System.out.println("NO PODES ELIMINAR, ESTA RELACIONADO");}
        
        // Antes de la redirección
        String mensaje = eliminarCliente ? "true" : "false";
        String urlRedireccion = "redirect:/clientes?mensaje=" + mensaje;
        return urlRedireccion;
    }

    @GetMapping("/editarCliente/{id_cliente}")
    public String editarCliente(@PathVariable int id_cliente, Model model){
        Optional<Cliente> optionalCliente = servicesCliente.listarIdCliente(id_cliente);

        
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            model.addAttribute("cliente", cliente);
        } else {
           
        }

        return "editarCliente"; 
    }
    
    @PostMapping("/buscarCliente")
    public String buscarCliente(Model model, @RequestParam("datoCliente") String dni){
        List<Cliente> clientes = servicesCliente.listarClientes();
        List<Cliente> clientesBuscados = new ArrayList<Cliente>();
        
        if(dni != null && !dni.isEmpty() && dni.matches("\\d+")){
            int dniEntero = Integer.parseInt(dni);
            for (Cliente c:clientes){
                if(c.getDni().intValue() == dniEntero){
                    System.out.println("LOS VALORES COINCIDEN SE BUSCARA...");
                    clientesBuscados.add(c);
                }
            }
        }else{
            System.out.println("SE INGRESO UNA CADENA VACIA, NO BUSCA NADA..");
            clientesBuscados = clientes;
        }

        model.addAttribute("clientes", clientesBuscados);
        return "buscarCliente";

    }
}
