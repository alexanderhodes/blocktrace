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

	private static final long serialVersionUID = 1L;

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
	public void init() {
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
	 * track shipments by loading data from database
	 *
	 * @return
	 */
	public String trackShipment() {
		// check if shipment id was entered
		if (trackingId == null || trackingId.isEmpty()) {
			// shipment id was not entered
			FacesContext.getCurrentInstance().addMessage("trackForm:trackingNumber",
					new FacesMessage(MessagesProducer.getValue("track1")));
			return "";
		}
		// query shipment from database
		Shipment shipment = shipmentService.findShipment(trackingId);
		// check if shipment exists
		if (shipment == null) {
			// shipment does not exist
			FacesContext.getCurrentInstance().addMessage("trackForm:trackingNumber",
					new FacesMessage(MessagesProducer.getValue("track2").replace("TRACKINGNO", trackingId)));
			return "";
		} else {
			// read data from blockchain
			this.trackingList = trackingService.getTrackingListShipment(trackingId);
			// check if tracking information are available
			if (trackingList.size() == 0) {
				// no tracking information are available
				FacesContext.getCurrentInstance().addMessage("trackForm:trackingNumber",
						new FacesMessage("There are no tracking information available for shipment number " + trackingId
								+ ". Please try again later."));
				return "";
			}
			// calculate percentage for progress bar
			calculatePercentage();
			// calculate int for progress bar
			requestStatusAsInt();

			return "";
		}
	}

	/**
	 * percentage that is used in progress bar
	 */
	private void calculatePercentage() {
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
	 * number that is used in progress bar
	 */
	private void requestStatusAsInt() {
		if (trackingList == null) {
			this.size = 0;
		}
		this.size = trackingList.size();
	}

}
