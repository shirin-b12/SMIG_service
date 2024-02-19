package org.acme;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import model.Utilisateurs;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.TokenService;

@Path("/utilisateur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurResource {

    @Inject
    EntityManager entityManager;
    @Inject
    TokenService tokenService;
    @Inject
    JsonWebToken jwt;
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurResource.class);

    @GET
    @RolesAllowed("User")
    public List<Utilisateurs> getUtilisateurs() {

        return Utilisateurs.listAll();
    }
    @POST
    @Path("/login")
    public Response login(Utilisateurs credentials) {
        Utilisateurs user = entityManager.createQuery("SELECT u FROM Utilisateurs u WHERE u.id_utilisateur = :id_utilisateur", Utilisateurs.class)
                .setParameter("id_utilisateur", credentials.id_utilisateur)
                .getSingleResult();
        if (user != null && user.mot_de_passe.equals(credentials.mot_de_passe)) {
            String token = tokenService.generateToken(user);
            LOGGER.info("Token generated: {}", token);
            return Response.ok(token).build();
        } else {
            LOGGER.warn("Unauthorized access attempt with username: {}", credentials.id_utilisateur);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Transactional
    @RolesAllowed("Admin")
    public Response addUtilisateur(Utilisateurs utilisateur) {
        LOGGER.info("Attempting to add user: {}", utilisateur);
        if (utilisateur != null) {
            utilisateur.persist();
            return Response.ok(utilisateur).build();
        } else {
            LOGGER.warn("Bad request: User object is null");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("User")
    public Response getUser(@PathParam("id") Long id) {
        Utilisateurs user = Utilisateurs.findById(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}