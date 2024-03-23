package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Roles;
import org.acme.service.RolesService;

@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RolesController {
    @Inject
    RolesService rolesService;

    @GET
    public Response listAll() {
        return Response.ok(rolesService.listAll()).build();
    }
    @POST
    public Response createRole(Roles role) {

            rolesService.createRole(role);
            return Response.ok(role).build();

    }
}
