package requests;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Artist-Code
 */
@RequiredArgsConstructor
public class RequestLoginImpl implements IRequestLogin {

    private static final Logger LOG = Logger.getLogger(RequestLoginImpl.class.getName());
    private final String URI = "http://servicios.epmtsd.gob.ec:5050/login";
    private final HttpClient client;
    private final CookieManager cookieManager;
    
    @Override
    public Optional<String> sendTypeGet() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URI))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                LOG.log(Level.INFO, "Error, can not get the info ".concat(String.valueOf(response.statusCode())));
                return Optional.empty();
            }
            return Optional.of(response.body());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            LOG.log(Level.WARNING, "Exception opening the web".concat(e.getMessage()));
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> sendTypePost(String body) {     
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URI))
                    .POST(BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json")
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 302) {
                LOG.log(Level.INFO, "Error, can not login ".concat(String.valueOf(response.statusCode())));
                return Optional.empty();
            }
            

            return Optional.of(response.body());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            LOG.log(Level.WARNING, "Exception on the logging ".concat(e.getMessage()));
            return Optional.empty();
        }
    }

}
