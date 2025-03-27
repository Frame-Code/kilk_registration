package UI.components;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Daniel Mora Cantillo
 */
public class FileChooser extends JFileChooser{
    private final int userSelection;

    public FileChooser() {
        super();
        setDialogTitle("Seleccione la ubicación donde se guardan los reportes");
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setAcceptAllFileFilterUsed(false);
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