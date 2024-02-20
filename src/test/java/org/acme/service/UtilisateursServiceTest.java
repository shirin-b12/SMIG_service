package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.model.Utilisateurs;
import org.acme.repository.UtilisateursRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Utilisateurs expectedUser = new Utilisateurs();
        expectedUser.id_utilisateur = 1;

        when(utilisateursRepository.findById(anyLong())).thenReturn(expectedUser);

        Utilisateurs result = utilisateursService.findById(1L);

        assertNotNull(result);
        assertEquals(expectedUser.id_utilisateur, result.id_utilisateur);
    }

    @Test
    void testAddUtilisateur() {
        // Création d'un nouvel utilisateur
        Utilisateurs newUser = new Utilisateurs();
        newUser.nom = "Lewis";
        newUser.prenom = "Hamilton";
        newUser.email = "lewis.hamilton@example.com";
        newUser.mot_de_passe = "password";

        doNothing().when(utilisateursRepository).persist(any(Utilisateurs.class));

        Utilisateurs createdUser = utilisateursService.addUtilisateur(newUser);

        verify(utilisateursRepository).persist(newUser);
        assertNotNull(createdUser);
        assertEquals(newUser.nom, createdUser.nom);
    }

    @Test
    void testLoginSuccess() {
        Utilisateurs user = new Utilisateurs();
        user.email = "secure@secure.com";
        user.mot_de_passe = "secure";
        String expectedToken = "token123";

        when(utilisateursRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(user);
        when(tokenService.generateToken(any(Utilisateurs.class))).thenReturn(expectedToken);

        String token = utilisateursService.login("secure@secure.com", "secure");
        assertNotNull(token);
        assertEquals(expectedToken, token);
    }

    @Test
    void testLoginFailure() {
        when(utilisateursRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(null);

        String token = utilisateursService.login("secure@secure.com", "wrongpassword");
        assertNull(token);
    }
}
