package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum AccountType {
        TRY(1001),
        GBP(1002),
        EUR(1003),
        USD(1004);

        private int type;

    @JsonCreator
    public static AccountType getNameByValue(final int value) {
        for (final AccountType s: AccountType.values()) {
            if (s.getType()== value) {
                return s;
            }
        }
        return null;
    }

    AccountType(int type){
            this.type = type;
        }

    @JsonValue
    public int getType(){
        return this.type;
    }
}
