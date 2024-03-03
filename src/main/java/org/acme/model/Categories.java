package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;

@Entity
public class Categories extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id_cat;

    @Column(nullable = false, unique = true)
    @NotNull
    private String nom_cat;

    public String getNom_cat() {
        return nom_cat;
    }

    public int getId_cat() {
        return id_cat;
    }
}
