package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "Users")
public class User {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idUser; 
    private String nombre; 
    private String apellido; 
    private String email; 
    private String celular; 
    private String password; 

    public User() {
    }

    public User(String nombre, String apellido, String email, String celular, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.password = password;
    }

    public User(Long idUser, String nombre, String apellido, String email, String celular, String password) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.password = password;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [idUser=" + idUser + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email
                + ", celular=" + celular + ", password=" + password + "]";
    }

}
