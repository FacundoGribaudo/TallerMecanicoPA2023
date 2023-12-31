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
import org.springframework.web.bind.annotation.ResponseBody;

import com.Proyecto.TallerMecanico.domain.Cliente;
import com.Proyecto.TallerMecanico.domain.Marca;
import com.Proyecto.TallerMecanico.domain.Modelo;
import com.Proyecto.TallerMecanico.domain.OrdenTrabajo;
import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.interfaceServices.IModeloServices;
import com.Proyecto.TallerMecanico.interfaceServices.IclienteServices;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcaServices;
import com.Proyecto.TallerMecanico.interfaceServices.IordenTrabajoService;
import com.Proyecto.TallerMecanico.interfaceServices.IvehiculoServices;

@Controller
@RequestMapping
public class VehiculoController {

    @Autowired
    private ImarcaServices servicesMarca;
    @Autowired
    private IvehiculoServices servicesVehiculo;
    @Autowired
    private IModeloServices servicesModelo;
    @Autowired
    private IclienteServices servicesCliente;
    @Autowired
    private IordenTrabajoService servicesOT;

    @GetMapping("/vehiculos")
    public String listarVehiculos(Model model, @RequestParam(name = "marcaId", required = false) Integer marcaId) {

        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();

        // Marcas
        List<Marca> marcas = servicesMarca.listarMarcas();
        List<Marca> marcasActivas = new ArrayList<Marca>(); // Segundo array que me almacena los objetos que coinciden

        // Cliente
        List<Cliente> clientes = servicesCliente.listarClientes();

        for (Marca marca : marcas) {
            if (marca.getEstado().toUpperCase().equals("activo".toUpperCase())) {
                marcasActivas.add(marca);
            }
        }

        model.addAttribute("vehiculo", vehiculos);
        model.addAttribute("marcasActivas", marcasActivas);
        // model.addAttribute("tecnicos", tecnicosActivos);
        model.addAttribute("clientes", clientes);
        return "vehiculos";
    }

    // Obtencion de Modelos segun la Marca seleccionada
    @GetMapping("/vehiculos/listarModelosPorMarca")
    @ResponseBody
    public List<Modelo> listarModelosPorMarca(@RequestParam(name = "marcaId", required = false) Integer marcaId) {
        List<Modelo> modelos = servicesModelo.listarModelos();

        List<Modelo> modelosPermitidos = new ArrayList<>();

        for (Modelo m : modelos) {
            if (m.getEstado().equalsIgnoreCase("activo")
                    && (marcaId == null || m.getMarca().getId_marca().equals(marcaId))) {
                modelosPermitidos.add(m);
            }
        }
        System.out.println(modelosPermitidos);
        return modelosPermitidos;
    }

    @PostMapping("/saveVehiculo")
    public String guardarVehiculo(Vehiculo v) {
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();

        for (Vehiculo vehiculoExistente : vehiculos) {
            if (!vehiculoExistente.getId_vehiculo().equals(v.getId_vehiculo())
                    && vehiculoExistente.getPatente().toUpperCase().equals(v.getPatente().toUpperCase())) {
                // Si ya existe un vehículo con la misma patente, redirige con mensaje de
                // patente repetida
                return "redirect:/vehiculos?mensaje=patenteRepetida";
            }
        }

        // Si no existe un vehículo con la misma patente, guarda el vehículo
        servicesVehiculo.save(v);
        return "redirect:/vehiculos";
    }

    @PostMapping("/saveVehiculoSelect")
    public String guardarVehiculoSelect(Vehiculo v) {
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();

        for (Vehiculo vehiculoExistente : vehiculos) {
            if (!vehiculoExistente.getId_vehiculo().equals(v.getId_vehiculo())
                    && vehiculoExistente.getPatente().toUpperCase().equals(v.getPatente().toUpperCase())) {
                // Si ya existe un vehículo con la misma patente, redirige con mensaje de
                // patente repetida
                return "redirect:/ordenTrabajo?mensaje=patenteRepetida";
            }
        }

        // Si no existe un vehículo con la misma patente, guarda el vehículo
        System.out.println("EL OBJETO VEHICULO NUEVO ES: " + v);
        servicesVehiculo.save(v);
        return "redirect:/ordenTrabajo";
    }

