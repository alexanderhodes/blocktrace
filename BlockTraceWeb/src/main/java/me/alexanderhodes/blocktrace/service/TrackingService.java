package me.alexanderhodes.blocktrace.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import me.alexanderhodes.blocktrace.model.Tracking;

/**
 * Created by alexa on 24.09.2017.
 */
@Stateless
public class TrackingService extends AbstractService<Tracking> implements Serializable {

	private static final long serialVersionUID = 1L;

	public TrackingService() {
		super(Tracking.class);
	}

	/**
	 * Save tracking in database
	 * 
	 * @param tracking Tracking which has to be saved in database
	 */
	public void persistTracking(Tracking tracking) {
		entityManager.persist(tracking);
	}

	/**
	 * query trackings for shipment from database
	 * 
	 * @param shipmentId id of shipment
	 * @return list of trackings for shipment
	 */
	public List<Tracking> getTrackingListShipment(String shipmentId) {
		TypedQuery<Tracking> query = entityManager.createNamedQuery(Tracking.GET_TRACKINGS_SHIPMENT, Tracking.class);
		// set parameter
		query.setParameter("shipmentId", shipmentId);

		return query.getResultList();
	}

	/**
	 * query all trackings from Blockchain
	 * 
	 * @return list of all stored trackings
	 */
	public List<Tracking> getTrackingList() {
		List<Tracking> trackingList = new ArrayList<>();

		try {
			// send request for all trackings
			// TODO: URL anpassen
			trackingList = listAll();
			// trackingList = sendRequest("http://localhost:3000/api/");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return trackingList;
	}

	/**
	 * query trackings for shipment from Blockchain
	 * 
	 * @param trackingId id of shipment
	 * @return list of trackings for shipment
	 */
	public List<Tracking> getTrackingList(String trackingId) {
		List<Tracking> trackingList = new ArrayList<>();

		try {
			// send request for trackings
			// TODO: URL anpassen
			trackingList = sendRequest("http://localhost:3000/api/");

			// TODO: Trackings mit bestimmter trackingID auslesen
		} catch (Exception e) {
			e.printStackTrace();
		}

		return trackingList;
	}

	/**
	 * upload tracking to blockchain
	 * 
	 * @param tracking that has to be uploaded
	 * @return uploaded tracking
	 */
	public Tracking uploadTracking(Tracking tracking) {
		try {
			// send post request to blockchain
			sendPost("http://localhost:3000/api/", tracking);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tracking;
	}

	/**
	 * 
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private List<Tracking> sendRequest(String url) throws Exception {
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
			public Date deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
			}
		});

		Gson gson = builder.create();
		Tracking[] trackings = gson.fromJson(buffer.toString(), Tracking[].class);

		return Arrays.asList(trackings);
	}

	/**
	 * 
	 * 
	 * @param url
	 * @param tracking
	 * @throws Exception
	 */
	private void sendPost(String url, Tracking tracking) throws Exception {
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
