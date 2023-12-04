package com.Proyecto.TallerMecanico.controller;

import java.math.BigDecimal;
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

        // Configurar los valores en los mapas de horas y minutos para cada servicio
        for (ServiciosTaller servicio : ot.getServiciosRealizar()) {
            int horasPromedio = servicio.getHorasDuracionPromedio(); // Obtén este valor de donde sea necesario
            int minutosPromedio = servicio.getMinutosDuracionPromedio(); // Obtén este valor de donde sea necesario

            ot.agregarHorasYMinutosParaServicio(servicio.getId_servicio(), horasPromedio, minutosPromedio);  //ESTABLEZCO ESTOS VALORES EN EL MAPA DE HORAS Y MINUTOS
        }

        ot.setPorcentajeImpuestoAgregado(new BigDecimal(0));
        ot.setPorcentajeDescuentoAgregado(new BigDecimal(0));
        ot.setPorcentajeAumentoAgregado(new BigDecimal(0));

        // Estableczco estado de la orden en Espera al crearla
        ot.setEstado("EN ESPERA");

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
    public String buscarOrden(
        @RequestParam("fechaOrdenBuscar") String fechaOrdenBuscar,
        @RequestParam("estadoOrdenBuscar") String estadoOrdenBuscar,
        Model model) {
        List<OrdenTrabajo> ordenTrabajoList = serviceOT.listarOrdenTrabajo();
        List<OrdenTrabajo> ordenTrabajoFiltradas = new ArrayList<>();

        List<Vehiculo> lista_vehiculos = vehiculoService.listarVehiculos();
        List<ServiciosTaller> lista_servicios = serTaller.listarServicios();
        List<Tecnico> lista_tecnicos = tecnicoService.listarTecnico();
        
        System.out.println("FECHA INGRESADA: "+ fechaOrdenBuscar);

        for (OrdenTrabajo ort : ordenTrabajoList) {
            boolean cumpleFecha = fechaOrdenBuscar.isEmpty() || ort.getFechaHoraOrden().toString().contains(fechaOrdenBuscar);
            boolean cumpleEstado = estadoOrdenBuscar == null || estadoOrdenBuscar.isEmpty() || ort.getEstado().equals(estadoOrdenBuscar);
    
            if (cumpleFecha && cumpleEstado) {
                ordenTrabajoFiltradas.add(ort);
            }
        }
    
        if (ordenTrabajoFiltradas.isEmpty()) {
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

    @PostMapping("/actualizarDetalleOrden")
    public String actualizarDetalleOrden(
        @RequestParam("nroOrden") int nro_orden,
        @RequestParam("servicioIds") List<Integer> servicioIds,
        @RequestParam("horas") List<Integer> horas,
        @RequestParam("minutos") List<Integer> minutos,
        @RequestParam(name = "%impuestoAgregado", required=false ) BigDecimal porcentajeImpuestoAgregado,
        @RequestParam(name = "%descuentoAgregado", required=false ) BigDecimal porcentajeDescuentoAgregado,
        @RequestParam(name = "%aumentoAgregado", required=false ) BigDecimal porcentajeAumentoAgregado,
        @RequestParam(name = "observaciones") String observaciones,
        @RequestParam(name = "estado") String estadoOrden) {

        // Obtén la OrdenTrabajo por su número de orden
        Optional<OrdenTrabajo> optionalOrdenTrabajo = serviceOT.listarIdOrdenTrabajo(nro_orden);

        if (optionalOrdenTrabajo.isPresent()) {
            OrdenTrabajo ordenTrabajo = optionalOrdenTrabajo.get();

            // Actualiza las horas y minutos para cada servicio
            for (int i = 0; i < servicioIds.size(); i++) {
                Optional<ServiciosTaller> optionalServicio = serTaller.listarIdServicios(servicioIds.get(i));

                if (optionalServicio.isPresent()) {
                    ServiciosTaller servicio = optionalServicio.get();
                    int horasParaServicio = horas.get(i);
                    int minutosParaServicio = minutos.get(i);
                    serviceOT.agregarHorasYMinutosParaServicio(ordenTrabajo, servicio, horasParaServicio, minutosParaServicio);
                }
            }

            // Si es null, que se establezca en cero
            if (porcentajeImpuestoAgregado == null) {
                porcentajeImpuestoAgregado = BigDecimal.ZERO;
            }
            if (porcentajeDescuentoAgregado == null) {
                porcentajeDescuentoAgregado = BigDecimal.ZERO;
            }
            if (porcentajeAumentoAgregado == null) {
                porcentajeAumentoAgregado = BigDecimal.ZERO;
            }

            ordenTrabajo.setPorcentajeImpuestoAgregado(porcentajeImpuestoAgregado);
            ordenTrabajo.setPorcentajeDescuentoAgregado(porcentajeDescuentoAgregado);
            ordenTrabajo.setPorcentajeAumentoAgregado(porcentajeAumentoAgregado);

            ordenTrabajo.setObservaciones(observaciones);
            
            System.out.println("estadoOrden: "+ estadoOrden);
            ordenTrabajo.setEstado(estadoOrden);
            System.out.println("---------estadoOrden: "+ estadoOrden);


            // Guarda la OrdenTrabajo actualizada en la base de datos
            serviceOT.save(ordenTrabajo);
    
            // Redirige a la página de detalles de la orden o a donde sea apropiado
            return "redirect:/detalleOrden/" + nro_orden;
        } else {
            return null;
        }

    }
    
}
