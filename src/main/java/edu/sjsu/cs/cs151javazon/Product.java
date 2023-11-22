package edu.sjsu.cs.cs151javazon;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private static final long serialVersionUID = 8248061374072736483L;
    private static Product instance;
    private String name, description, url;
    private double price, rating;
    private int quantity;
    private ArrayList<Review> reviews;
    public Product() { System.out.println("Product()"); }
    public Product(String name, int quantity, double price, String description, String url) {
        setName(name);
        setQuantity(quantity);
        setPrice(price);
        setDescription(description);
        setUrl(url);
    }
    public static Product getInstance() {
        if (instance == null) { return null; }
        return instance;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ArrayList<Review> getReviews() { return reviews; }
    public void setReviews(ArrayList<Review> reviews) { this.reviews = reviews; }


    public void calculateRating() {
        double sum = 0;
        for (Review review : reviews) { sum += review.getStars(); }
        rating = sum / reviews.size();
    }
    public double getRating() {
        calculateRating();
        return rating;
    }
    @Override
    public String toString() { return getDescription() + ", " + getPrice() + ", " + getRating() + ", " + getReviews(); }
}