package controller;

import UI.PrincipalWindow;
import dto.VehicleDTO;
import lombok.RequiredArgsConstructor;
import service.interfaces.IPlateParserService;
import service.interfaces.IConsultPlateService;
import service.interfaces.IVehicleInfoParserService;

import javax.swing.*;
import java.net.CookieManager;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniel Mora Cantillo
 */
@RequiredArgsConstructor
public class PrincipalWindowController {
    private final static Logger LOG = Logger.getLogger(PrincipalWindowController.class.getName());
    private final PrincipalWindow principalFrm;
    private final IPlateParserService plateParser;
    private final IConsultPlateService consultPlate;
    private final IVehicleInfoParserService vehicleInfoParserService;

    public void addListeners(HttpClient client, CookieManager cookieManager) {
        principalFrm.getBtnGenerate().addActionListener(e -> {
            String value = principalFrm.getTxtArea().getText();
            if(value == null || value.isEmpty()) {
                JOptionPane.showMessageDialog(principalFrm,
                        "Escribe los valores de las placas para continuar!",
                        "Placas nos identificadas",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            List<String> licencesPlate = plateParser.separatePlates(value);
            Optional<String> responseHTML = consultPlate.findInfoVehicle(licencesPlate.get(0)); //Por revisar su dato de retorno
            if(responseHTML.isEmpty()) {
                JOptionPane.showMessageDialog(principalFrm,
                        "Error consultando las placas",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(responseHTML.get().contains("No se encontro ningun registro")) {
                LOG.log(Level.WARNING, "No se encontro registros");
                JOptionPane.showMessageDialog(principalFrm,
                        "No se encontro registros",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Optional<VehicleDTO> vehicle = vehicleInfoParserService.parseVehicle(responseHTML.get());
            System.out.println(vehicle.get());
        });

        principalFrm.getBtnClean().addActionListener(e -> principalFrm.getTxtArea().setText(""));
        principalFrm.getBtnClose().addActionListener(e -> principalFrm.close());
    }




}
