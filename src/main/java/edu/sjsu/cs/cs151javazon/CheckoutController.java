package edu.sjsu.cs.cs151javazon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CheckoutController {
    @FXML
    private TableView<Product> cartTable;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> quantityCol;
    @FXML
    private TableColumn<Product, Double> priceCol;
    @FXML
    private TableColumn<Product, Button> removeCol;
    @FXML
    private Button clearCartButton;
    @FXML
    private Button checkoutButton;
    @FXML
    private Label totalPriceLabel;
    @FXML
    public void initialize() {
        // Set up the table columns
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity")); // Adjust if your Product class has a quantity field
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        // Load cart items
        loadCartItems();
        // Set up button actions
        //clearCartButton.setOnAction(event -> ShoppingCart.getInstance().clearCart());
        //checkoutButton.setOnAction(event -> performCheckout());
    }
    private void loadCartItems() {
        cartTable.getItems().setAll(ShoppingCart.getInstance().getProducts());
        updateTotalPrice();
    }
    private void updateTotalPrice() {
        double totalPrice = ShoppingCart.getInstance().getTotalPrice();
        totalPriceLabel.setText(String.format("Total Price: $%.2f", totalPrice));
    }
    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainProductPageController mainProductPageController = new MainProductPageController();
        Parent root = mainProductPageController.getRoot(currentStage);
        Scene currentScene = currentStage.getScene();
        double adjustedWidth = currentStage.getWidth() - (currentStage.getWidth() - currentScene.getWidth());
        double adjustedHeight = currentStage.getHeight() - (currentStage.getHeight() - currentScene.getHeight());
        currentStage.setScene(new Scene(root, adjustedWidth, adjustedHeight));
    }
    private Button createButton(String label, Runnable action) {
        Button button = new Button(label);
        button.setOnAction(e -> action.run());
        return button;
    }
    private void removeProductFromCart(Product product) {
        ShoppingCart.getInstance().removeProduct(product);
        loadCartItems();
    }
    @FXML
    protected void performCheckout() {
        // Implement checkout logic
        clearCart();
        showCheckoutSuccessPopup();
    }
    @FXML
    //Clear the cart when the button is pressed
    private void clearCart() {
        ShoppingCart.getInstance().clearCart();
        loadCartItems();
    }
    private void showCheckoutSuccessPopup() {
        // Create a new stage (window)
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Make it modal
        popupStage.setTitle("Checkout Success");
        // Create a label for the message
        Label messageLabel = new Label("Successfully checked out. Total price: $" +
                                       String.format("%.2f", ShoppingCart.getInstance().getTotalPrice()));
        // Create a layout and add the label to it
        VBox layout = new VBox(10);
        layout.getChildren().add(messageLabel);
        layout.setAlignment(Pos.CENTER);
        // Create a scene with the layout and set it on the stage
        Scene scene = new Scene(layout, 300, 100);
        popupStage.setScene(scene);
        // Show the popup stage
        popupStage.show();
    }
}


