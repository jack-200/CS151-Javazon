package edu.sjsu.cs.cs151javazon;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static edu.sjsu.cs.cs151javazon.Account.userRoles.BUYER;
import static edu.sjsu.cs.cs151javazon.Account.userRoles.SELLER;
import static edu.sjsu.cs.cs151javazon.HelloController.loadMainProductPageHelper;
import static edu.sjsu.cs.cs151javazon.Javazon.showFadingPopup;
import static edu.sjsu.cs.cs151javazon.ProductManager.textFile;

public class MainProductPageController {
    private static final String notSignedIn = "Sign In First";
    private static final double COLUMN_WIDTH = 200;
    private static final double GAP = 10;
    private static final String img_path = "file:src/main/resources/images/";
    static boolean changeOrder = false;
    private static MainProductPageController instance;
    private final GridPane gridPane = new GridPane(GAP, GAP);
    ArrayList<Product> order = null;
    @FXML
    private Button sign_in_button;
    public static MainProductPageController getInstance() {
        if (instance == null) {
            instance = new MainProductPageController();
        }
        return instance;
    }
    public Parent getRoot(Stage primaryStage) {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(generateHeader(), gridPane);
        vbox.setSpacing(10);
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        ChangeListener<Number> stageSizeListener =
                (observable, oldValue, newValue) -> generateProductGrid(primaryStage.getWidth());
        primaryStage.widthProperty().addListener(stageSizeListener);
        generateProductGrid(primaryStage.getWidth());
        return scrollPane;
    }
    private VBox generateHeader() {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(generateTopHeader(), generateBottomHeader());
        return vbox;
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
        if (!changeOrder) {
            order = ProductManager.getInstance().deserializeArrList(textFile);
        } else {
            order = ProductManager.getInstance().getProducts();
        }
        int row = 1, col = 0;
        for (Product product : order) {
            if (product != null) {
                Image image = new Image(product.getUrl());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(COLUMN_WIDTH);
                imageView.setFitHeight(COLUMN_WIDTH);
                gridPane.add(imageView, col, row);
                Hyperlink productLink = new Hyperlink(product.getName());
                Label productPrice = new Label("$" + product.getPrice());
                productPrice.setFont(new Font(20));
                VBox productText = new VBox();
                productText.getChildren().addAll(productLink, productPrice);
                gridPane.add(productText, col, row + 1);
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
    private StackPane generateTopHeader() {
        int searchFeatHeight = 30;
        ImageView logoImage = new ImageView(new Image(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Amazon_logo.svg/100px-Amazon_logo.svg.png"));
        TextField searchBar = new TextField();
        searchBar.setPrefHeight(searchFeatHeight);
        //Account hyperlink
        Button account_button = createButton("Account", () -> {
        });
        Button sell_product_button = new Button("Sell Product");
        sell_product_button.setOnAction(event -> {
            try {
                if (isUserSignedIn()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource("sellProduct.fxml"));
                    ScrollPane scrollPane = new ScrollPane();
                    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                    scrollPane.setPrefViewportHeight(450);
                    VBox vBox = new VBox();
                    vBox.getChildren().addAll((Pane) fxmlLoader.load());
                    scrollPane.setContent(vBox);
                    Scene scene = new Scene(scrollPane);
                    Javazon.getStage().setScene(scene);
                    Javazon.getStage().show();
                } else {
                    showFadingPopup(event, notSignedIn);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        sign_in_button = createButton("Sign In", () -> {
            try {
                if (AccountController.current == null) {
                    Javazon.switchScene("SignIn.fxml");
                } else if (AccountController.current.getStatus() != Account.Status.SIGNED_IN) {
                    Javazon.switchScene("SignIn.fxml");
                } else {
                    System.out.println("Already signed in");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Button cart_button = createButton("Cart", () -> {
            try {
                Javazon.switchScene("Checkout.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        if (AccountController.current != null && AccountController.current.getStatus() == Account.Status.SIGNED_IN) {
            sign_in_button.setText("Hello, " + AccountController.current.getFirstName() + "\nAccount");
        }
        Button myMarket_button = new Button("My Market");
        myMarket_button.setOnAction(event -> {
            if (isUserSignedIn()) {
                try {
                    AccountController.current.loadProducts();
                } catch (FileNotFoundException e) {
                    showFadingPopup(event, "My Market is empty.");
                }
                ArrayList<Product> marketList = null;
                // if user added products in the past, deserialize and set myMarket
                if (!AccountController.current.getMyMarket().isEmpty()) {
                    try {
                        marketList = AccountController.current.deserializeArrList(
                                AccountController.current.getMyMarketFile());
                    } catch (FileNotFoundException ignored) {
                    }
                    ScrollPane myMarket = new ScrollPane();
                    myMarket.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                    myMarket.setPrefViewportHeight(450);
                    VBox vBox = new VBox();
                    Button goBack_button = new Button("");
                    goBack_button.setOnAction(event2 -> {
                        loadMainProductPageHelper(event2);
                    });
                    ImageView goBackImage =
                            new ImageView(new Image("https://cdn-icons-png.flaticon.com/256/93/93634.png"));
                    goBackImage.setFitHeight(28);
                    goBackImage.setFitWidth(28);
                    goBack_button.setGraphic(goBackImage);
                    vBox.getChildren().add(goBack_button);
                    for (Product product : marketList) {
                        try {
                            Scene scene = AccountController.current.addToMarket(product);
                            Node node = scene.getRoot();
                            vBox.getChildren().add(node);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    myMarket.setContent(vBox);
                    Scene scene = new Scene(myMarket, 600, 600);
                    Javazon.getStage().setScene(scene);
                    Javazon.getStage().show();
                }
            } else {
                System.out.println("Not signed in. Cannot open myMarket");
            }
        });
        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10));
        Button buyer = new Button("Buyer");
        Button seller = new Button("Seller");
        if (isUserSignedIn()) {
            sign_in_button.setText("Hello, " + AccountController.current.getFirstName() + "\nAccount");
        }
        buyer.setOnAction(event -> {
            if (isUserSignedIn()) {
                hbox.getChildren().remove(myMarket_button);
                seller.setStyle("-fx-background-color: #FFFFFF");
                System.out.println("Buyer selected");
                buyer.setStyle("-fx-background-color: #FFFF00");
                AccountController.current.setRole(BUYER);
                hbox.getChildren().add(cart_button);
            } else {
                showFadingPopup(event, notSignedIn);
            }
        });
        seller.setOnAction(event -> {
            if (isUserSignedIn()) {
                hbox.getChildren().remove(cart_button);
                buyer.setStyle("-fx-background-color: #FFFFFF");
                System.out.println("Seller selected");
                seller.setStyle("-fx-background-color: #FFFF00");
                AccountController.current.setRole(SELLER);
                hbox.getChildren().add(myMarket_button);
            } else {
                showFadingPopup(event, notSignedIn);
            }
        });
        hbox.getChildren().addAll(logoImage, generateSearchBar(), sell_product_button, sign_in_button, buyer, seller);
        StackPane stackPane = new StackPane(hbox);
        stackPane.setStyle("-fx-background-color: #ADD8E6");
        return stackPane;
    }
    private StackPane generateBottomHeader() {
        ComboBox<String> comboBox = new ComboBox<>();
        ObservableList<String> items =
                FXCollections.observableArrayList("Featured", "Price: Low to High", "Price: High to Low",
                        "Avg. Customer Review");
        comboBox.setItems(items);
        comboBox.setValue("Sort By: Featured");
        comboBox.setOnAction(event -> {
            if (comboBox.getValue().equals("Price: Low to High")) {
                try {
                    sortLowToHigh(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (comboBox.getValue().equals("Price: High to Low")) {
                try {
                    sortHighToLow(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (comboBox.getValue().equals("Avg. Customer Review")) {
                System.out.println("sort by review");
            } else if (comboBox.getValue().equals("Featured")) {
                changeOrder = false;
                loadMainProductPageHelper(event);
            }
        });
        HBox hbox = new HBox(comboBox);
        StackPane stackPane = new StackPane(hbox);
        BackgroundFill backgroundFill =
                new BackgroundFill(Color.web("#BCEFFF"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        Background background = new Background(backgroundFill);
        stackPane.setBackground(background);
        return stackPane;
    }
    private Button createButton(String label, Runnable action) {
        Button button = new Button(label);
        button.setOnAction(e -> action.run());
        return button;
    }
    private boolean isUserSignedIn() {
        return (AccountController.current != null && AccountController.current.getStatus() == Account.Status.SIGNED_IN);
    }
    private HBox generateSearchBar() {
        int searchBarHeight = 30;
        TextField searchField = generateSearchField(searchBarHeight);
        Button searchIcon = generateSearchIcon(searchBarHeight, searchField);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(searchField, searchIcon);
        return hbox;
    }
    private void sortLowToHigh(ActionEvent event) throws IOException {
        sortBy(Comparator.comparingDouble(Product::getPrice));
        changeOrder = true;
        loadMainProductPageHelper(event);
    }
    private void sortHighToLow(ActionEvent event) throws IOException {
        sortBy(Comparator.comparingDouble(Product::getPrice));
        Collections.reverse(ProductManager.getInstance().getProducts());
        changeOrder = true;
        loadMainProductPageHelper(event);
    }
    private TextField generateSearchField(int searchBarHeight) {
        TextField searchField = new TextField();
        searchField.setPrefHeight(searchBarHeight);
        return searchField;
    }
    private static Button generateSearchIcon(int searchBarHeight, TextField searchField) {
        Button searchIcon = new Button();
        searchIcon.setPadding(new Insets(3));
        ImageView search_img = new ImageView(new Image((img_path + "200px-Magnifying_glass_icon.png")));
        search_img.setPreserveRatio(true);
        search_img.setFitHeight(searchBarHeight - searchIcon.getPadding().getTop() * 2);
        searchIcon.setGraphic(search_img);
        searchIcon.setOnAction(event -> {
            String enteredText = searchField.getText();
            System.out.println("Entered Text: " + enteredText);
            searchForText(enteredText);
            changeOrder = true;
            loadMainProductPageHelper(event);
        });
        return searchIcon;
    }
    private void sortBy(Comparator<Product> sortBy) {
        ProductManager.getInstance().loadProducts();
        ProductManager.getInstance().getProducts().sort(sortBy);
    }
    private static void searchForText(String enteredText) {
        ProductManager.getInstance().loadProducts();
        ArrayList<Product> searchResult = ProductManager.getInstance().searchProduct(enteredText);
        if (!searchResult.isEmpty()) {
            for (Product product : searchResult) {
                System.out.println("Found: " + product.getName());
            }
            ProductManager.getInstance().setProducts(searchResult);
        } else {
            System.out.println("Did not find " + enteredText);
        }
    }
    public Button getSignInButton() { return sign_in_button; }
    public void setSignInButton(Button sign_in_button) { this.sign_in_button = sign_in_button; }
}