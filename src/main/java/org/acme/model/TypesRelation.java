package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
public class TypesRelation extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id_type_relation;

    @Column(nullable = false, length = 255)
    private String intitule;

    // Getter and Setter for id_type_relation
    public Integer getIdTypeRelation() {
        return id_type_relation;
    }

    public void setIdTypeRelation(Integer id_type_relation) {
        this.id_type_relation = id_type_relation;
    }

    // Getter and Setter for intitule
    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

}
