package org.example.product;

import org.example.shipping.Shippable;

public class NonExpirableProduct extends Product implements Shippable {
    private boolean shippable;
    private double weight;

    public NonExpirableProduct(String name, double price, int quantity, boolean shippable, double weight) {
        super(name, price, quantity);
        this.shippable = shippable;
        this.weight = weight;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public boolean requiresShipping() {
        return shippable;
    }

    @Override
    public String getName() { return name; }

    @Override
    public double getWeight() {
        return weight;
    }
}
