package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class Favoris extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id_favori;

    @ManyToOne
    @JoinColumn(name = "id_ressource", nullable = false)
    private Ressources ressource;
    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateurs utilisateur;
    @Column(nullable = false)
    private Date date_de_creation;

    public int getId_favori() {
        return id_favori;
    }

    public void setId_favori(int id_favori) {
        this.id_favori = id_favori;
    }

    public Ressources getRessource() {
        return ressource;
    }

    public void setRessource(Ressources id_ressource) {
        this.ressource = id_ressource;
    }

    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Date getDate_de_creation() {
        return date_de_creation;
    }

    public void setDate_de_creation(Date date_de_creation) {
        this.date_de_creation = date_de_creation;
    }
}
