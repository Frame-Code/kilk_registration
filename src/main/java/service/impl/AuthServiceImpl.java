package service.impl;

import com.google.gson.Gson;
import dto.UserLoginDTO;
import java.util.Optional;

import io.github.cdimascio.dotenv.DotEnvException;
import lombok.RequiredArgsConstructor;
import requests.interfaces.IRequest;
import service.EnvSingleton;
import service.interfaces.IAuthService;

/**
 *
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IRequest requestLogin;

    @Override
    public Optional<String> log_in(String token) {
        try {
            EnvSingleton env = EnvSingleton.getInstance();
            String jsonBody = new Gson().toJson(
                    new UserLoginDTO(token,
                            env.getDotenv().get("USERNAME_WEB"),
                            env.getDotenv().get("PASSWORD_WEB")));
            return requestLogin.sendPost(jsonBody);
        } catch (DotEnvException e) {
            throw new RuntimeException("Can't load environment variables");
        }

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
