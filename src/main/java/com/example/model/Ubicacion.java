package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity 
@Table(name = "Ubicaciones")
public class Ubicacion {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idUbicacion ; 
    private String direccion; 
    private String barrio ; 
    private String ciudad ; 
}
