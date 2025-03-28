package service;

import dto.DocumentDataDTO;

import java.io.IOException;

/**
 * @author Daniel Mora Cantillo
 */
public abstract class DocumentGenerator {
    public abstract void generate(DocumentDataDTO documentDataDTO) throws IOException;
}
