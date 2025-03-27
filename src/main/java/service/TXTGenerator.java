package service;

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
    public void generate(String content, String path) {
        try {
            File file = new File(path);
            if(!file.createNewFile()) {
                LOG.log(Level.WARNING, "File already exists ");
                return;
            }

            LOG.log(Level.INFO, "File created ".concat(file.getName()));
            FileWriter writer = new FileWriter(file);
            BufferedWriter buffer = new BufferedWriter(writer);
            buffer.write(content);
            buffer.close();
            LOG.log(Level.INFO, "File generated correctly ".concat(file.getName()));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error creating the file");
        }
    }
}
