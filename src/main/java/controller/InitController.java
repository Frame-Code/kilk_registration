package controller;

import UI.Init;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import service.IAuthService;

/**
 *
 * @author Daniel Mora Cantillo
 */
@Builder
@AllArgsConstructor
public class InitController {
    private static final Logger LOG = Logger.getLogger(InitController.class.getName());
    
    private final Init frmInit;
    private final IAuthService authService;

    public void load() {
        Optional<String> token = authService.getToken();
        if (token.isEmpty()) {
            LOG.log(Level.WARNING, "Can not get the Token");
            return;
        }
        
        Optional<String> response = authService.log_in(token.get());
        if(response.isEmpty()) {
            LOG.log(Level.WARNING, "Cant not get response");
            return;
        }
        LOG.log(Level.INFO, "Login successfully");

    }

}
