package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.acme.model.Utilisateurs;

import java.util.List;

@ApplicationScoped
public class UtilisateursRepository {

    @Inject
    EntityManager entityManager;

    @Inject
    RolesRepository rolesRepository;

    public List<Utilisateurs> listAll() {
        return Utilisateurs.listAll();
    }

    public Utilisateurs findById(int id) {
        return Utilisateurs.findById(id);
    }

    public void persist(Utilisateurs utilisateur) {
        if (utilisateur.getRole() == null) {
            utilisateur.setRole(rolesRepository.findById(3));
        }

        Utilisateurs.persist(utilisateur);
    }

    public Utilisateurs findByUsernameAndPassword(String email, String motDePasse) {
        try {
            return entityManager.createQuery("SELECT u FROM Utilisateurs u WHERE u.email = :email AND u.mot_de_passe = :mot_de_passe", Utilisateurs.class)
                    .setParameter("email", email)
                    .setParameter("mot_de_passe", motDePasse)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
