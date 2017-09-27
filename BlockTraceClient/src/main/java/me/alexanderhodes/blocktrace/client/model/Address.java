package me.alexanderhodes.blocktrace.client.model;

import java.io.Serializable;

/**
 * Created by alexa on 23.09.2017.
 */
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
    private String street;
    private String number;
    private String department;
    private String zipCode;
    private String city;
    private String country;

    public Address() {

    }

    public Address(String street, String number, String department, String zipCode, String city, String country) {
        this.street = street;
        this.number = number;
        this.department = department;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
