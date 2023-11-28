package edu.sjsu.cs.cs151javazon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
    private Button addToCart, buyNow;
    @FXML
    private CheckBox isGift;
    public Label getName() { return name; }
    public Label getPrice() { return price; }
    public Label getDescription() {
        description.setWrapText(true);
        return description;
    }
    public Label getStock() { return stock; }
    public ImageView getImageView() { return imageView; }
    public Button getBuyNow() { return buyNow; }
    public Button getAddToCart() { return addToCart; }
    public CheckBox getIsGift() { return isGift; }
    @FXML
    protected void onGoBackClick(ActionEvent event) {
        loadMainProductPageHelper(event);
    }
}
