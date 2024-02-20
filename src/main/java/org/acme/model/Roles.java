package org.acme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Roles extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Integer id_role;

    @Column(nullable = false)
    public String nom_role;
}
