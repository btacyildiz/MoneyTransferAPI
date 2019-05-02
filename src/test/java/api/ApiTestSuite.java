package api;

import apicontroller.Router;
import io.javalin.Javalin;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class ApiTestSuite {
    private static Javalin app;
    @BeforeClass
    public synchronized static void started ()
    {
        app = ApiTestSuite.serverStarter();
    }

    @AfterClass
    public synchronized static void completed(){
    }

    private static Javalin serverStarter(){
        try {
            Javalin app = Javalin.create();
            app.disableStartupBanner();
            app.start(7000);
            new Router(app);
            System.out.println("SERVER IS STARTED");
        }catch(Exception e){
            System.out.println("Already started");
        }
        return app;
    }
}
