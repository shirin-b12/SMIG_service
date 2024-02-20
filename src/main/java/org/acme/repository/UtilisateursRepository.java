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

    public List<Utilisateurs> listAll() {
        return Utilisateurs.listAll();
    }

    public Utilisateurs findById(Long id) {
        return Utilisateurs.findById(id);
    }

    public void persist(Utilisateurs utilisateur) {
        Utilisateurs.persist(utilisateur);
    }

    public Utilisateurs findByUsernameAndPassword(int idUtilisateur, String motDePasse) {
        try {
            return entityManager.createQuery("SELECT u FROM Utilisateurs u WHERE u.id_utilisateur = :id_utilisateur AND u.mot_de_passe = :mot_de_passe", Utilisateurs.class)
                    .setParameter("id_utilisateur", idUtilisateur)
                    .setParameter("mot_de_passe", motDePasse)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
