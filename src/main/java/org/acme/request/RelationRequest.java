package org.acme.request;

public class RelationRequest {


    private int id_utilisateur1;

    private int id_utilisateur2;

    private int id_type_relation;

    public RelationRequest(int id_utilisateur1, int id_utilisateur2, int id_type_relation) {
        this.id_utilisateur1 = id_utilisateur1;
        this.id_utilisateur2 = id_utilisateur2;
        this.id_type_relation = id_type_relation;
    }

    // Getter for id_utilisateur1
    public int getId_utilisateur1() {
        return id_utilisateur1;
    }

    // Setter for id_utilisateur1
    public void setId_utilisateur1(int id_utilisateur1) {
        this.id_utilisateur1 = id_utilisateur1;
    }

    // Getter for id_utilisateur2
    public int getId_utilisateur2() {
        return id_utilisateur2;
    }

    // Setter for id_utilisateur2
    public void setId_utilisateur2(int id_utilisateur2) {
        this.id_utilisateur2 = id_utilisateur2;
    }

    // Getter for id_type_relation
    public int getId_type_relation() {
        return id_type_relation;
    }

    // Setter for id_type_relation
    public void setId_type_relation(int id_type_relation) {
        this.id_type_relation = id_type_relation;
    }

}
