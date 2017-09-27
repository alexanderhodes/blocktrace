package me.alexanderhodes.blocktrace.client.net;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import me.alexanderhodes.blocktrace.client.model.Tracking;

/**
 * Created by alexa on 26.09.2017.
 */
public class TrackingService extends AbstractService<Tracking> {

    public TrackingService () {
        super(Tracking.class);
    }

    /**
     * Send Request for receiving all tracking data
     * 
     * @return list containing all tracking data
     */
    public List<Tracking> getTrackingList () {
        try {
        	// send Request
            InputStream inputStream = requestInputStreamList("tracking");
            // Create received entities
            Tracking[] trackings = createEntities(inputStream);
            List<Tracking> trackingList = new ArrayList<>();

            for (Tracking t : trackings) {
                trackingList.add(t);
            }

            return trackingList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Send Request for receiving tracking list for shipment
     * 
     * @param shipmentId id of shipment
     * @return list containing tracking data for shipment
     */
    public List<Tracking> getTrackingList (String shipmentId) {
        try {
        	// send Request
            InputStream inputStream = requestInputStreamList("tracking", shipmentId);
            // Create received entities
            Tracking[] trackings = createEntities(inputStream);
            List<Tracking> trackingList = new ArrayList<>();
            
            for (Tracking t : trackings) {
                trackingList.add(t);
            }

            return trackingList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Uploading new Tracking to web application
     * 
     * @param tracking Tracking
     * @return saved Tracking
     */
    public Tracking postTracking (Tracking tracking) {
        try {
            return post(tracking, "tracking");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creating Tracking object from received data
     * 
     * @param inputStream InputStream received from request
     * @return Tracking object
     * @throws Exception
     */
    public Tracking createEntity(InputStream inputStream) throws Exception {
        // parsing InputStream to String
    	String json = readInputStream(inputStream);
    	// Initialize GSON for Parsing JSON String to object
        Gson gson = initGson();
        // parsing String to object
        return gson.fromJson(json, Tracking.class);
    }

    /**
     * Creating Tracking objects from received data
     * 
     * @param inputStream InputStream received from request
     * @return Tracking objects
     * @throws Exception
     */
    public Tracking[] createEntities (InputStream inputStream) throws Exception {
        Tracking[] trackings = new Tracking[]{};
        // parsing InputStream to String
        String json = readInputStream(inputStream);
    	// Initialize GSON for Parsing JSON String to object
        Gson gson = initGson();
        // parsing String to object
        return gson.fromJson(json, trackings.getClass());
    }


}
