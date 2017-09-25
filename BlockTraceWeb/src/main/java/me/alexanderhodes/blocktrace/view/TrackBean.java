package me.alexanderhodes.blocktrace.view;

import me.alexanderhodes.blocktrace.model.Shipment;
import me.alexanderhodes.blocktrace.model.ShipmentStatus;
import me.alexanderhodes.blocktrace.model.Tracking;
import me.alexanderhodes.blocktrace.service.ShipmentService;
import me.alexanderhodes.blocktrace.service.TrackingService;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alexa on 21.09.2017.
 */

@Named
@Stateful
@RequestScoped
public class TrackBean implements Serializable {

    @Inject
    private TrackingService trackingService;

    @Inject
    private ShipmentService shipmentService;

    private String trackingId;
    private List<Tracking> trackingList;
    private int percentage;
    private int size;

    @PostConstruct
    public void init () {
        this.trackingList = new ArrayList<>();
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public List<Tracking> getTrackingList() {
        return trackingList;
    }

    public void setTrackingList(List<Tracking> trackingList) {
        this.trackingList = trackingList;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     *
     * @return
     */
    public String trackShipment () {
        if (trackingId == null || trackingId == "") {
            FacesContext.getCurrentInstance().addMessage("trackForm:trackingNumber",
                    new FacesMessage("Enter a shipment number and select the track button."));
            return "";
        }

        Shipment shipment = shipmentService.findShipment(trackingId);

        if (shipment == null) {
            FacesContext.getCurrentInstance().addMessage("trackForm:trackingNumber",
                    new FacesMessage("There are no tracking information available for shipment number " +
                            trackingId + ". Please try again later."));
            return "";
        } else {
            // Daten aus Blockchain lesen und anzeigen
//            this.trackingList = trackingService.getTrackingList(trackingId);

            this.trackingList = new ArrayList<>();

            Tracking t1 = new Tracking();
            t1.setPlace("Abholung im Paketcenter");
            t1.setShipmentStatus(ShipmentStatus.DATARECEIVED);
            t1.setTimestamp(new Date());

            Tracking t2 = new Tracking();
            t2.setPlace("Sendung beim Logistik-Dienstleister eingetroffen");
            t2.setShipmentStatus(ShipmentStatus.DELIEVERED);
            t2.setTimestamp(new Date());

            Tracking t3 = new Tracking();
            t3.setPlace("Sendung beim Logistik-Dienstleister eingetroffen");
            t3.setShipmentStatus(ShipmentStatus.DELIEVERED);
            t3.setTimestamp(new Date());

            Tracking t4 = new Tracking();
            t4.setPlace("Sendung beim Logistik-Dienstleister eingetroffen");
            t4.setShipmentStatus(ShipmentStatus.DELIEVERED);
            t4.setTimestamp(new Date());

            Tracking t5 = new Tracking();
            t5.setPlace("Sendung beim Logistik-Dienstleister eingetroffen");
            t5.setShipmentStatus(ShipmentStatus.DELIEVERED);
            t5.setTimestamp(new Date());

            Tracking t6 = new Tracking();
            t6.setPlace("Sendung beim Logistik-Dienstleister eingetroffen");
            t6.setShipmentStatus(ShipmentStatus.DELIEVERED);
            t6.setTimestamp(new Date());

//            trackingList.add(t1);
//            trackingList.add(t2);
//            trackingList.add(t3);
//            trackingList.add(t4);
//            trackingList.add(t5);
//            trackingList.add(t6);

            if (trackingList.size() == 0) {
                FacesContext.getCurrentInstance().addMessage("trackForm:trackingNumber",
                        new FacesMessage("There are no tracking information available for shipment number " +
                        trackingId + ". Please try again later." ));
                return "";
            }

            calculatePercentage();
            requestStatusAsInt();

            return "";
        }
    }

    /**
     *
     */
    private void calculatePercentage () {
        if (trackingList == null || trackingList.size() == 0) {
            this.percentage = 0;
        } else if (trackingList.size() >= 12) {
            this.percentage = 100;
        } else {
            this.percentage = (trackingList.size() + 1) * 100;
            this.percentage = this.percentage / 13;
        }
    }

    /**
     *
     */
    private void requestStatusAsInt () {
        if (trackingList == null) {
            this.size =  0;
        }
        this.size = trackingList.size();
    }

}
