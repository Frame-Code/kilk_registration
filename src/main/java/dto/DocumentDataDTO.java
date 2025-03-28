package dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;

/**
 * @author Daniel Mora Cantillo
 */
@Builder
public class DocumentDataDTO {
    @Getter
    private String outputPath;
    @Getter
    private String inputPath;
    private Map<String, String> contentFields;
    @Getter
    private String rawContent;

    public Map<String, String> getContentFields() {
        return Collections.unmodifiableMap(contentFields);
    }

    public boolean isDocumentTxt() {
        return  contentFields == null || contentFields.isEmpty() || outputPath == null || outputPath.isEmpty();
    }

}
