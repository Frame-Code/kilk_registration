package controller;

import UI.InitWindow;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import service.interfaces.IAuthService;

import javax.swing.*;

/**
 *
 * @author Daniel Mora Cantillo
 */
@Builder
@AllArgsConstructor
public class InitWindowController {
    private static final Logger LOG = Logger.getLogger(InitWindowController.class.getName());
    
    private final InitWindow frmInitWindow;
    private final IAuthService authService;

    public boolean load() {
        Optional<String> token = authService.getToken();
        if (token.isEmpty()) {
            LOG.log(Level.WARNING, "Can't get the Token");
            JOptionPane.showMessageDialog(frmInitWindow,
                    "Error when login in",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
        
        Optional<String> response = authService.log_in(token.get());
        if(response.isEmpty()) {
            LOG.log(Level.WARNING, "Can't get response");
            JOptionPane.showMessageDialog(frmInitWindow,
                    "Error on the Log-in, possible bad credentials",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        LOG.log(Level.INFO, "Login successfully");
        return  true;

    }

}
