package edu.sjsu.cs.cs151javazon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static edu.sjsu.cs.cs151javazon.HelloController.loadMainProductPageHelper;

public class CheckoutController {
    public Button goBack;
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
    public Button getCheckoutButton() { return checkoutButton; }
    @FXML
    public void initialize() {
        // Set up the table columns
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        removeCol.setCellFactory(param -> new TableCell<Product, Button>() {
            private final Button removeButton = new Button("Remove");
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                    removeButton.setOnAction(event -> {
                        Product product = getTableView().getItems().get(getIndex());
                        removeProductFromCart(product);
                        loadCartItems(); // Refresh the cart items in the table
                    });
                }
            }
        });
        // Load cart items
        loadCartItems();
    }
    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        loadMainProductPageHelper(event);
    }
    private Button createButton(String label, Runnable action) {
        Button button = new Button(label);
        button.setOnAction(e -> action.run());
        return button;
    }
    @FXML
    protected void removeProductFromCart(Product product) {
        ShoppingCart.getInstance().removeProduct(product);
        loadCartItems();
    }
    @FXML
    protected void performCheckout() {
        // Implement checkout logic
        showCheckoutSuccessPopup();
        clearCart();
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
    @FXML
    //Clear the cart when the button is pressed
    private void clearCart() {
        ShoppingCart.getInstance().clearCart();
        loadCartItems();
    }
    private void loadCartItems() {
        cartTable.getItems().setAll(ShoppingCart.getInstance().getProducts());
        updateTotalPrice();
    }
    private void updateTotalPrice() {
        double totalPrice = ShoppingCart.getInstance().getTotalPrice();
        totalPriceLabel.setText(String.format("Total Price: $%.2f", totalPrice));
    }
}


