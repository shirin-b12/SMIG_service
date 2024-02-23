package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import org.acme.model.Categories;
import org.acme.model.Ressources;
import org.acme.model.Tag;
import org.acme.model.Utilisateurs;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@ApplicationScoped
public class CategoriesRepository  {

    public List<Categories> listAll() { return Categories.listAll();}

    public void persist(Categories categories) { Categories.persist(categories);}

    public Categories findById(int id) {
        return Categories.findById(id);
    }
}
