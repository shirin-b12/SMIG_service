package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Categories;
import java.util.logging.Logger;

import java.util.List;

@ApplicationScoped
public class CategoriesRepository {

    private static final Logger LOGGER = Logger.getLogger(CategoriesRepository.class.getName());

    public List<Categories> listAll() {
        try {
            return Categories.listAll();
        } catch (Exception e){
            LOGGER.severe("Une exception s'est produite lors de la recherche des catégories: " + e.getMessage());
            return null;
        }
    }

    public void persist(Categories categories) {
        try {
            Categories.persist(categories);
        } catch (Exception e){
            LOGGER.severe("Une exception s'est produite lors de la persistance de la catégorie: " + e.getMessage());
        }

    }

    public Categories findById(int id) {
        try {
            return Categories.findById(id);
        } catch (Exception e){
            LOGGER.severe("Une exception s'est produite lors de la recherche de la catégorie: " + e.getMessage());
            return null;
        }
    }
}
