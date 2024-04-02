package org.acme.model;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
public class Utilisateurs extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public int id_utilisateur;

    @Column(nullable = false)
    public String nom;

    @Column(nullable = false)
    public String prenom;

    @Column(nullable = false, unique = true)
    public String mot_de_passe;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = true)
    public String tel;

    @ManyToOne
    @JoinColumn(name = "id_role", nullable = true)
    public Roles role;

    @Column(nullable = true)
    private Integer id_image_profil;

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    @Column(nullable = true,unique = true)
    private String tokenRefrech;

    public void setToken(String token) {
        this.tokenRefrech = token;
    }


}
