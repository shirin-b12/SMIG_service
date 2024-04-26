package org.acme.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Type;
import org.acme.service.TypeService;

import java.util.List;

@Path("/types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TypeController {

    @Inject
    TypeService typeService;

    @GET
    @PermitAll
    public List<Type> getTypes() {
        return typeService.listAll();
    }

    @POST
    @Transactional
    @RolesAllowed("Moderateur")
    public Response createType(Type type) {
        Type createdType = typeService.createType(type);
        if (createdType != null) {
            return Response.ok(createdType).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
