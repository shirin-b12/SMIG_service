package org.acme.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Categories;
import org.acme.request.CarteVitalRequest;
import org.acme.service.CarteVitalService;

@Path("/carteVital")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
//fake carte vital

public class CarteVitalControler {
    @Inject
    CarteVitalService carteVitalService;

    @POST
    public Response verificationCarteVital(CarteVitalRequest request) {
        boolean confirmation = carteVitalService.verificationCarteVital(request.getCarteVital());
        if (confirmation) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
