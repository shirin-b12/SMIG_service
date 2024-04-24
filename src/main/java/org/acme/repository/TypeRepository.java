package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.model.Type;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class TypeRepository {
    private static final Logger LOGGER = Logger.getLogger(TypeRepository.class.getName());
    @Inject
    EntityManager entityManager;

    public List<Type> listAll() {
        try {
            return Type.listAll();
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la recherche des types: " + e.getMessage());
            return null;

        }
    }

    public void persist(Type type) {
        try {
            Type.persist(type);
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la persistance du type: " + e.getMessage());
        }
    }

    public Type findById(int id) {

        try {
            return entityManager.find(Type.class, id);
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la recherche du type: " + e.getMessage());
            return null;
        }
    }
}

