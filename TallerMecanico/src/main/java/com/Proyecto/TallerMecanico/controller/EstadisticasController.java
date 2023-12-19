package com.Proyecto.TallerMecanico.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Proyecto.TallerMecanico.domain.OrdenTrabajo;
import com.Proyecto.TallerMecanico.domain.ServiciosTaller;
import com.Proyecto.TallerMecanico.interfaceServices.IordenTrabajoService;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {
    
    @Autowired
    private IordenTrabajoService serviceOT;

    @GetMapping("/datosot")
    public List<OrdenTrabajo> getDataOrdenes(){
        return serviceOT.listarOrdenTrabajo();
    }

    @GetMapping("/datosServicios")
    public List<OrdenTrabajo> getDataServicios(){
        return serviceOT.listarOrdenTrabajo();
    }
}
