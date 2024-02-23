package org.acme.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Tag;
import org.acme.service.TagService;

import java.util.List;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagController {

    @Inject
    TagService tagService;

    @GET
    @PermitAll
    public List<Tag> getsTags() {
        return tagService.listAll();
    }

    @POST
    @Transactional
    @PermitAll
    public Response createTag(Tag tag) {
        Tag createdTag = tagService.createTag(tag);
        if (createdTag != null) {
            return Response.ok(createdTag).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
