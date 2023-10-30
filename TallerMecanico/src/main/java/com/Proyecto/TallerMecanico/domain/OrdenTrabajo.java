package com.Proyecto.TallerMecanico.domain;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.persistence.*; 
import lombok.Data;

@Data
@Entity
@Table(name="orden_trabajo")
public class OrdenTrabajo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="nro_orden")
    private int nro_orden; //Hace de id

    @Column(name="fecha_hora_creacion_orden")
    private LocalDateTime fechaHoraCreacionOrden = LocalDateTime.now();

    // Campo adicional para almacenar la fecha y hora formateada
    private String fechaHoraCreacionFormateada;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculoPertenece;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "orden_trabajo_servicio",
    joinColumns = @JoinColumn(name = "nro_orden"),
    inverseJoinColumns = @JoinColumn(name = "id_servicio"))
    private List<ServiciosTaller> serviciosRealizar;  

    public OrdenTrabajo(){}

    public void setFechaHoraCreacionFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        this.fechaHoraCreacionFormateada = fechaHoraCreacionOrden.format(formatter);
    }


}
