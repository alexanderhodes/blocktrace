package me.alexanderhodes.blocktrace.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by alexa on 23.09.2017.
 */
@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column
    private String surname;

    @Column
    private String lastName;

    @Column
    private String company;

    @Column(nullable = false)
    @Email
    private String email;

    @Column
    private String phone;

    @ManyToOne
    private Address address;

    public Customer () {
        address = new Address();
    }

    public Customer(String surname, String lastName, String company, String email, String phone, Address address) {
        this.surname = surname;
        this.lastName = lastName;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
