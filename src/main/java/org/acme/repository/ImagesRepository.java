package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Images;

import java.util.logging.Logger;

@ApplicationScoped
public class ImagesRepository {
    private static final Logger LOGGER = Logger.getLogger(ImagesRepository.class.getName());
    public Images findById(int id) {
        try {
            return Images.findById(id);
        } catch (Exception e){
            LOGGER.severe("Une exception s'est produite lors de la recherche de l'image: " + e.getMessage());
            return null;
        }
    }
    public void persist(Images image){
        try {
            Images.persist(image);
        } catch (Exception e){
            LOGGER.severe("Une exception s'est produite lors de la persistance de l'image: " + e.getMessage());
        }
    }
}
