package edu.sjsu.cs.cs151javazon;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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
    public Parent getRoot(Stage primaryStage) {
        File folder = new File("src/main/resources/images");
        GridPane gridPane = new GridPane(GAP, GAP);
        gridPane.setPadding(new Insets(10));
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            int colCount = (int) (primaryStage.getWidth() / COLUMN_WIDTH);
            for (int i = 0; i < colCount; i++) {
                ColumnConstraints column = new ColumnConstraints(COLUMN_WIDTH);
                gridPane.getColumnConstraints().add(column);
            }
            int row = 0, col = 0;
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
        };
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        primaryStage.widthProperty().addListener(stageSizeListener);
        return scrollPane;
    }
}