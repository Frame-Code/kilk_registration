
package service;

import java.util.Optional;

/**
 *
 * @author Daniel Mora Cantillo
 */
public interface IAuthService {
    Optional<String> log_in(String token);
    Optional<String> getToken();
}
