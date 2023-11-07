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
    public String buscarTecnico(Model model, @RequestParam("datosBuscados") String legajo) {
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        List<Tecnico> tecnicosEncontrados = new ArrayList<Tecnico>();

        for (Tecnico t : tecnicos) {
            if (t.getLegajo().toUpperCase().equals(legajo.toUpperCase())) {
                tecnicosEncontrados.add(t);
            } else {
                if (t.getApellido().equalsIgnoreCase(legajo)) {
                    tecnicosEncontrados.add(t);
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
    public String mostrarOrdenesTecnico(@PathVariable int id_tecnico, Model model) {
        List<OrdenTrabajo> tecnicosEnOrdenes = otService.listarOrdenTrabajo(); 
        
        //Lista para el template
        List<OrdenTrabajo> ordenesDelTecnico = new ArrayList<>();

        //Filtramos las ordenes correspondientes al trabajador que se consulta.
        for(OrdenTrabajo ot : tecnicosEnOrdenes){
            for(int i=0; i<ot.getTecnicosOrden().size(); i++){
                if(ot.getTecnicosOrden().get(i).getId_tecnico().equals(id_tecnico)){
                    System.out.println(""+ " ID TECNICO: " + ot.getTecnicosOrden().get(i).getId_tecnico());
                    System.out.println(""+" LA ORDEN QUE CORRESPONDE ES: " + ot.getNro_orden());
                    ordenesDelTecnico.add(ot);
                }
            }
        }
        model.addAttribute("ordenesDelTecnico",ordenesDelTecnico);
        return "ordenesTecnico";
    }

}
