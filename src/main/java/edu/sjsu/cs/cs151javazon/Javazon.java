package edu.sjsu.cs.cs151javazon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Javazon extends Application {
    private static final ShoppingCart SC = ShoppingCart.getInstance();
    private static final ProductManager PM = ProductManager.getInstance();
    private static final AccountManager AM = AccountManager.getInstance();
    private static Stage Javazon;
    public static void main(String[] args) {
        AM.loadAccounts();
        PM.loadProducts();
        launch();
    }
    public static Stage getStage() {
        return Javazon;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Javazon = stage;
        //        FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource("hello-view.fxml"));
        //        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
// Test sellProduct.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource("sellProduct.fxml"));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefViewportHeight(450);
        VBox vBox = new VBox();
        vBox.getChildren().addAll((Pane)fxmlLoader.load());
        scrollPane.setContent(vBox);
        Scene scene = new Scene(scrollPane);

        stage.setTitle("Javazon");
        stage.setScene(scene);
        stage.show();
    }
}