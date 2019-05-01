package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

// TODO make currencies only string
public enum Currency {
        TRY(1001),
        GBP(1002),
        EUR(1003),
        USD(1004);

        private int type;

    @JsonCreator
    public static Currency getNameByValue(@JsonProperty(required = true)final int value) {
        for (final Currency s: Currency.values()) {
            if (s.getType()== value) {
                return s;
            }
        }
        return null;
    }

    Currency(int type){
            this.type = type;
        }

    @JsonValue
    public int getType(){
        return this.type;
    }
}
