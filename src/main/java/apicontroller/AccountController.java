package apicontroller;

import data.AccountDAO;
import io.javalin.Handler;
import model.Account;

public class AccountController {

    /**
     * Creates account if not exists
     */
    public static Handler createAccount =  ctx ->{

        // TODO check for value types
        Account newAccount;
        try {
            newAccount = ctx.bodyAsClass(Account.class);
        }catch (Exception e){
            System.out.println("Create Account invalid request data request: " + ctx.body());
            ctx.result(ApiResult.INVALID_REQUEST.toJSON());
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            return;
        }

        // check for negative balance
        if(newAccount.getBalance() < 0){
            ctx.result(ApiResult.NEGATIVE_BALANCE.toJSON());
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            return;
        }

        // check currency type
        if(newAccount.getCurrency() == null){
            ctx.result(ApiResult.INVALID_CURRENCY.toJSON());
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
            return;
        }

        boolean result = AccountDAO.getInstance().addAccount(newAccount);
        if(!result){
            ctx.result(ApiResult.ACCOUNT_ALREADY_EXIST.toJSON());
            ctx.status(HTTPCodes.BAD_REQUEST.getCode());
        }else{
            ctx.result(ApiResult.SUCCESS.toJSON());
            ctx.status(HTTPCodes.CREATED.getCode());
        }
    };

    public static Handler deleteAccount = ctx -> {
        String accountID = ctx.pathParam("id");
        boolean res = AccountDAO.getInstance().removeAccount(accountID);
        if(!res){
            ctx.status(HTTPCodes.NOT_FOUND.getCode());
            return;
        }
        ctx.status(HTTPCodes.NO_CONTENT.getCode());
    };

    public static Handler getAccount = ctx -> {
        String accountID = ctx.pathParam("id");
        Account retrievedAccount = AccountDAO.getInstance().getAccount(accountID);
        if(retrievedAccount == null){
            ctx.status(HTTPCodes.NOT_FOUND.getCode());
            return;
        }
        ctx.result(retrievedAccount.toJSON());
        ctx.status(HTTPCodes.SUCCESS.getCode());
    };
}
