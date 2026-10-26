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

    public Ubicacion() {
    }

    public Ubicacion(String direccion, String barrio, String ciudad) {
        this.direccion = direccion;
        this.barrio = barrio;
        this.ciudad = ciudad;
    }

    public Ubicacion(Long idUbicacion, String direccion, String barrio, String ciudad) {
        this.idUbicacion = idUbicacion;
        this.direccion = direccion;
        this.barrio = barrio;
        this.ciudad = ciudad;
    }

    public Long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Ubicacion [idUbicacion=" + idUbicacion + ", direccion=" + direccion + ", barrio=" + barrio + ", ciudad="
                + ciudad + "]";
    }

}
