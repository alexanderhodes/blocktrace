package me.alexanderhodes.blocktrace.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by alexa on 23.09.2017.
 */
@NamedQueries({
        @NamedQuery(name = Shipment.GETLATESTSHIPMENT, query = Shipment.GETLATESTSHIPMENT_QUERY)
})
@Entity
public class Shipment implements Serializable {

    public static final String GETLATESTSHIPMENT = "Latest Shipment";
    public static final String GETLATESTSHIPMENT_QUERY = "SELECT s FROM Shipment s ORDER BY s.created DESC";

    @Id
    private String shipmentId;

    @Column(nullable = false)
    private String referenceNumber;

    @Column(nullable = false)
    private boolean pickup;

    @Column(nullable = false, updatable = false)
    private Date created;

    @Column
    private Date deliveryDate;

    @Column(nullable = false)
    private boolean emailNotification;

    @ManyToOne
    private ShipmentType shipmentType;

    @ManyToOne
    private Customer sender;

    @ManyToOne
    private Customer receiver;

    public Shipment () {

    }

    public Shipment(String referenceNumber, boolean pickup, boolean emailNotification, ShipmentType shipmentType,
                    Customer sender, Customer receiver) {
        this.referenceNumber = referenceNumber;
        this.pickup = pickup;
        this.emailNotification = emailNotification;
        this.shipmentType = shipmentType;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public boolean isPickup() {
        return pickup;
    }

    public void setPickup(boolean pickup) {
        this.pickup = pickup;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public boolean isEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getReceiver() {
        return receiver;
    }

    public void setReceiver(Customer receiver) {
        this.receiver = receiver;
    }

    @PrePersist
    public void onCreate () {
        this.created = new Date();
    }
}
