package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Categories;
import org.acme.model.Ressources;
import org.acme.repository.CategoriesRepository;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class CategoriesService {

    @Inject
    CategoriesRepository categoriesRepository;

    public List<Categories> listAll() {
        return categoriesRepository.listAll();
    }

    public Categories createCategory(Categories category) {
        if (category != null && category.getNom_cat() != null && !category.getNom_cat().isEmpty()) {
            categoriesRepository.persist(category);
            return category;
        }
        return null;
    }

}
