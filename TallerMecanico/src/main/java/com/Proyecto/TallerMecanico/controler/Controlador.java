package com.Proyecto.TallerMecanico.controler;

import org.springframework.ui.Model;
import com.Proyecto.TallerMecanico.domain.Marca;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcaServices;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;



@Controller 
@RequestMapping 
public class Controlador {
    
    @Autowired
    private ImarcaServices service; 
    
    @GetMapping("/")
    public String home(Model model){
        return "index";  //Renderizamos el template 
    }
    
    
    // ------------- ABM MARCA -------------
    @GetMapping("/marca")
    public String listar(Model model){
        List<Marca> marcas = service.listarMarcas();
        //System.out.println(marcas);
        model.addAttribute("marcas", marcas); 
        return "marca";
    }
    
    @PostMapping("/save")
    public String saveMarca(Marca m, Model model){
        service.save(m); 
        return "redirect:/marca";
    }
    
    @GetMapping("/editarMarca/{id_marca}")
    public String editarMarca(@PathVariable int id_marca, Model model){
        Optional<Marca> optionalMarca = service.listarIdMarca(id_marca);

        // Verifica si la marca existe antes de agregarla al modelo
        if (optionalMarca.isPresent()) {
            Marca marca = optionalMarca.get();
            model.addAttribute("marca", marca);
        } else {
            // Manejar el caso en el que la marca no existe (puedes redirigir o mostrar un mensaje de error)
        }

        return "editarMarca"; 
    }
    
    @GetMapping("/eliminarMarca/{id_marca}")
    public String eliminarMarca(Model model, @PathVariable int id_marca){
        service.delete(id_marca);
        return "redirect:/marca"; 
    }
    
    @PostMapping("/buscar")
    public String buscarMarca(Model model, String nombre){
        
        List<Marca> marcas = service.listarMarcas(); //Traigo todos los objetos de la db
        List<Marca> marcasBuscadas = new ArrayList<Marca>(); //Segundo array que me almacena los objetos que coinciden
        
        for (int i = 0; i<marcas.size(); i++){
            if(marcas.get(i).getNombre().equals(nombre)){
                System.out.println("ENTRO PORQUE EL NOMBRE COINCIDE");
                marcasBuscadas.add(marcas.get(i));
            }
        }
        model.addAttribute("marcas", marcasBuscadas); 
        return "marca"; 
    }
}
