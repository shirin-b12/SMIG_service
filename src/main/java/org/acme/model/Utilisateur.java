package org.acme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Utilisateur extends PanacheEntityBase {

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

    @Column(nullable = true)
    public Integer id_role; // Changed from int to Integer

    @Column(nullable = true)
    public Integer id_image_profil; // Changed from int to Integer

}
