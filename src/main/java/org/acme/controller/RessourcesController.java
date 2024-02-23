package org.acme.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;
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
    public void streamDesRessources(@Context SseEventSink eventSink, @Context Sse sse,@Observes Ressources newRessource) {
            try {
                eventSink.send(sse.newEventBuilder()
                        .name("ressources")
                        .data(newRessource)
                        .mediaType(MediaType.APPLICATION_JSON_TYPE)
                        .build());
            } catch (Exception e) {
                eventSink.send(sse.newEvent("Erreur lors de l'envoi de la nouvelle ressource"));
                eventSink.close();
            }
    }
}
