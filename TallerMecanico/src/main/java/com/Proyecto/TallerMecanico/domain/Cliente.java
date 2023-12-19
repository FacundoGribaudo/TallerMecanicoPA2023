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

    @Column(name = "nroLicencia")
    private String nroLicencia;

    @Column(name = "vtoLicencia")
    private String vtoLicencia;


    public Cliente(){

    }

    public Cliente(String nombre, String apellido, BigInteger dni, BigInteger tel, String loc, String nroLic, String vtoLic){
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni; 
        this.telefono = tel;
        this.localidad = loc; 
        this.nroLicencia = nroLic;
        this.vtoLicencia = vtoLic;
    }



}
