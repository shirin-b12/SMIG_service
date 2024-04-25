package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.acme.response.CommentaireResponce;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Commentaires extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id_commentaire;
    @Column(nullable = false)
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur_redacteur", nullable = true)
    private Utilisateurs id_utilisateur_redacteur;
    @Column(nullable = false)
    private LocalDateTime date_de_creation;
    @ManyToOne
    @JoinColumn(name = "id_ressource", nullable = true)
    private Ressources ressource;
    @ManyToOne
    @JoinColumn(name = "id_commentaire_rep", nullable = true)
    private Commentaires commentaire_rep;


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

    public Utilisateurs getId_utilisateur_redacteur() {
        return id_utilisateur_redacteur;
    }

    public void setId_utilisateur_redacteur(Utilisateurs id_utilisateur_redacteur) {
        this.id_utilisateur_redacteur =id_utilisateur_redacteur;
    }

    public LocalDateTime getDate_de_creation() {
        return date_de_creation;
    }

    public void setDate_de_creation(LocalDateTime date_de_creation) {
        this.date_de_creation = date_de_creation;
    }

    public Ressources getId_ressource() {
        return ressource;
    }

    public void setId_ressource(Ressources id_ressource) {
        this.ressource = id_ressource;
    }

    public Commentaires getId_commentaire_rep() {
        return this.commentaire_rep;
    }
    public void setId_commentaire_rep(Commentaires id_commentaire_rep) {
        this.commentaire_rep = id_commentaire_rep;
    }

    public CommentaireResponce mapCommentaireToCommentaireResponse() {
        CommentaireResponce commentaireResponse = new CommentaireResponce();
        commentaireResponse.setId_commentaire(this.getId_commentaire());
        commentaireResponse.setCommentaire(this.getCommentaire());
        commentaireResponse.setCreateur(this.getId_utilisateur_redacteur().mapUtilisateurToUtilisateurResponse());
        commentaireResponse.setDate_de_creation(this.getDate_de_creation());
        commentaireResponse.setId_ressource(this.getId_ressource().getId_ressource());
        if (this.getId_commentaire_rep() != null) {
            commentaireResponse.setId_commentaire_rep(this.commentaire_rep.mapCommentaireToCommentaireResponse());
        }
        return commentaireResponse;
    }
}
