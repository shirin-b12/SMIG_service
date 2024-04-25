package org.acme.response;

import java.time.LocalDateTime;

public class CommentaireResponce {

    private int id_commentaire;
    private String commentaire;
    private UtilisateurResponce createur;
    private LocalDateTime date_de_creation;
    private int ressource;
    private CommentaireResponce commentaire_rep;

    public int getId_commentaire() {
        return id_commentaire;
    }

    public void setId_commentaire(int id_commentaire) {
        this.id_commentaire = id_commentaire;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public UtilisateurResponce getCreateur() {
        return createur;
    }

    public void setCreateur(UtilisateurResponce createur) {
        this.createur = createur;
    }


    public LocalDateTime getDate_de_creation() {
        return date_de_creation;
    }

    public void setDate_de_creation(LocalDateTime date_de_creation) {
        this.date_de_creation = date_de_creation;
    }

    public int getId_ressource() {
        return ressource;
    }

    public void setId_ressource(int id_ressource) {
        this.ressource = id_ressource;
    }

    public CommentaireResponce getId_commentaire_rep() {
        return this.commentaire_rep;
    }

    public void setId_commentaire_rep(CommentaireResponce id_commentaire_rep) {
        this.commentaire_rep = id_commentaire_rep;
    }
}
