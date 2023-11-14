package edu.sjsu.cs.cs151javazon;

import java.util.ArrayList;

public class ProductManager {
    private static ProductManager instance;
    private final String textFile = "Products.txt";
    private final ArrayList<Product> products = new ArrayList<>();
    private ProductManager() {
    }
    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }
    public void loadProducts() {
    }
    public void addProduct(Product p) {
        products.add(p);
    }
    public Product searchProduct() {
        return products.get(0);
    }
}
