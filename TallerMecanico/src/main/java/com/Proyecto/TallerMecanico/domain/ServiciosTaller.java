package com.Proyecto.TallerMecanico.domain;

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

    public ServiciosTaller(){

    }

    public ServiciosTaller(String nombre, String descripcion){
        super();
        this.nombre = nombre;
        this.descripcion = descripcion; 
    }

}
