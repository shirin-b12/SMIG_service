package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.model.Roles;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class RolesRepository {
    private static final Logger LOGGER = Logger.getLogger(RolesRepository.class.getName());
    @Inject
    EntityManager entityManager;

    public List<Roles> listAll() {
        try {
            return Roles.listAll();
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la recherche des rôles: " + e.getMessage());
            return null;
        }
    }
    public void persist(Roles role) {
        try {
            Roles.persist(role);
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la persistance du rôle: " + e.getMessage());
        }
    }
    public static Roles findById(int id) {
        try {
            return  Roles.findById(id);
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la recherche du rôle: " + e.getMessage());
            return null;
        }
    }
}

