package me.alexanderhodes.blocktrace.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import me.alexanderhodes.blocktrace.model.Address;
import me.alexanderhodes.blocktrace.model.Country;
import me.alexanderhodes.blocktrace.model.Customer;
import me.alexanderhodes.blocktrace.model.Email;
import me.alexanderhodes.blocktrace.model.Product;
import me.alexanderhodes.blocktrace.model.Shipment;
import me.alexanderhodes.blocktrace.model.ShipmentStatus;
import me.alexanderhodes.blocktrace.model.ShipmentType;
import me.alexanderhodes.blocktrace.model.Tracking;
import me.alexanderhodes.blocktrace.service.AddressService;
import me.alexanderhodes.blocktrace.service.CountryService;
import me.alexanderhodes.blocktrace.service.CustomerService;
import me.alexanderhodes.blocktrace.service.ProductService;
import me.alexanderhodes.blocktrace.service.ShipmentService;
import me.alexanderhodes.blocktrace.service.ShipmentTypeService;
import me.alexanderhodes.blocktrace.service.TrackingService;
import me.alexanderhodes.blocktrace.util.Constants;
import me.alexanderhodes.blocktrace.util.MessagesProducer;

/**
 * Created by alexa on 24.09.2017.
 */
@Named
@Stateful
@RequestScoped
public class SendBean implements Serializable {

	private static final long serialVersionUID = 1L;

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
	public void init() {
		this.sender = new Customer();
		this.receiver = new Customer();
		this.shipment = new Shipment();

		this.countries = initCountries();
		this.shipmentTypeList = initShipmentTypeList();
		this.productList = initProductList();
	}

	private List<String> initCountries() {
		// load countries from json file and save them in a list
		List<Country> list = CountryService.getCountryList();
		List<String> countryNames = new ArrayList<>();

		for (Country c : list) {
			countryNames.add(c.getName());
		}

		return countryNames;
	}

	private List<ShipmentType> initShipmentTypeList() {
		return shipmentTypeService.listAll();
	}

	private List<Product> initProductList() {
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
	 * Create Shipment
	 *
	 * @return Adress
	 */
	public String createShipment() {
		// validate input
		if (!validateCustomer(sender, "sender") || !validateAddress(sender.getAddress(), "sender")
				|| !validateCustomer(receiver, "receiver") || !validateAddress(receiver.getAddress(), "receiver")) {
			// input is not valid
			return "";
		} else if (shipment.getShipmentType() == null) {
			// shipment type was not selected
			FacesContext.getCurrentInstance().addMessage("sendForm:shipmentShipmentType",
					new FacesMessage("Please select a type for your shipment."));
			return "";
		} else {
			try {
				// saving address
				addressService.persist(sender.getAddress());
				addressService.persist(receiver.getAddress());
				// saving customer
				sender = customerService.createCustomer(sender);
				receiver = customerService.createCustomer(receiver);
				// set Attributes at shipment
				shipment.setReceiver(receiver);
				shipment.setSender(sender);
				// save shipment
				shipment = shipmentService.persist(shipment);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// create first tracking information
			Tracking tracking = new Tracking(shipment, ShipmentStatus.DATARECEIVED, new Date(), "");
			trackingService.uploadTracking(tracking);

			// clean fields after saving data
			sender = new Customer();
			receiver = new Customer();
			shipment = new Shipment();

		}

		return "";
	}

	/**
	 * validate entered customer data
	 *
	 * @param customer Customer which has to be validated
	 * @param type type of customer (receiver or sender)
	 * @return boolean if input is valid
	 */
	private boolean validateCustomer(Customer customer, String type) {
		if (customer.getSurname() == null || customer.getSurname().isEmpty()) {
			// surname was not entered
			FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Surname",
					new FacesMessage(MessagesProducer.getValue("send1")));
			return false;
		} else if (customer.getLastName() == null || customer.getLastName().isEmpty()) {
			// last name was not entered
			FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "LastName",
					new FacesMessage(MessagesProducer.getValue("send2")));
			return false;
		} else if (customer.getEmail().length() > 0 && !customer.getEmail().matches(Constants.REGEX_MAIL)) {
			// entered email is not valid
			FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Email",
					new FacesMessage(MessagesProducer.getValue("send3")));
			return false;
		} else if (customer.getPhone().length() > 0 && !customer.getPhone().matches(Constants.REGEX_TELNUMBER)) {
			// entered phone is not valid
			FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Phone",
					new FacesMessage(MessagesProducer.getValue("send4")));
			return false;
		}
		return true;
	}

	/**
	 * validate entered address
	 *  
	 * @param address Address which has to be validated
	 * @param type type of the customer (receiver or sender)
	 * @return boolean if input is valid
	 */
	private boolean validateAddress(Address address, String type) {
		if (address.getStreet() == null || address.getStreet().isEmpty()) {
			// street was not entered
			FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Street",
					new FacesMessage(MessagesProducer.getValue("send5")));
			return false;
		} else if (address.getNumber() == null || address.getNumber().isEmpty()) {
			// number was not entered
			FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Number",
					new FacesMessage(MessagesProducer.getValue("send6")));
			return false;
		} else if (address.getZipCode() == null || address.getZipCode().isEmpty()) {
			// postal code was not entered
			FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "ZipCode",
					new FacesMessage(MessagesProducer.getValue("send7")));
			return false;
		} else if (address.getCity() == null || address.getCity().isEmpty()) {
			// city was not entered
			FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "City",
					new FacesMessage(MessagesProducer.getValue("send8")));
			return false;
		} else if (address.getCountry() == null || address.getCountry().isEmpty()) {
			// country was not entered
			FacesContext.getCurrentInstance().addMessage("sendForm:" + type + "Country",
					new FacesMessage(MessagesProducer.getValue("send9")));
			return false;
		}

		return true;
	}

}
