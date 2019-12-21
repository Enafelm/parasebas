package com.example.vamosjuntos;

public class Evento {
    private String nomEvent;
    private String direccio;
    private String fecha;
    private String hora;
    private String descripcio;

    public Evento(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public String getDireccio() {
        return direccio;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
}
