package me.alexanderhodes.blocktrace.client.net;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import me.alexanderhodes.blocktrace.client.model.Shipment;

/**
 * Created by alexa on 26.09.2017.
 */
public class ShipmentService extends AbstractService<Shipment> {

    public ShipmentService () {
        super(Shipment.class);
    }

    /**
     * Send Request for receiving data of one shipment
     * 
     * @param id id of requested shipment
     * @return shipment
     */
    public Shipment requestSingleShipment (String id) {
        try {
        	// send request
            InputStream inputStream = requestInputStreamSingle("shipment", id);
            // create received entity
            Shipment shipment = createEntity(inputStream);

            return shipment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Send Request for receiving shipments
     * 
     * @return list of requested shipments
     */
    public List<Shipment> requestShipments () {
        try {
        	// send request
            InputStream inputStream = requestInputStreamList("shipment");
            // create received entities
            Shipment[] shipments = createEntities(inputStream);
            List<Shipment> shipmentList = new ArrayList<>();
            
            for (Shipment s : shipments) {
            	shipmentList.add(s);
            }

            return shipmentList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creating Shipment object from received data
     * 
     * @param inputStream InputStream received from request
     * @return Shipment shipment
     * @throws Exception
     */
    public Shipment createEntity(InputStream inputStream) throws Exception {
    	// parsing InputStream to String
        String json = readInputStream(inputStream);
        // Initialize GSON for Parsing JSON String to object
        Gson gson = initGson();
        // parsing String to object
        return gson.fromJson(json, Shipment.class);
    }

    /**
     * Creating Shipment objects from received data
     *
     * @param inputStream InputStream received data
     * @return Shipment objects
     * @throws Exception
     */
    public Shipment[] createEntities (InputStream inputStream) throws Exception {
        Shipment[] shipments = new Shipment[]{};
        // parsing InputStream to String
        String json = readInputStream(inputStream);
        // Initialize GSON for Parsing JSON String to object
        Gson gson = initGson();
        // parsing String to object
        return gson.fromJson(json, shipments.getClass());
    }

}
