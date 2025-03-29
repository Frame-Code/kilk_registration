package service.impl;

import com.google.gson.Gson;
import dto.UserLoginDTO;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import requests.interfaces.IRequest;
import service.interfaces.IAuthService;

/**
 *
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final Dotenv dotenv = Dotenv.configure()
            .directory("/").filename(".env")
            .load();
    private final IRequest requestLogin;

    @Override
    public Optional<String> log_in(String token) {
        String jsonBody = new Gson().toJson(
                new UserLoginDTO(token,
                        dotenv.get("USERNAME_WEB"),
                        dotenv.get("PASSWORD_WEB")));
        return requestLogin.sendPost(jsonBody);
    }

    @Override
    public Optional<String> getToken() {
        return requestLogin.sendGet().map(html -> {
            int position = html.indexOf("token");
            if (position != 1) {
                return html.substring(position + 9, position + 49);
            }
            return html;
        });
    }
}
