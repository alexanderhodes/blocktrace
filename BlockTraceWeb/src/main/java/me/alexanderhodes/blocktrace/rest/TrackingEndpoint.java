package me.alexanderhodes.blocktrace.rest;

import me.alexanderhodes.blocktrace.model.Tracking;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by alexa on 23.09.2017.
 */
@Path("/tracking")
public class TrackingEndpoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTracking (Tracking tracking) {

        return Response.created(UriBuilder.fromResource(Tracking.class).path(tracking.getShipment().getShipmentId())
                .build()).build();
    }

}
