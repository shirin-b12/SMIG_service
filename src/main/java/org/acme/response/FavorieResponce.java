package org.acme.response;

import org.acme.model.Favoris;

public class FavorieResponce {
    private int id_favori;
    private int id_ressource;
    private String date_de_creation;

    public FavorieResponce(Favoris favoris) {
        id_favori = favoris.getId_favori();
        id_ressource = favoris.getId_ressource().getId_ressource();
        date_de_creation = favoris.getDate_de_creation().toString();

    }

    public int getId_favori() {
        return id_favori;
    }

    public void setId_favori(int id_favori) {
        this.id_favori = id_favori;
    }

    public int getId_ressource() {
        return id_ressource;
    }

    public void setId_ressource(int id_ressource) {
        this.id_ressource = id_ressource;
    }

    public String getDate_de_creation() {
        return date_de_creation;
    }

    public void setDate_de_creation(String date_de_creation) {
        this.date_de_creation = date_de_creation;
    }
}
