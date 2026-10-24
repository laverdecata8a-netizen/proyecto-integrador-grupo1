package com.example.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // para que sea autogenerado por la BD
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String rol; // 'admin', 'usuario', etc.
    private String estado; // 'activo', 'inactivo', 'baneado'
    private LocalDateTime fechaRegistro;

    // ===================
    // Constructor completo
    // ===================
    public Usuario(Long id, String nombre, String apellido, String email, String telefono,
            String direccion, String rol, String estado, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    // ===================
    // Constructor sin ID
    // (para insertar nuevos usuarios sin ID)
    // ===================
    public Usuario(String nombre, String apellido, String email, String telefono,
            String direccion, String rol, String estado, LocalDateTime fechaRegistro) {
        this(null, nombre, apellido, email, telefono, direccion, rol, estado, fechaRegistro);
    }

    // ===================
    // Constructor vacío
    // ===================
    public Usuario() {
        this.fechaRegistro = LocalDateTime.now();
    }

    // ===================
    // Getters y setters
    // ===================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", rol=" + rol + "]";
    }
}
