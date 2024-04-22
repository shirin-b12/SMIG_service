package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Relations;
import org.acme.model.TypesRelation;
import org.acme.model.Utilisateurs;
import org.acme.repository.RelationRepository;
import org.acme.repository.TypesRelationRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.RelationRequest;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RelationService {

    @Inject
    RelationRepository relationRepository;

    @Inject
    UtilisateursRepository utilisateursRepository;

    @Inject
    TypesRelationRepository typesRelationRepository;

    public List<Relations> listAll() {
        return relationRepository.listAll();
    }

    public Relations findById(int id) {
        return relationRepository.findById(id);
    }

    public Relations addRelation(RelationRequest request) {
        System.out.println(request.getId_type_relation()+" "+request.getId_utilisateur1()+" "+request.getId_utilisateur2());
        Utilisateurs user1 = utilisateursRepository.findById(request.getId_utilisateur1());
        Utilisateurs user2 = utilisateursRepository.findById(request.getId_utilisateur2());
        TypesRelation typesRelation = typesRelationRepository.findById(request.getId_type_relation());

        Relations relations = new Relations();
        relations.setTypeRelation(typesRelation);
        relations.setUtilisateur1(user1);
        relations.setUtilisateur2(user2);

        relationRepository.persist(relations);

        return relations;
    }

    public Relations updateRelation(int id, Relations relationsUpdates) {
        Relations relations = findById(id);
        if (relations != null) {
            relations.setTypeRelation(relationsUpdates.getTypeRelation());
            relations.setUtilisateur1(relationsUpdates.getUtilisateur1());
            relations.setUtilisateur2(relationsUpdates.getUtilisateur2());
            relationRepository.persist(relations);
            return relations;
        }
        return null;
    }

    public void deleteRelation(int id) {
        Relations relations = findById(id);
        if (relations != null) {
            relationRepository.delete(relations);
        }
    }

    public List<Relations> getRelationsByUserId(int userId) {
        return relationRepository.findRelationsByUserId(userId);
    }

    // In RelationService.java
    public boolean checkExistingRelation(int currentUserID, int otherUserID) {
        return relationRepository.existsRelationBetweenUsers(currentUserID, otherUserID);
    }

}
