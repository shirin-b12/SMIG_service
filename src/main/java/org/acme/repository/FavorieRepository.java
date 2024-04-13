package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Favoris;

@ApplicationScoped

public class FavorieRepository {
    public void persist(Favoris favoris){
        Favoris.persist(favoris);
    }
}
