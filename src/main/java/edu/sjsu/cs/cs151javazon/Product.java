package edu.sjsu.cs.cs151javazon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private static final long serialVersionUID = 8248061374072736483L;
    private static Product instance;
    private String name, description, url;
    private double price, rating;
    private int quantity;
    private ArrayList<Review> reviews;
    @FXML
    private Scene productPage;
    public Product() { System.out.println("Product()"); }
    public Product(String name, int quantity, double price, String description, String url) throws IOException {
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
                if (controller.getIsGift().isSelected()) {
                    // set new address
                }
                try {
                    Javazon.switchScene("Checkout.fxml");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            controller.getAddToCart().setOnAction(e -> {
                //transition to shopping cart scene
            });
        } else {
            System.out.println("Cannot create product page");
        }
        productPage = new Scene(root);
        Javazon.getStage().setScene(productPage);
    }
    @FXML
    public Scene getProductPage() { return productPage; }
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
    public void setReviews(ArrayList<Review> reviews) { this.reviews = reviews; }
    public void calculateRating() {
        double sum = 0;
        for (Review review : reviews) { sum += review.getStars(); }
        rating = sum / reviews.size();
    }
    public void setDescription(String description) { this.description = description; }
}