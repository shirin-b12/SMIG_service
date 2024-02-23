package org.acme.request;

public class RessourcesResponce {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String titre;
    private String description;
    private int visibilite;
    private String dateDeCreation;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(int visibilite) {
        this.visibilite = visibilite;
    }

    public String getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(String dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }
}
