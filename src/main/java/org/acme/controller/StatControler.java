package org.acme.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.Categories;
import org.acme.model.Type;
import org.acme.response.CategorieStatSurRessource;
import org.acme.response.RessourcesResponce;
import org.acme.service.StatService;

import java.util.List;

@Path("/stat/utilisateur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class StatControler {
    @Inject
    StatService statService;

    @GET
    @Path("/topCategoriesSurFavoris/{id}")
    public List<Categories> getStat(@PathParam("id") int id) {
        return statService.getTopCategories(id);
    }

    @GET
    @Path("/topTypeSurFavoris/{id}")
    public List<Type> getStatType(@PathParam("id") int id) {
        return statService.getTopType(id);
    }

    @GET
    @Path("/TopRessource/{id}")
    public List<RessourcesResponce> getTopViewdResource(@PathParam("id") int id) {
        return statService.getTopViewedResources(id);
    }

    @GET
    @Path("/TopFavorie/{id}")
    public List<RessourcesResponce> getTopFavorie(@PathParam("id") int id) {
        return statService.getTopViewedRessourcesInFavoris(id);
    }

    @GET
    @Path("/CategoriesSurRessources/{id}")
    public List<CategorieStatSurRessource> getCategoriesSurRessources(@PathParam("id") int id) {
        return statService.getCategoriesSurRessources(id);
    }
}
