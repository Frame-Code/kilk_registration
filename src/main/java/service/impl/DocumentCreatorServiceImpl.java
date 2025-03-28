package service.impl;

import dto.DocumentDataDTO;
import lombok.RequiredArgsConstructor;
import service.DocumentGenerator;
import service.interfaces.IDocumentCreatorService;

import java.io.IOException;
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
    public void createPDFReport(DocumentDataDTO documentDataDTO) {
        if(documentDataDTO.isDocumentTxt()) {
            LOG.log(Level.WARNING, "The data is not available to convert a document PDF");
            return;
        }

        var executor = Executors.newFixedThreadPool(5);
        Runnable task = () -> {
            try {
                generatorPDF.generate(documentDataDTO);
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Exception error: can't not generate the pdf, error: " + e.getMessage());
                throw new RuntimeException(e);
            }
        };

        try {
            executor.submit(task);
            return;
        } catch (CancellationException e) {
            LOG.log(Level.WARNING, "Interruption exception occur ".concat(e.getMessage()));
            return;
        } finally {
            executor.shutdown();
        }
    }

    @Override
    public void createTXTReport(DocumentDataDTO documentDataDTO) {
        if(!documentDataDTO.isDocumentTxt()) {
            LOG.log(Level.WARNING, "The data is not available to convert a document TXT");
            return;
        }
        try {
            generatorTXT.generate(documentDataDTO);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Exception error: can't not generate the txt, error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
