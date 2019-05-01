package apicontroller;
import data.AccountDAO;
import io.javalin.Handler;
import model.Account;
import model.Currency;
import model.Transfer;
import utility.AmountUtil;

public class TransferController {
    public static Handler createTransfer = ctx->{
        Transfer newTransfer;
        try {
            newTransfer = ctx.bodyAsClass(Transfer.class);
        }catch (Exception e){
            System.out.println("Create Transfer Error --> invalid payload " + ctx.body());
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            ctx.result(ApiResult.INVALID_REQUEST.toJSON());
            return;
        }

        // TODO amount value floating digits should not be more than 2


        final double TRANSFER_AMOUNT = newTransfer.getAmount();
        // check if transfer amount is greater than 0
        if(TRANSFER_AMOUNT <= 0 || AmountUtil.getFloatDigitCount(TRANSFER_AMOUNT) > 2){
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            ctx.result(ApiResult.INVALID_AMOUNT.toJSON());
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

        Currency transferCurrency = newTransfer.getCurrency();
        // check if currency is valid
        if(transferCurrency == null){
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            ctx.result(ApiResult.INVALID_CURRENCY.toJSON());
            return;
        }

        // check if currency is equal for both accounts
        if(sourceAccount.getCurrency().getType() != transferCurrency.getType() ||
            destinationAccount.getCurrency().getType() != transferCurrency.getType()){
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            ctx.result(ApiResult.CURRENCY_DOES_NOT_MATCH.toJSON());
            return;
        }

        // check if source account has enough balance
        if(sourceAccount.getBalance() < TRANSFER_AMOUNT){
            ctx.status(HTTPCodes.FORBIDDEN.getCode());
            ctx.result(ApiResult.INSUFFICIENT_FUNDS.toJSON());
            return;
        }

        transferBalance(sourceAccount,destinationAccount,TRANSFER_AMOUNT);

        ctx.status(HTTPCodes.SUCCESS.getCode());
        ctx.result(ApiResult.SUCCESS.toJSON());
    };

    public static synchronized void transferBalance(Account source, Account destination, double amount){
        source.withdraw(amount);
        destination.deposit(amount);
    }
}
