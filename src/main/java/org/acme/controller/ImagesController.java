package org.acme.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.Images;
import org.acme.service.ImagesService;

@Path("/images")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImagesController {

    @Inject
    ImagesService imagesService;

    @GET
    @PermitAll
    @Path("/{id}")
    public Images imagesParID(@PathParam("id") int id) {
        Images image = imagesService.imagesParID(id);
        return image;
    }

}
