package service.interfaces;

import dto.VehicleDTO;

import java.util.Optional;

/**
 * @author Daniel Mora Cantillo
 */
public interface IVehicleInfoParserService {
    Optional<VehicleDTO> parseVehicle(String htmlResponse);
}
