package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Images;

@ApplicationScoped
public class ImagesRepository {
    public Images findById(int id) {
        return Images.findById(id);
    }
}
