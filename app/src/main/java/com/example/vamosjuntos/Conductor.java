package com.example.vamosjuntos;

public class Conductor {
    private String tipoVehiculo;
    private String espaciosLibres;
    private String nomConductor;
    private String localidad;
    private String descripcion;

    public Conductor(String nomConductor) {
        this.nomConductor = nomConductor;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getEspaciosLibres() {
        return espaciosLibres;
    }

    public void setEspaciosLibres(String espaciosLibres) {
        this.espaciosLibres = espaciosLibres;
    }

    public String getNomConductor() {
        return nomConductor;
    }

    public void setNomConductor(String nomConductor) {
        this.nomConductor = nomConductor;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
