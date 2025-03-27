package service.interfaces;

import dto.VehicleDTO;

import java.util.List;
import java.util.Optional;

/**
 * @author Daniel Mora Cantillo
 */
public interface IMediatorPlateService {
    List<String> separatePlates(String platesString);
    List<Optional<String>> consultPlates(List<String> plates);
    Optional<VehicleDTO> parseVehicleFromHTML(String html);
    String getPlatesWithNovelties();
    String getPlatesWithBeforeDate();
    List<VehicleDTO> verifyRenovationDate(List<Optional<VehicleDTO>> vehicleList);

}
