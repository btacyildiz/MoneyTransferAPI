package api;

import apicontroller.Router;
import io.javalin.Javalin;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class ApiTestSuite {
    static Javalin app;
    @BeforeClass
    public static void started() {
        app = ApiTestSuite.serverStarter();
    }
    @AfterClass
    public static void completed() {
        app.stop();
    }

    public static Javalin serverStarter(){
        Javalin app = Javalin.create().start(7000);
        new Router(app);
        return app;
    }
}
