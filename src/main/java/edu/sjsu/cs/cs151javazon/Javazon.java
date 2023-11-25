package edu.sjsu.cs.cs151javazon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    public static void switchScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        Javazon.setScene(scene);
        //Javazon.show();
    }
    @Override
    public void start(Stage stage) throws IOException {
        Javazon = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Javazon");
        stage.setScene(scene);
        stage.show();
    }
}