package me.alexanderhodes.blocktrace.model.blockchain;

/**
 * Created by alexa on 28.09.2017.
 */
public class TrackingBlockchain {

    private String $class;
    private String id;
    private String shipment;
    private String shipmentStatus;
    private String timestamp;
    private String place;

    public TrackingBlockchain () {

    }

    public TrackingBlockchain(String $class, String id, String shipment, String shipmentStatus, String timestamp,
                              String place) {
        this.$class = $class;
        this.id = id;
        this.shipment = shipment;
        this.shipmentStatus = shipmentStatus;
        this.timestamp = timestamp;
        this.place = place;
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "{\"$class\": \"" + $class + '\"' + ", \"id\": \"" + id + '\"' + ", \"shipment\": \"" + shipment + '\"'
                + ", \"shipmentStatus\": \"" + shipmentStatus + '\"' + ", \"timestamp\": \"" + timestamp + '\"' +
                ", \"place\": \"" + place + '\"' + '}';
    }
}
