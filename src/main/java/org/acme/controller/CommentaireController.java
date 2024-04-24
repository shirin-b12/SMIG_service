package org.acme.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.Commentaires;
import org.acme.response.CommentaireResponce;
import org.acme.request.CommentaireRequest;
import org.acme.service.CommentaireService;

import java.util.List;

@Path("/commentaire")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CommentaireController {
    @Inject
    CommentaireService commentaireService;

    @GET
    public List<CommentaireResponce> getCommentaires() {
        return commentaireService.listAll();
    }
    @POST
    @PermitAll
    @Transactional
    public Commentaires createCommentaire(CommentaireRequest commentaires) {
        return commentaireService.createCommentaire(commentaires);
    }
    @GET
    @Path("/{idRessource}")
    public List<CommentaireResponce> getCommentairesByRessource(@PathParam("idRessource") int idRessource) {
        return commentaireService.getCommentsByRessourceId(idRessource);
    }
}
