package me.alexanderhodes.blocktrace.view;

import me.alexanderhodes.blocktrace.model.*;
import me.alexanderhodes.blocktrace.service.*;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alexa on 24.09.2017.
 */
@Named
@Stateful
@RequestScoped
public class SendBean implements Serializable {

    @Inject
    private ProductService productService;

    @Inject
    private ShipmentTypeService shipmentTypeService;

    @Inject
    private ShipmentService shipmentService;

    @Inject
    private CustomerService customerService;

    @Inject
    private AddressService addressService;

    @Inject
    private TrackingService trackingService;

    @Inject
    private EmailService emailService;

    // Sender
    private Customer sender;

    // Receiver
    private Customer receiver;

    // Shipment
    private Shipment shipment;

    private List<String> countries;
    private List<ShipmentType> shipmentTypeList;
    private List<Product> productList;

    @PostConstruct
    public void init () {
        this.sender = new Customer();
        this.receiver = new Customer();
        this.shipment = new Shipment();

        this.countries = initCountries();
        this.shipmentTypeList = initShipmentTypeList();
        this.productList = initProductList();
    }

    private List<String> initCountries () {
        List<Country> list = CountryService.getCountryList();
        List<String> countryNames = new ArrayList<>();

        for (Country c : list) {
            countryNames.add(c.getName());
        }

        return countryNames;
    }

    private List<ShipmentType> initShipmentTypeList () {
        return shipmentTypeService.listAll();
    }

    private List<Product> initProductList () {
        return productService.listAll();
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getReceiver() {
        return receiver;
    }

    public void setReceiver(Customer receiver) {
        this.receiver = receiver;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<ShipmentType> getShipmentTypeList() {
        return shipmentTypeList;
    }

    public void setShipmentTypeList(List<ShipmentType> shipmentTypeList) {
        this.shipmentTypeList = shipmentTypeList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    /**
     *
     * @return
     */
    public String createShipment () {
        // validate input --> if-Abfragen schreiben
        if (!validateCustomer(sender, "sender") || !validateAddress(sender.getAddress(), "sender")
                || !validateCustomer(receiver, "receiver") || !validateAddress(receiver.getAddress(), "receiver")) {
            return "";
        } else {
            // saving attributes
            try {
                addressService.persist(sender.getAddress());
                addressService.persist(receiver.getAddress());

                customerService.createCustomer(sender);
                customerService.createCustomer(receiver);

                shipment = shipmentService.persist(shipment);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // send E-Mail Notification
            if (shipment.isEmailNotification()) {
                // TODO: Subject und Message anpassen

                Email email = new Email();
                email.setReceiver(receiver.getEmail());
                email.setSubject("Sendung versendet");
                email.setMessage(shipment.getShipmentId());

//                emailService.send(email);
            }

            // create first tracking information
            Tracking tracking = new Tracking(shipment, ShipmentStatus.DATARECEIVED, new Date(), "");
            trackingService.uploadTracking(tracking);
        }

        return "";
    }

    /**
     *
     * @param customer
     * @param type
     * @return
     */
    private boolean validateCustomer (Customer customer, String type) {
        if (customer.getSurname() == null || customer.getSurname().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Surname",
                    new FacesMessage("Please enter your surname."));
            return false;
        } else if (customer.getLastName() == null || customer.getLastName().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "LastName",
                    new FacesMessage("Please enter your lastname."));
            return false;
        }else if (!customer.getEmail().matches("")) {
            FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Email",
                    new FacesMessage("Please enter an correct email."));
            return false;
        }else if (!customer.getPhone().matches("")) {
            FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Phone",
                    new FacesMessage("Please enter a correct phone number."));
            return false;
        }

        return true;
    }

    /**
     *
     * @param address
     * @param type
     * @return
     */
    private boolean validateAddress (Address address, String type) {
        if (address.getStreet() == null || address.getStreet().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Street",
                    new FacesMessage("Please enter a street."));
            return false;
        } else if (address.getNumber() == null || address.getNumber().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Number",
                    new FacesMessage("Please enter a number."));
            return false;
        } else if (address.getZipCode() == null || address.getZipCode().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "ZipCode",
                    new FacesMessage("Please enter a zip-code."));
            return false;
        } else if (address.getCity() == null || address.getCity().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "City",
                    new FacesMessage("Please enter a city."));
            return false;
        } else if (address.getCountry() == null || address.getCountry().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Country",
                    new FacesMessage("Please select a country."));
            return false;
        }

        return true;
    }

}
