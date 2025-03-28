package service;

import dto.DocumentDataDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;

import java.io.File;
import java.io.IOException;

/**
 * @author Daniel Mora Cantillo
 */
public class PDFGenerator extends DocumentGenerator {
    private final File NORMAL_FONT = new File("src\\main\\resources\\fonts\\arialmt.ttf");
    private final File BOLD_FONT = new File("src\\main\\resources\\fonts\\ARIALMTBLACK.TTF");

    @Override
    public void generate(DocumentDataDTO documentDataDTO) throws IOException {
        PDDocument document = PDDocument.load(new File(documentDataDTO.getInputPath()));

        // Asumimos que los campos están en la primera página
        PDPage page = document.getPage(0);

        PDType0Font customFont = PDType0Font.load(document, NORMAL_FONT);
        PDType0Font customFontBold = PDType0Font.load(document, BOLD_FONT);

        generate(document, page, customFont, customFontBold, documentDataDTO.getOutputPath(),
                documentDataDTO.getContentFields().get("nRevision"),
                documentDataDTO.getContentFields().get("marca"),
                documentDataDTO.getContentFields().get("modelo"),
                documentDataDTO.getContentFields().get("year"),
                documentDataDTO.getContentFields().get("newCheckYear"),
                documentDataDTO.getContentFields().get("chasis"),
                documentDataDTO.getContentFields().get("propietario"),
                documentDataDTO.getContentFields().get("placa"));

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
            //No. revision
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(74, 708, 68, 10); // x, y, width, height
            contentStream.fill();

            //Fecha revision
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(93, 698, 60, 11); // x, y, width, height
            contentStream.fill();

            // 1. Marca
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(181, 708, 190, 11); // x, y, width, height
            contentStream.fill();

            // 2. Modelo
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(185, 695, 190, 11);
            contentStream.fill();

            // 2. Year
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(185, 683, 130, 11);
            contentStream.fill();

            // 3. Chasis
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(193, 662, 200, 12);
            contentStream.fill();

            // 4. Propietario
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(150, 620, 200, 12);
            contentStream.fill();

            // 5. Placa
            contentStream.setNonStrokingColor(255, 255, 255);
            contentStream.addRect(467, 697, 88, 15);
            contentStream.fill();

            // Escribir los nuevos valores
            contentStream.setNonStrokingColor(0, 0, 0);

            // N. revision
            contentStream.beginText();
            contentStream.newLineAtOffset(75, 710); // x, y
            contentStream.showText(nRevision);
            contentStream.endText();

            // Fecha revision
            contentStream.beginText();
            contentStream.newLineAtOffset(94, 699); // x, y
            contentStream.showText(newCheckYear);
            contentStream.endText();

            // Marca
            contentStream.beginText();
            contentStream.newLineAtOffset(186, 710); // x, y
            contentStream.showText(newMarca);
            contentStream.endText();

            // Modelo
            contentStream.beginText();
            contentStream.newLineAtOffset(186, 699);
            contentStream.showText(newModelo);
            contentStream.endText();

            // Año
            contentStream.beginText();
            contentStream.newLineAtOffset(186, 686);
            contentStream.showText(year);
            contentStream.endText();

            // Chasis
            contentStream.beginText();
            contentStream.newLineAtOffset(193, 665);
            contentStream.showText(newChasis);
            contentStream.endText();

            // Propietario
            contentStream.beginText();
            contentStream.newLineAtOffset(152, 624);
            contentStream.showText(newPropietario);
            contentStream.endText();

            // Placa
            contentStream.beginText();
            contentStream.newLineAtOffset(490, 701);
            contentStream.setFont(customFontBold, 8);
            contentStream.showText(newPlaca);
            contentStream.endText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Guardar el documento modificado
        document.save(outputPath);
        document.close();
    }

}
