package edu.sjsu.cs.cs151javazon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    protected void onHelloButtonClick(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainProductPageController mainProductPageController = new MainProductPageController();
        Parent root = mainProductPageController.getRoot(currentStage);
        Scene currentScene = currentStage.getScene();
        double adjustedWidth = currentStage.getWidth() - (currentStage.getWidth() - currentScene.getWidth());
        double adjustedHeight = currentStage.getHeight() - (currentStage.getHeight() - currentScene.getHeight());
        currentStage.setScene(new Scene(root, adjustedWidth, adjustedHeight));
    }
}