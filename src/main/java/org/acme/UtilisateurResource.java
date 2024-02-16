package org.acme;


import jakarta.transaction.Transactional;
import model.Utilisateurs;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/utilisateur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurResource {

    @Inject
    EntityManager entityManager;

    @GET
    public List<Utilisateurs> getUtilisateurs() {
        return Utilisateurs.listAll();
    }

    @POST
    @Transactional
    public void addUtilisateur(Utilisateurs utilisateur) {
        utilisateur.persist();
    }
}
