package UI.components;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.Font;
import java.awt.BorderLayout;
import java.util.Objects;


/**
 * @author Daniel Mora Cantillo
 */
public class LoadingDialog extends JFrame {

    public LoadingDialog(String titleDialog, String titleLabel) {
        super(titleDialog);
        setLayout(new BorderLayout());
        setSize(300, 100);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(400,100);
        ImageIcon icono = new ImageIcon(Objects.requireNonNull(LoadingDialog.class.getResource("/images/applicationIcon.png")));
        setIconImage(icono.getImage());
        addComponents(titleLabel);
    }

    public void open() {setVisible(true);}
    public void close() {
        setVisible(false);
        dispose();
    }

    private void addComponents(String titleLabel) {
        JLabel loadingLabel = new JLabel(titleLabel, JLabel.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 15));
        add(loadingLabel, BorderLayout.CENTER);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        add(progressBar, BorderLayout.SOUTH);
    }
}
