package service;

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
public class PDFGenerator {
    private final File NORMAL_FONT = new File("src\\main\\resources\\fonts\\arialmt.ttf");
    private final File BOLD_FONT = new File("src\\main\\resources\\fonts\\ARIALMTBLACK.TTF");

    public void create(String inputPath, String outputPath, String nRevision,
                       String marca, String modelo,
                       String year, String chasis,
                       String propietario,
                       String placa) throws IOException {
        PDDocument document = PDDocument.load(new File(inputPath));

        // Asumimos que los campos están en la primera página
        PDPage page = document.getPage(0);

        PDType0Font customFont = PDType0Font.load(document, NORMAL_FONT);
        PDType0Font customFontBold = PDType0Font.load(document, BOLD_FONT);

        generate(document, page, customFont, customFontBold, outputPath, nRevision,
                marca, modelo, year, chasis, propietario, placa);
    }

    private void generate(PDDocument document, PDPage page,
                          PDType0Font customFont, PDType0Font customFontBold,
                          String outputPath, String nRevision,
                          String newMarca, String newModelo,
                          String year, String newChasis,
                          String newPropietario,
                          String newPlaca) throws IOException {

        try (PDPageContentStream contentStream = new PDPageContentStream(
                document, page,
                PDPageContentStream.AppendMode.APPEND,
                true, true)) {

            contentStream.setFont(customFont , 8);

            // Configurar transparencia para el fondo blanco que cubrirá el texto antiguo
            PDExtendedGraphicsState graphicsState = new PDExtendedGraphicsState();
            graphicsState.setNonStrokingAlphaConstant(1f);
            graphicsState.setStrokingAlphaConstant(1f);
            graphicsState.setAlphaSourceFlag(true);
            contentStream.setGraphicsStateParameters(graphicsState);

            // Sobrescribir los campos con fondo blanco primero
            //No. revision
            contentStream.setNonStrokingColor(1, 255, 255);
            contentStream.addRect(74, 708, 68, 10); // x, y, width, height
            contentStream.fill();

            // 1. Marca
            contentStream.setNonStrokingColor(1, 255, 255);
            contentStream.addRect(181, 708, 190, 11); // x, y, width, height
            contentStream.fill();

            // 2. Modelo
            contentStream.setNonStrokingColor(1, 255, 255);
            contentStream.addRect(185, 695, 190, 11);
            contentStream.fill();

            // 2. Year
            contentStream.setNonStrokingColor(1, 255, 255);
            contentStream.addRect(185, 683, 130, 11);
            contentStream.fill();

            // 3. Chasis
            contentStream.setNonStrokingColor(1, 255, 255);
            contentStream.addRect(193, 662, 200, 12);
            contentStream.fill();

            // 4. Propietario
            contentStream.setNonStrokingColor(1, 255, 255);
            contentStream.addRect(150, 620, 200, 12);
            contentStream.fill();

            // 5. Placa
            contentStream.setNonStrokingColor(1, 255, 255);
            contentStream.addRect(467, 697, 88, 15);
            contentStream.fill();

            // Escribir los nuevos valores
            contentStream.setNonStrokingColor(0, 0, 0);

            // Marca
            contentStream.beginText();
            contentStream.newLineAtOffset(75, 710); // x, y
            contentStream.showText(nRevision);
            contentStream.endText();

            // Marca
            contentStream.beginText();
            contentStream.newLineAtOffset(186, 709); // x, y
            contentStream.showText(newMarca);
            contentStream.endText();

            // Modelo
            contentStream.beginText();
            contentStream.newLineAtOffset(186, 698);
            contentStream.showText(newModelo);
            contentStream.endText();

            // Año
            contentStream.beginText();
            contentStream.newLineAtOffset(188, 686);
            contentStream.showText(year);
            contentStream.endText();

            // Chasis
            contentStream.beginText();
            contentStream.newLineAtOffset(193, 664);
            contentStream.showText(newChasis);
            contentStream.endText();

            // Propietario
            contentStream.beginText();
            contentStream.newLineAtOffset(152, 623);
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
