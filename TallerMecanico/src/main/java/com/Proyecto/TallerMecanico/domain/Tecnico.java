package com.Proyecto.TallerMecanico.domain;

import java.math.BigInteger;
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

    @Column(name = "direccion")
    private String direccion; 

    @Column(name = "estado")
    private String estado; 

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo; 
    
    // @ManyToMany(mappedBy = "tecnicos")
    // private List<Vehiculo> vehiculo; 


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
