package requests.impl;

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
import lombok.RequiredArgsConstructor;
import requests.interfaces.IRequest;

/**
 *
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class RequestSearchImpl implements IRequest {
    private static final Logger LOG = Logger.getLogger(RequestSearchImpl.class.getName());

    private final HttpClient client;
    private final CookieManager cookieManager;

    private Optional<String> loadCookiesAndGetTokenHTML() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://servicios.epmtsd.gob.ec:5050/consult-vehicle-ws"))
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
    public Optional<String> sendGet() {
        try {
            return loadCookiesAndGetTokenHTML();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            LOG.log(Level.WARNING, "Exception loading the cookies and got the _token ".concat(e.getMessage()));
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> sendPost(String body) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://servicios.epmtsd.gob.ec:5050/ajax-consult-vehicle-ws"))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json")
                    .header("Accept", "*/*")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 302) {
                LOG.log(Level.INFO, "Request to search has been successfully");
                return Optional.of(response.body());
            }
            LOG.log(Level.WARNING, "Error searching id, status code: ".concat(String.valueOf(response.statusCode())));
            return Optional.empty();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            LOG.log(Level.WARNING, "Internal exception ".concat(e.getMessage()));
            return Optional.empty();
        }
    }

    /**
     *
     * @author Daniel Mora Cantillo
     */
    @RequiredArgsConstructor
    public static class RequestLoginImpl implements IRequest {
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
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200 || response.statusCode() == 302) {
                    LOG.log(Level.INFO, "Request to login has been successfully");
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
}
