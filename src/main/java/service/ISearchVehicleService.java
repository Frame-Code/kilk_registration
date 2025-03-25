package service;

import java.util.Optional;

/**
 * @author Daniel Mora Cantillo
 */
public interface ISearchVehicleService {
    Optional<String> getInfoVehicle(String id);
}
