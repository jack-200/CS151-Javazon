package edu.sjsu.cs.cs151javazon;

import java.io.*;
import java.util.ArrayList;

public class AccountManager implements Serializable {
    public static final String textFile = "src/main/resources/edu/sjsu/cs/cs151javazon/Accounts.txt";
    public static int numUsers;
    private static AccountManager instance;
    private ArrayList<Account> accounts = new ArrayList<>();
    private AccountManager() {
    }
    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }
    public static void saveAccounts(ArrayList<Account> accounts) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(textFile);
        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
        out.writeObject(accounts);
    }
    // load new account to ArrayList
    public void loadAccount(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    //load all accounts
    public void loadAccounts() {
        accounts = deserializeArrList(textFile);
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
    public ArrayList<Account> addAccount(Account a) {
        accounts.add(a);
        return accounts;
    }
    public Account searchAccount(String username) {
        for (Account account : accounts) {
            if (account.getUserName().equals(username)) {
                return account;
            }
        }
        return null;
    }
}
