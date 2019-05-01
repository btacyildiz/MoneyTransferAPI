package apicontroller;
import data.AccountDAO;
import io.javalin.Handler;
import model.Account;
import model.Transfer;

public class TransferController {
    public static Handler createTransfer = ctx->{
        Transfer newTransfer;
        try {
            newTransfer = ctx.bodyAsClass(Transfer.class);
        }catch (Exception e){
            e.printStackTrace();
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            ctx.result(ApiResult.INVALID_REQUEST.toJSON());
            return;
        }

        // TODO amount value floating digits should not be more than 2
        // Check if transfer amount is negative
        if(newTransfer.getAmount() < 0){
            ctx.result(ApiResult.NEGATIVE_BALANCE.toJSON());
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            return;
        }

        // check if accounts are exists
        Account sourceAccount = AccountDAO.getInstance().getAccount(newTransfer.getSourceAccountID());
        Account destinationAccount = AccountDAO.getInstance().getAccount(newTransfer.getDestinationAccountID());
        if(sourceAccount == null || destinationAccount == null){
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            ctx.result(ApiResult.ACCOUNT_NOT_FOUND.toJSON());
            return;
        }

        if(sourceAccount.getAccountID().equals(destinationAccount.getAccountID())){
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            ctx.result(ApiResult.TRANSFER_EQUAL_ACCOUNTIDS.toJSON());
            return;
        }

        int transferCurrency = newTransfer.getCurrency().getType();
        // check if currency is equal for both accounts
        if(sourceAccount.getCurrency().getType() != transferCurrency ||
            destinationAccount.getCurrency().getType() != transferCurrency){
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            ctx.result(ApiResult.CURRENCY_DOES_NOT_MATCH.toJSON());
            return;
        }

        // check if transfer amount is greater than 0
        if(newTransfer.getAmount() <= 0){
            ctx.status(HTTPCodes.FORBIDDEN.getCode());
            ctx.result(ApiResult.ZERO_AMOUNT.toJSON());
            return;
        }

        // check if source account has enough balance
        if(sourceAccount.getBalance() < newTransfer.getAmount()){
            ctx.status(HTTPCodes.FORBIDDEN.getCode());
            ctx.result(ApiResult.INSUFFICIENT_FUNDS.toJSON());
            return;
        }


        // TODO check for concurrency
        // do the transfer
        double transferAmount = newTransfer.getAmount();

        // TODO try to make following two op atomic
        sourceAccount.setBalance(sourceAccount.getBalance() - transferAmount);
        destinationAccount.setBalance(destinationAccount.getBalance() + transferAmount);

        // persist to the data access object
        AccountDAO.getInstance().updateAccount(sourceAccount);
        AccountDAO.getInstance().updateAccount(destinationAccount);

        ctx.status(HTTPCodes.SUCCESS.getCode());
        ctx.result(ApiResult.SUCCESS.toJSON());
    };
}
