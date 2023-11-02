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

import com.Proyecto.TallerMecanico.domain.Marca;
import com.Proyecto.TallerMecanico.domain.Modelo;
import com.Proyecto.TallerMecanico.domain.Vehiculo;
import com.Proyecto.TallerMecanico.interfaceServices.IModeloServices;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcaServices;
import com.Proyecto.TallerMecanico.interfaceServices.IvehiculoServices;

@Controller
@RequestMapping
public class MarcaController {

    @Autowired
    private ImarcaServices servicesMarca;
    @Autowired
    private IvehiculoServices servicesVehiculo;
    @Autowired
    private IModeloServices servicesModelo;

    @GetMapping("/marca")
    public String listar(Model model){
        List<Marca> marcas = servicesMarca.listarMarcas();
        model.addAttribute("marcas", marcas); 
        return "marca";
    }
    
    @PostMapping("/save")
    public String saveMarca(Marca m){
        List<Marca> marcas = servicesMarca.listarMarcas();

        for (Marca marcaExistente : marcas) {
            if (!marcaExistente.getId_marca().equals(m.getId_marca()) && marcaExistente.getNombre().equalsIgnoreCase(m.getNombre())) {
                //Si ya existe la Marca, redirige con mensaje de Marca repetida
                return "redirect:/marca?mensaje=marcaRepetida";
            }
        }

        servicesMarca.save(m);
        return "redirect:/marca";
    }

    @PostMapping("/saveMarcaSelect")
    public String saveMarcaSelect(Marca m){
        List<Marca> marcas = servicesMarca.listarMarcas();

        for (Marca marcaExistente : marcas) {
            if (!marcaExistente.getId_marca().equals(m.getId_marca()) && marcaExistente.getNombre().equalsIgnoreCase(m.getNombre())) {
                //Si ya existe la Marca, redirige con mensaje de Marca repetida
                return "redirect:/vehiculos?mensaje=marcaRepetida";
            }
        }

        servicesMarca.save(m);
        return "redirect:/vehiculos";
    }
    
    @GetMapping("/editarMarca/{id_marca}")
    public String editarMarca(@PathVariable int id_marca, Model model){
        Optional<Marca> optionalMarca = servicesMarca.listarIdMarca(id_marca);

        // Verifica si la marca existe antes de agregarla al modelo
        if (optionalMarca.isPresent()) {
            Marca marca = optionalMarca.get();
            model.addAttribute("marca", marca);
        } else {
        }

        return "editarMarca"; 
    }
    
    @GetMapping("/eliminarMarca/{id_marca}")
    public String eliminarMarca(Model model, @PathVariable int id_marca) {
        List<Modelo> modelos = servicesModelo.listarModelos();
        List<Vehiculo> vehiculos = servicesVehiculo.listarVehiculos();
    
        Boolean eliminarMarca = true;
    
        // Verificar si la marca tiene modelos asociados
        for (Modelo m : modelos) {
            if (m.getMarca().getId_marca().equals(id_marca)) {
                eliminarMarca = true;
                break;  // La marca está asociada a modelos
            }
        }
    
        // Verificar si la marca está asociada solo a modelos y no a vehículos
        if (eliminarMarca) {
            for (Vehiculo v : vehiculos) {
                if (v.getMarca().getId_marca().equals(id_marca)) {
                    eliminarMarca = false;
                    break;  // La marca está asociada a vehículos. NO SE PUEDE ELIMINAR
                }
            }
        }
    
        // Si la marca está asociada solo a modelos, eliminarla y sus modelos
        if (eliminarMarca) {
            System.out.println("Se puede eliminar, no está asociada a vehículos");
            servicesMarca.delete(id_marca);
    
            // Eliminar modelos asociados a la marca
            for (Modelo m : modelos) {
                if (m.getMarca().getId_marca().equals(id_marca)) {
                    servicesModelo.delete(m.getId_modelo());
                }
            }
        } else {
            System.out.println("No se puede eliminar la marca o está asociada a vehículos");
        }
    
        // Antes de la redirección
        String mensaje = eliminarMarca ? "eliminar" : "noEliminar";
        String urlRedireccion = "redirect:/marca?mensaje=" + mensaje;
        return urlRedireccion;
    }
     
    @PostMapping("/buscar")
    public String buscarMarca(Model model, String nombre){
        
        List<Marca> marcas = servicesMarca.listarMarcas(); //Traigo todos los objetos de la db
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
        return "buscarMarca"; 
    }

}
