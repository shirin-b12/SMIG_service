package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.model.Ressources;

import java.util.List;

@ApplicationScoped
public class RessourcesRepository {

    @Inject
    EntityManager em;

    public List<Ressources> listAll() {
        return Ressources.listAll();
    }

    public void persist(Ressources ressource) {
        Ressources.persist(ressource);
    }
    public Ressources findById(int id) {
        return Ressources.findById(id);
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
            ressourceExistante.setimage(nouvelleRessource.getimage());

            em.merge(ressourceExistante);
            em.getTransaction().commit();
            return ressourceExistante;
        }
        return null;
    }

    public Ressources findById(int id) { return Ressources.findById(id); }
    public void delete(Ressources ressource) {
        Ressources.delete("id", ressource.getId_ressource());
    }
    public void deletebyCreateur(int id) {
        Ressources.delete("createur.id_utilisateur", id);
    }

}
