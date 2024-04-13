package org.acme.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Categories;
import org.acme.service.CategoriesService;

import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CategoriesController {

    @Inject
    CategoriesService categoriesService;

    @GET
    @PermitAll
    @Path("/all")
    public List<Categories> getCategories() {
        return categoriesService.listAll();
    }

    @POST
    @Transactional
    @PermitAll
    public Response createCategory(Categories category) {
        Categories createdCategory = categoriesService.createCategory(category);
        if (createdCategory != null) {
            return Response.ok(createdCategory).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
