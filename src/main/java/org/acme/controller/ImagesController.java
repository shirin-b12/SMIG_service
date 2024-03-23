package org.acme.controller;


import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import org.acme.model.Images;
import org.acme.service.ImagesService;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

@Path("/images")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImagesController {

    @Inject
    ImagesService imagesService;

    @GET
    @PermitAll
    @Path("/{id}")
    public Response imagesParID(@PathParam("id") int id) {
        Images image = imagesService.imagesParID(id);
        BufferedImage bufferedImage = imagesService.convertBytesToImage(image.getFichier());
        StreamingOutput stream = output -> ImageIO.write(bufferedImage, "jpg", output);
        return Response.ok(stream, MediaType.APPLICATION_OCTET_STREAM).build();

    }

    @POST
    @PermitAll
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createImage(@RestForm String description, @RestForm("image") FileUpload fileUpload) {
        Images createImage = imagesService.addImage(description, fileUpload);
        if(createImage != null) {
            return Response.ok(createImage).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


}
