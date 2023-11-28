package edu.sjsu.cs.cs151javazon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static edu.sjsu.cs.cs151javazon.Javazon.height;
import static edu.sjsu.cs.cs151javazon.Javazon.width;

public class HelloController {
    protected static void loadMainProductPageHelper(ActionEvent event) {
        HelloController HC = new HelloController();
        HC.loadMainProductPage(event);
    }
    @FXML
    protected void loadMainProductPage(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainProductPageController mainProductPageController = new MainProductPageController();
        Parent root = mainProductPageController.getRoot(currentStage);
        Scene currentScene = currentStage.getScene();
        double adjustedWidth = currentStage.getWidth() - (currentStage.getWidth() - currentScene.getWidth());
        double adjustedHeight = currentStage.getHeight() - (currentStage.getHeight() - currentScene.getHeight());
        if ((adjustedWidth < width) || (adjustedHeight < height)) {
            currentStage.setScene(new Scene(root, width, height));
        } else {
            currentStage.setScene(new Scene(root, adjustedWidth, adjustedHeight));
        }
    }
}