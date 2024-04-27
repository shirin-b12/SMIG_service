package org.acme.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.response.RessourcesResponce;
import org.acme.response.TopRessourceFavoris;
import org.acme.response.TypeMoisRessource;
import org.acme.service.StatAdminService;

import java.util.List;

@Path("/stat/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class StatAdminController {
    @Inject
    StatAdminService statService;
    @GET
    @Path("/nombreuDeRessoucesParCategoriesParMoi")
    public List<TypeMoisRessource> getNombreDeRessoucesParCategoriesParMoi() {
       return statService.getNombreDeRessoucesParCategoriesParMoi();

    }
    @GET
    @Path("/nombreuDeRessoucesParTagParMoi")
    public List<TypeMoisRessource> getNombreDeRessoucesParTagsParMoi() {
        return statService.getNombreDeRessoucesParTagsParMoi();

    }
    @GET
    @Path("/TopRessourceDansFavoris")
    public List<TopRessourceFavoris> getTopRessourceDansFavoris() {
        return statService.getTopRessourceDansFavoris();

    }
    @GET
    @Path("/TopCreateur")
    public List<TopCreateur> getTopCreateur() {
        return statService.getTopCreators();
    }
    @GET
    @Path("/TopRessource")
    public List<RessourcesResponce> getTopRessource() {
        return statService.getTopFastestResources();
    }

}
