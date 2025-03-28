package UI.components;

import javax.swing.JFileChooser;

/**
 *
 * @author Daniel Mora Cantillo
 */
public class FileChooser extends JFileChooser{
    private final int userSelection;

    public FileChooser(String title, int typeFileChooser, boolean acceptAllFiles) {
        super();
        setDialogTitle(title);
        setFileSelectionMode(typeFileChooser);
        setAcceptAllFileFilterUsed(acceptAllFiles);
        this.userSelection = showSaveDialog(null);
    }

    public String filePath() {
        if(userSelection == APPROVE_OPTION) {
            return getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }

}