package com.example.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "Aistamientos")
public class Avistamiento {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idAvistamiento; 
    private Long idGato; 
    private Long idUser; 
    private Long idFoto; 
    private LocalDate fecha; 

    public Avistamiento() {
    }

    public Avistamiento(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Avistamiento(LocalDate fecha, Long idAvistamiento, Long idFoto, Long idGato, Long idUser) {
        this.fecha = fecha;
        this.idAvistamiento = idAvistamiento;
        this.idFoto = idFoto;
        this.idGato = idGato;
        this.idUser = idUser;
    }

    public Long getIdAvistamiento() {
        return idAvistamiento;
    }

    public void setIdAvistamiento(Long idAvistamiento) {
        this.idAvistamiento = idAvistamiento;
    }

    public Long getIdGato() {
        return idGato;
    }

    public void setIdGato(Long idGato) {
        this.idGato = idGato;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Long idFoto) {
        this.idFoto = idFoto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Avistamiento{");
        sb.append("idAvistamiento=").append(idAvistamiento);
        sb.append(", idGato=").append(idGato);
        sb.append(", idUser=").append(idUser);
        sb.append(", idFoto=").append(idFoto);
        sb.append(", fecha=").append(fecha);
        sb.append('}');
        return sb.toString();
    }
    
    
}
