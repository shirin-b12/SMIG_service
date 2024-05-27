package org.acme.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Relations;
import org.acme.request.RelationRequest;
import org.acme.service.RelationService;

import java.util.List;

@Path("/relation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("Utilisateur")
@ApplicationScoped
public class RelationController {

    @Inject
    RelationService relationService;

    @GET
    public Response getAllRelations() {
        return Response.ok(relationService.listAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getRelation(@PathParam("id") int id) {
        try {
            Relations relations = relationService.findById(id);
            return Response.ok(relations).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Relations not found").build();
        }
    }

    @POST
    @Transactional
    public Response addRelation(RelationRequest relations) {
        Relations newRelations = relationService.addRelation(relations);
        return Response.status(Response.Status.CREATED).entity(newRelations).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateRelation(@PathParam("id") int id, Relations relations) {
        Relations updatedRelations = relationService.updateRelation(id, relations);
        if (updatedRelations != null) {
            return Response.ok(updatedRelations).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Relations not found").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteRelation(@PathParam("id") int id) {
        try {
            relationService.deleteRelation(id);
            return Response.ok("Relations deleted successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Relations not found").build();
        }
    }

    @GET
    @Path("/user/{userId}")
    public Response getRelationsByUserId(@PathParam("userId") int userId) {
        List<Relations> relations = relationService.getRelationsByUserId(userId);
        if (relations.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No relations found for user ID: " + userId).build();
        }
        return Response.ok(relations).build();
    }

    // In RelationController.java
    @GET
    @Path("/exists/{userId1}/{userId2}")
    public Response checkRelationExists(@PathParam("userId1") int userId1, @PathParam("userId2") int userId2) {
        boolean exists = relationService.checkExistingRelation(userId1, userId2);
        return Response.ok(exists).build();
    }

}
