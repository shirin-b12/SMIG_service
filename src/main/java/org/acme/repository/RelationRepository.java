package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.acme.model.Relations;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RelationRepository {

    @Inject
    EntityManager entityManager;

    public List<Relations> listAll() {
        return Relations.listAll();
    }

    public Relations findById(int id) {
        return Relations.findById(id);
    }

    public void persist(Relations relations) {
        Relations.persist(relations);
    }

    public void delete(Relations relations) {
        Relations.delete("id",relations.getIdRelation());
    }

    public List<Relations> findRelationsByUserId(int userId) {
        TypedQuery<Relations> query = entityManager.createQuery(
                "SELECT r FROM Relations r " +
                        "JOIN r.utilisateur1 u1 " +
                        "JOIN r.utilisateur2 u2 " +
                        "WHERE u1.id = :userId OR u2.id = :userId", Relations.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }


    public boolean existsRelationBetweenUsers(int userId1, int userId2) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(r) FROM Relations r WHERE (r.utilisateur1.id = :userId1 AND r.utilisateur2.id = :userId2) OR (r.utilisateur1.id = :userId2 AND r.utilisateur2.id = :userId1)",
                        Long.class)
                .setParameter("userId1", userId1)
                .setParameter("userId2", userId2)
                .getSingleResult();
        return count > 0;
    }

}
