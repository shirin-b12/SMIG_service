package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.acme.model.Relations;
import org.acme.model.TypesRelation;
import org.acme.model.Utilisateurs;
import org.acme.request.RelationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestHTTPEndpoint(RelationController.class)
public class RelationControllerIT {

    @Mock
    private Relations mockRelations;
    @Mock
    private RelationRequest mockRelationRequest;
    @Mock
    private Utilisateurs mockUtilisateur01;
    @Mock
    private Utilisateurs mockUtilisateur02;
    @Mock
    private Utilisateurs mockUtilisateur03;
    @Mock
    private TypesRelation mockTypesRelation01;
    @Mock
    private TypesRelation mockTypesRelation02;

    private String token;

    @BeforeEach
    public void setUp() {

        mockTypesRelation01 = new TypesRelation();
        mockTypesRelation01.setIdTypeRelation(69);
        mockTypesRelation01.setIntitule("DES GROS GAYS CA MERE");

        mockTypesRelation02 = new TypesRelation();
        mockTypesRelation02.setIdTypeRelation(70);
        mockTypesRelation02.setIntitule("Non tout va bien vasy mets moi Validé");

        mockUtilisateur01 = new Utilisateurs();
        mockUtilisateur01.setId_utilisateur(90);

        mockUtilisateur02 = new Utilisateurs();
        mockUtilisateur02.setId_utilisateur(91);

        mockUtilisateur03 = new Utilisateurs();
        mockUtilisateur03.setId_utilisateur(92);

        mockRelationRequest = new RelationRequest(mockUtilisateur01.getId_utilisateur(), mockUtilisateur02.getId_utilisateur(), mockTypesRelation01.getIdTypeRelation());

        mockRelations = new Relations();
        mockRelations.setIdRelation(99);
        mockRelations.setUtilisateur1(mockUtilisateur01);
        mockRelations.setUtilisateur2(mockUtilisateur02);
        mockRelations.setTypeRelation(mockTypesRelation01);

        loginAndRetrieveToken();
    }

    private void loginAndRetrieveToken() {
        Response response = given()
                .contentType(ContentType.JSON)
                //.body("{\"email\":\"test\", \"mot_de_passe\":\"test\"}")
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
                .header("Authorization", "Bearer " + token)
                .body(mockRelationRequest)
                .when().post()
                .then()
                .statusCode(201)
                .body("utilisateur1", is(notNullValue()))
                .body("utilisateur2", is(notNullValue()))
                .extract().response();

        // Uncomment for debugging purposes
        //System.out.println("\n " + response.getBody().print() + " \n");
    }

    @Test
    public void testGetAllRelations() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
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
                .header("Authorization", "Bearer " + token)
                .pathParam("id", mockRelations.getIdRelation())
                .when().get("/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        // Uncomment for debugging purposes
        System.out.println("\n " + response.getBody().print() + " \n");
    }

    @Test
    public void testUpdateRelation() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(mockRelations)
                .pathParam("id", mockRelations.getIdRelation())
                .when().put("/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        //System.out.println("\n " + response.jsonPath().get() + " \n");
    }

    @Test
    public void testDeleteRelation() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("id", mockRelations.getIdRelation())
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
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", mockUtilisateur01.getId_utilisateur())
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
        int userId1 = 21;
        int userId2 = 21;

        Response response = given()
                .header("Authorization", "Bearer " + token)
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
