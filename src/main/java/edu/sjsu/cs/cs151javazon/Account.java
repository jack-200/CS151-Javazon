package edu.sjsu.cs.cs151javazon;

import java.io.Serializable;

public class Account implements Serializable {
    private static Account instance;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String address;
    private String paymentMethod;
    private userRoles role;

    public Account() {
    }

    public Account(String firstName, String lastName, String email, String userName, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setUserName(userName);
        setPassword(password);
    }

    public static Account getInstance() {
        if (instance == null) {
            return null;
        }
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public userRoles getRole() {
        return role;
    }

    public void setRole(userRoles role) {
        this.role = role;
    }

    public enum userRoles {BUYER, SELLER}
}
