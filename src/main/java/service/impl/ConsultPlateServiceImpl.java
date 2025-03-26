package service.impl;

import com.google.gson.Gson;
import dto.SearchVehicleDTO;
import lombok.RequiredArgsConstructor;
import requests.interfaces.IRequest;
import service.interfaces.IConsultPlateService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
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
        if (token.isEmpty()) {
            LOG.log(Level.WARNING, "Token not founded");
            return Optional.empty();
        }

        String jsonBody = new Gson().toJson(new SearchVehicleDTO(token.get(), licensePlate));
        return search.sendPost(jsonBody);

    }

    @Override
    public List<Optional<String>> findInfoVehicle(List<String> licensesPlates) {
        if (licensesPlates.isEmpty()) {
            return List.of();
        }
        int nThreads = Math.min(licensesPlates.size(), 5);
        var executor = Executors.newFixedThreadPool(nThreads);

        Callable<List<Optional<String>>> task = () -> {
            return licensesPlates.stream()
                    .map(this::findInfoVehicle)
                    .toList();
        };

        Future<List<Optional<String>>> resultFuture = executor.submit(task);
        LOG.log(Level.INFO, "Executing the server requests");
        try {
            return resultFuture.get();
        } catch (CancellationException | ExecutionException | InterruptedException e) {
            LOG.log(Level.INFO, "Interruption exception occur ".concat(e.getMessage()));
            return List.of();
        }
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
