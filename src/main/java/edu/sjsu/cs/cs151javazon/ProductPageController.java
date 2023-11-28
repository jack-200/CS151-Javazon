package edu.sjsu.cs.cs151javazon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import static edu.sjsu.cs.cs151javazon.HelloController.loadMainProductPageHelper;

public class ProductPageController {
    @FXML
    private Label name, price, description, stock;
    @FXML
    private ImageView imageView;
    @FXML
    private MenuItem quantity;
    @FXML
    private Button addToCart, buyNow, addReview;
    @FXML
    private CheckBox isGift;
    @FXML
    private MenuButton menuButton;
    public Label getName() {
        name.setWrapText(true);
        return name;
    }
    public Label getPrice() { return price; }
    public Label getDescription() {
        description.setWrapText(true);
        return description;
    }
    public Label getStock() { return stock; }
    public ImageView getImageView() { return imageView; }
    public Button getBuyNow() { return buyNow; }
    public Button getAddToCart() { return addToCart; }
    public MenuItem getQuantity() { return quantity; }
    public CheckBox getIsGift() { return isGift; }
    //public MenuItem getQuantity(){ return quantity.get}
    @FXML
    protected void onGoBackClick(ActionEvent event) {
        loadMainProductPageHelper(event);
    }
    @FXML
    protected void onMenuItemClick(ActionEvent event) {
        quantity = (MenuItem) event.getSource();
        menuButton.setText(quantity.getText());
    }
    @FXML
    protected void onAddReviewClick(ActionEvent event) {
        System.out.println("Add review clicked");
    }
}
