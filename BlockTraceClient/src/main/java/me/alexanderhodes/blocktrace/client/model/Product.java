package me.alexanderhodes.blocktrace.client.model;

import java.io.Serializable;

/**
 * Created by alexa on 23.09.2017.
 */

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
    private double price;

    public Product () {

    }

    public Product (long id, double price) {
        this.id = id;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id == product.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
