package com.example.gestionclientes.entidades;

import java.io.Serializable;

public class Usuario  implements Serializable {
    int id_partner;
    private int nivel;

    public int getId_partner() {
        return id_partner;
    }

    public void setId_partner(int id_partner) {
        this.id_partner = id_partner;
    }

    private int visible;
    private String nombre;
    private String usuario;
    private String password;

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
