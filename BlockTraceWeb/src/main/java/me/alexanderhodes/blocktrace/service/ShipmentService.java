package me.alexanderhodes.blocktrace.service;

import me.alexanderhodes.blocktrace.model.Shipment;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by alexa on 23.09.2017.
 */
@Stateless
public class ShipmentService extends AbstractService<Shipment> implements Serializable {

    public ShipmentService () {
        super(Shipment.class);
    }

    public Shipment findShipment (String trackingId) {
        return entityManager.find(Shipment.class, trackingId);
    }

    public Shipment getLatestShipment () {
        TypedQuery<Shipment> typedQuery = entityManager.createNamedQuery(Shipment.GETLATESTSHIPMENT, Shipment.class);

        List<Shipment> shipments = typedQuery.getResultList();
        if (shipments != null && shipments.size() > 0) {
            return shipments.get(0);
        }
        return null;
    }

    /**
     *
     * @param shipment
     * @return
     */
    public Shipment persist (Shipment shipment) {
        String shipmentId = generateShipmentId();
        shipment.setShipmentId(shipmentId);

        entityManager.persist(shipment);
        return shipment;
    }

    /**
     *
     * @return
     */
    public String generateShipmentId () {
        String shipmentId;

        String dateFormat = new SimpleDateFormat("yyyyMMdd").format(new Date());

        Shipment latestShipment = getLatestShipment();

        try {
            if (latestShipment != null) {
                if (latestShipment.getShipmentId().startsWith(dateFormat)) {
                    shipmentId = new StringBuilder(dateFormat).append(checkLatestNumber(dateFormat,
                            latestShipment.getShipmentId())).toString();
                } else {
                    shipmentId = new StringBuilder(dateFormat).append("000001").toString();
                }
            } else {
                shipmentId = new StringBuilder(dateFormat).append("000001").toString();
            }

            shipmentId += calculateCheckNumber(shipmentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return shipmentId;
    }

    /**
     *
     * @param shipmentId
     * @return
     */
    private String calculateCheckNumber (String shipmentId) {
        Long id = Long.parseLong(shipmentId);
        return Long.toString(id % 9).toString();
    }

    /**
     *
     * @param today
     * @param latestShipmentId
     * @return
     */
    private String checkLatestNumber (String today, String latestShipmentId) {
        String last = latestShipmentId.replace(today, "");
        last = last.substring(0, last.length()-1);

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
