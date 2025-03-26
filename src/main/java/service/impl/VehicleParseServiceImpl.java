package service.impl;

import dto.VehicleDTO;
import service.interfaces.IVehicleInfoParserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Daniel Mora Cantillo
 */
public class VehicleParseServiceImpl implements IVehicleInfoParserService {
    private static final Logger LOG = Logger.getLogger(VehicleParseServiceImpl.class.getName());

    @Override
    public Optional<VehicleDTO> parseVehicle(String htmlResponse) {
        String html = htmlResponse.substring(0, Math.min(4200, htmlResponse.length()));
        List<String> labels = Arrays.asList(html.split("</tr>"));
        List<String> values = labels.stream()
                .filter(value ->
                        value.contains("Placa")
                                || value.contains("Chasis")
                                || value.contains("Fecha de Renovacion del documento de circulacion anual")
                                || value.contains("Marca")
                                || value.contains("Modelo")
                                || value.contains("Propietario"))
                .map(label -> {
                    Matcher matcher = Pattern.compile("<td>(.*?)</td>").matcher(label);
                    if (matcher.find()) {
                        return matcher.group(1);
                    }
                    return null;
                })
                .toList();

        VehicleDTO vehicle = new VehicleDTO();
        if (!isNull(values.get(0))) {
            vehicle.setPlaca(values.get(0));
        }
        if (!isNull(values.get(1))) {
            vehicle.setChasis(values.get(1));
        }
        if (!isNull(values.get(2))) {
            vehicle.setFechaRenovacion(getDateFromString(values.get(2)));
        }
        if (!isNull(values.get(3))) {
            vehicle.setMarca(values.get(3));
        }
        if (!isNull(values.get(4))) {
            vehicle.setModelo(values.get(4));
        }
        if (!isNull(values.get(5))) {
            vehicle.setPropietario(values.get(5));
        }

        if(vehicle.getChasis() == null || vehicle.getMarca() == null || vehicle.getModelo() == null) {
            LOG.log(Level.WARNING, "Not vehicle founded");
            return Optional.empty();
        }
        LOG.log(Level.INFO, "Vehicle founded");
        return Optional.of(vehicle);
    }

    private boolean isNull(String value) {
        return value == null || value.isEmpty();
    }

    private LocalDate getDateFromString(String date) {
        try {
            Optional<String> dateOpt = Arrays.stream(date.split(" ")).
                    findFirst();
            return dateOpt.map(s -> LocalDate.parse(s, DateTimeFormatter.ofPattern("dd-MM-yyyy"))).orElse(null);
        } catch (NullPointerException | DateTimeParseException | IllegalArgumentException e) {
            LOG.log(Level.SEVERE, "Error formating renovation date");
            return null;
        }
    }
}
