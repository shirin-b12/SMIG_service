package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.acme.model.Utilisateurs;

@ApplicationScoped
public class AuthRepository {
    @PersistenceContext
    EntityManager em;

    public void updateRefreshToken(int userId, String refreshToken) {
        Utilisateurs user = Utilisateurs.findById(userId);
        //TODO: il faut arriver à mettre à jour le token de rafraichissement
        if (user != null) {
            user.setToken(refreshToken);
            em.merge(user);

        }
    }
}
