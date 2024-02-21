package org.acme.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Utilisateurs;
import org.acme.service.UtilisateursService;

import java.util.List;

@Path("/utilisateur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateursController {

    @Inject
    UtilisateursService utilisateurService;

    @GET
    @PermitAll
    public List<Utilisateurs> getUtilisateurs() {
        return utilisateurService.listAll();
    }

    @POST
    @Path("/login")
    public Response login(Utilisateurs credentials) {
        String token = utilisateurService.login(credentials.email, credentials.mot_de_passe);
        if (token != null) {
            return Response.ok(token).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Transactional
    public Response addUtilisateur(Utilisateurs utilisateur) {
        Utilisateurs createdUser = utilisateurService.addUtilisateur(utilisateur);
        if (createdUser != null) {
            return Response.ok(createdUser).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("Utilisateur")
    public Response getUser(@PathParam("id") Long id) {
        Utilisateurs user = utilisateurService.findById(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
