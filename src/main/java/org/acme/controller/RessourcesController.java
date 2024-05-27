package org.acme.controller;

import io.smallrye.mutiny.Multi;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Images;
import org.acme.model.Ressources;
import org.acme.request.RessourcesRequest;
import org.acme.response.RessourcesResponce;
import org.acme.service.*;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Path("/ressources")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class RessourcesController {

    @Inject
    RessourcesService ressourcesService;

    @Inject
    CategoriesService categoriesService;

    @Inject
    TypeService typeService;

    @Inject
    TagService tagService;

    @Inject
    ImagesService imagesService;

    @Inject
    UtilisateursService utilisateursService;

    @Inject
    Event<Ressources> newRessourceEvent;

    @GET
    @Path("/all")
    public List<RessourcesResponce> getRessources() {
        return ressourcesService.listAll();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed("Utilisateur")
    @Transactional
    public Response createRessource(
            @RestForm String idCreateur,
            @RestForm FileUpload img,
            @RestForm String titre,
            @RestForm String description,
            @RestForm String idCat,
            @RestForm String idType,
            @RestForm String idTag) {
        try {
            Ressources ressource = new Ressources();
            ressource.setTitre(titre);
            ressource.setDescription(description);
            ressource.setDate_de_creation(LocalDateTime.now());
            ressource.setVisibilite(1);
            ressource.setType(typeService.findById(Integer.parseInt(idType)));
            ressource.setTag(tagService.findById(Integer.parseInt(idTag)));
            ressource.setCategorie(categoriesService.findById(Integer.parseInt(idCat)));
            ressource.setCreateur(utilisateursService.findUserById(Integer.parseInt(idCreateur)));

            if (img != null) {
                Images image = imagesService.addImage( "Image for resource id : "+ressource.getId_ressource(), img);
                ressource.setImage(image);
            }

            ressourcesService.createRessource(ressource.toRessourcesRequest());
            return Response.ok(ressource).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    @POST
    @Transactional
    @RolesAllowed("Utilisateur")
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
    @RolesAllowed("Utilisateur")
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
    @Path("/{id}")
    @Transactional
    @RolesAllowed("Utilisateur")
    public Response updateRessource(@PathParam("id") int id, RessourcesRequest request) {
        Ressources updatedRessource = ressourcesService.updateRessource(id, request);
        if (updatedRessource != null) {
            return Response.ok(updatedRessource).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @DELETE
    @Path("/{id}")
    @RolesAllowed("Utilisateur")
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
