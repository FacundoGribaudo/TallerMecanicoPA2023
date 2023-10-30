package com.Proyecto.TallerMecanico.domain;

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

    @OneToMany (mappedBy = "serviciosRealizar")
    private List<OrdenTrabajo> ordenesTrabajo;

    public ServiciosTaller(){

    }

    public ServiciosTaller(String nombre, String descripcion, List<OrdenTrabajo> ordenesTrabajo){
        super();
        this.nombre = nombre;
        this.descripcion = descripcion; 
        this.ordenesTrabajo = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ServiciosTaller [id_servicio=" + id_servicio + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
    }

}
