
import UI.InitWindow;
import UI.PrincipalWindow;
import com.formdev.flatlaf.FlatDarkLaf;
import controller.InitWindowController;
import controller.PrincipalWindowController;
import requests.impl.RequestSearchImpl;
import service.impl.AuthServiceImpl;
import service.impl.ConsultPlateServiceImpl;
import service.impl.PlateParserServiceImpl;
import service.impl.VehicleParseServiceImpl;


import javax.swing.*;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            LOG.log(Level.WARNING, "Cat not load look and feel");
        }

        InitWindow initWindowFrm = new InitWindow();
        PrincipalWindow principalFrm = new PrincipalWindow();

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        HttpClient client = HttpClient.newBuilder()
                .cookieHandler(cookieManager)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                InitWindowController initController = new InitWindowController(
                        initWindowFrm,
                        new AuthServiceImpl(new RequestSearchImpl.RequestLoginImpl(client, cookieManager))
                );
                if(initController.load()) {
                    return null;
                }
                throw new RuntimeException("Can't connect to the server");
            }

            @Override
            protected void done() {
                initWindowFrm.close();
                new PrincipalWindowController(principalFrm,
                        new PlateParserServiceImpl(),
                        new ConsultPlateServiceImpl(new RequestSearchImpl(client, cookieManager)),
                        new VehicleParseServiceImpl())
                        .addListeners(client, cookieManager);
                principalFrm.open();

            }
        };
        worker.execute();
        initWindowFrm.open();
    }
}
