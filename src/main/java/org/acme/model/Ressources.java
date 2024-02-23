package org.acme.model;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;

@Entity
public class Ressources extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public int id_ressource;

    @ManyToOne
    @JoinColumn(name = "id_createur", nullable = false)
    public Utilisateurs createur;

    @ManyToOne
    @JoinColumn(name = "id_cat", nullable = false)
    public Categories categorie;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    public Type type;

    @ManyToOne
    @JoinColumn(name = "id_tag", nullable = false)
    public Tag tag;

    @Column(nullable = true)
    public Integer id_image;

    @Column(nullable = false, length = 255)
    public String titre;

    @Column(nullable = false, length = 1000)
    public String description;

    @Column(nullable = true)
    public int visibilite;

    @Column(nullable = false)
    public LocalDateTime date_de_creation;

    @Column(nullable = false)
    public int vue;

}
