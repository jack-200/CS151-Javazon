package edu.sjsu.cs.cs151javazon;

import java.io.*;
import java.util.ArrayList;

public class ProductManager implements Serializable {
    public static final String textFile = "src/main/resources/edu/sjsu/cs/cs151javazon/Products.txt";
    public static int numProducts;
    private static ProductManager instance;
    private ArrayList<Product> products = new ArrayList<>();
    private ProductManager() {
    }
    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }
    public void saveProducts() {
        try {
            for (Product p : products) {
                System.out.println(p);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(textFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(products);
            System.out.println("Products saved to: " + textFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadProduct(ArrayList<Product> products) {
        this.products = products;
    }
    public void loadProducts() { products = deserializeArrList(textFile); }
    public ArrayList<Product> deserializeArrList(String file) {
        ArrayList<Product> products;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            products = (ArrayList<Product>) in.readObject();
            ProductManager.numProducts = products.size();
            return products;
        } catch (EOFException | ClassNotFoundException e) {
            return new ArrayList<>();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Product> searchString(String strToSearch) {
        ArrayList<Product> searchResult = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(strToSearch.toLowerCase())) {
                searchResult.add(product);
            } else if (product.getDescription().toLowerCase().contains(strToSearch.toLowerCase())) {
                searchResult.add(product);
            }
        }
        return searchResult;
    }
    public void addReview(Product product2, Review review) {
        for (Product product : products) {
            if (product.equals(product2)) {
                product.getReviews().add(review);
                System.out.println("Review Added");
            }
        }
    }
    public ArrayList<Product> getProducts() { return products; }
    public void setProducts(ArrayList<Product> products) { this.products = products; }
}
