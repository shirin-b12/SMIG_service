package org.acme.response;

public class CategorieStatSurRessource {
    private String nom;
    private int nombre;
    public CategorieStatSurRessource(String nom, int nombre, int id) {
        this.nom = nom;
        this.nombre = nombre;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getNombre() {
        return nombre;
    }
    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
}
