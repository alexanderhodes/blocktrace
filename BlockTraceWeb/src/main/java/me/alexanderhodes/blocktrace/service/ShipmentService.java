package me.alexanderhodes.blocktrace.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import me.alexanderhodes.blocktrace.model.Shipment;

/**
 * Created by alexa on 23.09.2017.
 */
@Stateless
public class ShipmentService extends AbstractService<Shipment> implements Serializable {

	private static final long serialVersionUID = 1L;

	public ShipmentService() {
		super(Shipment.class);
	}

	/**
	 * Find shipment by shipment Id
	 * 
	 * @param trackingId Id of Shipment
	 * @return Shipment requested shipment
	 */
	public Shipment findShipment(String trackingId) {
		// request shipment
		return entityManager.find(Shipment.class, trackingId);
	}

	/**
	 * Request last shipment that had been saved
	 * 
	 * @return Shipment latest shipment
	 */
	public Shipment getLatestShipment() {
		// create query for reading latest shipment
		TypedQuery<Shipment> typedQuery = entityManager.createNamedQuery(Shipment.GETLATESTSHIPMENT, Shipment.class);

		List<Shipment> shipments = typedQuery.getResultList();
		if (shipments != null && shipments.size() > 0) {
			// return latest shipment
			return shipments.get(0);
		}
		return null;
	}

	/**
	 * Storing shipment in database
	 *
	 * @param shipment Shipment that has to be stored
	 * @return stored Shipment
	 */
	public Shipment persist(Shipment shipment) {
		// generate id for shipment
		String shipmentId = generateShipmentId();
		shipment.setShipmentId(shipmentId);
		// store shipment in database
		entityManager.persist(shipment);
		return shipment;
	}

	/**
	 * Generate a new shipment id
	 *
	 * @return generated shipment id
	 */
	public String generateShipmentId() {
		String shipmentId;

		String dateFormat = new SimpleDateFormat("yyyyMMdd").format(new Date());
		// requesting latest shipment
		Shipment latestShipment = getLatestShipment();

		try {
			// check if shipment already exists
			if (latestShipment != null) {
				if (latestShipment.getShipmentId().startsWith(dateFormat)) {
					shipmentId = new StringBuilder(dateFormat)
							.append(checkLatestNumber(dateFormat, latestShipment.getShipmentId())).toString();
				} else {
					shipmentId = new StringBuilder(dateFormat).append("000001").toString();
				}
			} else {
				// no shipment exists
				shipmentId = new StringBuilder(dateFormat).append("000001").toString();
			}
			// create check number
			shipmentId += calculateCheckNumber(shipmentId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return shipmentId;
	}

	/**
	 * Calculate check number of tracking number
	 *
	 * @param shipmentId shipmentId
	 * @return shipmentId with check number
	 */
	private String calculateCheckNumber(String shipmentId) {
		Long id = Long.parseLong(shipmentId);
		return Long.toString(id % 9).toString();
	}

	/**
	 * Create new shipment number
	 * 
	 * @param today String of todays date
	 * @param latestShipmentId id of latest shipment
	 * @return new tracking number
	 */
	private String checkLatestNumber(String today, String latestShipmentId) {
		String last = latestShipmentId.replace(today, "");
		last = last.substring(0, last.length() - 1);

		while (last.startsWith("0")) {
			last = last.substring(1, last.length());
		}

		String l = String.valueOf(Integer.valueOf(last) + 1);

		while (l.length() < 5) {
			l = "0" + l;
		}

		return l;
	}

}
