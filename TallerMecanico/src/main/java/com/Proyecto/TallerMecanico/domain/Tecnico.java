package com.Proyecto.TallerMecanico.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tecnico")
public class Tecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tecnico")
    private Integer id_tecnico;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "telefono")
    private BigInteger telefono;

    @Column(name = "legajo")
    private String legajo; 

    @Column(name = "estado")
    private String estado; 

    @OneToMany (mappedBy = "tecnicosOrden")
    private List<OrdenTrabajo> ordenesTrabajo;

    public Tecnico(){}

    public Tecnico(String nombre, String apellido, BigInteger tel, String leg, String estado, List<OrdenTrabajo> ordenesTrabajo){
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = tel;
        this.legajo = leg;
        this.estado = estado; 
        this.ordenesTrabajo = new ArrayList<>();
    }

}
