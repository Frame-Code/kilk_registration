package service.impl;

import UI.components.FileChooser;
import service.interfaces.ISaveFileService;

import java.io.File;

/**
 * @author Daniel Mora Cantillo
 */
public class SaveFileServiceImpl implements ISaveFileService {
    private FileChooser fileChooser;

    @Override
    public void openFileChooser() {
        fileChooser = new FileChooser();
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
