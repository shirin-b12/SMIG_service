package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.Images;
import org.acme.model.Utilisateurs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestHTTPEndpoint(ImagesController.class)
public class  ImagesControllerIT {

    private String token;

    @BeforeEach
    public void setUp() {
        //RestAssured.baseURI = "http://localhost:8081"; // or the actual base URL if different
        loginAndRetrieveToken();
    }

    private void loginAndRetrieveToken() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"shirin@laqueen.com\", \"mot_de_passe\":\"123\"}")
                .when().post("http://localhost:8081/utilisateur/login")
                .then()
                .statusCode(200)
                .extract().response();

        token = response.jsonPath().getString("accessToken");
    }

    @Test
    public void testGetImageById() {
        int testImageId = 1; // Assuming an image with ID 1 exists in the database
        given()
                .when().get("http://localhost:8081/images/14")
                .then()
                .statusCode(200)
                .contentType(ContentType.BINARY);
    }

    @Test
    public void testCreateImage() {
        File testImageFile = new File("src/main/resources/test-image.jpg");

        given()
                .multiPart("description", "Test Image")
                .multiPart("image", testImageFile)
                .header("Authorization", "Bearer " + token) // Utilisez le jeton récupéré
                .header("Content-Type", "multipart/form-data")
                .when().post("http://localhost:8081/images")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testCreateImageUnauthorized() {
        File testImageFile = new File("src/main/resources/test-image.jpg");

        given()
                .multiPart("legende", "Unauthorized Test Image")
                .multiPart("fichier", testImageFile)
                .when().post("http://localhost:8081/images")
                .then()
                .statusCode(401); // Forbidden because no valid token is provided
    }
}
