package service;

import dto.DocumentDataDTO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Mora Cantillo
 */
public class TXTGenerator extends DocumentGenerator {
    private static final Logger LOG = Logger.getLogger(TXTGenerator.class.getName());

    @Override
    public void generate(DocumentDataDTO documentDataDTO) throws IOException {
        File file = new File(documentDataDTO.getOutputPath());
        if (!file.createNewFile()) {
            LOG.log(Level.WARNING, "File already exists ");
            return;
        }

        LOG.log(Level.INFO, "File created ".concat(file.getName()));
        FileWriter writer = new FileWriter(file);
        BufferedWriter buffer = new BufferedWriter(writer);
        buffer.write(documentDataDTO.getRawContent());
        buffer.close();
        LOG.log(Level.INFO, "File generated correctly ".concat(file.getName()));
    }
}
