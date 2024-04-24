package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Tag;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class TagRepository {
    private static final Logger LOGGER = Logger.getLogger(TagRepository.class.getName());

    public List<Tag> listAll() {
        try {
            return Tag.listAll();
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la recherche des tags: " + e.getMessage());
            return null;
        }
    }

    public void persist(Tag tag) {

        try {
            Tag.persist(tag);
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la persistance du tag: " + e.getMessage());
        }
    }

    public Tag findById(int id) {
        try {
            return Tag.findById(id);
        } catch (Exception e) {
            LOGGER.severe("Une exception s'est produite lors de la recherche du tag: " + e.getMessage());
            return null;
        }

    }

}
