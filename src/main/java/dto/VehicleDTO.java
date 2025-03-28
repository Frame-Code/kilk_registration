package dto;

import lombok.*;

import java.time.LocalDate;

/**
 * @author Daniel Mora Cantillo
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class VehicleDTO {
    private String placa;
    private String chasis;
    private LocalDate fechaRenovacion;
    private String anioVehiculo;
    private String marca;
    private String modelo;
    private String propietario;
}
