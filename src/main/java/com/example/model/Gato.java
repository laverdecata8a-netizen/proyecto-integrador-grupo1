package com.example.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "Gatos")
public class Gato {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idGato; 
    private String nombre; 
    private String color; 
    private LocalDate fechaIngreso; 
    private String descripcion;

    public Gato() {
    }

    public Gato(String nombre, String color, LocalDate fechaIngreso, String descripcion) {
        this.nombre = nombre;
        this.color = color;
        this.fechaIngreso = fechaIngreso;
        this.descripcion = descripcion;
    }

    public Gato(Long idGato, String nombre, String color, LocalDate fechaIngreso, String descripcion) {
        this.idGato = idGato;
        this.nombre = nombre;
        this.color = color;
        this.fechaIngreso = fechaIngreso;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Gato [idGato=" + idGato + ", nombre=" + nombre + ", color=" + color + ", fechaIngreso=" + fechaIngreso
                + ", descripcion=" + descripcion + "]";
    } 

    
}
