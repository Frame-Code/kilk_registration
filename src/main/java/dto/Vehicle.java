package dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author Daniel Mora Cantillo
 */
@Builder
@ToString
public class Vehicle {
    private String placa;
    private String chasis;
    private LocalDate fechaRenovacion;
    private String marca;
    private String modelo;
    private String propietario;
}
