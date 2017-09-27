package me.alexanderhodes.blocktrace.client.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import me.alexanderhodes.blocktrace.client.config.ConfigurationProvider;
import me.alexanderhodes.blocktrace.client.model.Shipment;
import me.alexanderhodes.blocktrace.client.net.ShipmentService;
import me.alexanderhodes.blocktrace.client.view.components.Button;
import me.alexanderhodes.blocktrace.client.view.components.Label;

/**
 * Created by alexa on 26.09.2017.
 */
public class ShipmentOverview {

    private final int zoom = ConfigurationProvider.ZOOM;
    private List<Shipment> shipmentList;
    private JTable table;
    private ShipmentService shipmentService;

    public static void main (String[] args) {
        ShipmentOverview to = new ShipmentOverview();
        to.initialize();
    }


    public ShipmentOverview() {
        shipmentService = new ShipmentService();
        shipmentList = shipmentService.requestShipments();
    }

    public void initialize () {
        JFrame frame = new JFrame();
        frame.setTitle("BlockTrace - Shipment Overview");
        frame.setBounds(100, 100, zoom * 560, zoom * 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);

        // Initialize JPanel
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);

        // Initialize Label
        Label lblUpside = new Label("Shipment Overview");
        lblUpside.setFont(new Font("Arial", Font.PLAIN, ConfigurationProvider.ZOOM*16));
        lblUpside.setBounds(zoom * 10, zoom * 10, zoom * 200, zoom * 30);
        contentPane.add(lblUpside);

        // Initialize JScrollPane
        JScrollPane pane = new JScrollPane();
        pane.setBounds(zoom * 10, zoom * 40, zoom * 530, zoom * 400);
        contentPane.add(pane);

        // Initialize JTable
        table = new JTable();
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, zoom * 15));
        table.setRowHeight(zoom * 20);
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, zoom * 15));

        pane.setViewportView(table);

        // Add Mouse Listener to Table that by clicking tracking data for shipment is shown
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	// get selected row
                Shipment shipment = shipmentList.get(table.getSelectedRow());
                // Initialize TrackingOverview
                TrackingOverview trackingOverview = new TrackingOverview(shipment);
                trackingOverview.initialize();
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        // Create new table model
        String[] head = {"Shipment", "Timestamp", "Shipment Type"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(head);
        // load shipments from web application
        model = loadData(model);
        // set table model
        table.setModel(model);

        // Initializing button for refreshing data
        Button button = new Button("refresh");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Updating table model by reloading data
                DefaultTableModel model = reloadData();
                table.setModel(model);
            }
        });

        button.setBounds(zoom * 10, zoom * 450, zoom * 80, zoom * 30);
        frame.getContentPane().add(button);
        // Initializing table
        Label lblBottom = new Label("Click on row for showing shipment details!");
        lblBottom.setBounds(zoom * 100, zoom * 450, zoom * 300, zoom * 30);
        frame.getContentPane().add(lblBottom);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Update data model of Table for showing information.
     *
     * @param model Table-Model representing current table
     * @return updated Table-Model
     */
    public DefaultTableModel loadData (DefaultTableModel model) {
    	// Load shipments from web application
        for (Shipment s : shipmentList) {
            String date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(s.getCreated());
            model.addRow(new String[] {s.getShipmentId(), date, s.getShipmentType().getName() });
        }

        return model;
    }

    /**
     * Reload data for showing actual data in table
     * 
     * @return new Table-Model
     */
    public DefaultTableModel reloadData () {
        DefaultTableModel model = new DefaultTableModel();
        // request shipments from web application
        shipmentList = shipmentService.requestShipments();
        // set table header
        String[] head = {"Shipment", "Timestamp", "Shipment Type"};
        model.setColumnIdentifiers(head);
        // set table rows
        for (Shipment s : shipmentList) {
            String date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(s.getCreated());
            model.addRow(new String[] {s.getShipmentId(), date, s.getShipmentType().getName() });
        }

        return model;
    }

}
