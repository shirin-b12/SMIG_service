package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;

import java.util.List;

@ApplicationScoped
public class RessourcesRepository {

    @Inject
    EntityManager entityManager;

    public List<Ressources> listAll() { return Ressources.listAll(); }

    public void persist(Ressources ressource) { Ressources.persist(ressource);}

}
