package org.acme.response;

import org.acme.model.Images;

import java.awt.Image;

public class RessourcesResponce {
    private int id;
    private String titre;
    private String description;
    private int visibilite;
    private String dateDeCreation;
    private UtilisateurResponce createur;
    private String nomCategorie;
    private String nomType;
    private String nomTag;
    private Images image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UtilisateurResponce getCreateur() {
        return createur;
    }

    public void setCreateur(UtilisateurResponce createur) {
        this.createur = createur;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    public String getNomTag() {
        return nomTag;
    }

    public void setNomTag(String nomTag) {
        this.nomTag = nomTag;
    }

    public Images getImage() {
        return image;
    }
    public void setImage(Images image) {
        this.image = image;
    }
}
