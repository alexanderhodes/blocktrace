package me.alexanderhodes.blocktrace.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import me.alexanderhodes.blocktrace.model.Shipment;
import me.alexanderhodes.blocktrace.service.ShipmentService;

/**
 * Created by alexa on 21.09.2017.
 */
@Path("shipment")
public class ShipmentEndpoint {

    @Inject
    private ShipmentService shipmentService;

    /**
     * Endpoint for creating a shipment
     * 
     * @param shipment Shipment
     * @return Response that shipment has been created
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createShipment (Shipment shipment) {
    	// create shipment
        shipment = shipmentService.persist(shipment);
        // send response to client
        return Response.created(UriBuilder.fromResource(ShipmentEndpoint.class).path(String.valueOf(shipment
                .getShipmentId())).build()).build();
    }

    /**
     * Endpoint for requesting a shipment by shipmentId
     * 
     * @param shipmentId id of requested shipment
     * @return Response containing requested shipment
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{shipment}")
    public Response getShipment (@PathParam("shipment") String shipmentId) {
    	// request shipment
        Shipment shipment = shipmentService.findShipment(shipmentId);
        if (shipment != null) {
        	// send response to client
            return Response.ok(shipment, MediaType.APPLICATION_JSON).build();
        } else {
        	// shipment was not found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint for get List of shipments
     * 
     * @return List of shipments
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShipments () {
    	// request shipment list
        List<Shipment> shipmentList = shipmentService.listAll();
        // send response to client
        return Response.ok(shipmentList).build();
    }

    /**
     * Endpoint for updating a shipment
     * 
     * @param shipmentId id of shipment to update
     * @param shipment Shipment which has to be updated
     * @return response containing updated shipment
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{shipment}")
    public Response updateShipment (@PathParam("shipment") String shipmentId, Shipment shipment) {
        shipment = shipmentService.merge(shipment);

        return Response.ok(shipment).build();
    }

}
