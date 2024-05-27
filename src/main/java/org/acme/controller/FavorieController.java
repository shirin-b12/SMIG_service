package org.acme.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.response.FavorieResponce;
import org.acme.request.FavorieRequest;
import org.acme.service.FavorieService;

import java.util.List;

@Path("/favori")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"Utilisateur","Moderateur","Admin"})
public class FavorieController {
    @Inject
    FavorieService favorieService;

    @POST
    @Transactional
    public Response addFavorie(FavorieRequest favorieRequest) {
       return favorieService.addFavori(favorieRequest);
    }
    @GET
    @Path("/{id}")
    public List<FavorieResponce> listFavorie(@PathParam("id") int id_utilisateur) {
       return favorieService.listFavorieResponce(id_utilisateur);
    }
    @Transactional
    @DELETE
    public Response removeFavorie(FavorieRequest favorieRequest) {
        return favorieService.removeFavorie(favorieRequest);
    }
}
