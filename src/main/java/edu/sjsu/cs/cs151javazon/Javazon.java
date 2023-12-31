package edu.sjsu.cs.cs151javazon;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class Javazon extends Application {
    public static final int width = 700;
    public static final int height = 600;
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
        Scene currentScene = Javazon.getScene();
        Scene newScene = new Scene(fxmlLoader.load(), currentScene.getWidth(), currentScene.getHeight());
        Javazon.setScene(newScene);
    }
    public static void showFadingPopup(ActionEvent actionEvent, String message) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Stage popupStage = new Stage();
        popupStage.initOwner(currentStage);
        popupStage.initStyle(StageStyle.TRANSPARENT);
        popupStage.setAlwaysOnTop(true);
        StackPane popupRoot = new StackPane();
        popupRoot.setStyle("-fx-background-color: transparent; -fx-padding: 5px;");
        Label label = new Label(message);
        label.setStyle("-fx-background-color: transparent; -fx-font-weight: bold;");
        label.setFont(new Font(20));
        Rectangle background = new Rectangle(0, 0);
        background.widthProperty().bind(Bindings.add(label.widthProperty(), 10));
        background.heightProperty().bind(Bindings.add(label.heightProperty(), 10));
        background.setArcWidth(20);
        background.setArcHeight(20);
        background.setFill(Color.web("#FF9900"));
        popupRoot.getChildren().addAll(background, label);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), popupRoot);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> popupStage.close());
        popupStage.setScene(new Scene(popupRoot));
        popupStage.setOnShown(event -> fadeTransition.play());
        popupStage.setX(currentStage.getX() + (currentStage.getWidth() / 2) - (message.length() * 3));
        popupStage.setY(currentStage.getY() + currentStage.getHeight() * 0.8);
        popupStage.show();
    }
    @Override
    public void start(Stage stage) throws IOException {
        Javazon = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Javazon");
        stage.setScene(scene);
        stage.show();
    }
}