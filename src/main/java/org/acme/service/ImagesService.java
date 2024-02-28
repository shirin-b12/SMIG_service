package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Images;
import org.acme.repository.ImagesRepository;

@ApplicationScoped
public class ImagesService {

    @Inject
    ImagesRepository imagesRepository;

    public Images imagesParID(int id) {
        return imagesRepository.findById(id);
    }

    public Images addImage(Images image) {
        if (image != null){
            imagesRepository.persist(image);
            return image;
        } return null;
    }
}