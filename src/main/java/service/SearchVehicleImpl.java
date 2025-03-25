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
public class SearchVehicleImpl implements ISearchService{
    private static final Logger LOG = Logger.getLogger(SearchVehicleImpl.class.getName());
    private final IRequest search;

    @Override
    public Optional<String> getInfoVehicle(String id) {
        Optional<String> html = search.sendGet();
        if(html.isEmpty()) {
            LOG.log(Level.WARNING, "Error loading the web page");
            return Optional.empty();
        }

        Optional<String> token = getToken();
        if(token.isEmpty()) {
            LOG.log(Level.WARNING, "Token not founded");
            return Optional.empty();
        }

        String jsonBody = new Gson().toJson(new SearchVehicle(token.get(), id));
        return search.sendPost(jsonBody);
    }

    private Optional<String> getToken() {
        Optional<String> html = search.sendGet();
        if(html.isEmpty()) {
            return Optional.empty();
        }

        int position = html.get().indexOf("token");
        if (position != 1) {
            return Optional.of(html.get().substring(position + 9, position + 49));
        }
        return Optional.empty();
    }
}
