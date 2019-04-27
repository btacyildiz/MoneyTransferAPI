package model;

public class Account {
    private String accountID;
    private double balance;

    private AccountType accountType;

    public Account(){}
    public Account(String accountID, AccountType type, double balance){
        this.accountID = accountID;
        this.balance = balance;
        this.accountType = type;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountID() {
        return accountID;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
