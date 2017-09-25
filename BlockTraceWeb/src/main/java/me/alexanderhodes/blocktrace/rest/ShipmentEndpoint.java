package me.alexanderhodes.blocktrace.rest;

import me.alexanderhodes.blocktrace.model.Shipment;
import me.alexanderhodes.blocktrace.service.ShipmentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

/**
 * Created by alexa on 21.09.2017.
 */
@Path("shipment")
public class ShipmentEndpoint {

    @Inject
    private ShipmentService shipmentService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createShipment (Shipment shipment) {
        shipment = shipmentService.persist(shipment);
        return Response.created(UriBuilder.fromResource(ShipmentEndpoint.class).path(String.valueOf(shipment
                .getShipmentId())).build()).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{shipment}")
    public Response getShipment (@PathParam("shipment") String shipmentId) {
        Shipment shipment = shipmentService.findShipment(shipmentId);
        if (shipment != null) {
            return Response.ok(shipment, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShipments () {
        List<Shipment> shipmentList = shipmentService.listAll();

        return Response.ok(shipmentList).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{shipment}")
    public Response updateShipment (@PathParam("shipment") String shipmentId, Shipment shipment) {
        shipment = shipmentService.merge(shipment);

        return Response.ok(shipment).build();
    }

}
