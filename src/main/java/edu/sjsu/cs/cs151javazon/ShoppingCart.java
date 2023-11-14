package edu.sjsu.cs.cs151javazon;

import java.util.ArrayList;

public class ShoppingCart {
    private static ShoppingCart instance;
    private final ArrayList<Product> products = new ArrayList<Product>();
    private ShoppingCart() {
    }
    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }
    public void loadProducts() {
    }
    public void addProduct(Product a) {
        products.add(a);
    }
}
