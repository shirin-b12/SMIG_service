package org.acme.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.service.FavorieService;

@Path("/favori")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("Utilisateur")
public class FavorieController {
    @Inject
    FavorieService favorieService;
    @POST
    @Path("/{id_utilisateur}/{id_ressource}")
    @Transactional
    public void addFavorie(int id_utilisateur, int id_ressource) {
        favorieService.addFavorie(id_utilisateur, id_ressource);
    }
    @GET
    public void listFavorie(int id_utilisateur) {
        favorieService.listFavorie(id_utilisateur);
    }
    @DELETE
    @Path("/{id_ressource}")
    public void removeFavorie(int id_ressource, int id_utilisateur) {
        favorieService.removeFavorie(id_ressource, id_utilisateur);
    }
}
