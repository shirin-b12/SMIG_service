package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.acme.model.Commentaires;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class CommentaireRepository {

    @Inject
    EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(CommentaireRepository.class.getName());

    public List<Commentaires> listAll() {
        try {
            return Commentaires.listAll();
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la recherche des commentaires: " + e.getMessage());
            return null;
        }
    }
    public void persist(Commentaires commentaires) {
        try {
            Commentaires.persist(commentaires);
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la persistance du commentaire: " + e.getMessage());
        }
    }
    public Commentaires findById(int id) {
        try {
            return entityManager.find(Commentaires.class, id);
        } catch (NoResultException e) {
            LOGGER.severe("Une exception s'est produite lors de la recherche du commentaire: " + e.getMessage());
            return null;
        }
    }
    public List<Commentaires> findByRessourceId(int idRessource) {
        try {
            return entityManager.createQuery("SELECT c FROM Commentaires c WHERE c.ressource.id = :idRessource", Commentaires.class)
                    .setParameter("idRessource", idRessource)
                    .getResultList();
        } catch (NoResultException e) {
            LOGGER.severe("Une exception s'est produite lors de la recherche des commentaires: " + e.getMessage());
            return null;
        }
    }
}
