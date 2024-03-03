package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.acme.model.Utilisateurs;

@ApplicationScoped
public class AuthRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public void updateRefreshToken(int userId, String refreshToken) {
        Utilisateurs user = Utilisateurs.findById(userId);
        if (user != null) {
            String truncatedToken = refreshToken.substring(0, Math.min(refreshToken.length(), 2048));
            user.setToken(truncatedToken);
            Utilisateurs managedUser = em.merge(user);
            em.persist(managedUser);
        }
    }
}
