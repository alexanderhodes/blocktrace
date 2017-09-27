package me.alexanderhodes.blocktrace.client.view;

import me.alexanderhodes.blocktrace.client.config.ConfigurationProvider;
import me.alexanderhodes.blocktrace.client.model.*;
import me.alexanderhodes.blocktrace.client.net.TrackingService;
import me.alexanderhodes.blocktrace.client.view.components.Button;
import me.alexanderhodes.blocktrace.client.view.components.Label;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by alexa on 26.09.2017.
 */
public class TrackingOverview {

    private final int zoom = ConfigurationProvider.ZOOM;
    private Shipment shipment;
    private JTable table;
    private List<Tracking> trackingList;
    private TrackingService trackingService;

    public TrackingOverview(Shipment shipment) {
        this.shipment = shipment;
        this.trackingService = new TrackingService();
    }

    public void initialize() {
        JFrame frame = new JFrame();
        frame.setBackground(Color.WHITE);
        frame.setResizable(false);
        frame.setTitle("BlockTrace - Shipment Details - " + shipment.getShipmentId());
        frame.setBounds(100, 100, zoom * 650, zoom * 450);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Labels for Shipment Data
        Label lblShipment = new Label("Shipment:");
        Label lblShipmentId = new Label(shipment.getShipmentId());
        String date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(shipment.getCreated());
        Label lblShipmentCreated = new Label(date);

        // Labels for Sender Data
        Label lblSender = new Label("Sender:");
        Label lblSenderName = new Label(shipment.getSender().getSurname() + " " + shipment.getSender().getLastName());

        Address senderAddress = shipment.getSender().getAddress();
        Label lblSenderStreet = new Label(senderAddress.getStreet() + " " + senderAddress.getNumber());
        Label lblSenderCity = new Label((senderAddress.getZipCode() + " " + senderAddress.getCity() + " (" +
                senderAddress.getCountry() + ")"));

        // Labels for Receiver Data
        Label lblReceiver = new Label("Receiver:");
        Label lblReceiverName = new Label(shipment.getReceiver().getSurname() + " " +
                shipment.getReceiver().getLastName());

        Address receiverAddress = shipment.getReceiver().getAddress();
        Label lblReceiverStreet = new Label(receiverAddress.getStreet() + " " + receiverAddress.getNumber());
        Label lblReceiverCity = new Label((receiverAddress.getZipCode() + " " + receiverAddress.getCity() + " (" +
                receiverAddress.getCountry() + ")"));

        // Button for generating new status
        Button btnNewStatus = new Button("Add Status");

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);

        JScrollPane pane = new JScrollPane();
        pane.setBounds(zoom * 10, zoom * 120, zoom * 620, zoom * 265);
        contentPane.add(pane);

        table = new JTable();
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, zoom * 15));
        table.setRowHeight(zoom * 20);
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, zoom * 15));

        pane.setViewportView(table);

        String[] head = {"No", "Timestamp", "Status"};

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(head);

        model = loadTrackingData(model);

        table.setModel(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(zoom * 25);
        table.getColumnModel().getColumn(1).setPreferredWidth(zoom * 140);
        table.getColumnModel().getColumn(2).setPreferredWidth(zoom * 485);

        // set bounds of labels
        lblShipment.setBounds(zoom * 10, zoom * 10, zoom * 80, zoom * 20);
        lblShipmentId.setBounds(zoom * 95, zoom * 10, zoom * 140, zoom * 20);
        lblShipmentCreated.setBounds(zoom * 240, zoom * 10, zoom * 200, zoom * 20);

        lblSender.setBounds(zoom * 10, zoom * 30, zoom * 80, zoom * 20);
        lblSenderName.setBounds(zoom * 95, zoom * 30, zoom * 250, zoom * 20);
        lblSenderStreet.setBounds(zoom * 95, zoom * 50, zoom * 205, zoom * 20);
        lblSenderCity.setBounds(zoom * 300, zoom * 50, zoom * 300, zoom * 20);

        lblReceiver.setBounds(zoom * 10, zoom * 70, zoom * 80, zoom * 20);
        lblReceiverName.setBounds(zoom * 95, zoom * 70, zoom * 250, zoom * 20);
        lblReceiverStreet.setBounds(zoom * 95, zoom * 90, zoom * 205, zoom * 20);
        lblReceiverCity.setBounds(zoom * 300, zoom * 90, zoom * 300, zoom * 20);

        btnNewStatus.setBounds(zoom * 10, zoom * 390, zoom * 80, zoom * 30);

        // Add components to view
        frame.getContentPane().add(lblShipment);
        frame.getContentPane().add(lblShipmentId);
        frame.getContentPane().add(lblShipmentCreated);

        frame.getContentPane().add(lblSender);
        frame.getContentPane().add(lblSenderName);
        frame.getContentPane().add(lblSenderStreet);
        frame.getContentPane().add(lblSenderCity);

        frame.getContentPane().add(lblReceiver);
        frame.getContentPane().add(lblReceiverName);
        frame.getContentPane().add(lblReceiverStreet);
        frame.getContentPane().add(lblReceiverCity);

        frame.getContentPane().add(btnNewStatus);

        btnNewStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (trackingList.size() < 12) {
                    Tracking tracking = updateTracking();

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    String timestamp = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(tracking.getTimestamp());

                    model.addRow(new String[]{String.valueOf(trackingList.size()), timestamp,
                            tracking.getShipmentStatus().getName()});

                    table.setModel(model);
                }
            }
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private DefaultTableModel loadTrackingData(DefaultTableModel model) {

        trackingList = trackingService.getTrackingList(shipment.getShipmentId());

        if (trackingList != null && trackingList.size() > 0) {
            for (int i = 0; i < trackingList.size(); i++) {
                String date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(trackingList.get(i).getTimestamp());
                model.addRow(new String[]{String.valueOf(i + 1), date, trackingList.get(i).getShipmentStatus().getName()});
            }
        }

        return model;
    }

    private Tracking updateTracking() {
        Tracking tracking = new Tracking();
        tracking.setShipment(shipment);
        tracking.setTimestamp(new Date());

        if (trackingList != null && trackingList.size() > 0) {
            // calculate next Tracking-Status
            List<ShipmentStatus> enumList = Arrays.asList(ShipmentStatus.values());

            Tracking latestTracking = trackingList.get(trackingList.size() - 1);
            int latestId = latestTracking.getShipmentStatus().getId();

            for (ShipmentStatus shipmentStatus : enumList) {
                if (shipmentStatus.getId() == (latestId + 1)) {
                    tracking.setShipmentStatus(shipmentStatus);
                }
            }

        } else {
            trackingList = new ArrayList<>();
            tracking.setShipmentStatus(ShipmentStatus.DATARECEIVED);
        }

        trackingList.add(tracking);

        trackingService.postTracking(tracking);

        return tracking;
    }

}
