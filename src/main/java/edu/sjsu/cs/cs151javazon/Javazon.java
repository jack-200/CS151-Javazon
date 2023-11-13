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

    private static Stage javazon;
    public static void main(String[] args) {
        AM.loadAccounts();
        PM.loadProducts();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        javazon = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setScene(scene);

        fxmlLoader = new FXMLLoader(Javazon.class.getResource("signIn.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load());
        stage.setTitle("Javazon");  
        stage.setScene(scene1);
        stage.show();

    }
    public static Stage getStage(){
        return javazon;
    }

}