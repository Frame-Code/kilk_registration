package service.impl;

import service.interfaces.IPlateParserService;

import java.util.Arrays;
import java.util.List;

/**
 * @author Daniel Mora Cantillo
 */
public class PlateParserServiceImpl implements IPlateParserService {
    @Override
    public List<String> separatePlates(String values) {
        var licensesPlate = Arrays.stream(values.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(plate -> !plate.isEmpty())
                .toList();
        return licensesPlate.stream().map(String::trim).map(String::toUpperCase).toList();
    }
}
