package edu.sjsu.cs.cs151javazon;

public class Account {
    private String firstName;
    private String lastName;
    private String address;
    private String paymentMethod;
    private userRoles role;
    public Account(String firstName, String lastName, String address, String paymentMethod) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPaymentMethod(paymentMethod);
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
    public userRoles getRole() { return role; }
    public void setRole(userRoles role) { this.role = role; }
    public enum userRoles {BUYER, SELLER}
}
