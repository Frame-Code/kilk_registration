package service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import requests.IRequestLogin;

/**
 *
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IRequestLogin requestLogin;

    public Optional<String> log_in() {
        return null;
    }

    @Override
    public Optional<String> init() {
        return requestLogin.sendTypeGet();
    }

    @Override
    public Optional<String> log_in(String token) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<String> getToken(String html) {
        int position  = html.indexOf("token");
        if(position != 1)  {
            return Optional.of(html.substring(position + 9, position + 49));
        }
        return Optional.empty(); 
    }
}
