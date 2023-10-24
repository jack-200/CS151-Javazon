package edu.sjsu.cs.cs151javazon;

import java.util.ArrayList;

public class AccountManager {
    private static AccountManager instance;
    private final String textFile = "Accounts.txt";
    private final ArrayList<Account> accounts = new ArrayList<>();
    private AccountManager() { }
    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }
    public void loadAccounts() {
    }
    public void addAccount(Account a) {
        accounts.add(a);
    }
    public Account searchAccount() {
        return accounts.get(0);
    }
}
