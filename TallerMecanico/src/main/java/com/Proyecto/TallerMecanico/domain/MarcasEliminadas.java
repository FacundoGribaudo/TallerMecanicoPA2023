package com.Proyecto.TallerMecanico.domain;

import java.time.*; // Este paquete contiene LocalDate, LocalTime y LocalDateTime.
import java.time.format.*;

import jakarta.persistence.*;
import lombok.Data;


/*
 * ESTA VA A SER LA TABLA QUE ALMACENE LOS REGISTROS ELIMINADOS
 * CON LO CUAL TIENE QUE TENER EXACTAMENTE LOS MISMOS ATRIBUTOS QUE 
 * EL OBJETO A ALIMINAR YA QUE SE ESTARIA DUPLICANDO Y ALMACENANDO EN 
 * ESTA TABLA
 */

@Entity
@Data
@Table(name = "MarcasEliminadas")

public class MarcasEliminadas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_marcaEliminada")
    private Integer id_marcaEliminada;

    @Column(name="nombre")
    private String nombre;

    @Column(name="estado")
    private String estado;

    @Column(name="fecha_eliminado")
    private String fecha_eliminado = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    public MarcasEliminadas(){
        
    }
    
    public MarcasEliminadas(String nombre, String estado){
        super(); 
        //this.id_marca = id;
        this.nombre = nombre;
        this.estado = estado;
    }
    




}
