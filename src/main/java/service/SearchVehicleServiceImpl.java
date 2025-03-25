package service;

import com.google.gson.Gson;
import dto.SearchVehicle;
import lombok.RequiredArgsConstructor;
import requests.IRequest;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class SearchVehicleServiceImpl implements ISearchVehicleService {
    private static final Logger LOG = Logger.getLogger(SearchVehicleServiceImpl.class.getName());
    private final IRequest search;

    @Override
    public Optional<String> getInfoVehicle(String id) {
        Optional<String> token = getToken();
        if(token.isEmpty()) {
            LOG.log(Level.WARNING, "Token not founded");
            return Optional.empty();
        }

        String jsonBody = new Gson().toJson(new SearchVehicle(token.get(), id));
        return search.sendPost(jsonBody);

    }

    private Optional<String> getToken() {
        return search.sendGet().map(html -> {
            int position = html.indexOf("token");
            if (position != 1) {
                return html.substring(position + 9, position + 49);
            }
            return html;
        });
    }
}
