package me.alexanderhodes.blocktrace.service;

import me.alexanderhodes.blocktrace.model.Product;

import javax.ejb.Stateless;
import java.io.Serializable;

/**
 * Created by alexa on 24.09.2017.
 */
@Stateless
public class ProductService extends AbstractService<Product> implements Serializable {

    public ProductService () {
        super(Product.class);
    }


}
