package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Commentaires;

import java.util.List;
@ApplicationScoped
public class CommentaireRepository {
    public List<Commentaires> listAll() {
        return Commentaires.listAll();
    }

    public void persist(Commentaires commentaires) {
        Commentaires.persist(commentaires);
    }
    public Commentaires findById(int id) {
        return Commentaires.findById(id);
    }
}
