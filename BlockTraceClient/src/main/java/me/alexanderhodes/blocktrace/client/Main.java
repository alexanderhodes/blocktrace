package me.alexanderhodes.blocktrace.client;

import me.alexanderhodes.blocktrace.client.config.ConfigurationProvider;
import me.alexanderhodes.blocktrace.client.view.ShipmentOverview;

import javax.swing.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main (String[] args) {
        try {
            if (checkServerAlive()) {
                ShipmentOverview shipmentOverview = new ShipmentOverview();
                shipmentOverview.initialize();
            } else {
            	JOptionPane.showConfirmDialog(null, "This client could not connect to the server. Please check your path in "
                		+ "config.properties or try again later.", "Error", JOptionPane.YES_OPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();

            JOptionPane.showConfirmDialog(null, "This client could not connect to the server. Please check your path in "
            		+ "config.properties or try again later.", "Error", JOptionPane.YES_OPTION);
        }
    }

    private static boolean checkServerAlive () throws Exception {
        URL url = new URL(ConfigurationProvider.getRestPath().replace("rest/", ""));
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        return (urlConnection.getResponseCode() == 200);
    }

}
