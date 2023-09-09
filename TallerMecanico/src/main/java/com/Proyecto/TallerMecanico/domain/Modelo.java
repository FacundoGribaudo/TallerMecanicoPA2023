package com.Proyecto.TallerMecanico.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "modelo") // Nombre de la tabla de modelos en la base de datos
public class Modelo {

    public Modelo(String nombre, String descripcion, String marca) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo")
    private Integer id_modelo;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_marca")
    private Marca marca; 

    public Modelo() {

    }

    public Modelo(String nombre) {
        super();
        this.nombre = nombre;
    }
}
