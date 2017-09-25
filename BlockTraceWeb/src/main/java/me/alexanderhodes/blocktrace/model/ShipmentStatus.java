package me.alexanderhodes.blocktrace.model;

/**
 * Created by alexa on 23.09.2017.
 */
public enum ShipmentStatus {

    DATARECEIVED(1, "The shipment data has been transmitted"),
    HANDOVER(2, "The shipment has been handed over from sender to transporter"),
    TRANSPORTLC(3, "The shipment will be transported to logistics center."),
    ARRIVEDLC(4, "The shipment arrived at logistics center."),
    SORTEDLC(5, "The shipment has been sorted in logistics center."),
    LEFTLC(6, "The shipment left logistics center."),
    ARRIVEDDC(7, "The shipment arrived at distribution center."),
    SORTEDDC(8, "The shipment has been sorted in distribution center."),
    LEFTDC(9, "The shipment left distribution center."),
    ARRIVEDDP(10, "The shipment arrived at delivery point."),
    INDELIVERY(11, "The shipment is in delivery."),
    DELIEVERED (12, "This shipment has been delivered.");

    private int id;
    private String name;
    private String place;

    ShipmentStatus (int id, String name) {
        this.id = id;
        this.name = name;
    }

    ShipmentStatus (int id, String name, String place) {
        this.id = id;
        this.name = name;
        this.place = place;
    }

    public String getName () {
        return this.name;
    }

    public String getPlace () {
        return this.place;
    }

}
