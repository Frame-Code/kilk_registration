package service.impl;

import UI.components.FileChooser;
import service.interfaces.ISaveFileService;

import javax.swing.JFileChooser;
import java.io.File;

/**
 * @author Daniel Mora Cantillo
 */
public class SaveFileServiceImpl implements ISaveFileService {
    private FileChooser fileChooser;

    @Override
    public void openFileChooser() {
        fileChooser = new FileChooser("Seleccione la ubicación donde se guardan los reportes", JFileChooser.DIRECTORIES_ONLY, false);
    }

    @Override
    public String getFilePath() {
        return fileChooser.filePath();
    }

    @Override
    public String setFileName(String fileName) {
        return fileChooser.filePath().concat(File.separator + fileName);
    }
}
