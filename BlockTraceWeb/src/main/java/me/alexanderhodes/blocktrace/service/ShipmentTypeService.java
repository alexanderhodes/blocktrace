package me.alexanderhodes.blocktrace.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import me.alexanderhodes.blocktrace.model.ShipmentType;

/**
 * Created by alexa on 24.09.2017.
 */
@Stateless
public class ShipmentTypeService extends AbstractService<ShipmentType> implements Serializable {

	private static final long serialVersionUID = 1L;

	public ShipmentTypeService () {
        super(ShipmentType.class);
    }

	/**
	 * Requesting ShipmentType by Id
	 * 
	 * @param id Id of Shipment Type
	 * @return ShipmentType
	 */
    public ShipmentType getShipmentType (long id) {
    	// create query
        TypedQuery<ShipmentType> query = entityManager.createNamedQuery(ShipmentType.GET_SHIPMENTTYPE_ID,
                ShipmentType.class);
        // set parameter
        query.setParameter("id", id);

        List<ShipmentType> list = query.getResultList();
        if (list != null && list.size() > 0) {
            // return found Shipment Type
        	return list.get(0);
        }
        return null;
    }


}
