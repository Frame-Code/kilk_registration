package service.impl;

import dto.DocumentDataDTO;
import dto.ResultTaskDTO;
import dto.VehicleDTO;
import lombok.RequiredArgsConstructor;
import service.CONTENT_FIELDS_MAP_ENUM;
import service.interfaces.*;
import service.interfaces.IDocumentCreatorService;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class MediatorPlateServiceImpl implements IMediatorPlateService {
    private static final Logger LOG = Logger.getLogger(MediatorPlateServiceImpl.class.getName());

    private StringBuilder platesWithNovelties;
    private StringBuilder platesWithBeforeDate;

    private final IPlateParserService plateParser;
    private final IConsultPlateService consultPlate;
    private final IVehicleInfoParserService vehicleInfoParserService;
    private final IDocumentCreatorService documentCreatorService;

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
            executor.shutdown();
            executor.shutdownNow();
            return resultFuture.get();
        } catch (CancellationException | ExecutionException | InterruptedException e) {
            executor.shutdown();
            LOG.log(Level.INFO, "Interruption exception occur ".concat(e.getMessage()));
            return List.of();
        } finally {
            executor.shutdown();
        }
    }

    @Override
    public Optional<VehicleDTO> parseVehicleFromHTML(String html) {
        platesWithNovelties = new StringBuilder();
        platesWithBeforeDate = new StringBuilder();

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
            resultFuture.get();
            executor.shutdown();
            executor.shutdownNow();
            return resultFuture.get();
        } catch (CancellationException | ExecutionException | InterruptedException ex) {
            executor.shutdown();
            LOG.log(Level.WARNING, "Interruption exception occur ".concat(ex.getMessage()));
            return Optional.empty();
        } finally {
            executor.shutdown();
        }

    }

    @Override
    public List<VehicleDTO> verifyRenovationDate(List<Optional<VehicleDTO>> vehicleList) {
        if(vehicleList.isEmpty()) {
            return List.of();
        }

        return vehicleList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(auto -> auto.getFechaRenovacion() != null)
                .map(auto -> {
                    if(auto.getFechaRenovacion().isBefore(LocalDate.of(2025,1,1))) {
                        platesWithBeforeDate.append(auto.getPlaca().concat("\n"));
                        return auto;
                    }
                    return auto;
                })
                .filter(auto -> auto.getFechaRenovacion().isAfter(LocalDate.of(2025,1,1)))
                .toList();

    }

    @Override
    public String getPlatesWithNovelties() {
        return platesWithNovelties.toString();
    }

    @Override
    public String getPlatesWithBeforeDate() {
        return platesWithBeforeDate.toString();
    }

    @Override
    public void exportFiles(List<VehicleDTO> vehicleList, ISaveFileService saveFileService,String inputPath) throws Exception {
        ResultTaskDTO resultTask1 = documentCreatorService.createTXTReport(DocumentDataDTO.builder()
                .outputPath(saveFileService.setFileName("Placas no encontradas.txt"))
                .rawContent(getPlatesWithNovelties())
                .build());
        verifyResultTask(resultTask1, "Reporte de 'placas no encontradas' creado correctamente");

        ResultTaskDTO resultTask2 = documentCreatorService.createTXTReport(DocumentDataDTO.builder()
                .outputPath(saveFileService.setFileName("Placas sin renovación.txt"))
                .rawContent(getPlatesWithBeforeDate())
                .build());
        verifyResultTask(resultTask2, "Reporte de 'placas sin renovación' creado correctamente");

        vehicleList.forEach(auto -> {
            ResultTaskDTO resultTask = documentCreatorService.createPDFReport(DocumentDataDTO.builder()
                    .inputPath(inputPath)
                    .outputPath(saveFileService.setFileName("Report ".concat(auto.getPlaca()).concat(".pdf")))
                    .contentFields(Map.of(
                            CONTENT_FIELDS_MAP_ENUM.N_REVISION.toString(), "000".concat(String.valueOf(new Random().nextInt(99) + 893)),
                            CONTENT_FIELDS_MAP_ENUM.MARCA.toString(), auto.getMarca(),
                            CONTENT_FIELDS_MAP_ENUM.MODELO.toString(), auto.getModelo(),
                            CONTENT_FIELDS_MAP_ENUM.YEAR.toString(), auto.getAnioVehiculo(),
                            CONTENT_FIELDS_MAP_ENUM.CHECK_YEAR.toString(), auto.getFechaRenovacion().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                            CONTENT_FIELDS_MAP_ENUM.CHASIS.toString(), auto.getChasis(),
                            CONTENT_FIELDS_MAP_ENUM.PROPIETARIO.toString(), auto.getPropietario(),
                            CONTENT_FIELDS_MAP_ENUM.PLACA.toString(), auto.getPlaca()
                    ))
                    .build());
            verifyResultTask(resultTask, "Reporte PDF de la placa: " + auto.getPlaca() + " creado correctamente");
        });
    }

    private void verifyResultTask(ResultTaskDTO resultTaskDTO, String messageOk) {
        if(!resultTaskDTO.isOk()) {
            showDialogMessage(resultTaskDTO.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE, true);
            return;
        }
        showDialogMessage(messageOk, "Success", JOptionPane.INFORMATION_MESSAGE, false);
    }

    private void showDialogMessage(String message, String title, int typeMessage, boolean isError) {
        int time = isError? 7000: 1250;
        JOptionPane pane = new JOptionPane(message, typeMessage);
        JDialog dialog = pane.createDialog(null, title);

        Timer timer = new Timer(time, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);
    }
}
