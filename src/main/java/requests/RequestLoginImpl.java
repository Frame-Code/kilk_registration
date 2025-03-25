package requests;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Artist-Code
 */
@Slf4j
public class RequestLoginImpl implements IRequestLogin {
    
    @Override
    public Optional<String> sendTypeGet() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://servicios.epmtsd.gob.ec:5050/login"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200
                    ? Optional.of(response.body())
                    : Optional.empty();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error("Error opening the web".concat(e.getMessage()));
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> sendTypePost(String body) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://servicios.epmtsd.gob.ec:5050/login"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200
                    ? Optional.of(response.body())
                    : Optional.empty();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error("Error opening the web".concat(e.getMessage()));
            return Optional.empty();
        }
    }

}
