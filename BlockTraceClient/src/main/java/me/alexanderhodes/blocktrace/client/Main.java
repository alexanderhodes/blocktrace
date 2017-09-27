package me.alexanderhodes.blocktrace.client;

import me.alexanderhodes.blocktrace.client.view.ShipmentOverview;

import javax.swing.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main (String[] args) {
        try {
            if (!checkServerAlive()) {
                ShipmentOverview shipmentOverview = new ShipmentOverview();
                shipmentOverview.initialize();
            }
        } catch (Exception e) {
            e.printStackTrace();

            JOptionPane.showConfirmDialog(null, "Es besteht keine Verbindung zum Server" +
                    "bitte versuchen Sie es später erneut.");

        }
    }

    private static boolean checkServerAlive () throws Exception {
        URL url = new URL("http://localhost:8080/blocktrace");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        return (urlConnection.getResponseCode() == 200);
    }

}
