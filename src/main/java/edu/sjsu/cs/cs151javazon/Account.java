package edu.sjsu.cs.cs151javazon;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;

public class Account implements Serializable {
    private static Account instance;
    public String myMarketFile;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String address;
    private String paymentMethod;
    private userRoles role;
    private ArrayList<Product> myMarket;
    private Status status;
    public Account() { }
    public Account(String firstName, String lastName, String email, String userName, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setUserName(userName);
        setPassword(password);
        myMarket = new ArrayList<>();
        setMyMarketFile("src/main/resources/edu/sjsu/cs/cs151javazon/MyMarket/" + firstName + "Market.txt");
    }
    public static Account getInstance() {
        if (instance == null) { return null; }
        return instance;
    }
    public static Account getInstance(String firstName, String lastName, String email, String userName,
                                      String password) {
        if (instance == null) {
            instance = new Account(firstName, lastName, email, userName, password);
        }
        return instance;
    }
    @Override
    public String toString() {
        return getFirstName() + ", " + getLastName() + ", " + getAddress() + ", " + getPaymentMethod();
    }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public userRoles getRole() { return role; }
    public void setRole(userRoles role) { this.role = role; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public ArrayList<Product> getMyMarket() { return myMarket; }
    public void addToMyMarket(Product product) {
        myMarket.add(product);
    }
    public Scene addToMarket(Product product) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Javazon.class.getResource("MyMarket.fxml"));
        Parent root = fxmlLoader.load();
        ProductPageController controller = fxmlLoader.getController();
        if (controller.getName() != null) {
            controller.getName().setText(product.getName());
            controller.getPrice().setText(Double.toString(product.getPrice()));
            controller.getDescription().setText(product.getDescription());
            Image image = new Image(product.getUrl());
            controller.getImageView().setImage(image);
        } else {
            System.out.println("couldn't add");
        }
        Scene myProduct = new Scene(root);
        return myProduct;
    }
    public void loadProduct(ArrayList<Product> myMarket) { this.myMarket = myMarket; }
    public void loadProducts() {
        myMarket = deserializeArrList(getMyMarketFile());
    }
    public ArrayList<Product> deserializeArrList(String file) {
        //ArrayList<Account> accounts;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            myMarket = (ArrayList<Product>) in.readObject();
            return myMarket;
        } catch (EOFException | ClassNotFoundException e) {
            return new ArrayList<>();
        } catch (FileNotFoundException e) {
            System.out.println("my market is empty.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public String getMyMarketFile() { return myMarketFile; }
    public void setMyMarketFile(String myMarketFile) { this.myMarketFile = myMarketFile; }
    enum Status {SIGNED_IN, SIGNED_OUT}
    public enum userRoles {BUYER, SELLER}
}