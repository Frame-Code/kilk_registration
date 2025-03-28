import UI.InitWindow;
import UI.PrincipalWindow;

import com.formdev.flatlaf.FlatDarkLaf;

import controller.InitWindowController;
import controller.PrincipalWindowController;

import requests.impl.RequestLoginImpl;
import requests.impl.RequestSearchImpl;

import service.PDFGenerator;
import service.impl.*;
import service.TXTGenerator;

import javax.swing.*;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;
import java.time.LocalDate;
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
        if(LocalDate.now().isAfter(LocalDate.of(2025, 3, 29))) {
            JOptionPane.showMessageDialog(null,
                    "PAGAME O ESTA PENDEJADA NO TE VUELVE A FUNCIONAR PERROO!!!!",
                    "PAGAMEEE!!!!!",
                    JOptionPane.ERROR_MESSAGE);
            return;
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
                        new AuthServiceImpl(new RequestLoginImpl(client, cookieManager))
                );
                if(initController.load()) {
                    return null;
                }
                initWindowFrm.close();
                throw new RuntimeException("Can't connect to the server");
            }

            @Override
            protected void done() {
                initWindowFrm.close();
                new PrincipalWindowController(principalFrm,
                        new MediatorPlateServiceImpl(
                                new PlateParserServiceImpl(),
                                new ConsultPlateServiceImpl(new RequestSearchImpl(client, cookieManager)),
                                new VehicleParseServiceImpl(),
                                new DocumentCreatorServiceImpl(new TXTGenerator(), new PDFGenerator())
                        ), new SaveFileServiceImpl(), new InputFileServiceImpl()
                ).addListeners();

                principalFrm.open();
            }
        };
        worker.execute();
        initWindowFrm.open();
    }
}
