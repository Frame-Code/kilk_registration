package service.impl;

import dto.DocumentDataDTO;
import dto.ResultTaskDTO;
import dto.VehicleDTO;
import lombok.RequiredArgsConstructor;
import service.DocumentGenerator;
import service.interfaces.IDocumentCreatorService;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class DocumentCreatorServiceImpl implements IDocumentCreatorService {
    private static final Logger LOG = Logger.getLogger(DocumentCreatorServiceImpl.class.getName());
    private final DocumentGenerator generatorTXT;
    private final DocumentGenerator generatorPDF;

    @Override
    public ResultTaskDTO createPDFReport(DocumentDataDTO documentDataDTO) {
        if(documentDataDTO.isDocumentTxt()) {
            LOG.log(Level.WARNING, "The data got can't be parse to a PDF");
            return ResultTaskDTO.builder()
                    .isOk(false)
                    .errorMessage("La información obtenida no puede ser convertida a PDF")
                    .build();
        }

        var executor = Executors.newFixedThreadPool(5);
        Callable<ResultTaskDTO> task = () -> {
            try {
                generatorPDF.generate(documentDataDTO);
                LOG.log(Level.INFO, "Report PDF generated correctly in path: " + documentDataDTO.getOutputPath());
                return ResultTaskDTO.builder()
                        .isOk(true)
                        .build();
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "IO Exception creating the report pdf with output path: " + documentDataDTO.getOutputPath() + " Error message: " + e.getMessage());
                return ResultTaskDTO.builder()
                        .isOk(false)
                        .errorMessage("Error creating the PDF report, error message: " + e.getMessage())
                        .build();
            }

        };

        Future<ResultTaskDTO> resultFuture = executor.submit(task);

        try {
            resultFuture.get();
            executor.shutdown();
            executor.shutdownNow();
            return resultFuture.get();
        } catch (CancellationException | ExecutionException | InterruptedException e) {
            executor.shutdown();
            Thread.currentThread().interrupt();
            LOG.log(Level.WARNING, "Interruption exception occur ".concat(e.getMessage()));
            return ResultTaskDTO.builder()
                    .isOk(false)
                    .errorMessage("Error, task interrupted")
                    .build();
        }  finally {
            executor.shutdown();
        }
    }

    @Override
    public ResultTaskDTO createTXTReport(DocumentDataDTO documentDataDTO) {
        if(!documentDataDTO.isDocumentTxt()) {
            LOG.log(Level.WARNING, "The data is not available to convert a document TXT");
            return ResultTaskDTO.builder()
                    .isOk(false)
                    .errorMessage("Error, no es posible convertir la información obtenido en un TXT")
                    .build();
        }
        try {
            generatorTXT.generate(documentDataDTO);
            LOG.log(Level.INFO, "Report TXT generated correctly");
            return ResultTaskDTO.builder()
                    .isOk(true)
                    .build();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "IO Exception creating the report txt with output path: ", documentDataDTO.getOutputPath());
            return ResultTaskDTO.builder()
                    .isOk(false)
                    .errorMessage("\"Error, task interrupted")
                    .build();
        }


    }
}
