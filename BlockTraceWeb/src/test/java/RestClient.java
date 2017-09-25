import com.google.gson.*;
import me.alexanderhodes.blocktrace.model.*;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by alexa on 23.09.2017.
 */
public class RestClient {

    @Test
    public void testGetRequest () throws Exception {
        String url = "http://localhost:8080/blocktrace/rest/shipment/201709230000017";

        URL obj = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();

        urlConnection.setRequestMethod("GET");

        System.out.println(urlConnection.getResponseCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        StringBuffer buffer = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            buffer.append(inputLine);
        }
        reader.close();

        System.out.println(buffer.toString());

        GsonBuilder builder = new GsonBuilder();


        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
            }
        });
        // TODO: Datetime-Parser erstellen

        Gson gson = builder.create();

        Shipment shipment = gson.fromJson(buffer.toString(), Shipment.class);
        System.out.print(shipment.getShipmentId());
    }

    @Test
    public void testCreatingShipment () {
        Customer sender = createSender();
        Customer receiver = createReceiver();

        Product product = new Product();
        product.setId(1);

        ShipmentType shipmentType = new ShipmentType();
        shipmentType.setId(1);

        Shipment shipment = createShipment(sender, receiver, product, shipmentType);

        try {
            sendRequest(shipment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRequest (Shipment shipment) throws Exception {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String json = gson.toJson(shipment);

        String url = "http://localhost:8080/blocktrace/rest/shipment/";
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

    public Customer createSender () {
        Address address = new Address();
        address.setId(1);
//        address.setStreet("Musterstrasse");
//        address.setNumber("1");
//        address.setZipCode("36132");
//        address.setCity("Eiterfeld");
//        address.setCountry("Deutschland");

        Customer customer = new Customer();
        customer.setId(1);
//        customer.setAddress(address);
//        customer.setEmail("alexander.hodes@hft-leipzig.de");
//        customer.setSurname("Max");
//        customer.setLastName("Mustermann");

        return customer;
    }

    public Customer createReceiver () {
        Address address = new Address();
        address.setId(2);

//        address.setStreet("Am Bahnhof");
//        address.setNumber("3");
//        address.setZipCode("72485");
//        address.setCity("Albstadt");
//        address.setCountry("Deutschland");

        Customer customer = new Customer();
        customer.setId(2);
//        customer.setAddress(address);
//        customer.setEmail("alexander.hodes@live.com");
//        customer.setSurname("John");
//        customer.setLastName("Smith");

        return customer;
    }

    public Shipment createShipment (Customer sender, Customer receiver, Product product, ShipmentType shipmentType) {
        Shipment shipment = new Shipment();
        shipment.setEmailNotification(true);
        shipment.setDeliveryDate(null);
        shipment.setPickup(false);
        shipment.setReferenceNumber("0");
        shipment.setShipmentType(shipmentType);
        shipment.setSender(sender);
        shipment.setReceiver(receiver);

        return shipment;
    }

}
