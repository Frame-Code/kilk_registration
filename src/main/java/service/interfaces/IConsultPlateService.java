package service.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * @author Daniel Mora Cantillo
 */
public interface IConsultPlateService {
    Optional<String> findInfoVehicle(String licensePlate);
    List<Optional<String>> findInfoVehicle(List<String> licensesPlates);
}
