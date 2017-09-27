package me.alexanderhodes.blocktrace.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Created by alexa on 23.09.2017.
 */
@NamedQueries({
        @NamedQuery(name = Tracking.GET_TRACKINGS_SHIPMENT, query = Tracking.GET_TRACKINGS_SHIPMENT_QUERY)
})
@Entity
public class Tracking implements Serializable {

	private static final long serialVersionUID = 1L;

    public static final String GET_TRACKINGS_SHIPMENT = "Tracking.Shipment";
    static final String GET_TRACKINGS_SHIPMENT_QUERY = "SELECT t FROM Tracking t WHERE t.shipment.shipmentId = :shipmentId";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Shipment shipment;
    @Column
    private ShipmentStatus shipmentStatus;
    @Column
    private Date timestamp;
    @Column
    private String place;

    public Tracking () {

    }

    public Tracking (Shipment shipment, ShipmentStatus shipmentStatus, Date timestamp, String place) {
        this.shipment = shipment;
        this.shipmentStatus = shipmentStatus;
        this.timestamp = timestamp;
        this.place = place;
    }

    public Tracking (long id, Shipment shipment, ShipmentStatus shipmentStatus, Date timestamp, String place) {
        this.id = id;
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
