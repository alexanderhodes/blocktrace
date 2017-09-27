package me.alexanderhodes.blocktrace.client.net;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.alexanderhodes.blocktrace.client.model.Shipment;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by alexa on 26.09.2017.
 */
public class ShipmentService extends AbstractService<Shipment> {

    public ShipmentService () {
        super(Shipment.class);
    }

    public Shipment requestSingleShipment (String id) {
        try {
            InputStream inputStream = requestInputStreamSingle("shipment", id);
            Shipment shipment = createEntity(inputStream);

            return shipment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Shipment> requestShipments () {
        try {
            InputStream inputStream = requestInputStreamList("shipment");
            List<Shipment> shipments = Arrays.asList(createEntities(inputStream));

            return shipments;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public Shipment createEntity(InputStream inputStream) throws Exception {
        String json = readInputStream(inputStream);

        Gson gson = initGson();

        return gson.fromJson(json, Shipment.class);
    }

    /**
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public Shipment[] createEntities (InputStream inputStream) throws Exception {
        Shipment[] shipments = new Shipment[]{};

        String json = readInputStream(inputStream);

        Gson gson = initGson();

        shipments = gson.fromJson(json, shipments.getClass());
        return shipments;
    }

}
