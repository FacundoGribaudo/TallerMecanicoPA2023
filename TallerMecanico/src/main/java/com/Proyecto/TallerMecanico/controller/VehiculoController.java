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
import com.Proyecto.TallerMecanico.domain.Tecnico;
import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.interfaceServices.IModeloServices;
import com.Proyecto.TallerMecanico.interfaceServices.IclienteServices;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcaServices;
import com.Proyecto.TallerMecanico.interfaceServices.ItecnicoServices;
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
    private ItecnicoServices servicesTecnico; 

    
    @GetMapping("/vehiculos")
    public String listarVehiculos(Model model, @RequestParam(name = "marcaId", required = false) Integer marcaId){
        
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();

        //Marcas 
        List<Marca> marcas = servicesMarca.listarMarcas();
        List<Marca> marcasActivas = new ArrayList<Marca>(); //Segundo array que me almacena los objetos que coinciden

        //Cliente
        List<Cliente> clientes = servicesCliente.listarClientes();

        // //Tecnicos
        // List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        // System.out.println(""+"Los tecnicos son: " + tecnicos); 
        // List<Tecnico> tecnicosActivos = new ArrayList<Tecnico>(); 


        for (Marca marca : marcas) {
            if (marca.getEstado().toUpperCase().equals("activo".toUpperCase())) {
                marcasActivas.add(marca);
            }
        }


        // //Muestro los tecnicos que estan en estado "activo" --> Disponibles para trabajar
        // for (Tecnico t:tecnicos){
        //     if(t.getEstado().toUpperCase().equals("activo".toUpperCase())){
        //         // System.out.println(""+ "Los tecnicos activos son: " + t.getNombre()); 
        //         tecnicosActivos.add(t); 
        //     }
        // }

        //System.out.println(""+ "Los objetos VEHICULO estan asi: " + vehiculos);
        
        model.addAttribute("vehiculo", vehiculos); 
        model.addAttribute("marcasActivas", marcasActivas); 
        // model.addAttribute("tecnicos", tecnicosActivos);
        model.addAttribute("clientes", clientes);
        return "vehiculos";
    }

    //Obtencion de Modelos segun la Marca seleccionada
    @GetMapping("/vehiculos/listar")
    @ResponseBody
    public List<Modelo> obtenerModelosPorMarca(@RequestParam(name = "marcaId", required = false) Integer marcaId) {
        List<Modelo> modelos = servicesModelo.listarModelos();

        List<Modelo> modelosPermitidos = new ArrayList<>();

        for (Modelo m : modelos) {
            if (m.getEstado().equalsIgnoreCase("activo") && (marcaId == null || m.getMarca().getId_marca().equals(marcaId))) {
                modelosPermitidos.add(m);
            }
        }
        System.out.println(modelosPermitidos);
        return modelosPermitidos;
    }

    @PostMapping("/saveVehiculo")
    public String guardarVehiculo(Vehiculo v){
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();

        for(Vehiculo vehiculoExistente : vehiculos) {
            if(vehiculoExistente.getPatente().toUpperCase().equals(v.getPatente().toUpperCase())) {
                // Si ya existe un vehículo con la misma patente, redirige con mensaje de patente repetida
                return "redirect:/vehiculos?mensaje=patenteRepetida";
            }
        }

        // Si no existe un vehículo con la misma patente, guarda el vehículo
        servicesVehiculo.save(v);
        return "redirect:/vehiculos"; 
    }

    @GetMapping("/eliminarVehiculo/{id_vehiculo}")
    public String eliminarVehiculo(Model model, @PathVariable int id_vehiculo){
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        Boolean eliminarVehiculo = true;

        for(Tecnico t:tecnicos){
            if(t.getVehiculo().getId_vehiculo().equals(id_vehiculo)){
                eliminarVehiculo = false;
            }
        }

        if(eliminarVehiculo == true){
            System.out.print("SI SE PUEDE ELIMINAR EL VEHICULO");
            servicesVehiculo.delete(id_vehiculo);
        }else{System.out.println("NO SE PUEDE ELIMINAR EL VEHICULO, ELIMINA LOS REGISTROS RELACIONADOS");}
        
        // Antes de la redirección
        String mensaje = eliminarVehiculo ? "true" : "false";
        String urlRedireccion = "redirect:/vehiculos?mensaje=" + mensaje;
        return urlRedireccion;
    }

    @GetMapping("/editarVehiculo/{id_vehiculo}")
    public String editarVehiculo(@PathVariable int id_vehiculo, Model model){
        Optional<Vehiculo> optionalVehiculo = servicesVehiculo.listarIdVehiculo(id_vehiculo);

        // Verifica si la marca existe antes de agregarla al modelo
        if (optionalVehiculo.isPresent()) {
            Vehiculo vehiculo = optionalVehiculo.get();
            model.addAttribute("vehiculo", vehiculo);
        } else {
        }

        //Marcas 
        List<Marca> marcas = servicesMarca.listarMarcas();
        List<Marca> marcasActivas = new ArrayList<Marca>(); //Segundo array que me almacena los objetos que coinciden

        //Modelo
        List<Modelo> modelos = servicesModelo.listarModelos();
        List<Modelo> modelosPermitidos = new ArrayList<Modelo>();

        //Cliente
        List<Cliente> clientes = servicesCliente.listarClientes();

        //Unicamente permito vincular modelos de aquellas marcas que estan activas. 
        for (Modelo m : modelos){
            System.out.println(m.getMarca().getEstado());
            if(m.getMarca().getEstado().toUpperCase().equals("activo".toUpperCase())){
                modelosPermitidos.add(m);
            }
            System.out.println(modelosPermitidos);
        }
        
        for(int i=0; i<marcas.size(); i++){
            if(marcas.get(i).getEstado().toUpperCase().equals("activo".toUpperCase())){
                marcasActivas.add(marcas.get(i));
            }
        }

        //model.addAttribute("vehiculo", vehiculos); 
        model.addAttribute("marcasActivas", marcasActivas); 
        model.addAttribute("modelosPermitidos", modelosPermitidos);
        model.addAttribute("clientes", clientes);

        return "editarVehiculo"; 
    }

    @PostMapping("/buscarVehiculo")
    public String buscarVehiculo(Model model, String patente){

        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos(); //Traigo todos los objetos de la db
        List<Vehiculo> vehiculosBuscados = new ArrayList<Vehiculo>(); //Un segundo array que me almacena los objetos vehiculos que coinciden con la patente buscada

        for(int i=0; i<vehiculos.size(); i++){
            if(vehiculos.get(i).getPatente().toUpperCase().equals(patente.toUpperCase())){
                vehiculosBuscados.add(vehiculos.get(i));
            }
        }

        if(vehiculosBuscados.size() == 0){
            vehiculosBuscados = vehiculos; 
        }

        model.addAttribute("vehiculo", vehiculosBuscados);
        return "buscarVehiculo";

    }

}
