package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Type;
import org.acme.repository.TypeRepository;

import java.util.List;

@ApplicationScoped
public class TypeService {

    @Inject
    TypeRepository typeRepository;

    public List<Type> listAll() {
        return typeRepository.listAll();
    }

    public Type createType(Type type) {
        if (type != null && type.getNom_type() != null && !type.getNom_type().isEmpty()) {
            typeRepository.persist(type);
            return type;
        }
        return null;
    }
}
