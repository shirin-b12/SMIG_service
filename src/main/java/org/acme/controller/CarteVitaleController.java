package org.acme.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.request.CarteVitaleRequest;
import org.acme.service.CarteVitaleService;

@Path("/carteVitale")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped

public class CarteVitaleController {
    @Inject
    CarteVitaleService carteVitaleService;

    @POST
    public Response verificationCarteVitale(CarteVitaleRequest request) {
        boolean confirmation = carteVitaleService.verificationCarteVitale(request.getCarteVitale());
        if (confirmation) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}