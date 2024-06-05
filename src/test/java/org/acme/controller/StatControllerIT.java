package org.acme.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.Categories;
import org.acme.model.Type;
import org.acme.response.CategorieStatSurRessource;
import org.acme.response.RessourcesResponce;
import org.acme.service.StatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(StatControler.class)
public class StatControllerIT {

    @Mock
    StatService statService;

    @InjectMocks
    StatControler statController;

    private String token;

    @BeforeEach
    public void setUp() {
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
    public void testGetTopCategories() {
        Categories category1 = new Categories();
        Categories category2 = new Categories();
        Categories category3 = new Categories();
        List<Categories> mockCategories = Arrays.asList(category1, category2, category3);

        when(statService.getTopCategories(1)).thenReturn(mockCategories);

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/topCategoriesSurFavoris/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", equalTo(3));
    }

    @Test
    public void testGetTopType() {
        Type type1 = new Type();
        Type type2 = new Type();
        Type type3 = new Type();
        List<Type> mockTypes = Arrays.asList(type1, type2, type3);

        when(statService.getTopType(1)).thenReturn(mockTypes);

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/topTypeSurFavoris/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", equalTo(3));
    }

    @Test
    public void testGetTopViewedResources() {
        RessourcesResponce res1 = new RessourcesResponce();
        RessourcesResponce res2 = new RessourcesResponce();
        RessourcesResponce res3 = new RessourcesResponce();
        List<RessourcesResponce> mockResources = Arrays.asList(res1, res2, res3);

        when(statService.getTopViewedResources(1)).thenReturn(mockResources);

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/TopRessource/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", equalTo(3));
    }

    @Test
    public void testGetTopViewedRessourcesInFavoris() {
        RessourcesResponce res1 = new RessourcesResponce();
        RessourcesResponce res2 = new RessourcesResponce();
        RessourcesResponce res3 = new RessourcesResponce();
        List<RessourcesResponce> mockResources = Arrays.asList(res1, res2, res3);

        when(statService.getTopViewedRessourcesInFavoris(1)).thenReturn(mockResources);

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/TopFavorie/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", equalTo(3));
    }

    @Test
    public void testGetCategoriesSurRessources() {
        CategorieStatSurRessource catStat1 = new CategorieStatSurRessource("Category1", 10, 1);
        CategorieStatSurRessource catStat2 = new CategorieStatSurRessource("Category2", 15, 1);
        List<CategorieStatSurRessource> mockCategoryStats = Arrays.asList(catStat1, catStat2);

        when(statService.getCategoriesSurRessources(1)).thenReturn(mockCategoryStats);

        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/CategoriesSurRessources/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", equalTo(2));
    }
}
