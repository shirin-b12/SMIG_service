package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.acme.model.Relations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestHTTPEndpoint(RelationController.class)
public class RelationControllerIT {

    private String token;

    @BeforeEach
    public void setUp() {
        //RestAssured.baseURI = "http://localhost:8081"; // or the actual base URL if different
        loginAndRetrieveToken();
    }

    private void loginAndRetrieveToken() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"test\", \"mot_de_passe\":\"test\"}")
                .when().post("http://localhost:8081/utilisateur/login")
                .then()
                .statusCode(200)
                .extract().response();

        token = response.jsonPath().getString("accessToken");
    }

    @Test
    @Transactional
    public void testCreateRelation() {
        // Mock a relation object
        Relations relation = new Relations();
        relation.setIdRelation(9999); // Set any necessary fields

        // Send a POST request to create the relation
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(relation)
                .when()
                .post()
                .then()
                .statusCode(201); // Assuming 201 Created status code for successful creation
        // You can add more assertions if needed
    }

    @Test
    public void testGetRelationById() {
        // Assuming the endpoint "/{id}" retrieves a relation by its ID
        given()
                .when()
                .header("Authorization", "Bearer " + token)
                .get("/{id}", 1) // Assuming ID 1 exists
                .then()
                .statusCode(200)
                .body("id_relation", equalTo(1)); // Adjust the expected response body according to your implementation
    }

    // Add similar tests for other CRUD operations like update and delete

}