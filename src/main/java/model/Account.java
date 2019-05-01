package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import utility.JsonUtil;

public class Account extends JsonUtil {
    private String accountID;
    private double balance;

    private Currency currency;

    @JsonCreator
    public Account(@JsonProperty(value = "accountID", required = true)String accountID,
                   @JsonProperty(value = "currency", required = true)Currency type,
                   @JsonProperty(value = "balance", required = true)double balance){
        this.accountID = accountID;
        this.balance = balance;
        this.currency = type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public synchronized void withdraw(double amount){
        this.balance -= amount;
    }

    public synchronized void deposit(double amount){
        this.balance += amount;
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
