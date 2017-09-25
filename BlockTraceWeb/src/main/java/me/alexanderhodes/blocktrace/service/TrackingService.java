package me.alexanderhodes.blocktrace.service;

import com.google.gson.*;
import me.alexanderhodes.blocktrace.model.Shipment;
import me.alexanderhodes.blocktrace.model.Tracking;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by alexa on 24.09.2017.
 */
@Stateless
public class TrackingService {

    public List<Tracking> getTrackingList () {
        List<Tracking> trackingList = new ArrayList<>();

        try {
            // TODO: URL anpassen
            trackingList = sendRequest("http://localhost:3000/api/");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trackingList;
    }

    public List<Tracking> getTrackingList (String trackingId) {
        List<Tracking> trackingList = new ArrayList<>();

        try {
            // TODO: URL anpassen
            trackingList = sendRequest("http://localhost:3000/api/");

            // TODO: Trackings mit bestimmter trackingID auslesen
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trackingList;
    }

    public Tracking uploadTracking (Tracking tracking) {
        try {
            sendPost("http://localhost:3000/api/", tracking);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tracking;
    }

    private List<Tracking> sendRequest (String url) throws Exception {
        URL obj = new URL(url);

        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        StringBuffer buffer = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            buffer.append(inputLine);
        }
        reader.close();

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();
        Tracking[] trackings = gson.fromJson(buffer.toString(), Tracking[].class);

        return Arrays.asList(trackings);
    }

    private void sendPost (String url, Tracking tracking) throws Exception {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String json = gson.toJson(tracking);

        URL obj = new URL(url);

        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json");

        urlConnection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        System.out.println(urlConnection.getResponseCode());
    }

}
