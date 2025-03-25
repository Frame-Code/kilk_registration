
import UI.Init;
import controller.InitController;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;
import requests.RequestLoginImpl;
import requests.RequestSearchImpl;
import service.AuthServiceImpl;
import service.SearchVehicleImpl;

public class App {

    public static void main(String[] args) {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        HttpClient client = HttpClient.newBuilder()
                .cookieHandler(cookieManager)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        
        InitController initController = new InitController(
                new Init(),
                new AuthServiceImpl(new RequestLoginImpl(client, cookieManager))
        );
        initController.load();

        SearchVehicleImpl seach = new SearchVehicleImpl(new RequestSearchImpl(client, cookieManager));
        System.out.println(seach.getInfoVehicle("MCT0232"));
    }
}
