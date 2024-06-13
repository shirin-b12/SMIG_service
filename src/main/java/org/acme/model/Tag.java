package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;

@Entity
public class Tag extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id_tag;

    @Column(nullable = false, unique = true)
    @NotNull
    private String nom_tag;

    public String getNom_tag() {
        return nom_tag;
    }

    public int getId_tag() {
        return id_tag;
    }

    public void setId_tag(int id){
        this.id_tag = id;
    }

    public void setNom_tag(String nom){
        this.nom_tag = nom;
    }
}
