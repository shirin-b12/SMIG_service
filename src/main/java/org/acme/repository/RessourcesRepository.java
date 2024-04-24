package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.model.Ressources;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class RessourcesRepository {

    @Inject
    EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(RessourcesRepository.class.getName());
    public List<Ressources> listAll() {
        return Ressources.listAll();
    }

    public void persist(Ressources ressource) {
        Ressources.persist(ressource);
    }

    public Ressources update(int id, Ressources nouvelleRessource) {

        Ressources ressourceExistante = em.find(Ressources.class, id);
        if (ressourceExistante != null) {
            em.getTransaction().begin();

            ressourceExistante.setTitre(nouvelleRessource.getTitre());
            ressourceExistante.setDescription(nouvelleRessource.getDescription());
            ressourceExistante.setVisibilite(nouvelleRessource.getVisibilite());
            ressourceExistante.setDate_de_creation(nouvelleRessource.getDate_de_creation());
            ressourceExistante.setVue(nouvelleRessource.getVue());
            ressourceExistante.setCreateur(nouvelleRessource.getCreateur());
            ressourceExistante.setCategorie(nouvelleRessource.getCategorie());
            ressourceExistante.setType(nouvelleRessource.getType());
            ressourceExistante.setTag(nouvelleRessource.getTag());
            ressourceExistante.setImage(nouvelleRessource.getImage());

            em.merge(ressourceExistante);
            em.getTransaction().commit();
            try {
                return ressourceExistante;
            } catch (Exception e) {
                LOGGER.severe("Une exception s'est produite lors de la mise à jour de la ressource: " + e.getMessage());
            }
        }
        return null;
    }

    public Ressources findById(int id) {
        try {
            return Ressources.findById(id);
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la recherche de la ressource: " + e.getMessage());
            return null;
        }
    }
    public void delete(Ressources ressource) {
        try {
            Ressources.delete("id", ressource.getId_ressource());
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la suppression de la ressource: " + e.getMessage());
        }
    }
    public void deletebyCreateur(int id) {
        try {
            Ressources.delete("createur.id_utilisateur", id);
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la suppression de la ressource: " + e.getMessage());
        }
    }

    public List<Ressources> findByCreateurId(int createurId) {
        return em.createQuery("SELECT r FROM Ressources r WHERE r.createur.id_utilisateur = :createurId", Ressources.class)
                .setParameter("createurId", createurId)
                .getResultList();
    }

}
