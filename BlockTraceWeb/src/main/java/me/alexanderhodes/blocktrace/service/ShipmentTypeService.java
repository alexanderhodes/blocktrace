package me.alexanderhodes.blocktrace.service;

import me.alexanderhodes.blocktrace.model.ShipmentType;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 * Created by alexa on 24.09.2017.
 */
@Stateless
public class ShipmentTypeService extends AbstractService<ShipmentType> implements Serializable {

    public ShipmentTypeService () {
        super(ShipmentType.class);
    }

    public ShipmentType getShipmentType (long id) {
        TypedQuery<ShipmentType> query = entityManager.createNamedQuery(ShipmentType.GET_SHIPMENTTYPE_ID,
                ShipmentType.class);
        query.setParameter("id", id);

        List<ShipmentType> list = query.getResultList();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


}
