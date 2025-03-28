package service.impl;

import UI.components.FileChooser;
import service.interfaces.ISaveFileService;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * @author Daniel Mora Cantillo
 */
public class InputFileServiceImpl implements ISaveFileService {
    private FileChooser fileChooser;

    @Override
    public void openFileChooser() {
        fileChooser = new FileChooser("Selecciona la plantilla, un archivo pdf", JFileChooser.FILES_ONLY, false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf"));
    }

    @Override
    public String getFilePath() {
        return fileChooser.filePath();
    }

    @Override
    public String setFileName(String fileName) {
        return File.separator + fileName;
    }
}
