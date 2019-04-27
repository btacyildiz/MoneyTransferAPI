package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import utility.JsonUtil;

public class Account extends JsonUtil {
    private String accountID;
    private double balance;

    private Currency currency;

    public Account(){}
    public Account(String accountID, Currency type, double balance){
        this.accountID = accountID;
        this.balance = balance;
        this.currency = type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountID() {
        return accountID;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String toJSON(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        }catch (Exception e){
            return "";
        }
    }

}
