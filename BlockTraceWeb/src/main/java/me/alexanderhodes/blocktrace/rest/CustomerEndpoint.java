package me.alexanderhodes.blocktrace.rest;

import me.alexanderhodes.blocktrace.model.Customer;
import me.alexanderhodes.blocktrace.service.CustomerService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by alexa on 23.09.2017.
 */
@Path("/customer")
public class CustomerEndpoint {

    @Inject
    private CustomerService customerService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer (Customer customer) {
        customer = customerService.createCustomer(customer);

        return Response.created(UriBuilder.fromResource(Customer.class).path(String.valueOf(customer.getId())).build())
                .build();
    }



}
