package edu.sjsu.cs.cs151javazon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static edu.sjsu.cs.cs151javazon.ProductManager.textFile;

public class SellProductController
{
    static Product current = null;
    static  FXMLLoader currentScene = null;
    ArrayList<Product> products = ProductManager.getInstance().deserializeArrList(textFile);

    @FXML
    public TextField name, price, url;
    @FXML
    private TextArea description;
    @FXML
    private Button AddItem;
    @FXML
    private ImageView imageView;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem quantity;

    Button button = new Button("Add");
    @FXML
    public void addProduct() throws IOException{
        setters(name, price, url, description, imageView);

        if (!name.getText().isEmpty() && quantity != null && !price.getText().isEmpty() && !url.getText().isEmpty() && !description.getText().isEmpty()){
            boolean validPrice = true;
            try{
                Double.parseDouble(price.getText());
            }catch(NumberFormatException e) {
                validPrice = false;
            }

            if (Product.getInstance() == null && validPrice) {
                Product product = new Product(name.getText(),Integer.parseInt(quantity.getText()),Double.parseDouble(price.getText()),description.getText(),url.getText());
                System.out.println(url.getText());
                current = product;
                products.add(product);
                ProductManager.getInstance().loadProduct(products);
                FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                out.writeObject(products);
                System.out.println("product added");
                System.out.println("Total number of products:" + products.size());

                // update vbox that is inside scroll pane
                FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource("sellProduct.fxml"));
                ScrollPane sc = (ScrollPane) Javazon.getStage().getScene().getRoot();
                VBox vbox = (VBox) sc.getContent();
                vbox.getChildren().addAll((Pane)fxmlLoader.load());
            }
            else{
                System.out.println("Enter valid price");
            }
        }
        else{
            System.out.println("Fill out all text fields");
        }
    }

    @FXML
    protected void onAddImageClick(){
        if(!url.getText().isEmpty()){
            Image image = new Image(url.getText());
            imageView.setImage(image);
        }
    }
    public void setters(TextField name, TextField price, TextField url, TextArea description, ImageView imageView){
        this.name = name;
        this.price = price;
        this.url = url;
        this.description = description;
        this.imageView = imageView;

    }

    @FXML
    protected void onMenuItemClick(ActionEvent event){
        quantity = (MenuItem) event.getSource();
        menuButton.setText(quantity.getText());
    }
    @FXML
    protected void onExitClick() throws IOException {
        // show main product page with new products if added
        FXMLLoader fxmlLoader1 = new FXMLLoader(Javazon.class.getResource("hello-view.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load());
        Javazon.getStage().setScene(scene1);
        Javazon.getStage().show();

    }

}
