package service;

import dto.DocumentDataDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

/**
 * @author Daniel Mora Cantillo
 */
public class PDFGenerator extends DocumentGenerator {
    //private final File NORMAL_FONT = new File("src\\main\\resources\\fonts\\arialmt.ttf");
    //private File NORMAL_FONT;
    //private File BOLD_FONT;
    //private File BOLD_FONT = new File("src\\main\\resources\\fonts\\ARIALMTBLACK.TTF");

    @Override
    public void generate(DocumentDataDTO documentDataDTO) throws IOException {
        PDDocument document = PDDocument.load(new File(documentDataDTO.getInputPath()));

        PDPage page = document.getPage(0);

        PDType0Font customFont = PDType0Font.load(document, PDFGenerator.class.getResourceAsStream("/fonts/arialmt.ttf"));
        PDType0Font customFontBold = PDType0Font.load(document, PDFGenerator.class.getResourceAsStream("/fonts/ARIALMTBLACK.TTF"));

        generate(document, page, customFont, customFontBold, documentDataDTO.getOutputPath(),
                documentDataDTO.getContentFields().get(CONTENT_FIELDS_MAP_ENUM.N_REVISION.toString()),
                documentDataDTO.getContentFields().get(CONTENT_FIELDS_MAP_ENUM.MARCA.toString()),
                documentDataDTO.getContentFields().get(CONTENT_FIELDS_MAP_ENUM.MODELO.toString()),
                documentDataDTO.getContentFields().get(CONTENT_FIELDS_MAP_ENUM.YEAR.toString()),
                documentDataDTO.getContentFields().get(CONTENT_FIELDS_MAP_ENUM.CHECK_YEAR.toString()),
                documentDataDTO.getContentFields().get(CONTENT_FIELDS_MAP_ENUM.CHASIS.toString()),
                documentDataDTO.getContentFields().get(CONTENT_FIELDS_MAP_ENUM.PROPIETARIO.toString()),
                documentDataDTO.getContentFields().get(CONTENT_FIELDS_MAP_ENUM.PLACA.toString()));

    }

    private void generate(PDDocument document, PDPage page,
                          PDType0Font customFont, PDType0Font customFontBold,
                          String outputPath, String nRevision,
                          String newMarca, String newModelo,
                          String year,String newCheckYear, String newChasis,
                          String newPropietario,
                          String newPlaca) throws IOException {

        try (PDPageContentStream contentStream = new PDPageContentStream(
                document, page,
                PDPageContentStream.AppendMode.APPEND,
                true, true)) {

            contentStream.setFont(customFont, 8);

            // Configurar transparencia para el fondo blanco que cubrirá el texto antiguo
            PDExtendedGraphicsState graphicsState = new PDExtendedGraphicsState();
            graphicsState.setNonStrokingAlphaConstant(1f);
            graphicsState.setStrokingAlphaConstant(1f);
            graphicsState.setAlphaSourceFlag(true);
            contentStream.setGraphicsStateParameters(graphicsState);

            // Sobrescribir los campos con fondo blanco primero
            //No. Revision
            paintBlank(contentStream, 74, 708, 68, 10);

            //Fecha revision
            paintBlank(contentStream, 93, 698, 60, 11);

            // 1. Marca
            paintBlank(contentStream, 181, 708, 190, 11);

            // 2. Modelo
            paintBlank(contentStream, 185, 695, 190, 11);

            // 2. Year
            paintBlank(contentStream, 185, 683, 130, 11);

            // 3. Chasis
            paintBlank(contentStream, 193, 662, 200, 12);

            // 4. Propietario
            paintBlank(contentStream, 150, 620, 200, 12);

            // 5. Placa
            paintBlank(contentStream, 467, 697, 88, 15);

            // 5. Placa QR
            paintBlank(contentStream, 100, 138, 400, 43);

            // Escribir los nuevos valores
            contentStream.setNonStrokingColor(0, 0, 0);

            // N. revision
            paintText(contentStream,75, 710, nRevision);

            // Fecha revision
            paintText(contentStream,94, 699, newCheckYear);

            // Marca
            paintText(contentStream,186, 710, newMarca);

            // Modelo
            paintText(contentStream,186, 699, newModelo);

            // Año
            paintText(contentStream,186, 686, year);

            // Chasis
            paintText(contentStream,193, 665, newChasis);

            // Propietario
            paintText(contentStream,152, 624, newPropietario);

            // Placa
            paintText(contentStream,calculateXByLength(newPlaca, 490), 701, newPlaca, customFontBold, 8);

            // AÑO-PLACA
            paintText(contentStream,106, 157, String.valueOf(LocalDate.now().getYear()), customFontBold, 25);

            // AÑO-PLACA
            paintText(contentStream,404, 157, String.valueOf(LocalDate.now().getYear()), customFontBold, 25);

            // PLACA-QR
            paintText(contentStream,calculateXByLength(newPlaca, 106), 138, newPlaca, customFont, 16);

            // PLACA-QR
            paintText(contentStream,calculateXByLength(newPlaca, 405), 138, newPlaca, customFont, 16);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Guardar el documento modificado
        document.save(outputPath);
        document.close();
    }

    private void paintBlank(PDPageContentStream contentStream, int x, int y, int width, int height) throws IOException {
        contentStream.setNonStrokingColor(255, 255, 255);
        contentStream.addRect(x, y, width, height);
        contentStream.fill();
    }

    private void paintText(PDPageContentStream contentStream, int x, int y, String text) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    private void paintText(PDPageContentStream contentStream, int x, int y, String text, PDType0Font customFont, int fontSize) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.setFont(customFont, fontSize);
        contentStream.showText(text);
        contentStream.endText();
    }

    private int calculateXByLength(String plate, int x) {
        return plate.length() <= 6? x + 3: plate.contains("I")? x + 2: x;
    }

}
