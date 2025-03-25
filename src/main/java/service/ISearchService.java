package service;

import java.util.Optional;

/**
 * @author Daniel Mora Cantillo
 */
public interface ISearchService {
    Optional<String> getInfoVehicle(String id);
}
