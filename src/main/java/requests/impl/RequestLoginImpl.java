package requests.impl;

import lombok.RequiredArgsConstructor;
import requests.interfaces.IRequest;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class RequestLoginImpl implements IRequest {
    private static final Logger LOG = Logger.getLogger(RequestLoginImpl.class.getName());
    private final String URI = "http://servicios.epmtsd.gob.ec:5050/login";
    private final HttpClient client;
    private final CookieManager cookieManager;

    private Optional<String> loadCookiesAndGetTokenHTML() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URI))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            LOG.log(Level.WARNING, "Error, cannot load cookies".concat(String.valueOf(response.statusCode())));
            return Optional.empty();
        }
        LOG.log(Level.INFO, "Cookies loaded correctly");
        return Optional.of(response.body());
    }

    @Override
    public Optional<String> sendPost(String body) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URI))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json")
                    .header("Accept", "*/*")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 302) {
                if(response.body().contains("Sus datos de acceso son incorrectos.")) {
                    LOG.log(Level.WARNING, "Error can't login, bad access credentials");
                    return Optional.empty();
                }
                LOG.log(Level.INFO, "Login successfully");
                return Optional.of(response.body());
            }

            if(response.statusCode() == 200) {
                if(response.body().contains("Sus datos de acceso son incorrectos.")) {
                    LOG.log(Level.WARNING, "Error can't login, bad access credentials");
                    return Optional.empty();
                }
                LOG.log(Level.INFO, "Login successfully");
                return Optional.of(response.body());
            }



            LOG.log(Level.WARNING, "Error on the login, status code: ".concat(String.valueOf(response.statusCode())));
            return Optional.empty();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            LOG.log(Level.WARNING, "Exception on the logging ".concat(e.getMessage()));
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> sendGet() {
        try {
            return loadCookiesAndGetTokenHTML();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            LOG.log(Level.WARNING, "Exception loading the cookies and got the _token ".concat(e.getMessage()));
            return Optional.empty();
        }
    }

}
