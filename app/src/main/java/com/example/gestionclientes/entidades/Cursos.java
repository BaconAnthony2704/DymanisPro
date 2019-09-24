package com.example.gestionclientes.entidades;

public class Cursos {
    private String codigo;
    private String nombre;
    private String duracion;
    private String facilitador;

    public Cursos() {

    }

    public Cursos(String codigo, String nombre, String duracion, String facilitador) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.duracion = duracion;
        this.facilitador = facilitador;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }
}
