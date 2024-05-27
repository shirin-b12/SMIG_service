package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Favoris;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped

public class FavorieRepository {
    private static final Logger LOGGER = Logger.getLogger(FavorieRepository.class.getName());

    public void persist(Favoris favoris) {
        try {
            Favoris.persist(favoris);
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la persistance du favoris: " + e.getMessage());
        }
    }

    public List<Favoris> all() {
        try {
            return Favoris.listAll();
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la récupération de la liste des favoris: " + e.getMessage());
            return null;
        }
    }

}
