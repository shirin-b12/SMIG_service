package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;

@Entity
public class Type extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id_type;

    @Column(nullable = false, unique = true)
    @NotNull
    private String nom_type;

    public String getNom_type() {
        return nom_type;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id){
        this.id_type = id;
    }

    public void setNom_type(String nom){
        this.nom_type = nom;
    }
}
