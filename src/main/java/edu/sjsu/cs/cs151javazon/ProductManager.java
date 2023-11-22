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
    public void loadProduct(ArrayList<Product> products) {
        this.products = products;
    }
    public void loadProducts(){ products = deserializeArrList(textFile); }
    public ArrayList<Product> deserializeArrList(String file) {
        ArrayList<Product> products;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            products = (ArrayList<Product>) in.readObject(); // fix
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

    public ArrayList<Product> addProduct(Product p) {
        products.add(p);
        return products;
    }
    // will use later
    public Product searchProduct(String name) {
        for(Product product : products){
            if(product.getName().equals(name)){
                return product;
            }
        }
        return null;
    }
}
