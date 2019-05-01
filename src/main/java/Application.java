import apicontroller.Router;
import io.javalin.Javalin;

public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create();
        app.disableStartupBanner();
        app.start(7000);
        new Router(app);
    }
}
