package edu.sjsu.cs.cs151javazon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static edu.sjsu.cs.cs151javazon.HelloController.loadMainProductPageHelper;
import static edu.sjsu.cs.cs151javazon.Javazon.showFadingPopup;
import static edu.sjsu.cs.cs151javazon.MainProductPageController.isUserSignedIn;
import static edu.sjsu.cs.cs151javazon.MainProductPageController.notSignedIn;

public class ProductPageController {
    private Product currProduct;
    @FXML
    private Label name, price, description, stock;
    @FXML
    private ImageView imageView;
    @FXML
    private MenuItem quantity;
    @FXML
    private Button addToCart, buyNow;
    @FXML
    private MenuButton menuButton;
    public Label getName() {
        name.setWrapText(true);
        return name;
    }
    public Label getPrice() { return price; }
    public void setCurrProduct(Product product) { currProduct = product; }
    public Label getDescription() {
        description.setWrapText(true);
        return description;
    }
    public Label getStock() { return stock; }
    public ImageView getImageView() { return imageView; }
    public Button getBuyNow() { return buyNow; }
    public Button getAddToCart() { return addToCart; }
    public MenuItem getQuantity() { return quantity; }
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
        if (!isUserSignedIn()) {
            showFadingPopup(event, notSignedIn);
            return;
        }
        Stage reviewStage = new Stage();
        reviewStage.setAlwaysOnTop(true);
        reviewStage.setTitle("Review Form");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);
        Label ratingLabel = new Label("Rating:");
        GridPane.setConstraints(ratingLabel, 0, 0);
        ComboBox<Integer> ratingComboBox = new ComboBox<>();
        ratingComboBox.getItems().addAll(1, 2, 3, 4, 5);
        ratingComboBox.setValue(5);
        GridPane.setConstraints(ratingComboBox, 1, 0);
        Label descriptionLabel = new Label("Description:");
        GridPane.setConstraints(descriptionLabel, 0, 1);
        TextField descriptionTextField = new TextField();
        GridPane.setConstraints(descriptionTextField, 1, 1);
        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 2);
        submitButton.setOnAction(e -> {
            int rating = ratingComboBox.getValue();
            String description = descriptionTextField.getText();
            System.out.println("Rating: " + rating + ", Description: " + description);
            ProductManager.getInstance().addReview(currProduct, new Review(rating, description));
            ProductManager.getInstance().saveProducts();
            reviewStage.close();
        });
        grid.getChildren().addAll(ratingLabel, ratingComboBox, descriptionLabel, descriptionTextField, submitButton);
        Scene scene = new Scene(grid, 300, 200);
        reviewStage.setScene(scene);
        reviewStage.show();
    }
}
