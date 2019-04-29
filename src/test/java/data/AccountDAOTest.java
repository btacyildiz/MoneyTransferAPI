package data;

import model.Account;
import model.Currency;
import org.testng.Assert;
import org.testng.annotations.Test;

public class  AccountDAOTest{
    @Test
    public void concurrencyTest(){

        final int TRY_COUNT = 1000;
        Account a = new Account("1", Currency.GBP, 0);
        AccountDAO.getInstance().addAccount(a);


        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i= TRY_COUNT;
                while(i>0){
                    Account b = AccountDAO.getInstance().getAccount(a.getAccountID());
                    b.setBalance(b.getBalance() + 1.0);
                }

                i= TRY_COUNT;
                while(i>0){
                    Account b = AccountDAO.getInstance().getAccount(a.getAccountID());
                    b.setBalance(b.getBalance() - 1.0);
                }
            }
        });
        Thread writeThread = new Thread(new Runnable() {
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
        });

        synchronized(this){
            readThread.start();
            writeThread.start();
        }

        Assert.assertEquals(0.0, AccountDAO.getInstance().getAccount(a.getAccountID()).getBalance());
    }
}
