package me.alexanderhodes.blocktrace.service;

import me.alexanderhodes.blocktrace.model.Address;

import java.io.Serializable;

/**
 * Created by alexa on 24.09.2017.
 */
public class AddressService extends AbstractService<Address> implements Serializable {

    public AddressService () {
        super(Address.class);
    }

}
