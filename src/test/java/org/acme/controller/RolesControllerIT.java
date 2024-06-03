package org.acme.controller;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.model.Roles;
import org.acme.service.RolesService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(RolesController.class)
public class RolesControllerIT {

    @InjectMock
    RolesService rolesServiceMock;

    @Test
    void testListAll(){
        List<Roles> rolesList = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            Roles x = new Roles();
            x.setId_role(i);
            x.setNom_role("Role " + i);

            rolesList.add(x);
        }

        when(rolesServiceMock.listAll()).thenReturn(rolesList);

        given()
            .basePath("/roles")
            .when().get()
            .then()
            .statusCode(200)
            .body("", hasSize(rolesList.size()))
            .body("id_role", everyItem(notNullValue()))
            .body("nom_role", everyItem(notNullValue()));

        verify(rolesServiceMock).listAll();
    }

    @Test
    void testCreationRole(){
        Roles role = new Roles();
        role.setId_role(1);
        role.setNom_role("Test Role");

        when(rolesServiceMock.createRole(role)).thenReturn(role);

        ArgumentCaptor<Roles> argumentCaptor = ArgumentCaptor.forClass(Roles.class);

        given()
            .basePath("/roles")
            .contentType(ContentType.JSON)
            .body(role)
        .when()
            .post()
        .then()
            .statusCode(200);

        verify(rolesServiceMock).createRole(argumentCaptor.capture());

        Roles capturedRole = argumentCaptor.getValue();
        assertEquals(role.getId_role(), capturedRole.getId_role());
        assertEquals(role.getNom_role(), capturedRole.getNom_role());
    }
}
