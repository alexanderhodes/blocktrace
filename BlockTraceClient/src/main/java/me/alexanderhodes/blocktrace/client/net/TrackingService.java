package me.alexanderhodes.blocktrace.client.net;

import com.google.gson.*;
import me.alexanderhodes.blocktrace.client.model.Tracking;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alexa on 26.09.2017.
 */
public class TrackingService extends AbstractService<Tracking> {

    private static final String REST_API_URL = "http://localhost:8080/blocktrace/rest/tracking";

    public TrackingService () {
        super(Tracking.class);
    }

    public List<Tracking> getTrackingList () {
        try {
            InputStream inputStream = requestInputStreamList("tracking");

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

    public List<Tracking> getTrackingList (String shipmentId) {
        try {
            InputStream inputStream = requestInputStreamList("tracking", shipmentId);

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

    public Tracking postTracking (Tracking tracking) {
        try {
            return post(tracking, "tracking");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Tracking createEntity(InputStream inputStream) throws Exception {
        String json = readInputStream(inputStream);

        Gson gson = initGson();

        return gson.fromJson(json, Tracking.class);
    }

    public Tracking[] createEntities (InputStream inputStream) throws Exception {
        Tracking[] trackings = new Tracking[]{};

        String json = readInputStream(inputStream);

        Gson gson = initGson();

        trackings = gson.fromJson(json, trackings.getClass());
        return trackings;
    }


}
