package com.Proyecto.TallerMecanico.domain;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "direccion")
    private String direccion; 

    @Column(name = "estado")
    private String estado; 

    public Tecnico(){}

    public Tecnico(String nombre, String apellido, BigInteger tel, String direccion, String estado){
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = tel;
        this.direccion = direccion;
        this.estado = estado; 
    }

}
