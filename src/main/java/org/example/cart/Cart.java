package org.example.cart;

import java.util.ArrayList;
import java.util.List;
import org.example.product.Product;
import org.example.shipping.Shippable;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void add(Product product, int quantity) {
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock.");
        }
        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public List<Shippable> getShippableItems() {
        List<Shippable> shippabls = new ArrayList<>();
        for (CartItem item : items) {
            if (item.product.requiresShipping() && item.product instanceof Shippable) {
                for (int i = 0; i < item.quantity; i++) {
                    shippabls.add((Shippable) item.product);
                }
            }
        }
        return shippabls;
    }
}