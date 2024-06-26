package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Images;
import org.acme.repository.ImagesRepository;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.jboss.resteasy.reactive.server.core.multipart.DefaultFileUpload;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class ImagesService {

    @Inject
    ImagesRepository imagesRepository;

    public Images imagesParID(int id) {
        return imagesRepository.findById(id);
    }

    public Images addImage(String description, FileUpload image) {

        if (image != null) {
            String fileName = image.fileName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            if (!fileExtension.equals("jpg") && !fileExtension.equals("png")) {
                throw new IllegalArgumentException("Invalid file type. Only jpg and png files are supported.");
            }

            Images newImage = new Images();
            newImage.setLegende(description);
            try {
                Path tempFile = Paths.get(image.uploadedFile().toUri()); // Assuming uploadedFile() returns a path to the temp file.
                byte[] fileContent = Files.readAllBytes(tempFile);
                newImage.setFichier(fileContent);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read file content", e);
            }
            imagesRepository.persist(newImage);
            return newImage;
        }
        return null;
    }
    public BufferedImage convertBytesToImage(byte[] imageData) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert bytes to image", e);
        }
    }
}

// Custom class that implements FileUpload

