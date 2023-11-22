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
    public TextField name, price, link;
    @FXML
    private TextArea description;
    @FXML
    private Button AddItem;
    @FXML
    private Hyperlink uploadPhoto;
    @FXML
    private ImageView imageView;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem quantity;

    Button button = new Button("Add");
    @FXML
    public void addProduct() throws IOException{
        setters(name, price, link, description, imageView);


        if (!name.getText().isEmpty() && !price.getText().isEmpty() && !link.getText().isEmpty() && !description.getText().isEmpty()){
            if (Product.getInstance() == null) {
                Product product = new Product(name.getText(),Integer.parseInt(quantity.getText()),Double.parseDouble(price.getText()),description.getText(),link.getText());

                current = product;
                products.add(product);
                ProductManager.getInstance().loadProduct(products);
                FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                out.writeObject(products);
                System.out.println("product added");
                System.out.println("Total number of products:" + products.size());

                //resetTextFields();
            }
        }
        else{
            System.out.println("Fill out all text fields");
        }
            FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource("sellProduct.fxml"));
            SellProductController controller = fxmlLoader.getController();

            ScrollPane sc = (ScrollPane) Javazon.getStage().getScene().getRoot();
            VBox vbox = (VBox) sc.getContent();
            vbox.getChildren().addAll((Pane)fxmlLoader.load());


    }
    //   https://m.media-amazon.com/images/I/61qAtEWZTNL._AC_SX522_.jpg
    @FXML
    protected void onAddImageClick(){
        if(!link.getText().isEmpty()){
            Image image = new Image(link.getText());
            imageView.setImage(image);
        }
    }
    public void setters(TextField name, TextField price, TextField link, TextArea description, ImageView imageView){
        this.name = name;
        this.price = price;
        this.link = link;
        this.description = description;
        this.imageView = imageView;

    }
    public void resetTextFields(){
        this.name.clear();
        this.price.clear();
        this.link.clear();
        this.description.clear();
        resetImageView();

    }
    public void resetImageView(){
        Image image = new Image("https://imgs.search.brave.com/JHZ_N_4PEASRhUZmrd48lE5lPuEt5ygYRAJdwIkJOJ4/rs:fit:860:0:0/g:ce/aHR0cHM6Ly9zdGF0/aWMudGhlbm91bnBy/b2plY3QuY29tL3Bu/Zy81Njk4ODA1LTIw/MC5wbmc");
        imageView.setImage(image);

    }
    @FXML
    protected void onMenuItemClick(ActionEvent event){
        quantity = (MenuItem) event.getSource();
        menuButton.setText(quantity.getText());
    }

}
