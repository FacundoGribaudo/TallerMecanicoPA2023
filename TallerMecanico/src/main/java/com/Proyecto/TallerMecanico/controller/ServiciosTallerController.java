package com.Proyecto.TallerMecanico.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Proyecto.TallerMecanico.domain.ServiciosTaller;
import com.Proyecto.TallerMecanico.interfaceServices.IserviciosTallerService;

import org.springframework.ui.Model;

@Controller
@RequestMapping
public class ServiciosTallerController {

    @Autowired
    private IserviciosTallerService SerTallerService;

    @GetMapping("/serviciosTaller")
    public String listarServicios(Model model) {
        List<ServiciosTaller> listaServicios = SerTallerService.listarServicios();
        model.addAttribute("lista_servicios", listaServicios);
        return "serviciosTaller";

    }

    @PostMapping("/registrarServicio")
    public String registrarServicio(ServiciosTaller s) {
        List<ServiciosTaller> servicios = SerTallerService.listarServicios();

        for (ServiciosTaller servicioExistente : servicios) {
            if (!servicioExistente.getId_servicio().equals(s.getId_servicio())
                    && servicioExistente.getNombre().equals(s.getNombre())) {
                // Si ya existe un servicio con el mismo nombre y un ID diferente, redirige con
                // un mensaje de servicio repetido
                return "redirect:/serviciosTaller?mensaje=servicioRepetido";
            }
        }

        SerTallerService.save(s);
        return "redirect:/serviciosTaller";
    }

    @GetMapping("/eliminarServicio/{id_servicio}")
    public String eliminarServicioTaller(Model model, @PathVariable int id_servicio) {
        SerTallerService.delete(id_servicio);
        return "redirect:/serviciosTaller";
    }

    @GetMapping("/editarServicio/{id_servicio}")
    public String editarServicioTaller(@PathVariable int id_servicio, Model model) {
        Optional<ServiciosTaller> optionalServicio = SerTallerService.listarIdServicios(id_servicio);

        if (optionalServicio.isPresent()) {
            ServiciosTaller servicioTaller = optionalServicio.get();
            model.addAttribute("servicio_taller", servicioTaller);
        } else {

        }
        return "editarServicios";
    }

    @PostMapping("/buscarServicio")
    public String buscarServicio(Model model, @RequestParam("nombreServicio") String nombre_servicio) {
        List<ServiciosTaller> listaServicios = SerTallerService.listarServicios();
        List<ServiciosTaller> listaServiciosBuscados = new ArrayList<ServiciosTaller>();

        for (ServiciosTaller s : listaServicios) {
            if (s.getNombre().toUpperCase().equals(nombre_servicio)) {
                listaServiciosBuscados.add(s);
            }
        }

        if (listaServiciosBuscados.size() == 0) {
            listaServiciosBuscados = listaServicios;
        }

        model.addAttribute("lista_servicios", listaServiciosBuscados);
        return "buscarServicio";

    }

}
