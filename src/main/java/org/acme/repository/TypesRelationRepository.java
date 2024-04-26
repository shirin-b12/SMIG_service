package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.model.TypesRelation;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TypesRelationRepository {

    @Inject
    EntityManager entityManager;

    public List<TypesRelation> listAll() {
        return TypesRelation.listAll();
    }

    public TypesRelation findById(int id) {
        return TypesRelation.findById(id);
    }

    public void persist(TypesRelation typesRelation) {
        TypesRelation.persist(typesRelation);
    }

    public void delete(TypesRelation typesRelation) {
        TypesRelation.delete(String.valueOf(typesRelation));
    }
}
