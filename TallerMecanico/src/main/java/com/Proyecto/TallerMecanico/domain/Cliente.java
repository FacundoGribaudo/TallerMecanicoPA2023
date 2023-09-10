package com.Proyecto.TallerMecanico.domain;

import java.math.BigInteger;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente")

public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer id_cliente;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "dni")
    private BigInteger dni;

    @Column(name = "telefono")
    private BigInteger telefono;

    @Column(name = "localidad")
    private String localidad;


    public Cliente(){

    }

    public Cliente(String nombre, String apellido, BigInteger dni, BigInteger tel, String loc){
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni; 
        this.telefono = tel;
        this.localidad = loc; 
    }



}
