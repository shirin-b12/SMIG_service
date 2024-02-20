package org.acme.controller;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.Utilisateurs;
import org.acme.service.UtilisateursService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@QuarkusTest
@TestHTTPEndpoint(UtilisateursController.class)
class UtilisateursControllerIT {

    @InjectMock
    UtilisateursService utilisateurService;

    @Test
    void testGetUtilisateurs() {
        List<Utilisateurs> mockUtilisateurs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Utilisateurs utilisateur = new Utilisateurs();
            utilisateur.id_utilisateur = i;
            utilisateur.nom = "Nom" + i;
            utilisateur.prenom = "Prenom" + i;
            utilisateur.email = "email" + i + "@example.com";
            mockUtilisateurs.add(utilisateur);
        }

        when(utilisateurService.listAll()).thenReturn(mockUtilisateurs);

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
        Utilisateurs credentials = new Utilisateurs();
        credentials.id_utilisateur = 1;
        credentials.mot_de_passe = "password";
        String expectedToken = "token123";

        when(utilisateurService.login(anyInt(), anyString())).thenReturn(expectedToken);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(credentials)
                .when().post("/login")
                .then()
                .statusCode(200)
                .body(is(expectedToken));

        // Test d'échec de connexion
        when(utilisateurService.login(anyInt(), anyString())).thenReturn(null);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(credentials)
                .when().post("/login")
                .then()
                .statusCode(401); // Unauthorized
    }
}
