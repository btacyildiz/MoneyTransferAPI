package data;

import model.Account;
import model.Currency;
import model.InvalidTypeException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class  AccountDAOTest{
    @Test
    public void concurrencyTest(){

        final int TRY_COUNT = 50;
        final int THREAD_COUNT = 50;
        Account a;
             a = new Account("1", Currency.GBP, 0);

        AccountDAO.getInstance().addAccount(a);

        ArrayList<Thread> threads = new ArrayList<>();
        for(int i=0; i<TRY_COUNT; i++){
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    int i= TRY_COUNT;
                    while(--i>0){
                        Account b = AccountDAO.getInstance().getAccount(a.getAccountID());
                        b.setBalance(b.getBalance() + 1.0);
                    }

                    i= TRY_COUNT;
                    while(--i>0){
                        Account b = AccountDAO.getInstance().getAccount(a.getAccountID());
                        b.setBalance(b.getBalance() - 1.0);
                    }
                }
            }));
        }


        synchronized(this){
            for(int i=0; i<THREAD_COUNT; i++)
                threads.get(i).start();
        }

        Assert.assertEquals(0.0, AccountDAO.getInstance().getAccount(a.getAccountID()).getBalance());

    }
}
