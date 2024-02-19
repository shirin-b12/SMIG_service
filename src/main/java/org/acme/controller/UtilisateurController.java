package org.acme.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Utilisateur;
import org.acme.service.UtilisateurService;

import java.util.List;

@Path("/utilisateur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurController {

    @Inject
    UtilisateurService utilisateurService;

    @GET
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurService.listAll();
    }

    @POST
    @Path("/login")
    public Response login(Utilisateur credentials) {
        String token = utilisateurService.login(credentials.id_utilisateur, credentials.mot_de_passe);
        if (token != null) {
            return Response.ok(token).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Transactional
    @RolesAllowed("Admin")
    public Response addUtilisateur(Utilisateur utilisateur) {
        Utilisateur createdUser = utilisateurService.addUtilisateur(utilisateur);
        if (createdUser != null) {
            return Response.ok(createdUser).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("User")
    public Response getUser(@PathParam("id") Long id) {
        Utilisateur user = utilisateurService.findById(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
