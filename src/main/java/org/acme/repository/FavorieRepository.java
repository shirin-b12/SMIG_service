package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Favoris;

@ApplicationScoped

public class FavorieRepository {
    public void persist(Favoris favorie){
        Favoris.persist(favorie);
    }
    public Favoris findFavoriebyUtilisateur(int idutilisateur){
        return Favoris.find("id_utilisateur = ?1", idutilisateur).firstResult();
    }
}
