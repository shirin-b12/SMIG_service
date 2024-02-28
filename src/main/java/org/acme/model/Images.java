package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

public class Images extends PanacheEntityBase {

    private int id_image;

    public int getId_image() {

        return id_image;
    }

    public void setId_image(int id_image) {
        this.id_image = id_image;
    }


    private String fichier;

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    private String legende;

    public String getLegende() {
        return legende;
    }

    public void setLegende(String legende) {
        this.legende = legende;
    }
}
