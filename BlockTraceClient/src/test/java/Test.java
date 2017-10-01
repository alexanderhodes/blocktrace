import me.alexanderhodes.blocktrace.client.model.Shipment;
import me.alexanderhodes.blocktrace.client.model.ShipmentStatus;
import me.alexanderhodes.blocktrace.client.model.Tracking;
import me.alexanderhodes.blocktrace.client.net.ShipmentService;
import me.alexanderhodes.blocktrace.client.net.TrackingService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by alexa on 26.09.2017.
 */
public class Test {

//    @org.junit.Test
    public void testResolution () {

    }

//    @org.junit.Test
    public void testRest () {
        ShipmentService shipmentService = new ShipmentService();
        List<Shipment> shipments = shipmentService.requestShipments();

//        Shipment shipment = shipmentService.requestSingleShipment("201709250000010");
//        System.out.println(shipment.getCreated());
//
//        List<Shipment> shipmentList = shipmentService.requestShipments();
//        System.out.println(shipmentList.size());

        TrackingService trackingService = new TrackingService();
        List<Tracking> trackings = new ArrayList<>();

        for (Shipment s : shipments) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            Tracking t1 = new Tracking(s, ShipmentStatus.DATARECEIVED, new Date(), ShipmentStatus.DATARECEIVED.getPlace());
            calendar.add(Calendar.HOUR, 1);
            Tracking t2 = new Tracking(s, ShipmentStatus.HANDOVER, calendar.getTime(), ShipmentStatus.HANDOVER.getPlace());
            calendar.add(Calendar.HOUR, 1);
            Tracking t3 = new Tracking(s, ShipmentStatus.TRANSPORTLC, calendar.getTime(), ShipmentStatus.TRANSPORTLC.getPlace());
            calendar.add(Calendar.HOUR, 1);
            Tracking t4 = new Tracking(s, ShipmentStatus.ARRIVEDLC, calendar.getTime(), ShipmentStatus.ARRIVEDLC.getPlace());
            calendar.add(Calendar.HOUR, 1);
            Tracking t5 = new Tracking(s, ShipmentStatus.SORTEDLC, calendar.getTime(), ShipmentStatus.SORTEDLC.getPlace());
            calendar.add(Calendar.HOUR, 1);
            Tracking t6 = new Tracking(s, ShipmentStatus.LEFTLC, calendar.getTime(), ShipmentStatus.LEFTLC.getPlace());
            calendar.add(Calendar.HOUR, 1);
            Tracking t7 = new Tracking(s, ShipmentStatus.ARRIVEDDC, calendar.getTime(), ShipmentStatus.ARRIVEDDC.getPlace());


            trackings.add(t1);
            trackings.add(t2);
            trackings.add(t3);
            trackings.add(t4);
            trackings.add(t5);
            trackings.add(t6);
            trackings.add(t7);

            for (Tracking t : trackings) {
                trackingService.postTracking(t);
            }

            trackings = new ArrayList<>();
        }
    }

}
