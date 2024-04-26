package org.acme.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.TypesRelation;
import org.acme.service.TypesRelationService;

@Path("/typesrelation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TypesRelationController {

    @Inject
    TypesRelationService typesRelationService;

    @GET
    public Response getAllTypesRelations() {
        return Response.ok(typesRelationService.listAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getTypesRelation(@PathParam("id") int id) {
        try {
            TypesRelation typesRelation = typesRelationService.findById(id);
            return Response.ok(typesRelation).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("TypesRelation not found").build();
        }
    }

    @POST
    @Transactional
    public Response addTypesRelation(TypesRelation typesRelation) {
        TypesRelation newTypesRelation = typesRelationService.addTypesRelation(typesRelation);
        return Response.status(Response.Status.CREATED).entity(newTypesRelation).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateTypesRelation(@PathParam("id") int id, TypesRelation typesRelation) {
        TypesRelation updatedTypesRelation = typesRelationService.updateTypesRelation(id, typesRelation);
        if (updatedTypesRelation != null) {
            return Response.ok(updatedTypesRelation).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("TypesRelation not found").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteTypesRelation(@PathParam("id") int id) {
        try {
            typesRelationService.deleteTypesRelation(id);
            return Response.ok("TypesRelation deleted successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("TypesRelation not found").build();
        }
    }
}
