package org.acme.controller;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.Utilisateurs;
import org.acme.service.UtilisateursService;
import org.acme.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.*;

@QuarkusTest
@TestHTTPEndpoint(UtilisateursController.class)
class UtilisateursControllerIT {

    @InjectMock
    UtilisateursService utilisateurService;

    @InjectMock
    AuthService authService;

    @BeforeEach
    void setUp() {
        // Resetting mocks before each test to ensure test isolation
        reset(utilisateurService, authService);
    }

    @Test
    void testGetUtilisateurs() {
        // Setup
        List<Utilisateurs> mockUtilisateurs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Utilisateurs utilisateur = new Utilisateurs();
            utilisateur.setId_utilisateur(i);
            utilisateur.setNom("Nom" + i);
            utilisateur.setPrenom("Prenom" + i);
            utilisateur.setEmail("email" + i + "@example.com");
            mockUtilisateurs.add(utilisateur);
        }
        when(utilisateurService.listAll()).thenReturn(mockUtilisateurs);

        // Execute & Verify
        given()
                .when().get()
                .then()
                .statusCode(200)
                .body("", hasSize(mockUtilisateurs.size()))
                .body("id_utilisateur", everyItem(notNullValue()))
                .body("nom", everyItem(notNullValue()))
                .body("prenom", everyItem(notNullValue()))
                .body("email", everyItem(notNullValue()));

        verify(utilisateurService).listAll();
    }

    @Test
    void testLogin() {
        // Setup
        Utilisateurs credentials = new Utilisateurs();
        credentials.setEmail("secure@secure.com");
        credentials.setMot_de_passe("password");
        Map<String, String> expectedTokens = new HashMap<>();
        expectedTokens.put("accessToken", "access123");
        expectedTokens.put("refreshToken", "refresh123");

        when(authService.login(anyString(), anyString())).thenReturn(expectedTokens);

        // Execute & Verify successful login
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(credentials)
                .when().post("/login")
                .then()
                .statusCode(200)
                .body("accessToken", is(expectedTokens.get("accessToken")))
                .body("refreshToken", is(expectedTokens.get("refreshToken")));

        // Setup & Execute & Verify failed login
        when(authService.login(anyString(), anyString())).thenReturn(null);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(credentials)
                .when().post("/login")
                .then()
                .statusCode(401); // Unauthorized
    }

    // New test for adding a user
    @Test
    void testAddUtilisateur() {
        // Setup
        Utilisateurs newUser = new Utilisateurs();
        newUser.setNom("NewUser");
        newUser.setEmail("newuser@example.com");
        newUser.setMot_de_passe("newpassword");

        Utilisateurs persistedUser = new Utilisateurs();
        persistedUser.setId_utilisateur(1);
        persistedUser.setNom(newUser.getNom());
        persistedUser.setEmail(newUser.getEmail());
        persistedUser.setMot_de_passe(newUser.getMot_de_passe());

        when(utilisateurService.addUtilisateur(any())).thenReturn(persistedUser);

        // Execute & Verify
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(newUser)
                .when().post()
                .then()
                .statusCode(200)
                .body("id_utilisateur", is(1))
                .body("nom", is(newUser.getNom()))
                .body("email", is(newUser.getEmail()));
    }

    // New test for updating a user
    @Test
    void testUpdateUtilisateur() {
        // Setup
        int userIdToUpdate = 1;
        Utilisateurs updateUser = new Utilisateurs();
        updateUser.setNom("UpdatedName");
        updateUser.setEmail("updated@example.com");
        updateUser.setMot_de_passe("updatedpassword");

        when(utilisateurService.updateUtilisateur(eq(userIdToUpdate), ArgumentMatchers.<Utilisateurs>any())).thenReturn(updateUser);

        // Execute & Verify
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", userIdToUpdate)
                .body(updateUser)
                .when().put("/update/{id}")
                .then()
                .statusCode(200)
                .body("nom", is(updateUser.getNom()))
                .body("email", is(updateUser.getEmail()));
    }

    // New test for getting a single user by ID
    @Test
    void testGetUser() {
        // Setup
        int userId = 1;
        Utilisateurs user = new Utilisateurs();
        user.setId_utilisateur(userId);
        user.setNom("SpecificUser");
        user.setEmail("specific@example.com");
        user.setMot_de_passe("specificpassword");

        when(utilisateurService.findById(userId)).thenReturn(user);

        // Execute & Verify
        given()
                .pathParam("id", userId)
                .when().get("/{id}")
                .then()
                .statusCode(200)
                .body("id_utilisateur", is(userId))
                .body("nom", is(user.getNom()))
                .body("email", is(user.getEmail()));
    }
}
