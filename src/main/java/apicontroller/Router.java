package apicontroller;

import io.javalin.Javalin;

public class Router {
    private Javalin router;
    public Router(Javalin router){
        this.router = router;
        this.setRoutes();
    }
    private void setRoutes(){
        this.router.put("/account", AccountController.createAccount);
    }
}
