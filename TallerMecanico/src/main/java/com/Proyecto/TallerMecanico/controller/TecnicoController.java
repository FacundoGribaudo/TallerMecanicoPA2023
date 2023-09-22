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

import com.Proyecto.TallerMecanico.domain.Tecnico;
import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.interfaceServices.ItecnicoServices;
import com.Proyecto.TallerMecanico.interfaceServices.IvehiculoServices;

@Controller
@RequestMapping
public class TecnicoController {
     
    @Autowired
    private IvehiculoServices servicesVehiculo;
    @Autowired
    private ItecnicoServices servicesTecnico; 


    @GetMapping("/tecnicos")
    public String listarTecnicos(Model model){
        
        /*
         * DEFINIMOS QUE MUCHOS TECNICOS PUEDAN ESTAR EN UN MISMO VEHICULO
         * ASI QUE DEBEMOS PODER SELECCIONAR Y MOSTRAR LOS VEHICULOS DONDE ESTAN 
         * TRABAJANDO LOS TECNICOS
         */
        
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();

        model.addAttribute("tecnicos", tecnicos);
        model.addAttribute("vehiculos", vehiculos);
        
        return "tecnico";
    }

    @PostMapping("/saveTecnico")
    public String guardarTecnico(Tecnico t){
        // List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        // Boolean guardarTecnico = true;

        // for(Tecnico tec:tecnicos){
        //     if(tec.getLegajo().toUpperCase().equals(t.getLegajo().toUpperCase()))
        //         guardarTecnico = false;
        // }

        // if (guardarTecnico == true){
        //     servicesTecnico.save(t);
        // }
        servicesTecnico.save(t);
        return "redirect:/tecnicos";
    }

    @GetMapping("/eliminarTecnico/{id_tecnico}")
    public String eliminarTecnico(Model model, @PathVariable int id_tecnico){
        servicesTecnico.delete(id_tecnico);
        return "redirect:/tecnicos"; 
    }

    @GetMapping("/editarTecnico/{id_tecnico}")
    public String editarTecnico(@PathVariable int id_tecnico, Model model){
        Optional<Tecnico> optionalTecnico = servicesTecnico.listarIdTecnico(id_tecnico);
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();

        
        if (optionalTecnico.isPresent()) {
            Tecnico tecnico = optionalTecnico.get();
            model.addAttribute("tecnico", tecnico);
        } else {
            
        }

        model.addAttribute("vehiculos", vehiculos);
        return "editarTecnico"; 
    }

    @PostMapping("/buscarTecnico")
    public String buscarTecnico(Model model, @RequestParam("datosBuscados") String legajo){
        List<Tecnico> tecnicos = servicesTecnico.listarTecnico();
        List<Tecnico> tecnicosEncontrados = new ArrayList<Tecnico>();
        
        for (Tecnico t:tecnicos){
            if(t.getLegajo().toUpperCase().equals(legajo.toUpperCase())){
                tecnicosEncontrados.add(t);
            }
        }

        if (tecnicosEncontrados.size() == 0){
            tecnicosEncontrados = tecnicos;
        }

       
        model.addAttribute("tecnicos", tecnicosEncontrados);

        return "buscarTecnico";
    }
}
