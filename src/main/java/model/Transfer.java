package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import utility.JsonUtil;

public class Transfer extends JsonUtil {

    private String sourceAccountID;
    private String destinationAccountID;
    private Double amount;
    private Currency currency;

    public Transfer() {
    }
    @JsonCreator
    public Transfer(@JsonProperty(value = "sourceAccountID", required = true)String sourceAccountID,
                    @JsonProperty(value = "destinationAccountID", required = true)String destinationAccountID,
                    @JsonProperty(value = "amount", required = true)Double amount,
                    @JsonProperty(value = "currency", required = true)Currency currency) {
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
