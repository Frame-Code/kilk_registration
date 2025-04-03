package service;

import io.github.cdimascio.dotenv.DotEnvException;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;

/**
 * @author Daniel Mora Cantillo
 */
@Getter
public class EnvSingleton {
    private static EnvSingleton INSTANCE;

    private final Dotenv dotenv;

    private EnvSingleton() throws DotEnvException{
        this.dotenv = Dotenv.configure()
                .directory("./").filename(".env")
                .load();
    }

    public static synchronized  EnvSingleton getInstance() throws DotEnvException {
        if(INSTANCE != null) {
            return  INSTANCE;
        }
        return new EnvSingleton();
    }


}

