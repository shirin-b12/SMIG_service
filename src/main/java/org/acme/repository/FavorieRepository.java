package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Favoris;
import org.acme.model.Images;

@ApplicationScoped

public class FavorieRepository {
    public void persist(Favoris favorie){
        Favoris.persist(favorie);
    }
}
