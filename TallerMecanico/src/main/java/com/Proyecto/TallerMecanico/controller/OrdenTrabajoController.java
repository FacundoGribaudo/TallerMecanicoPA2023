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
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;

import com.Proyecto.TallerMecanico.interfaceServices.IordenTrabajoService;
import com.Proyecto.TallerMecanico.interfaceServices.IserviciosTallerService;
import com.Proyecto.TallerMecanico.interfaceServices.IvehiculoServices;
import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.domain.OrdenTrabajo;
import com.Proyecto.TallerMecanico.domain.ServiciosTaller;

@Controller
@RequestMapping
public class OrdenTrabajoController {
    
    @Autowired
    private IordenTrabajoService serviceOT;
    @Autowired
    private IvehiculoServices vehiculoService;
    @Autowired
    private IserviciosTallerService serTaller;
    

    @GetMapping("/ordenTrabajo")
    public String listarOrdenTrabajo(Model model){
        
        //Listas
        List<OrdenTrabajo> ordenTrabajoList = serviceOT.listarOrdenTrabajo(); 
        
        // Formatear la fecha y hora en el formato deseado (dd/MM/yyyy HH:mm:ss)
        for (OrdenTrabajo ordenTrabajo : ordenTrabajoList) {
            ordenTrabajo.setFechaHoraCreacionFormateada();
        }

        List<Vehiculo> lista_vehiculos = vehiculoService.listarVehiculos();
        List<ServiciosTaller> lista_servicios = serTaller.listarServicios();
        // List<Cliente> lista_clientesExistentes = clienteService.listarClientes();
        
        //Render HTML
        model.addAttribute("listaOT", ordenTrabajoList);
        model.addAttribute("lista_vehiculos", lista_vehiculos);
        model.addAttribute("lista_servicios", lista_servicios);
        // model.addAttribute("clientesConOT", lista_clientesFiltrados);
        
        
        return "ordenTrabajo";      
    }

    @PostMapping("/registrarOrdenTrabajo")
    public String registrarOrden(@RequestParam("serviciosRealizar") List<Integer> serviciosIds, OrdenTrabajo ot) {
        List<ServiciosTaller> serviciosSeleccionados = new ArrayList<>();
        for (Integer servicioId : serviciosIds) {
            Optional<ServiciosTaller> servicioOptional = serTaller.listarIdServicios(servicioId);
            servicioOptional.ifPresent(serviciosSeleccionados::add);
        }
        ot.setServiciosRealizar(serviciosSeleccionados);
    
        // Resto del código para guardar la orden y redirigir
        serviceOT.save(ot);
        return "redirect:/ordenTrabajo";
    }

    @GetMapping("/eliminarOrden/{id_orden}")
    public String eliminarOrden(Model model, @PathVariable int id_orden){
        serviceOT.delete(id_orden);
        return "redirect:/ordenTrabajo"; 
    }

    @GetMapping("/editarOrden/{id_orden}")
    public String editarOrden(@PathVariable int id_orden, Model model){
        Optional<OrdenTrabajo> optionalOrdenT = serviceOT.listarIdOrdenTrabajo(id_orden);
        System.out.println("entro al editar "); 
        
        if (optionalOrdenT.isPresent()) {
            OrdenTrabajo ordenTrabajo = optionalOrdenT.get();
            model.addAttribute("orden_trabajo", ordenTrabajo);
            System.out.println(ordenTrabajo);
        } else {
           
        }

        List<Vehiculo> lista_vehiculos = vehiculoService.listarVehiculos();
        List<ServiciosTaller> lista_servicios = serTaller.listarServicios();
        model.addAttribute("lista_vehiculos", lista_vehiculos);
        model.addAttribute("lista_servicios", lista_servicios);

        return "editarOrdenTrabajo"; 
    }

    @PostMapping("/buscarOrden")
    public String buscarOrden(Model model, @RequestParam("fechaOrden") String orden){
        List<OrdenTrabajo> ordenTrabajoList = serviceOT.listarOrdenTrabajo(); 
        List<OrdenTrabajo> ordenTrabajoFiltradas = new ArrayList<>();

        for(OrdenTrabajo ort:ordenTrabajoList){
            ort.setFechaHoraCreacionFormateada();
            if(ort.getFechaHoraCreacionOrden().toString().equals(orden)){
                ordenTrabajoFiltradas.add(ort);
            }
        }

        if(ordenTrabajoFiltradas.size() == 0){
            ordenTrabajoFiltradas = ordenTrabajoList;
        }

        model.addAttribute("listaOT", ordenTrabajoFiltradas);
        return "buscarOrdenTrabajo";




    }

}
