package me.alexanderhodes.blocktrace.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import me.alexanderhodes.blocktrace.model.Email;
import me.alexanderhodes.blocktrace.model.Shipment;
import me.alexanderhodes.blocktrace.model.ShipmentStatus;
import me.alexanderhodes.blocktrace.model.Tracking;
import me.alexanderhodes.blocktrace.model.blockchain.TrackingBlockchain;
import me.alexanderhodes.blocktrace.model.blockchain.TrackingID;
import me.alexanderhodes.blocktrace.util.ConfigPropertyProducer;
import me.alexanderhodes.blocktrace.util.MessagesProducer;
import me.alexanderhodes.blocktrace.util.TrackingConverter;

/**
 * Created by alexa on 24.09.2017.
 */
@Stateless
public class TrackingService extends AbstractService<Tracking> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TrackingConverter trackingConverter;

    @Inject
    private ShipmentService shipmentService;

    @Inject
    private EmailService emailService;

    public TrackingService() {
        super(Tracking.class);
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
//			trackingList = listAll(); // --> nur für TEST
            TrackingBlockchain[] trackingBlockchains = sendRequest(ConfigPropertyProducer.getBlockchainRestPath());

            trackingList = convertList(trackingBlockchains);
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
            String url = ConfigPropertyProducer.getBlockchainQueriesPath() + "/selectTrackingByShipment?shipment="
                    + trackingId;
            TrackingBlockchain[] trackingBlockchains = sendRequest(url);

            trackingList = convertList(trackingBlockchains);
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
            tracking = sendPost(tracking);
            // save tracking Id
            TrackingID trackingID = createTrackingId(tracking);
            // send Mail
            sendMail(tracking);
            // create transaction
            createTransaction(trackingID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tracking;
    }

    /**
     * send request to blockchain for receiving saved trackings
     *
     * @param url url of webservice
     * @return List of received trackings in format of Blockchain
     * @throws Exception
     */
    private TrackingBlockchain[] sendRequest(String url) throws Exception {
        URL obj = new URL(url);

        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("GET");

        // read received content
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        StringBuffer buffer = new StringBuffer();

        // read content and append to String
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
        // convert received content to objects
        TrackingBlockchain[] trackingBlockchains = gson.fromJson(buffer.toString(), TrackingBlockchain[].class);

        return trackingBlockchains;
    }

    /**
     * send post method to blockchain for saving tracking
     *
     * @param tracking Tracking which has to be saved in Blockchain
     * @throws Exception
     */
    private Tracking sendPost(Tracking tracking) throws Exception {
        TrackingBlockchain trackingBlockchain = trackingConverter.toTrackingBlockchain(tracking);

        // generate Id
        String id = generateTrackingBlockchainId();
        trackingBlockchain.setId(id);
        // convert object to json
        String json = trackingBlockchain.toString();

        URL obj = new URL(ConfigPropertyProducer.getBlockchainRestPath());
        // start connection
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json");

        urlConnection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        // write body
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        System.out.println(urlConnection.getResponseCode());

        tracking.setId(Long.parseLong(id));

        return tracking;
    }

    /**
     * check latest tracking that is stored in Blockchain to get the id of that object for
     * generating the id of the new Tracking object which has to be stored in Blockchain.
     *
     * @return generatedId
     * @throws Exception
     */
    private String generateTrackingBlockchainId() throws Exception {
        String id = null;

        // query latest tracking id from database
        TypedQuery<TrackingID> query = entityManager.createNamedQuery(TrackingID.GET_LATEST_TRACKINGID,
                TrackingID.class);
        List<TrackingID> list = query.getResultList();

        // check if already tracking has been sent to blockchain
        if (list != null && list.size() > 0) {
            // calculate and set new id
            int number = Integer.valueOf(String.valueOf(list.get(0).getId()))+1;
            id = String.valueOf(number);
        }

        if (id == null) {
            // no tracking has been sent to blockchain yet
            int testId = 1;

            while (id == null) {
                // check if tracking already exists
                URL obj = new URL(ConfigPropertyProducer.getBlockchainRestPath() + "/" + testId);

                HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
                urlConnection.setRequestMethod("HEAD");

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == 0) {
                    id = "1";
                } else if (responseCode != 0 && responseCode == 404) {
                    id = String.valueOf(testId);
                }

                testId += 1;
            }
        }

        return id;
    }

    /**
     * converts list of received json data to objects
     *
     * @param trackingBlockchains list of received data from blockchain
     * @return list of Tracking objects
     */
    private List<Tracking> convertList (TrackingBlockchain[] trackingBlockchains) {
        List<Tracking> trackingList = new ArrayList<>();

        for (TrackingBlockchain trackingBlockchain : trackingBlockchains) {
            // convert tracking received to object
            Tracking tracking = trackingConverter.toTracking(trackingBlockchain);
            // set shipment of tracking
            Shipment shipment = shipmentService.findShipment(trackingBlockchain.getShipment());

            tracking.setShipment(shipment);

            trackingList.add(tracking);
        }

        return trackingList;
    }

    /**
     * save trackingid for knowing which id has been used for sending to blockchain
     *
     * @param tracking Tracking that has been saved
     * @return TrackingId
     */
    public TrackingID createTrackingId (Tracking tracking) {
        // set attributes
        TrackingID trackingID = new TrackingID();
        trackingID.setId(tracking.getId());
        trackingID.setTimestamp(createTimestamp(new Date()));
        trackingID.setSaved(true);
        // saved trackingId in database
        entityManager.persist(trackingID);

        return trackingID;
    }

    /**
     * send mail to receiver of shipment
     *
     * @param tracking Tracking
     */
    private void sendMail (Tracking tracking) {
        if (tracking.getShipment() != null) {
            boolean notificationEnabled = tracking.getShipment().isEmailNotification();

            if (notificationEnabled) {
                // notification is enabled
                System.out.println("######## send mail");
                ShipmentStatus shipmentStatus = tracking.getShipmentStatus();

                int statusId = shipmentStatus.getNumber()-1;

                String date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(tracking.getTimestamp());
                // generate body
                String emailBody = MessagesProducer.getValue("emailBody");
                emailBody = emailBody.replace("SHIPMENTNO", tracking.getShipment().getShipmentId());
                emailBody = emailBody.replace("DATE", date);
                emailBody = emailBody.replace("STATUS", MessagesProducer.getValue("status"+statusId));

                String customerName = tracking.getShipment().getReceiver().getSurname() + " " +
                        tracking.getShipment().getReceiver().getLastName();
                // generate header
                String emailHeader = MessagesProducer.getValue("emailHeader");
                emailHeader = emailHeader.replace("CUSTOMERNAME", customerName);

                StringBuffer emailText = new StringBuffer();
                emailText.append(emailHeader);
                emailText.append("<br/><br/>");
                emailText.append(emailBody);
                emailText.append("<br/><br/>");
                emailText.append(MessagesProducer.getValue("emailFooter"));
                // generate subject
                String subject = MessagesProducer.getValue("emailSubject");
                subject = subject.replace("SHIPMENTNO", tracking.getShipment().getShipmentId());
                // set attributes
                Email email = new Email();
                email.setMessage(emailText.toString());
                email.setSubject(subject);
                email.setReceiver(tracking.getShipment().getReceiver().getEmail());
                // send mail
                emailService.send(email);
            }
        }
    }

    /**
     * upload transation to blockchain
     *
     * @param trackingID trackingId that has to be saved
     * @throws Exception
     */
    private void createTransaction(TrackingID trackingID) throws Exception {
        URL obj = new URL(ConfigPropertyProducer.getBlockchainTrackPath());
        // open connection
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json");

        urlConnection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        // set body
        wr.writeBytes(trackingID.toString());
        wr.flush();
        wr.close();
    }

    /**
     * method for creating a timestamp
     *
     * @param date current date
     * @return converted date in String format
     */
    private String createTimestamp(Date date) {
        StringBuilder builder = new StringBuilder();
        builder.append(new SimpleDateFormat("yyyy-MM-dd").format(date));
        builder.append("T");
        builder.append(new SimpleDateFormat("HH:mm:ss.SSSS").format(date));
        builder.append("Z");

        return builder.toString();
    }

}
