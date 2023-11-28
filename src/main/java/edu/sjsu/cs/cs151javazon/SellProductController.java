package edu.sjsu.cs.cs151javazon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static edu.sjsu.cs.cs151javazon.HelloController.loadMainProductPageHelper;
import static edu.sjsu.cs.cs151javazon.Javazon.showFadingPopup;
import static edu.sjsu.cs.cs151javazon.ProductManager.textFile;

public class SellProductController {
    static Product current = null;
    static FXMLLoader currentScene = null;
    @FXML
    public TextField name, price, url;
    ArrayList<Product> products = ProductManager.getInstance().deserializeArrList(textFile);
    @FXML
    private TextArea description;
    @FXML
    private Button AddItem;
    @FXML
    private ImageView imageView;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem quantity;
    @FXML
    public void addProduct(ActionEvent event) throws IOException {
        setters(name, price, url, description, imageView);
        if (!name.getText().isEmpty() && quantity != null && !price.getText().isEmpty() && !url.getText().isEmpty() &&
            !description.getText().isEmpty()) {
            boolean validPrice = true;
            try {
                Double.parseDouble(price.getText());
            } catch (NumberFormatException e) {
                validPrice = false;
            }
            if (Product.getInstance() == null && validPrice) {
                Product product = new Product(name.getText(), Integer.parseInt(quantity.getText()),
                        Double.parseDouble(price.getText()), description.getText(), url.getText());
                current = product;
                // serialize
                products.add(product);
                ProductManager.getInstance().loadProduct(products);
                FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                out.writeObject(products);
                // serialize
                AccountController.current.addToMyMarket(product);
                AccountController.current.loadProduct(AccountController.current.getMyMarket());
                FileOutputStream fileOutputStream1 = new FileOutputStream(AccountController.current.getMyMarketFile());
                ObjectOutputStream out1 = new ObjectOutputStream(fileOutputStream1);
                out1.writeObject(AccountController.current.getMyMarket());
                showFadingPopup(event, "Product Added\n" + "Total number of products: " + products.size());
                // update vbox that is inside scroll pane
                FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource("sellProduct.fxml"));
                ScrollPane sc = (ScrollPane) Javazon.getStage().getScene().getRoot();
                VBox vbox = (VBox) sc.getContent();
                vbox.getChildren().addAll((Pane) fxmlLoader.load());
            } else {
                System.out.println("Enter valid price");
            }
        } else {
            System.out.println("Fill out all text fields");
        }
    }
    public void setters(TextField name, TextField price, TextField url, TextArea description, ImageView imageView) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.description = description;
        this.imageView = imageView;
    }
    @FXML
    protected void onAddImageClick() {
        if (!url.getText().isEmpty()) {
            Image image = new Image(url.getText());
            imageView.setImage(image);
        }
    }
    @FXML
    protected void onMenuItemClick(ActionEvent event) {
        quantity = (MenuItem) event.getSource();
        menuButton.setText(quantity.getText());
    }
    @FXML
    protected void onExitClick(ActionEvent event) {
        loadMainProductPageHelper(event);
    }
}
