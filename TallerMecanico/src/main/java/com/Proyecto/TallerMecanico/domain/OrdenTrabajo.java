package com.Proyecto.TallerMecanico.domain;


import java.time.*;

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

    @Column(name="Fecha creacion Orden")
    private LocalDate fechaCreacionOrden = LocalDate.now();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculoPertenece;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_servicio")
    private ServiciosTaller servicioRealizar;  

    public OrdenTrabajo(){}

    


}
