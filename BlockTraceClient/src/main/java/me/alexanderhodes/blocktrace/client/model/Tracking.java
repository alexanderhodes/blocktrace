package me.alexanderhodes.blocktrace.client.model;

import java.util.Date;

/**
 * Created by alexa on 23.09.2017.
 */
public class Tracking {

    private long id;
    private Shipment shipment;
    private ShipmentStatus shipmentStatus;
    private Date timestamp;
    private String place;

    public Tracking () {

    }

    public Tracking (Shipment shipment, ShipmentStatus shipmentStatus, Date timestamp, String place) {
        this.shipment = shipment;
        this.shipmentStatus = shipmentStatus;
        this.timestamp = timestamp;
        this.place = place;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
