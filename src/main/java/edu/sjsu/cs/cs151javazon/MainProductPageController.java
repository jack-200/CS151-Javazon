package edu.sjsu.cs.cs151javazon;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

import static edu.sjsu.cs.cs151javazon.ProductManager.textFile;

public class MainProductPageController {
    private static final double COLUMN_WIDTH = 200;
    private static final double GAP = 10;
    private static final String img_path = "file:src/main/resources/images/";
    @FXML
    public static Label static_label;
    private static MainProductPageController instance;
    private final GridPane gridPane = new GridPane(GAP, GAP);
    public static MainProductPageController getInstance() {
        if (instance == null) {
            instance = new MainProductPageController();
        }
        return instance;
    }
    public Parent getRoot(Stage primaryStage) {
        StackPane header = generateHeader();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(header, gridPane);
        vbox.setSpacing(20);
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            generateProductGrid(primaryStage.getWidth());
        };
        primaryStage.widthProperty().addListener(stageSizeListener);
        generateProductGrid(primaryStage.getWidth());
        return scrollPane;
    }
    private StackPane generateHeader() {
        int searchFeatHeight = 30;
        ImageView logoImage = new ImageView(new Image(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Amazon_logo.svg/100px-Amazon_logo.svg.png"));
        TextField searchBar = new TextField();
        searchBar.setPrefHeight(searchFeatHeight);
        Button searchIcon = generateSearchIcon(searchFeatHeight, searchBar);
        Button sell_product_button = createButton("Sell Product", () -> {
            try {
                Javazon.switchScene("sellProduct.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Button sign_in_button = createButton("Sign In", () -> {
            try {
                Javazon.switchScene("SignIn.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Button cart_button = createButton("Cart", () -> System.out.println("Cart Button clicked"));
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        hbox.getChildren().addAll(logoImage, searchBar, searchIcon, sell_product_button, sign_in_button, cart_button);
        StackPane stackPane = new StackPane(hbox);
        stackPane.setStyle("-fx-background-color: #00ffff;");
        return stackPane;
    }
    private void generateProductGrid(double stageWidth) {
        int colCount = (int) (stageWidth / COLUMN_WIDTH);
        gridPane.setPadding(new Insets(10));
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        for (int i = 0; i < colCount; i++) {
            ColumnConstraints column = new ColumnConstraints(COLUMN_WIDTH);
            gridPane.getColumnConstraints().add(column);
        }
        int row = 1, col = 0;
        for (Product product : ProductManager.getInstance().deserializeArrList(textFile)) {
            if (product != null) {
                String url = product.getUrl();
                Image image = new Image(product.getUrl());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(COLUMN_WIDTH);
                imageView.setFitHeight(COLUMN_WIDTH);
                gridPane.add(imageView, col, row);
                Hyperlink productLink = new Hyperlink(product.getName());
                gridPane.add(productLink, col, row + 1);
                productLink.setOnAction(e -> {
                    try {
                        product.createProductPage();
                        Scene productPage = product.getProductPage();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                col++;
                if (col == colCount) {
                    col = 0;
                    row += 2;
                }
            } else {
                System.out.println("null pointer");
            }
        }
    }
    private static Button generateSearchIcon(int searchFeatHeight, TextField searchBar) {
        Button searchIcon = new Button();
        searchIcon.setPadding(new Insets(3));
        ImageView search_img = new ImageView(new Image((img_path + "200px-Magnifying_glass_icon.png")));
        search_img.setPreserveRatio(true);
        search_img.setFitHeight(searchFeatHeight - searchIcon.getPadding().getTop() * 2);
        searchIcon.setGraphic(search_img);
        searchIcon.setOnAction(e -> {
            String enteredText = searchBar.getText();
            System.out.println("Entered Text: " + enteredText);
        });
        return searchIcon;
    }
    private Button createButton(String label, Runnable action) {
        Button button = new Button(label);
        button.setOnAction(e -> action.run());
        return button;
    }
}