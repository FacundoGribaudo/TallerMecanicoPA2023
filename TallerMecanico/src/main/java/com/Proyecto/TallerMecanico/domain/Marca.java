package com.Proyecto.TallerMecanico.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data //Facilita los gettes y setter
@Entity //Indico que es una entidad para una DB
@Table(name = "marca") //Indico en que tabla se alamacenaran los datos de esta entidad


public class Marca {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_marca")
    private Integer id_marca;
    
    @Column(name="nombre")
    private String nombre;

    @Column(name="estado")
    private String estado;

    public Marca(){
        
    }
    
    public Marca(String nombre, String estado){
       super(); 
        //this.id_marca = id;
        this.nombre = nombre;
        this.estado = estado;
    }
    

}
