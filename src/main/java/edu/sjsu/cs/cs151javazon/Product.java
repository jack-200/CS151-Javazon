package edu.sjsu.cs.cs151javazon;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private static Product instance;
    private String name, description, link;
    private double price, rating;
    private int quantity;
    private ArrayList<Review> reviews;
    public Product() { System.out.println("Product()"); }
    public Product(String name, int quantity, double price, String description, String link) {
        setName(name);
        setQuantity(quantity);
        setPrice(price);
        setDescription(description);
        setLink(link);
    }
    public void setLink(String link) { this.link = link; }
    public static Product getInstance() {
        if (instance == null) { return null; }
        return instance;
    }
    @Override
    public String toString() { return getDescription() + ", " + getPrice() + ", " + getRating() + ", " + getReviews(); }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getRating() {
        calculateRating();
        return rating;
    }
    public ArrayList<Review> getReviews() { return reviews; }
    public void calculateRating() {
        double sum = 0;
        for (Review review : reviews) { sum += review.getStars(); }
        rating = sum / reviews.size();
    }
    public void setReviews(ArrayList<Review> reviews) { this.reviews = reviews; }
    public void setDescription(String description) { this.description = description; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String gettLink() { return link; }
}