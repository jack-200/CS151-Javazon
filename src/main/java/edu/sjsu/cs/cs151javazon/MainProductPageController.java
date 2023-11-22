package edu.sjsu.cs.cs151javazon;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class MainProductPageController {
    private static final double COLUMN_WIDTH = 200;
    private static final double GAP = 10;
    private final GridPane gridPane = new GridPane(GAP, GAP);
    private final File folder = new File("src/main/resources/images");
    public Parent getRoot(Stage primaryStage) {
        gridPane.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            updateGrid(primaryStage.getWidth());
        };
        primaryStage.widthProperty().addListener(stageSizeListener);
        updateGrid(primaryStage.getWidth());
        return scrollPane;
    }
    private void updateGrid(double stageWidth) {
        int colCount = (int) (stageWidth / COLUMN_WIDTH);
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        Button clickableButton1 = new Button("Button 1");
        clickableButton1.setOnAction(e -> {
            System.out.println("Button 1 clicked");
        });
        Button clickableButton2 = new Button("Button 2");
        clickableButton2.setOnAction(e -> {
            System.out.println("Button 2 clicked");
        });
        gridPane.add(clickableButton1, 0, 0);
        gridPane.add(clickableButton2, 1, 0);
        for (int i = 0; i < colCount; i++) {
            ColumnConstraints column = new ColumnConstraints(COLUMN_WIDTH);
            gridPane.getColumnConstraints().add(column);
        }
        int row = 1, col = 0;
        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isFile()) {
                Image image = new Image(fileEntry.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(COLUMN_WIDTH);
                imageView.setFitHeight(COLUMN_WIDTH);
                gridPane.add(imageView, col, row);
                gridPane.add(new Label(fileEntry.getName()), col, row + 1);
                col++;
                if (col == colCount) {
                    col = 0;
                    row += 2;
                }
            }
        }
    }
}