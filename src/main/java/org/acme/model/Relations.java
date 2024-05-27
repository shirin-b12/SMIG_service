package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
public class Relations extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id_relation;

    @ManyToOne
    @JoinColumn(name = "id_type_relation", nullable = false)
    private TypesRelation type_relation;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur1", nullable = false)
    private Utilisateurs utilisateur1;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur2", nullable = false)
    private Utilisateurs utilisateur2;

    // Getter and Setter for id_relation
    public int getIdRelation() {
        return id_relation;
    }

    public void setIdRelation(Integer id_relation) {
        this.id_relation = id_relation;
    }

    // Getter and Setter for type_relation
    public TypesRelation getTypeRelation() {
        return type_relation;
    }

    public void setTypeRelation(TypesRelation type_relation) {
        this.type_relation = type_relation;
    }

    // Getter and Setter for utilisateur1
    public Utilisateurs getUtilisateur1() {
        return utilisateur1;
    }

    public void setUtilisateur1(Utilisateurs utilisateur1) {
        this.utilisateur1 = utilisateur1;
    }

    // Getter and Setter for utilisateur2
    public Utilisateurs getUtilisateur2() {
        return utilisateur2;
    }

    public void setUtilisateur2(Utilisateurs utilisateur2) {
        this.utilisateur2 = utilisateur2;
    }

}
