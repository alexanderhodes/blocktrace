package me.alexanderhodes.blocktrace.service;

import java.io.Serializable;

import javax.ejb.Stateless;

import me.alexanderhodes.blocktrace.model.Product;

/**
 * Created by alexa on 24.09.2017.
 */
@Stateless
public class ProductService extends AbstractService<Product> implements Serializable {

	private static final long serialVersionUID = 1L;

	public ProductService () {
        super(Product.class);
    }


}
