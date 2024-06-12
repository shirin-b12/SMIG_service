package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.model.EtatUtilisateur;
import org.acme.model.Utilisateurs;
import org.acme.repository.RolesRepository;
import org.acme.repository.UtilisateursRepository;
import org.acme.request.ChangeStatu;
import org.acme.request.UpdateUserRequest;
import org.acme.response.UtilisateurResponce;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class UtilisateursServiceTest {

    @InjectMocks
    UtilisateursService utilisateursService;

    @Mock
    UtilisateursRepository utilisateursRepository;

    @Mock
    TokenService tokenService;

    @Mock
    FavorieService favorieService;

    @Mock
    RessourcesService ressourceService;

    @Mock
    ImagesService imagesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Utilisateurs expectedUser = new Utilisateurs();
        expectedUser.setId_utilisateur(1);

        when(utilisateursRepository.findById(anyInt())).thenReturn(expectedUser);

        UtilisateurResponce result = utilisateursService.findById(1);

        assertNotNull(result);
        assertEquals(expectedUser.getId_utilisateur(), result.getId_utilisateur());
    }

    @Test
    void testListAll() {
        Utilisateurs user1 = new Utilisateurs();
        user1.setId_utilisateur(1);
        Utilisateurs user2 = new Utilisateurs();
        user2.setId_utilisateur(2);

        when(utilisateursRepository.listAll()).thenReturn(Arrays.asList(user1, user2));

        List<UtilisateurResponce> result = utilisateursService.listAll();

        assertEquals(2, result.size());
    }

    @Test
    void testAddUtilisateur() {
        Utilisateurs newUser = new Utilisateurs();
        newUser.setId_utilisateur(1);
        newUser.setMot_de_passe("password");

        when(utilisateursRepository.findById(anyInt())).thenReturn(newUser);

        UtilisateurResponce result = utilisateursService.addUtilisateur(newUser);

        assertNotNull(result);
        verify(utilisateursRepository, times(1)).persist(newUser);
    }

    @Test
    void testUpdateUtilisateur() {
        Utilisateurs existingUser = new Utilisateurs();
        existingUser.setId_utilisateur(1);

        UpdateUserRequest request = new UpdateUserRequest();
        request.setNom("Updated Name");
        request.setPrenom("Updated Prenom");
        request.setEmail("updated@example.com");

        when(utilisateursRepository.findById(anyInt())).thenReturn(existingUser);

        UtilisateurResponce result = utilisateursService.updateUtilisateur(1, request);

        assertNotNull(result);
        assertEquals(request.getNom(), result.getNom());
        verify(utilisateursRepository, times(1)).persist(existingUser);
    }

    @Test
    void testDeleteUtilisateur() {
        Utilisateurs user = new Utilisateurs();
        user.setId_utilisateur(1);

        when(utilisateursRepository.findById(anyInt())).thenReturn(user);

        utilisateursService.deleteUtilisateur(1);

        verify(ressourceService, times(1)).deleteRessourcebyCreateur(user.getId_utilisateur());
        verify(favorieService, times(1)).deleteFavoriebyUtilisateur(user.getId_utilisateur());
        verify(utilisateursRepository, times(1)).delete(user);
    }

    @Test
    void testUpdateUtilisateurStatu() {
        Utilisateurs user = new Utilisateurs();
        user.setId_utilisateur(1);

        ChangeStatu changeStatu = new ChangeStatu();
        changeStatu.setStatu("normal");

        when(utilisateursRepository.findById(anyInt())).thenReturn(user);

        Utilisateurs result = utilisateursService.updateUtilisateurStatu(1, changeStatu);

        assertNotNull(result);
        assertEquals(EtatUtilisateur.normal, result.getEtat_utilisateur());
        verify(utilisateursRepository, times(1)).persist(user);
    }

    @Test
    void testAddProfileImageToUser() {
        Utilisateurs user = new Utilisateurs();
        user.setId_utilisateur(1);

        FileUpload fileUpload = mock(FileUpload.class);

        when(utilisateursRepository.findById(anyInt())).thenReturn(user);

        UtilisateurResponce result = utilisateursService.addProfileImageToUser(1, fileUpload);

        assertNotNull(result);
        verify(imagesService, times(1)).addImage(anyString(), eq(fileUpload));
        verify(utilisateursRepository, times(1)).persist(user);
    }

}
