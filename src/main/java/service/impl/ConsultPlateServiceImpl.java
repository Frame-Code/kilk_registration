package service.impl;

import com.google.gson.Gson;
import dto.SearchVehicleDTO;
import lombok.RequiredArgsConstructor;
import requests.interfaces.IRequest;
import service.interfaces.IConsultPlateService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class ConsultPlateServiceImpl implements IConsultPlateService {
    private static final Logger LOG = Logger.getLogger(ConsultPlateServiceImpl.class.getName());
    private final IRequest search;

    @Override
    public Optional<String> findInfoVehicle(String licensePlate) {
        Optional<String> token = getToken();
        if(token.isEmpty()) {
            LOG.log(Level.WARNING, "Token not founded");
            return Optional.empty();
        }

        String jsonBody = new Gson().toJson(new SearchVehicleDTO(token.get(), licensePlate));
        return search.sendPost(jsonBody);

    }

    @Override
    public Optional<String> findInfoVehicle(List<String> licensesPlates) {
        return Optional.empty();
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
