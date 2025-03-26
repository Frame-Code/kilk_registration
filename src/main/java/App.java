
import UI.Init;
import UI.Principal;
import com.formdev.flatlaf.FlatDarkLaf;
import controller.InitController;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import requests.RequestLoginImpl;
import requests.RequestSearchImpl;
import service.AuthServiceImpl;
import service.SearchVehicleServiceImpl;

public class App {

    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            LOG.log(Level.WARNING, "Cat not load look and feel");
        }

        Init initFrm = new Init();
        Principal principalFrm = new Principal();

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                CookieManager cookieManager = new CookieManager();
                cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
                HttpClient client = HttpClient.newBuilder()
                        .cookieHandler(cookieManager)
                        .followRedirects(HttpClient.Redirect.NORMAL)
                        .build();

                InitController initController = new InitController(
                        initFrm,
                        new AuthServiceImpl(new RequestLoginImpl(client, cookieManager))
                );
                initController.load();
                
                //SearchVehicleServiceImpl seach = new SearchVehicleServiceImpl(new RequestSearchImpl(client, cookieManager));

                //System.out.println(seach.getInfoVehicle("MCT0232"));
                return null;
            }

            @Override
            protected void done() {
                initFrm.close();
                principalFrm.open();

            }
        };
        worker.execute();
        initFrm.open();
    }

}
