package service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Daniel Mora Cantillo
 */
public class LicensePlateServiceImpl implements ILicensePlateService{
    @Override
    public List<String> getLicensesPlate(String values) {
        var licensesPlate = Arrays.stream(values.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();
        return licensesPlate.stream().map(String::trim).toList();
    }
}
