package org.acme.response;

public class TopRessourceFavoris {
    private String ressource;
    private int nombre;

    public String getRessource() {
        return ressource;
    }

    public void setRessource(String ressource) {
        this.ressource = ressource;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public TopRessourceFavoris(String ressource, int nombre) {
        this.ressource = ressource;
        this.nombre = nombre;
    }
}
