package org.acme.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;
import org.acme.request.RessourcesRequest;
import org.acme.service.RessourcesService;

import java.util.List;
import java.util.Optional;

@Path("/ressources")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RessourcesController {

    @Inject
    RessourcesService ressourcesService;

    @GET
    @PermitAll
    public List<Ressources> getRessources() {
        return ressourcesService.listAll();
    }

    @POST
    @Transactional
    @PermitAll
    public Response createRessource(RessourcesRequest request) {
        Optional<Ressources> createdRessource = ressourcesService.createRessource(
                request.ressource,
                request.idCat,
                request.idType,
                request.idTag,
                request.idCreateur
        );
        if (createdRessource.isPresent()) {
            return Response.ok(createdRessource.get()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
