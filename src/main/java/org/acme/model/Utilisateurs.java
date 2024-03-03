package org.acme.model;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

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
    public Integer id_image_profil;

}
