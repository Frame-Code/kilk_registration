package service.interfaces;


/**
 * @author Daniel Mora Cantillo
 */
public interface ISaveFileService {
    void openFileChooser();
    String getFilePath();
    String setFileName(String fileName);
}
