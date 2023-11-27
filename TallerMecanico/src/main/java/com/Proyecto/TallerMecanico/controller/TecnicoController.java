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

import com.Proyecto.TallerMecanico.domain.OrdenTrabajo;
import com.Proyecto.TallerMecanico.domain.Tecnico;
import com.Proyecto.TallerMecanico.interfaceServices.IordenTrabajoService;
import com.Proyecto.TallerMecanico.interfaceServices.ItecnicoServices;

@Controller
@RequestMapping
public class TecnicoController {

    @Autowired
    private ItecnicoServices servicesTecnico;
    @Autowired
    private IordenTrabajoService otService;

    @GetMapping("/tecnicos")
    public String listarTecnicos(Model model) {
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        model.addAttribute("lista_tecnicos", tecnicos);
        return "tecnico";
    }

    @PostMapping("/saveTecnico")
    public String guardarTecnico(Tecnico t) {
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();

        for (Tecnico tecnicoExistente : tecnicos) {
            if (!tecnicoExistente.getId_tecnico().equals(t.getId_tecnico())
                    && tecnicoExistente.getLegajo().equals(t.getLegajo())) {
                // Si ya existe el DNI, redirige con mensaje de Cliente repetido
                return "redirect:/tecnicos?mensaje=tecnicoRepetido";
            }
        }
        servicesTecnico.save(t);
        return "redirect:/tecnicos";
    }

    @PostMapping("/saveTecnicoSelect")
    public String guardarTecnicoSelect(Tecnico t) {
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();

        for (Tecnico tecnicoExistente : tecnicos) {
            if (!tecnicoExistente.getId_tecnico().equals(t.getId_tecnico())
                    && tecnicoExistente.getLegajo().equals(t.getLegajo())) {
                // Si ya existe el DNI, redirige con mensaje de Cliente repetido
                return "redirect:/ordenTrabajo?mensaje=tecnicoRepetido";
            }
        }
        servicesTecnico.save(t);
        return "redirect:/ordenTrabajo";
    }

    // solo se elimina si un tecnico no esta asociado a una orden de trabajo
    // Ver si se puede borrar el tecnico y que se elimine de la orden, sin eliminar
    // la orden
    @GetMapping("/eliminarTecnico/{id_tecnico}")
    public String eliminarTecnico(Model model, @PathVariable int id_tecnico) {
        List<OrdenTrabajo> ordenes = otService.listarOrdenTrabajo();
        Boolean eliminarTecnico = true;

        for (OrdenTrabajo ot : ordenes) {
            for (Tecnico tecnico : ot.getTecnicosOrden()) {
                if (tecnico.getId_tecnico().equals(id_tecnico)) {
                    eliminarTecnico = false;
                    break; // Ya encontramos el tecnico en una orden, no es necesario seguir buscando
                }
            }
        }

        if (eliminarTecnico) {
            System.out.println("Se puede eliminar tecnico, no esta relacionado");
            servicesTecnico.delete(id_tecnico);
        } else {
            System.out.println("No se puede eliminar tecnico");
        }

        // Antes de la redireccion
        String mensaje = eliminarTecnico ? "true" : "false";
        return "redirect:/tecnicos?mensaje=" + mensaje;
    }

    @GetMapping("/editarTecnico/{id_tecnico}")
    public String editarTecnico(@PathVariable int id_tecnico, Model model) {
        Optional<Tecnico> optionalTecnico = servicesTecnico.listarIdTecnico(id_tecnico);

        if (optionalTecnico.isPresent()) {
            Tecnico tecnico = optionalTecnico.get();
            model.addAttribute("tecnico", tecnico);
        } else {
            System.out.println("Fallo algo");
        }

        return "editarTecnico";
    }

    @PostMapping("/buscarTecnico")
    public String buscarTecnico(Model model, @RequestParam("datosBuscados") String datosBuscados) {
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        List<Tecnico> tecnicosEncontrados = new ArrayList<Tecnico>();

        if (datosBuscados != null && !datosBuscados.isEmpty()) {
            if (datosBuscados.matches("\\d+")) {
                // Si el dato ingresado es numérico, buscar por Legajo
                for (Tecnico t : tecnicos) {
                    if (String.valueOf(t.getLegajo()).contains(datosBuscados)) {
                        System.out.println("LOS VALORES COINCIDEN SE BUSCARA...");
                        tecnicosEncontrados.add(t);
                    }
                }
            } else {
                // Si el dato ingresado no es numérico, buscar por apellido
                for (Tecnico t : tecnicos) {
                    if (t.getApellido().toLowerCase().contains(datosBuscados.toLowerCase())) {
                        System.out.println("LOS APELLIDOS COINCIDEN SE BUSCARA...");
                        tecnicosEncontrados.add(t);
                    }
                }
            }
        }


        if (tecnicosEncontrados.isEmpty()) {
            model.addAttribute("mensaje", "No se encontraron coincidencias.");
        } else {
            model.addAttribute("mensaje", null);
        }
        
        model.addAttribute("lista_tecnicos", tecnicosEncontrados);

        return "buscarTecnico";
    }

    @GetMapping("/ordenesTecnico/{id_tecnico}")
    public String mostrarOrdenesTecnico(
        @PathVariable int id_tecnico,
        @RequestParam(name = "estadoFilter", required = false) String estadoFilter,
        Model model) {
        List<OrdenTrabajo> tecnicosEnOrdenes = otService.listarOrdenTrabajo(); 
        
        //Lista para el template
        List<OrdenTrabajo> ordenesDelTecnico = new ArrayList<>();
        
        // Filtramos las órdenes correspondientes al trabajador que se consulta.
        for (OrdenTrabajo ot : tecnicosEnOrdenes) {
            for (int i = 0; i < ot.getTecnicosOrden().size(); i++) {
                if (ot.getTecnicosOrden().get(i).getId_tecnico().equals(id_tecnico)) {
                    // Aplicar filtro por estado (si se proporciona)
                    if (estadoFilter == null || estadoFilter.isEmpty() || ot.getEstado().equals(estadoFilter)) {
                        ordenesDelTecnico.add(ot);
                    }
                }
            }
        }

        if (ordenesDelTecnico.isEmpty()) {
            model.addAttribute("mensaje", "No se encontraron coincidencias.");
        } else {
            model.addAttribute("mensaje", null);
        }
        model.addAttribute("ordenesDelTecnico",ordenesDelTecnico);
        model.addAttribute("estadoFilter", estadoFilter);

        return "ordenesTecnico";
    }

}
