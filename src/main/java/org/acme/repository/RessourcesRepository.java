package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.model.Ressources;

import java.util.List;

@ApplicationScoped
public class RessourcesRepository {

    public List<Ressources> listAll() {
        return Ressources.listAll();
    }

    public void persist(Ressources ressource) {
        Ressources.persist(ressource);
    }

    public Ressources findById(int id) { return Ressources.findById(id); }
    public void delete(Ressources ressource) {
        Ressources.delete("id", ressource.getId_ressource());
    }
    public void deletebyCreateur(int id) {
        Ressources.delete("createur.id_utilisateur", id);
    }

}
