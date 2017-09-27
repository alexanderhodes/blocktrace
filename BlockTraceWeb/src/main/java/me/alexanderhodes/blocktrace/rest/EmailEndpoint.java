package me.alexanderhodes.blocktrace.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import me.alexanderhodes.blocktrace.model.Customer;
import me.alexanderhodes.blocktrace.model.Email;
import me.alexanderhodes.blocktrace.model.Shipment;
import me.alexanderhodes.blocktrace.service.EmailService;
import me.alexanderhodes.blocktrace.service.ShipmentService;

/**
 * Created by alexa on 23.09.2017.
 */
@Path("/email")
public class EmailEndpoint {

	@Inject
	private EmailService emailService;

	@Inject
	private ShipmentService shipmentService;

	/**
	 * 
	 * @param shipmentId
	 * @param message
	 * @return
	 */
	@GET
	public Response sendEmail(@QueryParam("shipment") String shipmentId, @QueryParam("message") int message) {
		// TODO: E-Mail mit entsprechendem Inhalt an Empfänger der Sendung
		// senden
		Shipment shipment = shipmentService.findShipment(shipmentId);
		Customer receiver = shipment.getReceiver();

		Email email = new Email();
		email.setReceiver("alexander.hodes@live.com");
		email.setMessage("Shipment: " + shipmentId + " message " + message);
		email.setSubject("Test");

		emailService.send(email);

		return Response.accepted().build();
	}

}
