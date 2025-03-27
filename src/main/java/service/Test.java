package service;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

/**
 * @author Daniel Mora Cantillo
 */
public class Test {

    public static void editVehiclePDF(String inputPath, String outputPath, String nRevision,
                                      String newMarca, String newModelo, String year,
                                      String newChasis, String newPropietario,
                                      String newPlaca) throws IOException {

        // Cargar el documento PDF

        // Crear un nuevo content stream para modificar la página

    }

    public static void main(String[] args) {
        String inputFile = "C:\\Users\\Artist-Code\\Downloads\\Document 1 this.name.pdf"; // Ruta del archivo;
        String outputFile = "C:\\Users\\Artist-Code\\Downloads\\documento_editado.pdf";
        try {
             editVehiclePDF(inputFile, outputFile, "090998097809",
                    "NUEVA_MARCA", "NUEVO_MODELO", "2024",
                    "NUEVO_CHASIS", "NUEVO_PROPIETARIO",
                    "IBA1250");
            System.out.println("PDF modificado exitosamente!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
