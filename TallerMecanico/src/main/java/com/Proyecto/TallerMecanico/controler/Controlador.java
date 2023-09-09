package com.Proyecto.TallerMecanico.controler;

import org.springframework.ui.Model;
import com.Proyecto.TallerMecanico.domain.Marca;
import com.Proyecto.TallerMecanico.domain.Modelo;
import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.interfaceServices.IModeloServices;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcaServices;
import com.Proyecto.TallerMecanico.interfaceServices.IvehiculoServices;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestParam;




@Controller 
@RequestMapping 
public class Controlador {
    
    @Autowired
    private ImarcaServices service; 
    @Autowired
    private IvehiculoServices servicesVehiculo;
    @Autowired
    private IModeloServices servicesModelo;
    
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
            if(marcas.get(i).getNombre().toUpperCase().equals(nombre.toUpperCase())){
                System.out.println("ENTRO PORQUE EL NOMBRE COINCIDE");
                marcasBuscadas.add(marcas.get(i));
            }
        }

        if(marcasBuscadas.size() == 0){
            marcasBuscadas = marcas; 
        }

        model.addAttribute("marcas", marcasBuscadas); 
        return "marca"; 
    }


    // ------------- ABM VEHICULO -------------
    @GetMapping("/vehiculos")
    public String listarVehiculos(Model model){
        
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();
        
        System.out.println("VEHICULOS GUARDADOS");
        System.out.println(vehiculos);

        //Marcas 
        List<Marca> marcas = service.listarMarcas();
        List<Marca> marcasActivas = new ArrayList<Marca>(); //Segundo array que me almacena los objetos que coinciden

        for(int i=0; i<marcas.size(); i++){
            if(marcas.get(i).getEstado().toUpperCase().equals("activo".toUpperCase())){
                marcasActivas.add(marcas.get(i));
            }
        }

        

        model.addAttribute("vehiculo", vehiculos); 
        model.addAttribute("marcasActivas", marcasActivas); 
        return "vehiculos";
    }

    @PostMapping("/saveVehiculo")
    public String guardarVehiculo(Vehiculo v, Model model){
        servicesVehiculo.save(v);
        return "redirect:/vehiculos"; 
    }  

    @GetMapping("/eliminarVehiculo/{id_vehiculo}")
    public String delete(Vehiculo vehiculos,@PathVariable int id_vehiculo){
        servicesVehiculo.delete(id_vehiculo);
        return "redirect:/vehiculos"; 
    }



    // ------------- ABM MODELO -------------
    // Cambia el nombre del atributo para los modelos en el modelo
    @GetMapping("/modelo")
    public String listarModelos(Model model){
        List<Modelo> modelos = servicesModelo.listarModelos(); // Obtén la lista de modelos
        model.addAttribute("modelos", modelos); // Usa "modelos" como el nombre del atributo en el modelo

        //Marcas 
        List<Marca> marcas = service.listarMarcas();
        List<Marca> marcasActivas = new ArrayList<Marca>(); //Segundo array que me almacena los objetos que coinciden

        for(int i=0; i<marcas.size(); i++){
            if(marcas.get(i).getEstado().toUpperCase().equals("activo".toUpperCase())){
                marcasActivas.add(marcas.get(i));
            }
        }

        model.addAttribute("marcasActivas", marcasActivas); 

        return "modelo"; // Retorna la vista "modelo" para mostrar los modelos
    }

    @PostMapping("/saveModelo")
    public String agregarModelo(Modelo mo, Model model) {
        servicesModelo.save(mo); 
        // Redirige a la página de modelos para mostrar la lista actualizada
        return "redirect:/modelo";
    }
 
    @GetMapping("/eliminarModelo/{id_modelo}")
    public String delete(Modelo modelo,@PathVariable int id_modelo){
        servicesModelo.delete(id_modelo);
        return "redirect:/modelo"; 
    }

    @GetMapping("/editarModelo/{id_modelo}")
    public String editarModelo(@PathVariable int id_modelo, Model model){
        Optional<Modelo> optionalModelo = servicesModelo.listarIdModelo(id_modelo);

        //Marcas 
        List<Marca> marcas = service.listarMarcas();
        List<Marca> marcasActivas = new ArrayList<Marca>(); //Segundo array que me almacena los objetos que coinciden

        for(int i=0; i<marcas.size(); i++){
            if(marcas.get(i).getEstado().toUpperCase().equals("activo".toUpperCase())){
                marcasActivas.add(marcas.get(i));
            }
        }

        model.addAttribute("marcasActivas", marcasActivas); 

        // Verifica si la marca existe antes de agregarla al modelo
        if (optionalModelo.isPresent()) {
            Modelo modelo = optionalModelo.get();
            model.addAttribute("modelo", modelo);
        } else {
            // Manejar el caso en el que la marca no existe (puedes redirigir o mostrar un mensaje de error)
        }

        return "editarModelo"; 
    }
}




