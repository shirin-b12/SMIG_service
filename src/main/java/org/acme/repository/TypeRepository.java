package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.acme.model.Tag;
import org.acme.model.Type;

import java.util.List;

@ApplicationScoped
public class TypeRepository {

    @Inject
    EntityManager entityManager;

    public List<Type> listAll() { return Type.listAll();}

    public void persist(Type type) { Type.persist(type);}

    public Type findById(int id) {
        return Type.findById(id);
    }
}

