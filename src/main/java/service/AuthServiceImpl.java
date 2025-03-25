package service;

import com.google.gson.Gson;
import dto.UserLogin;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import requests.IRequestLogin;

/**
 *
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final Dotenv dotenv = Dotenv.load();
    private final IRequestLogin requestLogin;

    @Override
    public Optional<String> init() {
        return requestLogin.sendTypeGet();
    }

    @Override
    public Optional<String> log_in(String token) {
        String jsonBody = new Gson().toJson(new UserLogin(token, dotenv.get("USERNAME_WEB"), dotenv.get("PASSWORD_WEB")));
        return requestLogin.sendTypePost(jsonBody);
    }

    @Override
    public Optional<String> getToken(String html) {
        int position = html.indexOf("token");
        if (position != 1) {
            return Optional.of(html.substring(position + 9, position + 49));
        }
        return Optional.empty();
    }
}
