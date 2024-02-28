package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
public class Ressources extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id_ressource;

    @ManyToOne
    @JoinColumn(name = "id_createur", nullable = false)
    private Utilisateurs createur;

    @ManyToOne
    @JoinColumn(name = "id_cat", nullable = false)
    private Categories categorie;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "id_tag", nullable = false)
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "id_image", nullable = true)
    private Images image;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = true)
    private int visibilite;

    @Column(nullable = false)
    private LocalDateTime date_de_creation;

    @Column(nullable = false)
    private int vue;

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

    public Images getimage() {
        return image;
    }

    public void setimage(Images image) {
        this.image = image;
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

    public int getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(int visibilite) {
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
