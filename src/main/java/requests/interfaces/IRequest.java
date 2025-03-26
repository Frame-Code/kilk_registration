package requests.interfaces;

import java.util.Optional;

/**
 *
 * @author Daniel Mora Cantillo
 */
public interface IRequest {
    Optional<String> sendGet();
    Optional<String> sendPost(String body);
}
