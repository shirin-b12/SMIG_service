package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.acme.model.Relations;
import org.acme.request.RelationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import org.mockito.Mock;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestHTTPEndpoint(RelationController.class)
public class RelationControllerIT {

    @Mock
    private Relations mockRelations;

    @Mock
    private  RelationRequest mockRelationRequest;
    private int idTest = 1;
    private int userIdTest = 21;
    private String token;

    @BeforeEach
    public void setUp() {

        mockRelations = new Relations();
        mockRelationRequest = new RelationRequest(21, 21, idTest);
        // Initialize mockRelations with necessary data
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
    public void testAddRelation() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(mockRelationRequest)
                .when().post()
                .then()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .extract().response();

        // Uncomment for debugging purposes
        System.out.println("\n " + response.getBody().print() + " \n");
    }

    @Test
    public void testGetAllRelations() {
        Response response = given()
                .when().get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        // Uncomment for debugging purposes
        System.out.println("\n " + response.getBody().print() + " \n");
    }

    @Test
    public void testGetRelation() {
        Response response = given()
                .pathParam("id", idTest)
                .when().get("/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        // Uncomment for debugging purposes
        System.out.println("\n " + response.getBody().print() + " \n");
    }

    @Test
    public void testUpdateRelation() {
        Relations updatedRelations = new Relations();
        // Initialize updatedRelations with necessary data

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedRelations)
                .pathParam("id", idTest)
                .when().put("/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        // Uncomment for debugging purposes
        System.out.println("\n " + response.jsonPath().get() + " \n");
    }

    @Test
    public void testDeleteRelation() {
        Response response = given()
                .pathParam("id", idTest)
                .when().delete("/{id}")
                .then()
                .statusCode(200)
                .body(is("Relations deleted successfully"))
                .extract().response();

        // Uncomment for debugging purposes
        System.out.println("\n " + response.getBody().print() + " \n");
    }

    @Test
    public void testGetRelationsByUserId() {
        Response response = given()
                .pathParam("userId", userIdTest)
                .when().get("/user/{userId}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        // Uncomment for debugging purposes
        System.out.println("\n " + response.getBody().print() + " \n");
    }

    @Test
    public void testCheckRelationExists() {
        int userId1 = 1111;
        int userId2 = 2222;

        Response response = given()
                .pathParam("userId1", userId1)
                .pathParam("userId2", userId2)
                .when().get("/exists/{userId1}/{userId2}")
                .then()
                .statusCode(200)
                .extract().response();

        // Uncomment for debugging purposes
        System.out.println("\n " + response.getBody().print() + " \n");
    }
}
