package org.acme.controller;

import io.smallrye.mutiny.Multi;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Ressources;
import org.acme.request.RessourcesRequest;
import org.acme.response.RessourcesResponce;
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
    public List<RessourcesResponce> getRessources() {
        return ressourcesService.listAll();
    }

    @POST
    @Transactional
    @PermitAll
    public Response createRessource(RessourcesRequest request) throws Exception {
        RessourcesResponce createdRessource = ressourcesService.createRessource(request);
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
    public Response getRessourceById(@PathParam("id") int id) throws Exception {
        RessourcesResponce ressources = ressourcesService.findById(id);
        if (ressources != null) {
            return Response.ok(ressources).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PUT
    @Path("/{ressourceId}/image/{image}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response linkImage(@PathParam("ressourceId") int ressourceId, @PathParam("image") int image) {
        Ressources ressourceWithImage = ressourcesService.linkImage(image, ressourceId);
        if (ressourceWithImage != null) {
            return Response.ok(ressourceWithImage).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Transactional
    @PermitAll
    public Response updateRessource(@PathParam("id") int id, RessourcesRequest request) {
        Ressources updatedRessource = ressourcesService.updateRessource(id, request);
        if (updatedRessource != null) {
            return Response.ok(updatedRessource).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @DELETE
    @Path("/delete/{id}")
    //@RolesAllowed("Utilisateur")
    @PermitAll
    @Transactional
    public Response deleteRessource(@PathParam("id") int id) throws Exception {
        ressourcesService.deleteRessource(id);
        return Response.noContent().build();
    }
    @PUT
    @Path("/validate/{id}")
    @Transactional
    public Response validateRessource(@PathParam("id") int id) throws Exception {
        Ressources validatedRessource = ressourcesService.validateRessource(id);
        if (validatedRessource != null) {
            return Response.ok(validatedRessource).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/byCreateur/{createurId}")
    public Response getRessourcesByCreateur(@PathParam("createurId") int createurId) {
        List<RessourcesResponce> ressources = ressourcesService.findByCreateurId(createurId);
        if (ressources != null && !ressources.isEmpty()) {
            return Response.ok(ressources).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}
