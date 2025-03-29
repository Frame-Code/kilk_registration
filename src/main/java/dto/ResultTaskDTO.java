package dto;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Daniel Mora Cantillo
 */
@Builder
@Getter
public class ResultTaskDTO {
    private boolean isOk;
    private String errorMessage;
}
