package me.alexanderhodes.blocktrace.view;

import me.alexanderhodes.blocktrace.model.Shipment;
import me.alexanderhodes.blocktrace.model.Tracking;
import me.alexanderhodes.blocktrace.service.ShipmentService;
import me.alexanderhodes.blocktrace.service.TrackingService;
import me.alexanderhodes.blocktrace.util.MessagesProducer;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private SimpleDateFormat dateFormat;
    private String trackingId;
    private List<Tracking> trackingList;
    private int percentage;
    private int size;

    @PostConstruct
    public void init () {
        this.trackingList = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
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
        if (trackingId == null || trackingId.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("trackForm:trackingNumber",
                    new FacesMessage(MessagesProducer.getValue("track1")));
            return "";
        }

        Shipment shipment = shipmentService.findShipment(trackingId);

        if (shipment == null) {
            FacesContext.getCurrentInstance().addMessage("trackForm:trackingNumber",
                    new FacesMessage(MessagesProducer.getValue("track2").replace("TRACKINGNO", trackingId)));
            return "";
        } else {
            // Daten aus Blockchain lesen und anzeigen
            this.trackingList = trackingService.getTrackingListShipment(trackingId);

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
