package edu.sjsu.cs.cs151javazon;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ProductPageController {
    @FXML
    private Label name, price, description, stock;
    @FXML
    private ImageView imageView;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem quantity;
    @FXML
    private Button addToCart, buyNow, goBack;
    @FXML
    private CheckBox isGift;
    public Label getName() { return name; }
    public Label getPrice() { return price; }
    public Label getDescription() { return description; }
    public Label getStock() { return stock; }
    public ImageView getImageView() { return imageView; }
    public Button getBuyNow() { return buyNow; }
    public Button getAddToCart() { return addToCart; }
    public CheckBox getIsGift() { return isGift; }
    @FXML
    protected void onGoBackClick() throws IOException {
        Javazon.switchScene("hello-view.fxml");
    }
}
