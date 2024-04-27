package org.acme.controller;

public class TopCreateur {
    String createur;
    int nombre;

    public TopCreateur(String createur, int nombre) {
        this.createur = createur;
        this.nombre = nombre;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
}
