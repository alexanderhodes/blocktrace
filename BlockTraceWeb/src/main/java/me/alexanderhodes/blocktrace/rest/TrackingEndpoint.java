package me.alexanderhodes.blocktrace.rest;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import me.alexanderhodes.blocktrace.model.Tracking;
import me.alexanderhodes.blocktrace.service.ShipmentService;
import me.alexanderhodes.blocktrace.service.TrackingService;
import me.alexanderhodes.blocktrace.util.TrackingComparator;

/**
 * Created by alexa on 23.09.2017.
 */
@Path("/tracking")
public class TrackingEndpoint {

    @Inject
    private TrackingService trackingService;

    @Inject
    private ShipmentService shipmentService;

    /**
     * Endpoint for creating a tracking
     * 
     * @param tracking Tracking
     * @return Response that tracking has been created
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTracking (Tracking tracking) {
        // Upload tracking to blockchain
        tracking = trackingService.uploadTracking(tracking);
        // send response to client

        return Response.created(UriBuilder.fromResource(Tracking.class).path(tracking.getShipment().getShipmentId())
        		.build()).build();
    }

    /**
     * Endpoint for requesting all trackings
     * 
     * @return List of all trackings
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrackingList () {
    	// request list of all trackings
        List<Tracking> trackingList = trackingService.getTrackingList();
        // send response to client
        return Response.ok(trackingList).build();
    }

    /**
     * Endpoint for requesting tracking for one specific shipment
     * 
     * @param shipmentId Id of requested shipment
     * @return List of tracking for shipment
     */
    @GET
    @Path("{shipmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrackingList (@PathParam("shipmentId") String shipmentId) {
        // request list of shipments
        List<Tracking> trackingList = trackingService.getTrackingList(shipmentId);
    	// send response to client
        return Response.ok(trackingList).build();
    }
}
