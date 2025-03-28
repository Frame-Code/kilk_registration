package dto;

import lombok.Builder;

/**
 * @author Daniel Mora Cantillo
 */
@Builder
public class ResultOperation {
    private boolean isResultOk;
    private String message;
}
