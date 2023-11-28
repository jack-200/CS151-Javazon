package edu.sjsu.cs.cs151javazon;

import java.util.ArrayList;

public class ShoppingCart {
    private static ShoppingCart instance;
    private final ArrayList<Product> products = new ArrayList<>();
    private ShoppingCart() {
    }
    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public void clearCart() {
        products.clear();
    }
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Product p : products) {
            totalPrice += p.getPrice();
        }
        return totalPrice;
    }
    public int getProductCount() {
        return products.size();
    }
    public void listProducts() {
        products.forEach((product) -> System.out.println(product.getName()));
    }
    public void updateQuantity(Product product, int quantity) {
        if (products.contains(product)) {
            if (quantity > 0) {
                products.add(product);
            } else {
                removeProduct(product);
            }
        }
    }
    public void removeProduct(Product product) {
        products.remove(product);
    }
    public ArrayList<Product> getProducts() {
        return products;
    }
}
