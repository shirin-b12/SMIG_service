package org.acme.request;

import org.acme.model.Ressources;

public class RessourcesRequest {
    private int idCat;
    private int idType;
    private int idTag;
    private int idCreateur;
    private String titre;
    private String description;
    private Ressources.Visibilite visibilite;

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdTag() {
        return idTag;
    }

    public void setIdTag(int idTag) {
        this.idTag = idTag;
    }

    public int getIdCreateur() {
        return idCreateur;
    }

    public void setIdCreateur(int idCreateur) {
        this.idCreateur = idCreateur;
    }

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

    public Ressources.Visibilite getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(Ressources.Visibilite visibilite) {
        this.visibilite = visibilite;
    }
}
