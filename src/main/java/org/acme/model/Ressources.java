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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Visibilite visibilite;

    @Column(nullable = false)
    public LocalDateTime date_de_creation;

    @Column(nullable = false)
    public int vue;

    public enum Visibilite {
        PUBLIC,
        PRIVE
    }

    public int getId_ressource() {
        return id_ressource;
    }

    public void setId_ressource(int id_ressource) {
        this.id_ressource = id_ressource;
    }

    public Utilisateurs getCreateur() {
        return createur;
    }

    public void setCreateur(Utilisateurs createur) {
        this.createur = createur;
    }

    public Categories getCategorie() {
        return categorie;
    }

    public void setCategorie(Categories categorie) {
        this.categorie = categorie;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Integer getId_image() {
        return id_image;
    }

    public void setId_image(Integer id_image) {
        this.id_image = id_image;
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

    public Visibilite getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(Visibilite visibilite) {
        this.visibilite = visibilite;
    }

    public LocalDateTime getDate_de_creation() {
        return date_de_creation;
    }

    public void setDate_de_creation(LocalDateTime date_de_creation) {
        this.date_de_creation = date_de_creation;
    }

    public int getVue() {
        return vue;
    }

    public void setVue(int vue) {
        this.vue = vue;
    }
}
