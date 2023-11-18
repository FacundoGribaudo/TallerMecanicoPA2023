package com.Proyecto.TallerMecanico.controller;

import java.time.LocalDateTime;
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

import com.Proyecto.TallerMecanico.interfaceServices.IordenTrabajoService;
import com.Proyecto.TallerMecanico.interfaceServices.IserviciosTallerService;
import com.Proyecto.TallerMecanico.interfaceServices.ItecnicoServices;
import com.Proyecto.TallerMecanico.interfaceServices.IvehiculoServices;
import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.domain.OrdenTrabajo;
import com.Proyecto.TallerMecanico.domain.ServiciosTaller;
import com.Proyecto.TallerMecanico.domain.Tecnico;

@Controller
@RequestMapping
public class OrdenTrabajoController {

    @Autowired
    private IordenTrabajoService serviceOT;
    @Autowired
    private IvehiculoServices vehiculoService;
    @Autowired
    private IserviciosTallerService serTaller;
    @Autowired
    private ItecnicoServices tecnicoService;

    @GetMapping("/ordenTrabajo")
    public String listarOrdenTrabajo(Model model) {

        // Listas
        List<OrdenTrabajo> ordenTrabajoList = serviceOT.listarOrdenTrabajo();

        // Formatear la fecha y hora en el formato deseado (dd/MM/yyyy HH:mm:ss)
        for (OrdenTrabajo ordenTrabajo : ordenTrabajoList) {
            ordenTrabajo.setFechaHoraFormateada();
        }

        List<Vehiculo> lista_vehiculos = vehiculoService.listarVehiculos();
        List<ServiciosTaller> lista_servicios = serTaller.listarServicios();
        List<Tecnico> lista_tecnicos = tecnicoService.listarTecnico();

        // Render HTML
        model.addAttribute("listaOT", ordenTrabajoList);
        model.addAttribute("lista_vehiculos", lista_vehiculos);
        model.addAttribute("lista_servicios", lista_servicios);
        model.addAttribute("lista_tecnicos", lista_tecnicos);

        return "ordenTrabajo";
    }

    @PostMapping("/registrarOrdenTrabajo")
    public String registrarOrden(@RequestParam("serviciosRealizar") List<Integer> serviciosIds,
            @RequestParam("tecnicosOrden") List<Integer> tecnicosIds,
            @RequestParam("fechaHoraOrden") LocalDateTime fechaHoraOrden, OrdenTrabajo ot) {
        List<ServiciosTaller> serviciosSeleccionados = new ArrayList<>();
        List<Tecnico> tecnicosSeleccionados = new ArrayList<>();

        for (Integer servicioId : serviciosIds) {
            Optional<ServiciosTaller> servicioOptional = serTaller.listarIdServicios(servicioId);
            servicioOptional.ifPresent(serviciosSeleccionados::add);
        }
        ot.setServiciosRealizar(serviciosSeleccionados);

        for (Integer tecnicoId : tecnicosIds) {
            Optional<Tecnico> tecnicoOptional = tecnicoService.listarIdTecnico(tecnicoId);
            tecnicoOptional.ifPresent(tecnicosSeleccionados::add);
        }
        ot.setTecnicosOrden(tecnicosSeleccionados);

        // Configurar la fecha de orden en el objeto OrdenTrabajo
        ot.setFechaHoraOrden(fechaHoraOrden);

        // Resto del código para guardar la orden y redirigir
        serviceOT.save(ot);
        return "redirect:/ordenTrabajo";
    }

    @GetMapping("/eliminarOrden/{id_orden}")
    public String eliminarOrden(Model model, @PathVariable int id_orden) {
        serviceOT.delete(id_orden);
        return "redirect:/ordenTrabajo";
    }

    // VER - QUE SEA VER DETALLE ORDEN Y DENTRO SE PUEDAN EDITAR LOS DATOS
    @GetMapping("/editarOrden/{id_orden}")
    public String editarOrden(@PathVariable int id_orden, Model model) {
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
        List<Tecnico> lista_tecnicos = tecnicoService.listarTecnico();
        model.addAttribute("lista_vehiculos", lista_vehiculos);
        model.addAttribute("lista_servicios", lista_servicios);
        model.addAttribute("lista_tecnicos", lista_tecnicos);

        return "editarOrdenTrabajo";
    }

    @PostMapping("/buscarOrden")
    public String buscarOrden(Model model, @RequestParam("fechaOrdenBuscar") String orden) {
        List<OrdenTrabajo> ordenTrabajoList = serviceOT.listarOrdenTrabajo();
        List<OrdenTrabajo> ordenTrabajoFiltradas = new ArrayList<>();

        List<Vehiculo> lista_vehiculos = vehiculoService.listarVehiculos();
        List<ServiciosTaller> lista_servicios = serTaller.listarServicios();
        List<Tecnico> lista_tecnicos = tecnicoService.listarTecnico();
        
        System.out.println("FECHA INGRESADA: "+orden);
        for (OrdenTrabajo ort : ordenTrabajoList) {
            if (ort.getFechaHoraOrden().toString().contains(orden)) {
                ordenTrabajoFiltradas.add(ort);
                System.out.println("fecha igual");
            } else {
                System.out.println("fecha ingresada distinta:");
            }
        }

        if (ordenTrabajoFiltradas.isEmpty() || orden=="") {
            model.addAttribute("mensaje", "No se encontraron coincidencias.");
        } else {
            model.addAttribute("mensaje", null);
        }

        model.addAttribute("listaOT", ordenTrabajoFiltradas);
        model.addAttribute("lista_vehiculos", lista_vehiculos);
        model.addAttribute("lista_servicios", lista_servicios);
        model.addAttribute("lista_tecnicos", lista_tecnicos);
        return "buscarOrdenTrabajo";
    }

    @GetMapping("/detalleOrden/{nro_orden}")
    public String consultarDetalle(Model model, @PathVariable int nro_orden) {
        // Obtén la orden de trabajo específica por su número de orden
        Optional<OrdenTrabajo> optionalOrdenTrabajo = serviceOT.listarIdOrdenTrabajo(nro_orden);

        if (optionalOrdenTrabajo.isPresent()) {
            OrdenTrabajo ordenTrabajo = optionalOrdenTrabajo.get();
            Vehiculo vehiculo = ordenTrabajo.getVehiculoPertenece();

            model.addAttribute("ordenTrabajo", ordenTrabajo);
            model.addAttribute("vehiculo", vehiculo);
            model.addAttribute("serviciosSeleccionados", ordenTrabajo.getServiciosRealizar());
            model.addAttribute("tecnicosSeleccionados", ordenTrabajo.getTecnicosOrden());

            return "detalleOrden";
        } else {
            return null;
        }
    }

}
