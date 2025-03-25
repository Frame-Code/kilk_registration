package requests;

import java.util.Optional;

/**
 *
 * @author Daniel Mora Cantillo
 */
public interface IRequestLogin {
    Optional<String> sendTypeGet();
    Optional<String> sendTypePost(String body);
}
