package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Roles;
import org.acme.repository.RolesRepository;

import java.util.List;

@ApplicationScoped

public class RolesService {
    @Inject
    RolesRepository rolesRepository;
    public List<Roles> listAll() {
        return rolesRepository.listAll();
    }
    public Roles createRole(Roles role) {
        rolesRepository.persist(role);
        return role;
    }
}
