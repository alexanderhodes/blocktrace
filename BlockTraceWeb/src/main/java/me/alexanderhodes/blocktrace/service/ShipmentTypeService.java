package me.alexanderhodes.blocktrace.service;

import me.alexanderhodes.blocktrace.model.ShipmentType;

import javax.ejb.Stateless;
import java.io.Serializable;

/**
 * Created by alexa on 24.09.2017.
 */
@Stateless
public class ShipmentTypeService extends AbstractService<ShipmentType> implements Serializable {

    public ShipmentTypeService () {
        super(ShipmentType.class);
    }



}
