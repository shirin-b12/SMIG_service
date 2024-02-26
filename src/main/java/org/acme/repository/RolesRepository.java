package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.model.Roles;

import java.util.List;

@ApplicationScoped
public class RolesRepository {

    @Inject
    EntityManager entityManager;

    public List<Roles> listAll() { return Roles.listAll();}

    public void persist(Roles role) { Roles.persist(role);}

    public Roles findById(int id) {
        return Roles.findById(id);
    }
}

