package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.acme.model.Commentaires;

import java.util.List;
@ApplicationScoped
public class CommentaireRepository {
    @Inject
    EntityManager entityManager;

    public List<Commentaires> listAll() {
        return Commentaires.listAll();
    }

    public void persist(Commentaires commentaires) {
        Commentaires.persist(commentaires);
    }
    public Commentaires findById(int id) {
        return Commentaires.findById(id);
    }

    public List<Commentaires> findByRessourceId(int idRessource) {
        try {
            return entityManager.createQuery("SELECT c FROM Commentaires c WHERE c.ressource.id = :idRessource", Commentaires.class)
                    .setParameter("idRessource", idRessource)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
