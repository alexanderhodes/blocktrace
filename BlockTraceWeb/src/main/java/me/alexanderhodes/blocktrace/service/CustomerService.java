package me.alexanderhodes.blocktrace.service;

import java.io.Serializable;

import javax.ejb.Stateless;

import me.alexanderhodes.blocktrace.model.Customer;

/**
 * Created by alexa on 23.09.2017.
 */
@Stateless
public class CustomerService extends AbstractService<Customer> implements Serializable {

	private static final long serialVersionUID = 1L;

	public CustomerService () {
        super(Customer.class);
    }

	/**
	 * create customer
	 * 
	 * @param customer Customer that has to be saved
	 * @return customer
	 */
    public Customer createCustomer (Customer customer) {
    	// save customer
        entityManager.persist(customer);
        return customer;
    }

}
