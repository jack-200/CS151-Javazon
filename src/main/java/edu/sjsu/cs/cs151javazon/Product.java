package edu.sjsu.cs.cs151javazon;

import java.util.ArrayList;

public class Product {
    private String description;
    private double price;
    private double rating;
    private ArrayList<Review> reviews;
    public Product(String description, double price, ArrayList<Review> reviews) {
        setDescription(description);
        setPrice(price);
        setReviews(reviews);
        calculateRating();
    }
    public void calculateRating() {
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getStars();
        }
        rating = sum / reviews.size();
    }
    @Override
    public String toString() {
        return getDescription() + ", " + getPrice() + ", " + getRating() + ", " + getReviews();
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getRating() {
        calculateRating();
        return rating;
    }
    public ArrayList<Review> getReviews() {
        return reviews;
    }
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
