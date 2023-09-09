package com.Proyecto.TallerMecanico.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data //Facilita los gettes y setter
@Entity //Indico que es una entidad para una DB
@Table(name = "vehiculo") //Indico en que tabla se alamacenaran los datos de esta entidad


public class Vehiculo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_vehiculo")
    private Integer id_vehiculo;
    
    @Column(name="patente")
    private String patente;
    
    @Column(name="kilometros")
    private Float kilometros;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_marca")
    private Marca marca; 

    public Vehiculo(){

    }

    public Vehiculo(String patente, Float kilometros){
        super(); 
        this.patente = patente;
        this.kilometros = kilometros; 
     }


}
