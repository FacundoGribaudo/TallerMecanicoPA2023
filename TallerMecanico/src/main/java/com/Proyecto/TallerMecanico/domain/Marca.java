package com.Proyecto.TallerMecanico.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data //Facilita los gettes y setter
@Entity //Indico que es una entidad para una DB
@Table(name = "marca") //Indico en que tabla se alamacenaran los datos de esta entidad


public class Marca {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_marca;
    private String nombre;
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
