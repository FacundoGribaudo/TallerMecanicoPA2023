package com.Proyecto.TallerMecanico.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.Proyecto.TallerMecanico.domain.OrdenTrabajo;
import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.interfaceServices.IclienteServices;
import com.Proyecto.TallerMecanico.interfaceServices.IordenTrabajoService;
import com.Proyecto.TallerMecanico.interfaceServices.IvehiculoServices;

@Controller
@RequestMapping
public class ClienteController {

    @Autowired
    private IvehiculoServices servicesVehiculo;
    @Autowired
    private IclienteServices servicesCliente;
    @Autowired
    private IordenTrabajoService otService;

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = servicesCliente.listarClientes();
        model.addAttribute("clientes", clientes);
        return "clientes";
    }

    @PostMapping("/saveCliente")
    public String agregarCliente(Cliente c) {
        List<Cliente> clientes = servicesCliente.listarClientes();

        for (Cliente clienteExistente : clientes) {
            if (!clienteExistente.getId_cliente().equals(c.getId_cliente())
                    && clienteExistente.getDni().equals(c.getDni())) {
                // Si ya existe el DNI, redirige con mensaje de Cliente repetido
                return "redirect:/clientes?mensaje=clienteRepetido";
            }
        }

        servicesCliente.save(c);
        return "redirect:/clientes";
    }

    @PostMapping("/saveClienteSelect")
    public String saveClienteSelect(Cliente c) {
        List<Cliente> clientes = servicesCliente.listarClientes();

        for (Cliente clienteExistente : clientes) {
            if (!clienteExistente.getId_cliente().equals(c.getId_cliente())
                    && clienteExistente.getDni().equals(c.getDni())) {
                // Si ya existe el DNI, redirige con mensaje de Cliente repetido
                return "redirect:/vehiculos?mensaje=clienteRepetido";
            }
        }

        servicesCliente.save(c);
        return "redirect:/vehiculos";
    }

    @GetMapping("/eliminarCliente/{id_cliente}")
    public String eliminarCliente(Model model, @PathVariable int id_cliente) {
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();
        Boolean eliminarCliente = true;

        for (Vehiculo v : vehiculos) {
            if (v.getCliente().getId_cliente().equals(id_cliente)) {
                eliminarCliente = false;
            }
        }

        if (eliminarCliente == true) {
            System.out.println("si PODES ELIMINAR, NO ESTA RELACIONADO");
            servicesCliente.delete(id_cliente);
        } else {
            System.out.println("NO PODES ELIMINAR, ESTA RELACIONADO");
        }

        // Antes de la redirección
        String mensaje = eliminarCliente ? "true" : "false";
        String urlRedireccion = "redirect:/clientes?mensaje=" + mensaje;
        return urlRedireccion;
    }

    @GetMapping("/editarCliente/{id_cliente}")
    public String editarCliente(@PathVariable int id_cliente, Model model) {
        Optional<Cliente> optionalCliente = servicesCliente.listarIdCliente(id_cliente);

        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            model.addAttribute("cliente", cliente);
        } else {

        }

        return "editarCliente";
    }

    @PostMapping("/buscarCliente")
    public String buscarCliente(Model model, @RequestParam("datoCliente") String datoCliente) {
        List<Cliente> clientes = servicesCliente.listarClientes();
        List<Cliente> clientesBuscados = new ArrayList<Cliente>();


        if (datoCliente != null && !datoCliente.isEmpty()) {
            if (datoCliente.matches("\\d+")) {
                // Si el dato ingresado es numérico, buscar por DNI
                for (Cliente c : clientes) {
                    if (String.valueOf(c.getDni()).contains(datoCliente)) {
                        System.out.println("LOS VALORES COINCIDEN SE BUSCARA...");
                        clientesBuscados.add(c);
                    }
                }
            } else {
                // Si el dato ingresado no es numérico, buscar por apellido
                for (Cliente c : clientes) {
                    if (c.getApellido().toLowerCase().contains(datoCliente.toLowerCase())) {
                        System.out.println("LOS APELLIDOS COINCIDEN SE BUSCARA...");
                        clientesBuscados.add(c);
                    }
                }
            }
        }

        if (clientesBuscados.isEmpty()) {
            model.addAttribute("mensaje", "No se encontraron coincidencias.");
        } else {
            model.addAttribute("mensaje", null);
        }

        model.addAttribute("clientes", clientesBuscados);
        return "buscarCliente";

    }

    @GetMapping("/historialServicios/{id_cliente}")
    public String consultarHistorial(Model model, @PathVariable int id_cliente) {

        // Datos
        List<Cliente> clientesRegistrados = servicesCliente.listarClientes();
        List<Vehiculo> vehiculosRegistrados = servicesVehiculo.listarVehiculos();
        List<OrdenTrabajo> otRegistrados = otService.listarOrdenTrabajo();



        // Datos filtrados
        List<Cliente> clienteHistorial = new ArrayList<>();
        List<OrdenTrabajo> otHistorial = new ArrayList<>();

        for (OrdenTrabajo ot : otRegistrados) {
            for (Vehiculo v2 : vehiculosRegistrados) {
                if (ot.getVehiculoPertenece().getId_vehiculo() == v2.getId_vehiculo()
                        && v2.getCliente().getId_cliente() == id_cliente) {
                    otHistorial.add(ot);
                } 
            }
            
        }

        for (Cliente i : clientesRegistrados) {
            if (i.getId_cliente() == id_cliente) {
                clienteHistorial.add(i);
            }
        }

        String nombreCl = "";
        Integer clienteID = 0;
        for (Cliente cl : clienteHistorial) {
            nombreCl = "" + cl.getNombre() + " " + cl.getApellido();
            clienteID = cl.getId_cliente();
            System.out.println("" + "DATOS CLIENTE = " + nombreCl);
        }
        
        model.addAttribute("cliente", nombreCl);
        model.addAttribute("clienteID", clienteID);
        model.addAttribute("datos_orden", otHistorial);
        return "historialServiciosCliente";

    }

    @PostMapping("/buscarOrdenEnCliente/{id_cliente}")
    public String buscarOrdenEnCliente(Model model, @RequestParam("fechaBuscada") String fechaBuscada, @PathVariable int id_cliente){

        //Lista sobre la que busco
        List<OrdenTrabajo> otBuscada = otService.listarOrdenTrabajo();
        List<OrdenTrabajo> otEncontradas = new ArrayList<>();
        List<Cliente> clientesRegistrados = servicesCliente.listarClientes();
        
        //Formateo la fecha para poder coincidir con la DB
        String fechaBuscadaFormat = LocalDate.parse(fechaBuscada).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));;

        //Itero la lista, buscando las ordenes que coincidan con la fecha. 
        for (OrdenTrabajo ot : otBuscada){
            if (ot.getFechaHoraOrden().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).equals(fechaBuscadaFormat) && ot.getVehiculoPertenece().getCliente().getId_cliente().equals(id_cliente)){
                otEncontradas.add(ot);
            }
        }

        String nombreCl = "";
        Integer clienteID = 0;
        for (Cliente cl : clientesRegistrados) {
            if (cl.getId_cliente().equals(id_cliente)){
                nombreCl = "" + cl.getNombre() + " " + cl.getApellido();
                clienteID = id_cliente;
            }
        }
        
        model.addAttribute("cliente", nombreCl);
        model.addAttribute("clienteID", clienteID);
        model.addAttribute("datos_orden", otEncontradas);

        return "ordenBuscadaEnCliente";
    }

}
