package edu.sjsu.cs.cs151javazon;

import edu.sjsu.cs.cs151javazon.exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

import static edu.sjsu.cs.cs151javazon.AccountManager.textFile;
import static edu.sjsu.cs.cs151javazon.HelloController.loadMainProductPageHelper;
import static edu.sjsu.cs.cs151javazon.Javazon.showFadingPopup;

public class AccountController {
    static Account current = null;
    ArrayList<Account> accounts = AccountManager.getInstance().deserializeArrList(textFile);
    @FXML
    private Label FirstNameLabel, LastNameLabel, EmailLabel, UsernameLabel, PasswordLabel, AddressLabel;
    @FXML
    private TextField firstname, lastname, email, username, address, paymentMethod;
    @FXML
    private Text passwordReq, uppercaseReq, lowercaseReq, specialCharReq, numberReq, lengthReq;
    @FXML
    private Hyperlink DontHaveAccount, HaveAccount;
    @FXML
    private Button buyer, seller, goBack;
    @FXML
    private PasswordField passwordField;
    public AccountController() throws IOException, ClassNotFoundException { }
    @FXML
    protected void onBuyerClick() {
    }
    @FXML
    protected void onSellerClick() {
    }
    @FXML
    protected void onSignUpClick(ActionEvent event) throws PasswordException {
        if (!firstname.getText().isEmpty() && !lastname.getText().isEmpty() && !email.getText().isEmpty() &&
            !username.getText().isEmpty()) {
            if (Account.getInstance() == null) {
                try {
                    checkPassword(passwordField.getText());
                    if (AccountManager.getInstance().searchAccount(username.getText()) == null) {
                        Account account = Account.getInstance(firstname.getText(), lastname.getText(), email.getText(),
                                username.getText(), passwordField.getText());
                        current = account;
                        accounts.add(account);
                        AccountManager.getInstance().loadAccount(accounts);
                        AccountManager.saveAccounts(accounts);
                        Javazon.switchScene("SignUpFinish.fxml");
                    } else {
                        showFadingPopup(event, "Failed to create account");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UpperCaseCharacterMissing e) {
                    System.out.println(e.getMessage());
                } catch (LowerCaseCharacterMissing e) {
                    System.out.println(e.getMessage());
                } catch (SpecialCharacterMissing e) {
                    System.out.println(e.getMessage());
                } catch (NumberCharacterMissing e) {
                    System.out.println(e.getMessage());
                } catch (Minimum8CharactersRequired e) {
                    System.out.println(e.getMessage());
                }
            } else {
                showFadingPopup(event, "Account Exists");
            }
        } else {
            showFadingPopup(event, "Fill out all text fields");
        }
    }
    public void checkPassword(String password) throws PasswordException {
        boolean uppercase = false;
        boolean lowercase = false;
        boolean specialChar = false;
        boolean numberChar = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                uppercase = true;
            }
            if (Character.isLowerCase(password.charAt(i))) {
                lowercase = true;
            }
            if (!Character.isAlphabetic(password.charAt(i)) && !Character.isDigit(password.charAt(i))) {
                specialChar = true;
            }
            if (Character.isDigit(password.charAt(i))) {
                numberChar = true;
            }
        }
        if (!uppercase) {
            uppercaseReq.setFill(Color.RED);
            throw new UpperCaseCharacterMissing("UpperCaseCharacterMissing");
        }
        if (uppercase) {
            uppercaseReq.setFill(Color.GREEN);
        }
        if (!lowercase) {
            lowercaseReq.setFill(Color.RED);
            throw new LowerCaseCharacterMissing("LowerCaseCharacterMissing");
        }
        if (lowercase) {
            lowercaseReq.setFill(Color.GREEN);
        }
        if (!specialChar) {
            specialCharReq.setFill(Color.RED);
            throw new SpecialCharacterMissing("SpecialCharacterMissing");
        }
        if (specialChar) {
            specialCharReq.setFill(Color.GREEN);
        }
        if (!numberChar) {
            numberReq.setFill(Color.RED);
            throw new NumberCharacterMissing("NumberCharacterMissing");
        }
        if (numberChar) {
            numberReq.setFill(Color.GREEN);
        }
        if (password.length() < 8) {
            lengthReq.setFill(Color.RED);
            throw new Minimum8CharactersRequired("Minimum8CharactersRequired");
        }
        if (password.length() >= 8) {
            lengthReq.setFill(Color.GREEN);
        }
    }
    @FXML
    protected void onSignUpSaveClick(ActionEvent event) throws IOException {
        if (address.getText().isEmpty()) {
            showFadingPopup(event, "Fill out address");
        } else {
            current.setAddress(address.getText());
            if (buyer.isPressed()) {
                current.setRole(Account.userRoles.BUYER);
            } else {
                current.setRole(Account.userRoles.SELLER);
            }
            current.setStatus(Account.Status.SIGNED_IN);
            AccountManager.saveAccounts(accounts);
            showFadingPopup(event, "Account Created");
            loadMainProductPageHelper(event);
        }
    }
    @FXML
    protected void onSignInClick(ActionEvent event) {
        if (!username.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            Account account = AccountManager.getInstance().searchAccount(username.getText());
            if (account == null) {
                showFadingPopup(event, "Account does not exist");
            } else if (passwordField.getText().equals(account.getPassword())) {
                showFadingPopup(event, "Signed In");
                account.setStatus(Account.Status.SIGNED_IN);
                current = account;
                loadMainProductPageHelper(event);
            } else {
                showFadingPopup(event, "Username and password do not match");
            }
        } else {
            showFadingPopup(event, "Fill out all text fields");
        }
    }
    @FXML
    protected void onDontHaveAccountClick() throws IOException {
        Javazon.switchScene("SignUp.fxml");
    }
    @FXML
    protected void onHaveAccountClick() throws IOException {
        Javazon.switchScene("SignIn.fxml");
    }
    @FXML
    protected void onGoBackClick(ActionEvent event) {
        loadMainProductPageHelper(event);
    }
}


