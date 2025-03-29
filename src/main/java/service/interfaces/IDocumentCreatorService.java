package service.interfaces;

import dto.DocumentDataDTO;
import dto.ResultTaskDTO;

import java.io.IOException;

/**
 * @author Daniel Mora Cantillo
 */
public interface IDocumentCreatorService {
    ResultTaskDTO createPDFReport(DocumentDataDTO documentDataDTO);
    ResultTaskDTO createTXTReport(DocumentDataDTO documentDataDTO);
}
