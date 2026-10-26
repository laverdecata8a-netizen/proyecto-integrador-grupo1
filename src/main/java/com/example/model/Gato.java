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
    private int idGato; 
    private String nombre; 
    private String color; 
    private LocalDate fechaIngreso; 
    private String descripcion; 

    
}
