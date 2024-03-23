package org.acme.request;

import java.time.LocalDateTime;

public class CommentaireRequest {
    private int idRessource;
    private int idCreateur;
    private String commentaire;
    private int idCommentaireRep;
    private LocalDateTime dateDeCreation;

    public int getIdCommentaireRep() {
        return idCommentaireRep;
    }

    public void setIdCommentaireRep(int idCommentaireRep) {
        this.idCommentaireRep = idCommentaireRep;
    }

    public LocalDateTime getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(LocalDateTime dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public int getIdRessource() {
        return idRessource;
    }

    public void setIdRessource(int idRessource) {
        this.idRessource = idRessource;
    }

    public int getIdCreateur() {
        return idCreateur;
    }

    public void setIdCreateur(int idCreateur) {
        this.idCreateur = idCreateur;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
