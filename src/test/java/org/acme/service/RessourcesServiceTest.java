package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import org.acme.model.*;
import org.acme.repository.*;
import org.acme.request.RessourcesRequest;
import org.acme.response.RessourcesResponce;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class RessourcesServiceTest {

    @InjectMocks
    RessourcesService ressourcesService;

    @Mock
    RessourcesRepository ressourcesRepository;

    @Mock
    CategoriesRepository categoriesRepository;

    @Mock
    ImagesRepository imagesRepository;

    @Mock
    TypeRepository typeRepository;

    @Mock
    TagRepository tagRepository;

    @Mock
    UtilisateursRepository utilisateursRepository;

    private RessourcesRequest request;
    private RessourcesRequest ressourceUpdateRequest;
    private Ressources ressource1;
    private Ressources ressource2;
    private Utilisateurs createur;
    private Categories categorie;
    private Tag tag;
    private Type type;
    private Images image;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Ressources existingRessource = new Ressources();
        existingRessource.setId_ressource(1);

        ressourceUpdateRequest = new RessourcesRequest();
        ressourceUpdateRequest.setTitre("Updated Titre");
        ressourceUpdateRequest.setDescription("Updated Description");
        ressourceUpdateRequest.setVisibilite(1);
        ressourceUpdateRequest.setDateDeCreation(LocalDateTime.now());

        createur = new Utilisateurs();
        createur.setId_utilisateur(1);

        image = new Images();
        image.setId_image(1);

        categorie = new Categories();
        categorie.setId_cat(1);
        categorie.setNom_cat("Categorie Test");

        tag = new Tag();
        tag.setId_tag(1);
        tag.setNom_tag("Tag Test");

        type = new Type();
        type.setId_type(1);
        type.setNom_type("Type Test");

        request = new RessourcesRequest();

        ressource1 = new Ressources();
        ressource1.setId_ressource(1);
        ressource1.setCreateur(createur);
        ressource1.setTitre("Titre de la ressource1");
        ressource1.setDescription("Description de la ressource1");
        ressource1.setCategorie(categorie);
        ressource1.setTag(tag);
        ressource1.setType(type);
        ressource1.setVisibilite(1);
        ressource1.setDate_de_creation(LocalDateTime.now());

        ressource2 = new Ressources();
        ressource2.setId_ressource(2);
        ressource2.setCreateur(createur);
        ressource2.setTitre("Titre de la ressource2");
        ressource2.setDescription("Description de la ressource2");
        ressource2.setCategorie(categorie);
        ressource2.setTag(tag);
        ressource2.setType(type);
        ressource2.setVisibilite(1);
        ressource2.setDate_de_creation(LocalDateTime.now());

    }

    @Test
    void testFindById() throws Exception {
        when(ressourcesRepository.findById(1)).thenReturn(ressource1);

        RessourcesResponce result = ressourcesService.findById(1);

        assertNotNull(result);
        assertEquals(ressource1.getId_ressource(), result.getId());

        // Test ressource not found scenario
        when(ressourcesRepository.findById(2)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            ressourcesService.findById(2);
        });

        assertEquals("La ressource n'a pas été trouvée.", exception.getMessage());
    }

    @Test
    void testListAll() {
        when(ressourcesRepository.listAll()).thenReturn(Arrays.asList(ressource1, ressource2));

        List<RessourcesResponce> result = ressourcesService.listAll();

        assertEquals(2, result.size());
    }

    @Test
    void testCreateRessource() throws Exception {
        RessourcesRequest request = new RessourcesRequest();
        request.setIdCat(1);
        request.setIdType(1);
        request.setIdTag(1);
        request.setIdCreateur(1);
        request.setTitre("Test Titre");
        request.setDescription("Test Description");
        request.setVisibilite(1);
        request.setDateDeCreation(LocalDateTime.now());
        request.setImageId(0);

        Categories categorie = new Categories();
        Type type = new Type();
        Tag tag = new Tag();
        Utilisateurs createur = new Utilisateurs();

        when(categoriesRepository.findById(1)).thenReturn(categorie);
        when(typeRepository.findById(1)).thenReturn(type);
        when(tagRepository.findById(1)).thenReturn(tag);
        when(utilisateursRepository.findById(1)).thenReturn(createur);

        RessourcesResponce result = ressourcesService.createRessource(request);

        assertNotNull(result);
        assertEquals(request.getTitre(), result.getTitre());
        verify(ressourcesRepository, times(1)).persist(any(Ressources.class));
    }

    @Test
    void testLinkImage() {
        when(ressourcesRepository.findById(1)).thenReturn(ressource1);
        when(imagesRepository.findById(1)).thenReturn(image);

        Ressources result = ressourcesService.linkImage(1, 1);

        assertNotNull(result);
        assertEquals(image.getId_image(), result.getImage().getId_image());
        verify(ressourcesRepository, times(1)).update(eq(1), any(Ressources.class));
    }

    @Test
    void testUpdateRessource() {
        when(ressourcesRepository.findById(1)).thenReturn(ressource1);

        Ressources result = ressourcesService.updateRessource(1, ressourceUpdateRequest);

        assertNotNull(result);
        assertEquals(ressourceUpdateRequest.getTitre(), result.getTitre());
        verify(ressourcesRepository, times(1)).persist(ressource1);

        // Test ressource not found scenario
        when(ressourcesRepository.findById(2)).thenReturn(null);

        Ressources notFoundResult = ressourcesService.updateRessource(2, ressourceUpdateRequest);

        assertNull(notFoundResult);
    }

    @Test
    void testDeleteRessource() throws Exception {
        when(ressourcesRepository.findById(1)).thenReturn(ressource1);
        doNothing().when(ressourcesRepository).delete(ressource1);

        ressourcesService.deleteRessource(1);

        verify(ressourcesRepository, times(1)).delete(ressource1);

        /*
        // Test ressource not found scenario
        when(ressourcesRepository.findById(2)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            ressourcesService.deleteRessource(2);
        });

        assertEquals("La ressource n'a pas été trouvée.", exception.getMessage());
        */
    }

    @Test
    void testDeleteRessourcebyCreateur() {
        doNothing().when(ressourcesRepository).deletebyCreateur(1);

        ressourcesService.deleteRessourcebyCreateur(1);

        verify(ressourcesRepository, times(1)).deletebyCreateur(1);
    }

    @Test
    void testValidateRessource() throws Exception {
        when(ressourcesRepository.findById(1)).thenReturn(ressource2);

        Ressources result = ressourcesService.validateRessource(1);

        assertNotNull(result);
        assertTrue(result.isValidate_Ressource());
        verify(ressourcesRepository, times(1)).persist(ressource2);

        // Test ressource not found scenario
        when(ressourcesRepository.findById(2)).thenReturn(null);

        Ressources notFoundResult = ressourcesService.validateRessource(2);

        assertNull(notFoundResult);
    }

    @Test
    void testFindByCreateurId() {
        when(ressourcesRepository.findByCreateurId(1)).thenReturn(Arrays.asList(ressource1, ressource2));

        List<RessourcesResponce> result = ressourcesService.findByCreateurId(1);

        assertEquals(2, result.size());
    }
}
