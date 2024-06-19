package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(CategoriesController.class)

// Test unitaire d'API pour "CategoriesControllerIT"

public class CategoriesControllerIT {
    private String token;

    @BeforeEach
    public void setUp() {
        //RestAssured.baseURI = "http://localhost:8081"; // or the actual base URL if different
    }

    @Test
    public void testGetCategories() {
        given()
                .contentType(ContentType.JSON)
                .when().get("http://localhost:8081/categories")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateCategory() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nom_cat\":\"technologie\"}")
                .when().post("http://localhost:8081/categories")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateCategoryFailed() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nom\":\"technologie\"}")
                .when().post("http://localhost:8081/categories")
                .then()
                .statusCode(400);
    }
}