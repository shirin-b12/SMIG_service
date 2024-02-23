package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Tag;
import org.acme.model.Utilisateurs;

import java.util.List;

@ApplicationScoped
public class TagRepository {

    public List<Tag> listAll() { return Tag.listAll();}

    public void persist(Tag tag) { Tag.persist(tag);}

    public Tag findById(int id) {
        return Tag.findById(id);
    }

}
