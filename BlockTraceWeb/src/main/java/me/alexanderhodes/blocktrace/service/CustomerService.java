package me.alexanderhodes.blocktrace.service;

import me.alexanderhodes.blocktrace.model.Customer;

import javax.ejb.Stateless;
import java.io.Serializable;

/**
 * Created by alexa on 23.09.2017.
 */
@Stateless
public class CustomerService extends AbstractService<Customer> implements Serializable {

    public CustomerService () {
        super(Customer.class);
    }

    public Customer createCustomer (Customer customer) {
        entityManager.persist(customer);
        return customer;
    }

}
