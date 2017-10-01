package me.alexanderhodes.blocktrace.businesslogic;

import me.alexanderhodes.blocktrace.model.Email;
import me.alexanderhodes.blocktrace.model.ShipmentStatus;
import me.alexanderhodes.blocktrace.model.Tracking;
import me.alexanderhodes.blocktrace.model.blockchain.TrackingID;
import me.alexanderhodes.blocktrace.service.EmailService;
import me.alexanderhodes.blocktrace.service.TrackingService;
import me.alexanderhodes.blocktrace.util.ConfigPropertyProducer;
import me.alexanderhodes.blocktrace.util.MessagesProducer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by alexa on 30.09.2017.
 */
@ApplicationScoped
public class SomeJob implements Runnable {

    @Inject
    private TrackingService trackingService;

    @Inject
    private EmailService emailService;

    @Override
    public void run() {
//        List<TrackingID> trackingIDList = trackingService.getTrackingIdListNotSaved();
//
//        String timestamp = createTimestamp(new Date());
//        // send Transaction
//        for (TrackingID trackingID : trackingIDList) {
//            try {
//                trackingID.setTimestamp(timestamp);
//                // send post for saving transaction
//                createTransaction(trackingID);
//
//                trackingID.setSaved(true);
//
//                trackingService.updateTrackingId(trackingID);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, -5);
//
//        // send E-Mails
//        List<Tracking> trackingList = trackingService.getTrackingList();
//        List<Tracking> newTrackingList = new ArrayList<>();
//
//        for (Tracking tracking : trackingList) {
//            if (tracking.getTimestamp().after(calendar.getTime())) {
//                newTrackingList.add(tracking);
//            }
//        }
//
//        for (Tracking tracking : newTrackingList) {
//            System.out.println("######## Job sending mails");
//            if (tracking.getShipment() != null) {
//                boolean notificationEnabled = tracking.getShipment().isEmailNotification();
//
//                if (notificationEnabled) {
//                    System.out.println("######## send mail");
//                    ShipmentStatus shipmentStatus = tracking.getShipmentStatus();
//
//                    int statusId = shipmentStatus.getNumber()-1;
//
//                    String date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(tracking.getTimestamp());
//
//                    String emailBody = MessagesProducer.getValue("emailBody");
//                    emailBody = emailBody.replace("SHIPMENTNO", tracking.getShipment().getShipmentId());
//                    emailBody = emailBody.replace("DATE", date);
//                    emailBody = emailBody.replace("STATUS", MessagesProducer.getValue("status"+statusId));
//
//                    StringBuffer emailText = new StringBuffer();
//                    emailText.append(MessagesProducer.getValue("emailHeader"));
//                    emailText.append("<br/><br/>");
//                    emailText.append(emailBody);
//                    emailText.append("<br/><br/>");
//                    emailText.append(MessagesProducer.getValue("emailFooter"));
//
//                    String subject = MessagesProducer.getValue("emailSubject");
//                    subject = subject.replace("SHIPMENTNO", tracking.getShipment().getShipmentId());
//
//                    Email email = new Email();
//                    email.setMessage(emailText.toString());
//                    email.setSubject(subject);
//                    email.setReceiver(tracking.getShipment().getReceiver().getEmail());
//
//                    emailService.send(email);
//                }
//            }
//        }

    }

//    private void createTransaction(TrackingID trackingID) throws Exception {
//        URL obj = new URL(ConfigPropertyProducer.getBlockchainTrackPath());
//
//        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
//        urlConnection.setRequestMethod("POST");
//        urlConnection.setRequestProperty("Content-Type", "application/json");
//
//        urlConnection.setDoOutput(true);
//        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
//        wr.writeBytes(trackingID.toString());
//        wr.flush();
//        wr.close();
//    }
//
//    private String createTimestamp(Date date) {
//        StringBuilder builder = new StringBuilder();
//        builder.append(new SimpleDateFormat("yyyy-MM-dd").format(date));
//        builder.append("T");
//        builder.append(new SimpleDateFormat("HH:mm:ss.SSSS").format(date));
//        builder.append("Z");
//
//        return builder.toString();
//    }


}
