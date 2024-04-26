package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.TypesRelation;
import org.acme.repository.TypesRelationRepository;

import java.util.List;

@ApplicationScoped
public class TypesRelationService {

    @Inject
    TypesRelationRepository typesRelationRepository;

    public List<TypesRelation> listAll() {
        return typesRelationRepository.listAll();
    }

    public TypesRelation findById(int id) {
        return typesRelationRepository.findById(id);
    }

    public TypesRelation addTypesRelation(TypesRelation typesRelation) {
        typesRelationRepository.persist(typesRelation);
        return typesRelation;
    }

    public TypesRelation updateTypesRelation(int id, TypesRelation typesRelationUpdates) {
        TypesRelation typesRelation = findById(id);
        if (typesRelation != null) {
            typesRelation.setIntitule(typesRelationUpdates.getIntitule());
            typesRelationRepository.persist(typesRelation);
            return typesRelation;
        }
        return null;
    }

    public void deleteTypesRelation(int id) {
        TypesRelation typesRelation = findById(id);
        if (typesRelation != null) {
            typesRelationRepository.delete(typesRelation);
        }
    }
}
