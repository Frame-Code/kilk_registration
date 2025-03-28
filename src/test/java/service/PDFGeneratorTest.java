package service;

import dto.DocumentDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author Daniel Mora Cantillo
 */
public class PDFGeneratorTest {
    PDFGenerator pdfGenerator;
    @BeforeEach
    void setUp() {
        pdfGenerator = new PDFGenerator();
    }
    @Test
    void generate() {
       /* String inputPath = "C:\\Users\\Artist-Code\\Downloads\\Document 1 this.name.pdf";
        String outputPath = "C:\\Users\\Artist-Code\\Downloads\\Document_edited.pdf";
        try {
            pdfGenerator.generate(DocumentDataDTO.builder()
                    .inputPath(inputPath)
                    .outputPath(outputPath)
                    .contentFields(Map.of(
                            CONTENT_FIELDS_MAP_ENUM.N_REVISION.toString(), "000",
                            CONTENT_FIELDS_MAP_ENUM.MARCA.toString(), "auto.getMarca()",
                            CONTENT_FIELDS_MAP_ENUM.MODELO.toString(), "auto.getModelo()",
                            CONTENT_FIELDS_MAP_ENUM.YEAR.toString(), "auto.getVehiculo()",
                            CONTENT_FIELDS_MAP_ENUM.CHECK_YEAR.toString(), "autt",
                            CONTENT_FIELDS_MAP_ENUM.CHASIS.toString(), "auto.getChasis()",
                            CONTENT_FIELDS_MAP_ENUM.PROPIETARIO.toString(), "auto.getPropietario()",
                            CONTENT_FIELDS_MAP_ENUM.PLACA.toString(), "MSAS811"
                    )).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }
}