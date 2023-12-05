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

import com.Proyecto.TallerMecanico.domain.Marca;
import com.Proyecto.TallerMecanico.domain.Modelo;
import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.interfaceServices.IModeloServices;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcaServices;
import com.Proyecto.TallerMecanico.interfaceServices.IvehiculoServices;

@Controller
@RequestMapping
public class ModeloController {
    @Autowired
    private ImarcaServices servicesMarca; 
    @Autowired
    private IvehiculoServices servicesVehiculo;
    @Autowired
    private IModeloServices servicesModelo;

    @GetMapping("/modelo")
    public String listarModelos(Model model){
        
        List<Modelo> modelos = servicesModelo.listarModelos(); 
        model.addAttribute("modelos", modelos);

        //Marcas 
        List<Marca> marcas = servicesMarca.listarMarcas();

        model.addAttribute("marcas", marcas); 
        return "modelo";
    }

    @PostMapping("/saveModelo")
    public String agregarModelo(Modelo mo) {
        List<Modelo> modelos = servicesModelo.listarModelos();

        for (Modelo modeloExistente : modelos) {
            if(!modeloExistente.getId_modelo().equals(mo.getId_modelo()) && modeloExistente.getNombre().toUpperCase().equals(mo.getNombre().toUpperCase())) {
                //Se controla que NO se repita el modelo en la misma marca. Puede existir el mismo modelo pero de distinta MARCA
                if (modeloExistente.getMarca().getNombre().toUpperCase().equals(mo.getMarca().getNombre().toUpperCase())){ 
                    //Si ya existe el Modelo, redirige con mensaje de Modelo repetido
                    return "redirect:/modelo?mensaje=modeloRepetido";
                }
            }
        }
        servicesModelo.save(mo); 
        return "redirect:/modelo";
    }

    @PostMapping("/saveModeloSelect")
    public String saveModeloSelect(Modelo mo) {
        List<Modelo> modelos = servicesModelo.listarModelos();

        for (Modelo modeloExistente : modelos) {
            if(!modeloExistente.getId_modelo().equals(mo.getId_modelo()) && modeloExistente.getNombre().toUpperCase().equals(mo.getNombre().toUpperCase())) {
                //Se controla que NO se repita el modelo en la misma marca. Puede existir el mismo modelo pero de distinta MARCA
                if (modeloExistente.getMarca().getNombre().toUpperCase().equals(mo.getMarca().getNombre().toUpperCase())){ 
                    //Si ya existe el Modelo, redirige con mensaje de Modelo repetido
                    return "redirect:/vehiculos?mensaje=modeloRepetido";
                }
            }
        }
        servicesModelo.save(mo); 
        return "redirect:/vehiculos";
    }
 
    @GetMapping("/eliminarModelo/{id_modelo}")
    public String delete(Modelo modelo,@PathVariable int id_modelo){
        
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();
        Boolean eliminarModelo = true;

        for(Vehiculo v:vehiculos){
            if(v.getModelo().getId_modelo().equals(id_modelo)){
                eliminarModelo = false;
            }
        }
        
        if(eliminarModelo == true){
            System.out.println("si PODES ELIMINAR, NO ESTA RELACIONADO");

            servicesModelo.delete(id_modelo);
        }else{System.out.println("NO PODES ELIMINAR, ESTA RELACIONADO");}

        // Antes de la redirección
        String mensaje = eliminarModelo ? "true" : "false";
        String urlRedireccion = "redirect:/modelo?mensaje=" + mensaje;
        return urlRedireccion;
    }

    @GetMapping("/editarModelo/{id_modelo}")
    public String editarModelo(@PathVariable int id_modelo, Model model){
        Optional<Modelo> optionalModelo = servicesModelo.listarIdModelo(id_modelo);

        //Marcas 
        List<Marca> marcas = servicesMarca.listarMarcas();
        model.addAttribute("marcas", marcas); 

        if (optionalModelo.isPresent()) {
            Modelo modelo = optionalModelo.get();
            model.addAttribute("modelo", modelo);
        } else {
            
        }

        return "editarModelo"; 
    }

    @PostMapping("/buscarModelo")
    public String buscarModelo(Model model, @RequestParam("nombreModelo") String nombre){
        List<Modelo> modelos = servicesModelo.listarModelos();
        List<Modelo> modelosBuscados = new ArrayList<Modelo>();
        
        for(Modelo m : modelos){
            if (m.getNombre().toUpperCase().contains(nombre.toUpperCase())){
                modelosBuscados.add(m); 
            }
        }
       

        if (modelosBuscados.isEmpty()) {
            model.addAttribute("mensaje", "No se encontraron coincidencias.");
        } else {
            model.addAttribute("mensaje", null);
        }

        model.addAttribute("modelos", modelosBuscados);

        return "buscarModelo"; 
    }

}
