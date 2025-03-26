package controller;

import UI.Principal;
import service.ILicensePlateService;

import javax.swing.*;

/**
 * @author Daniel Mora Cantillo
 */
public class PrincipalController {
    private final Principal principalFrm;
    private final ILicensePlateService licensePlateService;

    public PrincipalController(Principal principalFrm, ILicensePlateService licensePlateService) {
        this.principalFrm = principalFrm;
        this.licensePlateService = licensePlateService;
        addListeners();
    }

    private void addListeners() {
        principalFrm.getBtnGenerate().addActionListener(e -> {
            String value = principalFrm.getTxtArea().getText();
            if(value == null || value.isEmpty()) {
                JOptionPane.showMessageDialog(principalFrm,
                        "Escribe los valores de las placas para continuar!",
                        "Placas nos identificadas",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            licensePlateService.getLicensesPlate(value).forEach(System.out::println);
        });

        principalFrm.getBtnClean().addActionListener(e -> principalFrm.getTxtArea().setText(""));
        principalFrm.getBtnClose().addActionListener(e -> principalFrm.close());
    }




}
