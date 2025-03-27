package service;

import dto.VehicleDTO;
import lombok.RequiredArgsConstructor;
import service.interfaces.IConsultPlateService;
import service.interfaces.IMediatorPlate;
import service.interfaces.IPlateParserService;
import service.interfaces.IVehicleInfoParserService;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class IMediatorPlateImpl implements IMediatorPlate {
    private StringBuilder platesWithNovelties = new StringBuilder();
    private static final Logger LOG = Logger.getLogger(IMediatorPlateImpl.class.getName());
    private final IPlateParserService plateParser;
    private final IConsultPlateService consultPlate;
    private final IVehicleInfoParserService vehicleInfoParserService;

    @Override
    public List<String> separatePlates(String platesString) {
        return plateParser.separatePlates(platesString);
    }

    @Override
    public List<Optional<String>> consultPlates(List<String> plates) {
        if (plates.isEmpty()) {
            return List.of();
        }
        int nThreads = Math.min(plates.size(), 5);
        var executor = Executors.newFixedThreadPool(nThreads);

        Callable<List<Optional<String>>> task = () -> {
            Thread.sleep(700);
            LOG.log(Level.INFO, "Executing the server requests...");
            return plates.stream()
                    .map(consultPlate::findInfoVehicle)
                    .toList();
        };

        Future<List<Optional<String>>> resultFuture = executor.submit(task);
        try {
            resultFuture.get();
            return resultFuture.get();
        } catch (CancellationException | ExecutionException | InterruptedException e) {
            LOG.log(Level.INFO, "Interruption exception occur ".concat(e.getMessage()));
            return List.of();
        } finally {
            executor.shutdown();
        }
    }

    @Override
    public Optional<VehicleDTO> parseVehicleFromHTML(String html) {
        var executor = Executors.newFixedThreadPool(3);

        Callable<Optional<VehicleDTO>> task = () -> {
            Optional<VehicleDTO> vehicle = vehicleInfoParserService.parseVehicle(html);
            if (vehicle.isEmpty()) {
                return Optional.empty();
            }
            var auto = vehicle.get();
            if (auto.getChasis() == null || auto.getChasis().isBlank() &&
                    auto.getModelo() == null || auto.getModelo().isBlank() &&
                    auto.getMarca() == null || auto.getMarca().isBlank()) {
                platesWithNovelties.append(auto.getPlaca().concat("\n"));
            }
            return vehicle.filter(vehicleDto -> !platesWithNovelties.toString().contains(vehicleDto.getPlaca()));
        };
        Future<Optional<VehicleDTO>> resultFuture = executor.submit(task);
        try {
            return resultFuture.get();
        } catch (CancellationException | ExecutionException | InterruptedException ex) {
            LOG.log(Level.WARNING, "Interruption exception occur ".concat(ex.getMessage()));
            return Optional.empty();
        } finally {
            executor.shutdown();
        }

    }

    @Override
    public String getPlatesWithNovelties() {
        return platesWithNovelties.toString();
    }
}
