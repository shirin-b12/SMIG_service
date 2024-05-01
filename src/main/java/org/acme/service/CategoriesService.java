package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Categories;
import org.acme.repository.CategoriesRepository;
import org.acme.response.UtilisateurResponce;

import java.util.List;

@ApplicationScoped
public class CategoriesService {

    @Inject
    CategoriesRepository categoriesRepository;

    public List<Categories> listAll() {
        return categoriesRepository.listAll();
    }
    public Categories findById(int id) {

        return categoriesRepository.findById(id);
    }
    public Categories createCategory(Categories category) {
        if (category != null && category.getNom_cat() != null && !category.getNom_cat().isEmpty()) {
            categoriesRepository.persist(category);
            return category;
        }
        return null;
    }
}
