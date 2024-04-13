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
    private Ressources id_ressource;
    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateurs id_utilisateur;
    @Column(nullable = true)
    private Date date_de_creation;

    public int getId_favori() {
        return id_favori;
    }

    public void setId_favori(int id_favori) {
        this.id_favori = id_favori;
    }

    public Ressources getId_ressource() {
        return id_ressource;
    }

    public void setId_ressource(Ressources id_ressource) {
        this.id_ressource = id_ressource;
    }

    public Utilisateurs getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(Utilisateurs id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public Date getDate_de_creation() {
        return date_de_creation;
    }

    public void setDate_de_creation(Date date_de_creation) {
        this.date_de_creation = date_de_creation;
    }
}
