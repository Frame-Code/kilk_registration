package service.interfaces;

import dto.DocumentDataDTO;

/**
 * @author Daniel Mora Cantillo
 */
public interface IDocumentCreatorService {
    void createPDFReport(DocumentDataDTO documentDataDTO);
    void createTXTReport(DocumentDataDTO documentDataDTO);
}
