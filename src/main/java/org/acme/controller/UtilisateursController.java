package org.acme.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Utilisateurs;
import org.acme.service.AuthService;
import org.acme.service.UtilisateursService;

import java.util.List;
import java.util.Map;

@Path("/utilisateur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateursController {

    @Inject
    UtilisateursService utilisateurService;
    @Inject
    AuthService authService;

    @GET
    @PermitAll
    public List<Utilisateurs> getUtilisateurs() {
        return utilisateurService.listAll();
    }

    @POST
    @Path("/login")
    @PermitAll
    public Response login(Utilisateurs credentials) {
        Map<String, String> token = authService.login(credentials.getEmail(), credentials.getMot_de_passe());
        if (token != null) {
            return Response.ok(token).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    @POST
    @Path("/refreshtok")
    @PermitAll
    @Consumes(MediaType.TEXT_PLAIN)
    public Response refreshtok(String token) {
        Map<String, String> newToken = authService.login(token);
        if (token != null) {
            return Response.ok(newToken).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Transactional
    @PermitAll
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
    public Response getUser(@PathParam("id") int id) {
        Utilisateurs user = utilisateurService.findById(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Transactional
    public Response updateUtilisateur(@PathParam("id") int id, Utilisateurs utilisateur) {
        Utilisateurs updatedUser = utilisateurService.updateUtilisateur(id, utilisateur);
        if (updatedUser != null) {
            return Response.ok(updatedUser).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
   /* @DELETE
    @Path("/delete/{id}")
    @Transactional
    public Response deleteUtilisateur(@PathParam("id") int id) {
        boolean isDeleted = utilisateurService.deleteUtilisateur(id);
        if (isDeleted) {
            return Response.ok("User deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
    }*/

    @DELETE
    @Path("/delete/{id}")
    public Response deleteUtilisateur(@PathParam("id") int id) {
        try {
            utilisateurService.deleteUtilisateur(id);
            return Response.ok("User deleted successfully").build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
