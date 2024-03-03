package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Favorie;
import org.acme.model.Images;

@ApplicationScoped

public class FavorieRepository {
    public void persist(Favorie favorie){
        Favorie.persist(favorie);
    }
}
