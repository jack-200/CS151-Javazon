package edu.sjsu.cs.cs151javazon;

import java.io.*;
import java.util.ArrayList;

public class AccountManager implements Serializable {
    private static AccountManager instance;
    public static int numUsers;
    private String textFile = "Accounts.txt";
    private ArrayList<Account> accounts = new ArrayList<>();
    private AccountManager() { }
    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }
    // load new account to ArrayList
    public void loadAccount(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    //load all accounts
    public void loadAccounts() {
        accounts = deserializeArrList("Accounts.txt");
    }
    public ArrayList<Account> addAccount(Account a) {
        accounts.add(a);
        return accounts;
    }
    public Account searchAccount(String username) {
        for(Account account : accounts){
            if(account.getUserName().equals(username)){
                return account;
            }
        }
        return null;
    }
    public ArrayList<Account> deserializeArrList(String file) {
        ArrayList<Account> accounts;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            accounts = (ArrayList<Account>) in.readObject();
            AccountManager.numUsers = accounts.size();


            return accounts;
        } catch (EOFException | ClassNotFoundException e) {
            return new ArrayList<>();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
