package me.alexanderhodes.blocktrace.rest;

import me.alexanderhodes.blocktrace.model.Tracking;
import me.alexanderhodes.blocktrace.service.TrackingService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

/**
 * Created by alexa on 23.09.2017.
 */
@Path("/tracking")
public class TrackingEndpoint {

    @Inject
    private TrackingService trackingService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTracking (Tracking tracking) {
       // TODO: zum testen
        trackingService.persistTracking(tracking);
     //   trackingService.uploadTracking(tracking);

        return Response.created(UriBuilder.fromResource(Tracking.class).build()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrackingList () {
        List<Tracking> trackingList = trackingService.getTrackingList();

        return Response.ok(trackingList).build();
    }

    @GET
    @Path("{shipmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrackingList (@PathParam("shipmentId") String shipmentId) {
        List<Tracking> trackingList = trackingService.getTrackingListShipment(shipmentId);
        // TODO: zum testen
//        List<Tracking> trackingList = trackingService.getTrackingList(shipmentId);

        return Response.ok(trackingList).build();
    }

}
