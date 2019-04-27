package data;

import model.Account;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountDAO {
    // store account id to Account object.
    private static final Map ACCOUNT_STORE = new ConcurrentHashMap();
    private static AccountDAO instance;
    public static AccountDAO getInstance(){
        if(instance == null){
            instance = new AccountDAO();
        }
        return instance;
    }
    private AccountDAO(){//for signleton
    }

    public boolean addAccount(Account account){
        if(ACCOUNT_STORE.containsKey(account.getAccountID())){
            return false;
        }
        ACCOUNT_STORE.put(account.getAccountID(),account);
        return true;
    }
}
