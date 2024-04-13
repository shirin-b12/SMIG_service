package org.acme.controller;

import io.smallrye.mutiny.Multi;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Images;
import org.acme.model.Ressources;
import org.acme.model.Utilisateurs;
import org.acme.request.RessourcesRequest;
import org.acme.request.RessourcesResponce;
import org.acme.service.RessourcesService;

import java.util.List;


@Path("/ressources")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RessourcesController {

    @Inject
    RessourcesService ressourcesService;

    @Inject
    Event<Ressources> newRessourceEvent;

    @GET
    @PermitAll
    @Path("/all")
    public List<Ressources> getRessources() {
        return ressourcesService.listAll();
    }

    @POST
    @Transactional
    @PermitAll
    public Response createRessource(RessourcesRequest request) {
        Ressources createdRessource = ressourcesService.createRessource(request);
        if (createdRessource != null) {
            return Response.ok(createdRessource).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @PermitAll
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<RessourcesResponce> streamDesRessources() {
        return ressourcesService.getRessourceStream();
    }

    @GET
    @Path("/{id}")
    public Response getRessourceById(@PathParam("id") int id) {
        Ressources user = ressourcesService.findById(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PUT
    @Transactional
    @PermitAll
    public Response linkImage(Images image, int ressourceId) {
        Ressources ressourceWithImage = ressourcesService.linkImage(image, ressourceId);
        if (ressourceWithImage != null) {
            return Response.ok(ressourceWithImage).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
