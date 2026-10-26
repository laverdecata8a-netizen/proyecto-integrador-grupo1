package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "fotos")
public class Foto {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long idFoto;
    private long idGato;
    private String url;
    private String fechaFoto;
    private boolean esPrincipal;
    private String descripcion;

    
    public Foto() {
    }

    public Foto(String url, String fechaFoto, boolean esPrincipal, String descripcion) {
        this.url = url;
        this.fechaFoto = fechaFoto;
        this.esPrincipal = esPrincipal;
        this.descripcion = descripcion;
    }
    public Foto(long idFoto, long idGato, String url, String fechaFoto, boolean esPrincipal, String descripcion) {
        this.idFoto = idFoto;
        this.idGato = idGato;
        this.url = url;
        this.fechaFoto = fechaFoto;
        this.esPrincipal = esPrincipal;
        this.descripcion = descripcion;
    }
    public long getIdFoto() {
        return idFoto;
    }
    public void setIdFoto(long idFoto) {
        this.idFoto = idFoto;
    }
    public long getIdGato() {
        return idGato;
    }
    public void setIdGato(long idGato) {
        this.idGato = idGato;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getFechaFoto() {
        return fechaFoto;
    }
    public void setFechaFoto(String fechaFoto) {
        this.fechaFoto = fechaFoto;
    }
    public boolean isEsPrincipal() {
        return esPrincipal;
    }
    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    @Override
    public String toString() {
        return "Foto [idFoto=" + idFoto + ", idGato=" + idGato + ", url=" + url + ", fechaFoto=" + fechaFoto
                + ", esPrincipal=" + esPrincipal + ", descripcion=" + descripcion + "]";
    }

}
