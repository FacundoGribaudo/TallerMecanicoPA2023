package com.Proyecto.TallerMecanico.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="serviciosTaller")
public class ServiciosTaller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_servicios")
    private Integer id_servicio;

    @Column (name="nombre")
    private String nombre;

    @Column (name = "descripcion")
    private String descripcion;

    @Column(name = "precio_hora", precision = 10, scale = 2)
    private BigDecimal precioHora;

    @Column(name = "porcentaje_descuentos", precision = 5, scale = 2)
    private BigDecimal porcentajeDescuentos;

    @Column(name = "porcentaje_impuestos", precision = 5, scale = 2)
    private BigDecimal porcentajeImpuestos;

    @OneToMany (mappedBy = "serviciosRealizar")
    private List<OrdenTrabajo> ordenesTrabajo;

    public ServiciosTaller(){

    }

    public ServiciosTaller(String nombre, String descripcion, BigDecimal precioHora, BigDecimal porcentajeDescuentos, BigDecimal porcentajeImpuestos, List<OrdenTrabajo> ordenesTrabajo){
        super();
        this.nombre = nombre;
        this.descripcion = descripcion; 
        this.precioHora = precioHora;
        this.porcentajeDescuentos = porcentajeDescuentos;
        this.porcentajeImpuestos = porcentajeImpuestos;
        this.ordenesTrabajo = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ServiciosTaller [id_servicio=" + id_servicio + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio="+ precioHora +", porcentajeDescuentos="+porcentajeDescuentos+", porcentajeImpuestos="+porcentajeImpuestos+"]";
    }

}
