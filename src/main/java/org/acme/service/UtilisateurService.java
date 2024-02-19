package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.acme.model.Utilisateur;
import java.util.List;

@ApplicationScoped
public class UtilisateurService {

    @Inject
    EntityManager entityManager;

    @Inject
    TokenService tokenService;

    public List<Utilisateur> listAll() {
        return Utilisateur.listAll();
    }

    public Utilisateur findById(Long id) {
        return Utilisateur.findById(id);
    }

    public Utilisateur addUtilisateur(Utilisateur utilisateur) {
        if (utilisateur != null) {
            utilisateur.persist();
            return utilisateur;
        }
        return null;
    }

    public String login(int idUtilisateur, String motDePasse) {
        try {
            Utilisateur user = entityManager.createQuery("SELECT u FROM Utilisateur u WHERE u.id_utilisateur = :id_utilisateur", Utilisateur.class)
                    .setParameter("id_utilisateur", idUtilisateur)
                    .getSingleResult();

            if (user != null && user.mot_de_passe.equals(motDePasse)) {
                return tokenService.generateToken(user);
            }
        } catch (NoResultException e) {
            // TODO: Handle error
        }
        return null;
    }
}
