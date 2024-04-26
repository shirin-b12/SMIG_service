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
import org.acme.request.UpdateUserRequest;
import org.acme.request.ChangeStatu;
import org.acme.request.UtilisateurLoginRequest;
import org.acme.response.UtilisateurResponce;
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
    public List<UtilisateurResponce> getUtilisateurs() {
        return utilisateurService.listAll();
    }

    @POST
    @Path("/login")
    @PermitAll
    public Response login(UtilisateurLoginRequest credentials) {
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
        UtilisateurResponce createdUser = utilisateurService.addUtilisateur(utilisateur);
        if (createdUser != null) {
            return Response.ok(createdUser).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    //@RolesAllowed("Utilisateur")
    public Response getUser(@PathParam("id") int id) {
        UtilisateurResponce user = utilisateurService.findById(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Utilisateur")
    @Transactional
    public Response updateUtilisateur(@PathParam("id") int id, UpdateUserRequest request) {
        UtilisateurResponce updatedUser = utilisateurService.updateUtilisateur(id, request);
        if (updatedUser != null) {
            return Response.ok(updatedUser).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/statu/{id}")
    @Transactional
    public Response updatestatu(@PathParam("id") int id, ChangeStatu utilisateur) {
        UtilisateurResponce existingUser = utilisateurService.findById(id);
        utilisateur.setId(id);
        if (existingUser == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Utilisateurs updatedUser = utilisateurService.updateUtilisateurStatu(id, utilisateur);
        if (updatedUser != null) {
            return Response.ok(updatedUser).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Admin")
    @Transactional
    public Response deleteUtilisateur(@PathParam("id") int id) {
        try {
            utilisateurService.deleteUtilisateur(id);
            return Response.ok("User deleted successfully").build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