    @GetMapping("/eliminarVehiculo/{id_vehiculo}")
    public String eliminarVehiculo(Model model, @PathVariable int id_vehiculo) {
        List<OrdenTrabajo> ordenesTrabajo = servicesOT.listarOrdenTrabajo();

        Boolean tecnicoAsociado = false;
        Boolean oTAsociada = false;

        // verificar si tiene orden de trabajo asociada
        for (OrdenTrabajo ot : ordenesTrabajo) {
            if (ot.getVehiculoPertenece() != null
                    && ot.getVehiculoPertenece().getId_vehiculo().equals(id_vehiculo)) {
                oTAsociada = true;
                break; // Puedes salir del bucle tan pronto como encuentres una asociación
            }
        }

        if (tecnicoAsociado && !oTAsociada) {
            // tecnico asociado pero no una OT
            return "redirect:/vehiculos?mensaje=tecnicoAsociado";
        } else if (!tecnicoAsociado && oTAsociada) {
            // OT asociada pero no un tecnico
            return "redirect:/vehiculos?mensaje=oTAsociada";
        } else if (tecnicoAsociado && oTAsociada) {
            // tecnico y OT asociadas
            return "redirect:/vehiculos?mensaje=tecnicoYOTAsociada";
        } else {
            servicesVehiculo.delete(id_vehiculo);

        }

        return "redirect:/vehiculos";
    }

    @GetMapping("/editarVehiculo/{id_vehiculo}")
    public String editarVehiculo(@PathVariable int id_vehiculo, Model model) {
        Optional<Vehiculo> optionalVehiculo = servicesVehiculo.listarIdVehiculo(id_vehiculo);

        // Verifica si la marca existe antes de agregarla al modelo
        if (optionalVehiculo.isPresent()) {
            Vehiculo vehiculo = optionalVehiculo.get();
            model.addAttribute("vehiculo", vehiculo);
        } else {
        }

        // Marcas
        List<Marca> marcas = servicesMarca.listarMarcas();
        List<Marca> marcasActivas = new ArrayList<Marca>(); // Segundo array que me almacena los objetos que coinciden

        // Modelo
        List<Modelo> modelos = servicesModelo.listarModelos();
        List<Modelo> modelosPermitidos = new ArrayList<Modelo>();

        // Cliente
        List<Cliente> clientes = servicesCliente.listarClientes();

        // Unicamente permito vincular modelos de aquellas marcas que estan activas.
        for (Modelo m : modelos) {
            System.out.println(m.getMarca().getEstado());
            if (m.getMarca().getEstado().toUpperCase().equals("activo".toUpperCase())) {
                modelosPermitidos.add(m);
            }
            System.out.println(modelosPermitidos);
        }

        for (int i = 0; i < marcas.size(); i++) {
            if (marcas.get(i).getEstado().toUpperCase().equals("activo".toUpperCase())) {
                marcasActivas.add(marcas.get(i));
            }
        }

        // model.addAttribute("vehiculo", vehiculos);
        model.addAttribute("marcasActivas", marcasActivas);
        model.addAttribute("modelosPermitidos", modelosPermitidos);
        model.addAttribute("clientes", clientes);

        return "editarVehiculo";
    }

    @PostMapping("/buscarVehiculo")
    public String buscarVehiculo(Model model, String patente) {
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();
        List<Vehiculo> vehiculosBuscados = new ArrayList<Vehiculo>();

        List<Cliente> clientes = servicesCliente.listarClientes();

        // Eliminar espacios en blanco de la patente ingresada
        String patenteSinEspacios = patente.replaceAll("\\s", "").toUpperCase();

        for (Vehiculo vehiculo : vehiculos) {
            // Eliminar espacios en blanco de la patente del vehículo
            String patenteVehiculoSinEspacios = vehiculo.getPatente().replaceAll("\\s", "").toUpperCase();

            if (patenteVehiculoSinEspacios.contains(patenteSinEspacios)) {
                vehiculosBuscados.add(vehiculo);
            } else {
                // Si no coincide la patente, verificamos si el cliente coincide en apellido
                for (Cliente cliente : clientes) {
                    if (cliente.getId_cliente() == vehiculo.getCliente().getId_cliente() &&
                            cliente.getApellido().contains(patenteSinEspacios)) {
                        vehiculosBuscados.add(vehiculo);
                        break; // Salimos del bucle interno
                    }
                }
            }
        }

        model.addAttribute("vehiculos", vehiculosBuscados);

        if (vehiculosBuscados.isEmpty()) {
            model.addAttribute("mensaje", "No se encontraron coincidencias.");
        } else {
            model.addAttribute("mensaje", null);
        }

        return "buscarVehiculo";
    }

}
