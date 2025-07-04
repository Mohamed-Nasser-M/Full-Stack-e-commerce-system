package org.example.product;

import java.time.LocalDate;
import org.example.shipping.Shippable;

public class ExpirableProduct extends Product implements Shippable {
    private LocalDate expiryDate;
    private double weight;

    public ExpirableProduct(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
        this.weight = weight;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public boolean requiresShipping() {
        return true;
    }

    @Override
    public String getName() { return name; }

    @Override
    public double getWeight() { return weight; }
}
