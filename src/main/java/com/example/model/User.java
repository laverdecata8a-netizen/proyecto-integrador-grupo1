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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getNombre() {
        return nombre;
    }

    public void setNombre(Long nombre) {
        this.nombre = nombre;
    }

    public Long getApellido() {
        return apellido;
    }

    public void setApellido(Long apellido) {
        this.apellido = apellido;
    }

    public Long getEmail() {
        return email;
    }

    public void setEmail(Long email) {
        this.email = email;
    }

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public Long getPassword() {
        return password;
    }

    public void setPassword(Long password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [idUser=" + idUser + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email
                + ", celular=" + celular + ", password=" + password + "]";
    }

}
