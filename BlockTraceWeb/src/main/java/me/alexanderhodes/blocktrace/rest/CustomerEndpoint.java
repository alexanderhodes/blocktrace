package me.alexanderhodes.blocktrace.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import me.alexanderhodes.blocktrace.model.Customer;
import me.alexanderhodes.blocktrace.service.CustomerService;

/**
 * Created by alexa on 23.09.2017.
 */
@Path("/customer")
public class CustomerEndpoint {

	@Inject
	private CustomerService customerService;

	/**
	 * Endpoint for creating a customer
	 * 
	 * @param customer Customer
	 * @return Response that customer has been created
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCustomer(Customer customer) {
		// create customer
		customer = customerService.createCustomer(customer);
		// send response to client
		return Response.created(UriBuilder.fromResource(Customer.class).path(String.valueOf(customer.getId())).build())
				.build();
	}

}
