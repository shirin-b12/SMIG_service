package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Categories;

import java.util.List;

@ApplicationScoped
public class CategoriesRepository {

    public List<Categories> listAll() {
        return Categories.listAll();
    }

    public void persist(Categories categories) {
        Categories.persist(categories);
    }

    public Categories findById(int id) {
        return Categories.findById(id);
    }
}
