package com.Proyecto.TallerMecanico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Proyecto.TallerMecanico.domain.MarcasEliminadas;
import com.Proyecto.TallerMecanico.interfaceServices.ImarcasEliminadasServicios;

@Controller
@RequestMapping
public class RegistrosEliminadosController {

    @Autowired
    private ImarcasEliminadasServicios servicesMarcaEliminada; 

    @GetMapping("/registrosEliminados")
    public String listarMarcasEliminadas(Model model){
        
        List<MarcasEliminadas> marcasEliminadas = servicesMarcaEliminada.listarMarcasEliminadas();
        
        System.out.println(marcasEliminadas);

        model.addAttribute("marcasEliminadas", marcasEliminadas); 
        return "registrosEliminados"; 
    }
}
