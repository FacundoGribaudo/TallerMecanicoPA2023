package com.Proyecto.TallerMecanico.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Proyecto.TallerMecanico.domain.Marca;
import com.Proyecto.TallerMecanico.domain.MarcasEliminadas;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcaServices;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcasEliminadasServicios;

@Controller
@RequestMapping
public class RegistrosEliminadosController {

    @Autowired
    private ImarcasEliminadasServicios servicesMarcaEliminada; 
    @Autowired
    private ImarcaServices servicesMarca; 

    @GetMapping("/registrosEliminados")
    public String listarMarcasEliminadas(Model model){
        
        List<MarcasEliminadas> marcasEliminadas = servicesMarcaEliminada.listarMarcasEliminadas();
        
        //System.out.println(marcasEliminadas);

        model.addAttribute("marcasEliminadas", marcasEliminadas); 
        return "registrosEliminados"; 
    }

    @GetMapping("/recuperarRegistro/{id_marcaRecuperar}")
    public String recuperarRegistro(@PathVariable int id_marcaRecuperar){
        List<MarcasEliminadas> marcasEliminadas = servicesMarcaEliminada.listarMarcasEliminadas();

        for (MarcasEliminadas me : marcasEliminadas){
            if (me.getId_marcaEliminada().equals(id_marcaRecuperar)){

                //Creo el objeto "Marca" con los mismos atributos y lo persisto
                Marca marca_restablecer = new Marca(me.getNombre(), me.getEstado());
                servicesMarca.save(marca_restablecer);
            }
        }

        servicesMarcaEliminada.deleteMarcaEliminada(id_marcaRecuperar);
        return "redirect:/registrosEliminados"; 
    }

    @GetMapping("/vaciarHistorialMarca")
    public String vaciarHistorialMarca(){
        List<MarcasEliminadas> marcasLimpiar = servicesMarcaEliminada.listarMarcasEliminadas();

        for (MarcasEliminadas me: marcasLimpiar){
            servicesMarcaEliminada.deleteMarcaEliminada(me.getId_marcaEliminada());
        }
        
        return "redirect:/registrosEliminados"; 
    }

    @PostMapping("/registrosEliminados/buscar")
    public String buscarRegistros(Model model, @RequestParam("nombreMarca") String nombreMarca, 
    @RequestParam("fechaDesdeBuscar") String fechaDesdeBuscar,
    @RequestParam("fechaHastaBuscar") String fechaHastaBuscar
    ){
        
        List<MarcasEliminadas> marcasEliminadas = servicesMarcaEliminada.listarMarcasEliminadas();
        List<MarcasEliminadas> marcasEliminadasBuscadas = new ArrayList<>(); 

        //Formateo la fecha para que sea "dd-MM-yyyy"
        if (fechaDesdeBuscar != "" && fechaHastaBuscar != ""){
            String fechaDesdeFormat = LocalDate.parse(fechaDesdeBuscar).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));;
            String fechaHastaFormat = LocalDate.parse(fechaHastaBuscar).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));;

            //Alternativa ambos filtros usados

            if (nombreMarca != "" && fechaDesdeFormat != "" && fechaHastaFormat != ""){
                for (MarcasEliminadas me: marcasEliminadas){
                    if (me.getNombre().equals(nombreMarca) && me.getFecha_eliminado().compareTo(fechaDesdeFormat) >= 0 && me.getFecha_eliminado().compareTo(fechaHastaFormat) <= 0){
                        marcasEliminadasBuscadas.add(me);
                        //System.out.println(""+"BUSCADO:: " + me);
                    }
                }
            }
        } else if (nombreMarca != "" && fechaDesdeBuscar == "" && fechaHastaBuscar == ""){ //Alternativa solo filtro nombre
            for (MarcasEliminadas me: marcasEliminadas){
                if (me.getNombre().equals(nombreMarca)){
                    marcasEliminadasBuscadas.add(me);
                    //System.out.println(""+"NOMBRE BUSCADO: " + me);
                }
            }
        } if (nombreMarca == "" || nombreMarca == " " && fechaDesdeBuscar != "" && fechaHastaBuscar != ""){ //Alternativa solo filtro fecha
            String fechaDesdeFormat = LocalDate.parse(fechaDesdeBuscar).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));;
            String fechaHastaFormat = LocalDate.parse(fechaHastaBuscar).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));;

            for (MarcasEliminadas me: marcasEliminadas){
                if (me.getFecha_eliminado().compareTo(fechaDesdeFormat) >= 0 && me.getFecha_eliminado().compareTo(fechaHastaFormat) <= 0){
                    marcasEliminadasBuscadas.add(me);
                }
            }
        }


        if (marcasEliminadasBuscadas.isEmpty()) {
            model.addAttribute("mensaje", "No se encontraron coincidencias.");
        } else {
            model.addAttribute("mensaje", null);
        }

        model.addAttribute("marcasEliminadasBuscadas", marcasEliminadasBuscadas);
        return "buscarRegistrosEliminados";
    }




}
