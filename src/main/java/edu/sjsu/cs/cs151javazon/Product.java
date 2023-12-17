package edu.sjsu.cs.cs151javazon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import static edu.sjsu.cs.cs151javazon.Javazon.showFadingPopup;
import static edu.sjsu.cs.cs151javazon.MainProductPageController.isUserSignedIn;

public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 8248061374072736483L;
    private static Product instance;
    private String name, description, url;
    private double price, rating;
    private int quantity;
    private ArrayList<Review> reviews;
    private Product() { }
    public Product(String name, int quantity, double price, String description, String url) throws IOException {
        this(name, quantity, price, description, url, new ArrayList<>());
    }
    public Product(String name, int quantity, double price, String description, String url, ArrayList<Review> reviews)
            throws IOException {
        setName(name);
        setQuantity(quantity);
        setPrice(price);
        setDescription(description);
        setUrl(url);
        setReviews(reviews);
    }
    public static Product getInstance() {
        if (instance == null) { return null; }
        return instance;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return Double.compare(other.price, price) == 0 && Objects.equals(name, other.name) &&
               Objects.equals(description, other.description) && Objects.equals(url, other.url);
    }
    public String toString() { return getName() + " $" + getPrice() + ", " + getReviews().size(); }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public ArrayList<Review> getReviews() {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        return reviews;
    }
    public void setReviews(ArrayList<Review> reviews) { this.reviews = reviews; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    @FXML
    public void createProductPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource("IndividualProductPage.fxml"));
        Parent root = fxmlLoader.load();
        ProductPageController controller = fxmlLoader.getController();
        if (controller.getName() != null) {
            controller.getName().setText(name);
            controller.getPrice().setText(Double.toString(price));
            controller.getDescription().setText(description);
            Image image = new Image(url);
            controller.getImageView().setImage(image);
            controller.getStock().setText("In Stock");
            controller.getBuyNow().setOnAction(e -> {
                if (isUserSignedIn()) {
                    if (controller.getQuantity() != null) {
                        for (int i = 0; i < Integer.parseInt(controller.getQuantity().getText()); i++) {
                            ShoppingCart.getInstance().addProduct(this);
                        }
                        try {
                            Javazon.switchScene("Checkout.fxml");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        showFadingPopup(e, "Select Quantity First");
                    }
                } else {
                    showFadingPopup(e, "Sign in to buy product");
                }
            });
            controller.setCurrProduct(this);
            controller.getAddToCart().setOnAction(e -> {
                controller.getBuyNow().fire();
            });
        } else {
            System.out.println("Cannot create product page");
        }
        Javazon.getStage().setScene(new Scene(root));
    }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getRating() {
        calculateRating();
        return rating;
    }
    public void calculateRating() {
        if (reviews == null) {
            rating = 0.0;
            return;
        }
        double sum = 0;
        for (Review review : reviews) { sum += review.getStars(); }
        rating = sum / reviews.size();
    }
}