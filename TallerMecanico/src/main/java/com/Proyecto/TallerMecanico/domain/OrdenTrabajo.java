package com.Proyecto.TallerMecanico.domain;


import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    //Fecha en que se hizo la orden de trabajo
    @Column(name="fecha_orden")
    private LocalDateTime fechaHoraOrden;
    // Campo adicional para almacenar la fecha y hora de la orden formateada
    private String fechaHoraOrdenFormateada;

    //Fecha en que se registro la orden de trabajo
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "orden_trabajo_tecnico",
    joinColumns = @JoinColumn(name = "nro_orden"),
    inverseJoinColumns = @JoinColumn(name = "id_tecnico"))
    private List<Tecnico> tecnicosOrden;  

    // Mapa para almacenar la relación servicio-horas
    @ElementCollection
    @CollectionTable(name = "orden_trabajo_horas", joinColumns = @JoinColumn(name = "nro_orden"))
    @MapKeyColumn(name = "id_servicio")
    @Column(name = "horas")
    private Map<Integer, Integer> horasPorServicio = new HashMap<>();

    // Mapa para almacenar la relación servicio-minutos
    @ElementCollection
    @CollectionTable(name = "orden_trabajo_minutos", joinColumns = @JoinColumn(name = "nro_orden"))
    @MapKeyColumn(name = "id_servicio")
    @Column(name = "minutos")
    private Map<Integer, Integer> minutosPorServicio = new HashMap<>();

    @Column(name="porcentaje_impuesto_agregado")
    private BigDecimal porcentajeImpuestoAgregado;

    @Column(name="porcentaje_descuento_agregado")
    private BigDecimal porcentajeDescuentoAgregado;

    @Column(name="porcentaje_aumento_agregado")
    private BigDecimal porcentajeAumentoAgregado;

    @Column(name = "observaciones", length = 1000)
    private String observaciones;

    public OrdenTrabajo(){}

    public void setFechaHoraFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        this.fechaHoraCreacionFormateada = fechaHoraCreacionOrden.format(formatter);
        this.fechaHoraOrdenFormateada = fechaHoraOrden.format(formatter);
    }

    // Método para agregar horas y minutos para un servicio específico
    public void agregarHorasYMinutosParaServicio(Integer id_servicio, int horas, int minutos) {
        // Verificar que id_servicio no sea nulo antes de agregar horas y minutos
        if (id_servicio != null) {
            horasPorServicio.put(id_servicio, horas);
            minutosPorServicio.put(id_servicio, minutos);
        } else {
            // Manejar el caso donde id_servicio es nulo
            throw new IllegalArgumentException("El id del servicio no puede ser nulo");
        }
    }
}
