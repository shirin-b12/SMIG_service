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

}
