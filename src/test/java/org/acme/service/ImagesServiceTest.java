package org.acme.service;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.repository.ImagesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.junit.jupiter.api.Test;
import org.acme.model.Images;
import org.mockito.MockitoAnnotations;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ImagesServiceTest {
    @InjectMock
    ImagesService imagesService;
    @Mock
    ImagesRepository imagesRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddImage() {
        FileUpload fileUpload = Mockito.mock(FileUpload.class);
        when(fileUpload.fileName()).thenReturn("test-image.jpg");
        when(fileUpload.uploadedFile()).thenReturn(Paths.get("src/main/resources/test-image.jpg").toFile().toPath());

        Images mockImage = new Images();
        mockImage.setLegende("Test Image");
        when(imagesService.addImage(Mockito.anyString(), Mockito.any(FileUpload.class))).thenReturn(mockImage);

        Images image = imagesService.addImage("Test Image", fileUpload);
        assertNotNull(image, "Image should not be null");
        assertEquals("Test Image", image.getLegende(), "Image legend should match");
    }

    @Test
    public void testConvertBytesToImage() throws IOException {
        byte[] testImageBytes = Files.readAllBytes(Paths.get("src/main/resources/test-image.jpg"));
        BufferedImage mockBufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        when(imagesService.convertBytesToImage(Mockito.any(byte[].class))).thenReturn(mockBufferedImage);

        BufferedImage image = imagesService.convertBytesToImage(testImageBytes);
        assertNotNull(image, "Image should not be null");
    }
    @Test
    public void testImagesParID() {
        // Create a mock Images object
        Images mockImage = new Images();
        mockImage.setLegende("Test Image");
        mockImage.setFichier(new byte[0]);
        mockImage.setId_image(1);

        // Define the behavior of imagesService.method when called
        when(imagesService.imagesParID(anyInt())).thenReturn(mockImage);

        // Call the method to test
        Images image = imagesService.imagesParID(1);

        // Verify the result
        assertNotNull(image, "Image should not be null");
        assertEquals("Test Image", image.getLegende(), "Image legend should match");
    }
}
