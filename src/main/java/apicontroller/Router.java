package apicontroller;

import io.javalin.Javalin;

public class Router {
    private Javalin router;
    public Router(Javalin router){
        this.router = router;
        this.setRoutes();
    }
    private void setRoutes(){
        /* Account */
        // TODO witdraw or deposit might be added
        this.router.put("/account", AccountController.createAccount);
        this.router.get("/account/:id", AccountController.getAccount);
        this.router.delete("/account/:id", AccountController.deleteAccount);
        /* Transfer */
        this.router.post("/transfer", TransferController.createTransfer);
    }
}
