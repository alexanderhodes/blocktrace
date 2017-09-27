package me.alexanderhodes.blocktrace.client.model;

import java.io.Serializable;

/**
 * Created by alexa on 23.09.2017.
 */
public class ShipmentType implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
    private String name;
    private int height;
    private int width;
    private int length;
    private double weight;
    private Product product;

    public ShipmentType () {

    }

    public ShipmentType (long id, String name, int height, int width, int length, double weight, Product product) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.width = width;
        this.length = length;
        this.weight = weight;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipmentType that = (ShipmentType) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
