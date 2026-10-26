package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "USers")
public class User {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idUser; 
    private Long nombre; 
    private Long apellido; 
    private Long email; 
    private Long celular; 
    private Long password; 

    public User() {
    }

    public User(Long apellido, Long celular, Long email, Long nombre, Long password) {
        this.apellido = apellido;
        this.celular = celular;
        this.email = email;
        this.nombre = nombre;
        this.password = password;
    }

    public User(Long apellido, Long celular, Long email, Long idUser, Long nombre, Long password) {
        this.apellido = apellido;
        this.celular = celular;
        this.email = email;
        this.idUser = idUser;
        this.nombre = nombre;
        this.password = password;
    }

    


}
