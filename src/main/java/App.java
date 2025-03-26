
import UI.Init;
import UI.Principal;
import com.formdev.flatlaf.FlatDarkLaf;
import controller.InitController;
import dto.Vehicle;
import requests.RequestLoginImpl;
import requests.RequestSearchImpl;
import service.AuthServiceImpl;
import service.SearchVehicleServiceImpl;


import javax.swing.*;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                
                SearchVehicleServiceImpl seach = new SearchVehicleServiceImpl(new RequestSearchImpl(client, cookieManager));

                Optional<String> htmlOpt = seach.getInfoVehicle("MBE7632");
                if(htmlOpt.isEmpty()) {
                    System.out.println("Error encontrando la plca");
                    return null;
                }
                String html = htmlOpt.get();
                html = html.substring(0, 4200);
                List<String> labels = Arrays.asList(html.split("</tr>"));
                List<Optional<String>> values = labels.stream()
                        .filter(value ->
                                value.contains("Placa")
                                        || value.contains("Chasis")
                                        || value.contains("Marca")
                                        || value.contains("Modelo")
                                        || value.contains("Propietario")
                                        || value.contains("Fecha de Renovacion del documento de circulacion anual"))
                        .map(label -> {
                            Matcher matcher = Pattern.compile("<td>(.*?)</td>").matcher(label);
                            if(matcher.find()) {
                                return Optional.of(matcher.group(1));
                            }
                            return Optional.<String>empty();
                        })
                        .toList();

                Vehicle vehicle = Vehicle.builder()
                        .placa(values.get(0).orElse(""))
                        .chasis(values.get(1).orElse(""))
                        .marca(values.get(2).orElse(""))
                        .modelo(values.get(3).orElse(""))
                        .propietario(values.get(4).orElse(""))
                        .build();
                System.out.println(vehicle.toString());

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
