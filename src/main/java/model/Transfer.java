package model;

import utility.JsonUtil;

public class Transfer extends JsonUtil {

    private String sourceAccountID;
    private String destinationAccountID;
    private Double amount;
    private Currency currency;

    public Transfer() {
    }

    public Transfer(String sourceAccountID, String destinationAccountID, Double amount, Currency currency) {
        this.sourceAccountID = sourceAccountID;
        this.destinationAccountID = destinationAccountID;
        this.amount = amount;
        this.currency = currency;
    }

    public String getSourceAccountID() {
        return sourceAccountID;
    }

    public String getDestinationAccountID() {
        return destinationAccountID;
    }

    public Double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
