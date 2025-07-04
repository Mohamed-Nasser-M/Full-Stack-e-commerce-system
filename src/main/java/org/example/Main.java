package org.example;

import java.time.LocalDate;
import org.example.cart.Cart;
import org.example.cart.CartItem;
import org.example.customer.Customer;
import org.example.product.ExpirableProduct;
import org.example.product.NonExpirableProduct;
import org.example.shipping.ShippingService;


public class Main {
    public static void main(String[] args) {
        // Define Products
        ExpirableProduct cheese = new ExpirableProduct("Cheese", 100, 5, LocalDate.of(2025, 8, 1), 0.2);
        ExpirableProduct biscuits = new ExpirableProduct("Biscuits", 150, 3, LocalDate.of(2025, 9, 1), 0.7);
        NonExpirableProduct tv = new NonExpirableProduct("TV", 3000, 10, true, 10);
        NonExpirableProduct scratchCard = new NonExpirableProduct("ScratchCard", 50, 100, false, 0);

        // Customer
        Customer customer = new Customer("Ali", 1000);

        // Cart
        Cart cart = new Cart();
        cart.add(cheese, 2);
        cart.add(biscuits, 1);
        cart.add(scratchCard, 1);

        // Checkout
        checkout(customer, cart);
    }

    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.err.println("Error: Cart is empty.");
            return;
        }

        for (CartItem item : cart.getItems()) {
            if (item.product.isExpired()) {
                System.err.println("Error: Product " + item.product.getName() + " is expired.");
                return;
            }
            if (item.quantity > item.product.getQuantity()) {
                System.err.println("Error: Product " + item.product.getName() + " is out of stock.");
                return;
            }
        }

        double subtotal = cart.getSubtotal();
        double shipping = 30; // flat rate
        double total = subtotal + shipping;

        if (total > customer.getBalance()) {
            System.err.println("Error: Insufficient balance.");
            return;
        }

        // Reduce product stock
        for (CartItem item : cart.getItems()) {
            item.product.reduceQuantity(item.quantity);
        }

        // Ship items
        ShippingService.shipItems(cart.getShippableItems());

        // Deduct balance
        customer.deduct(total);

        // Print receipt
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %-12s %.0f\n", item.quantity, item.product.getName(), item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal         %.0f\n", subtotal);
        System.out.printf("Shipping         %.0f\n", shipping);
        System.out.printf("Amount           %.0f\n", total);
        System.out.printf("Customer balance %.0f\n", customer.getBalance());
    }
}
