package controller;

import UI.PrincipalWindow;

import UI.components.LoadingDialog;
import dto.VehicleDTO;

import lombok.RequiredArgsConstructor;

import service.interfaces.IDocumentCreatorService;
import service.interfaces.IMediatorPlateService;
import service.interfaces.ISaveFileService;

import javax.swing.*;
import java.util.ArrayList;
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
    private final IMediatorPlateService mediatorPlate;
    private final ISaveFileService saveFileService;
    private final ISaveFileService inputFileService;
    private final LoadingDialog loadingDialog = new LoadingDialog("Loading", "Generating reports (don´t close this window)");

    public void addListeners() {
        principalFrm.getBtnClean().addActionListener(e -> principalFrm.getTxtArea().setText(""));
        principalFrm.getBtnClose().addActionListener(e -> principalFrm.close());

        principalFrm.getBtnGenerate().addActionListener(e -> {

            String platesUI = principalFrm.getTxtArea().getText();
            if (platesUI == null || platesUI.isEmpty()) {
                JOptionPane.showMessageDialog(principalFrm,
                        "Escribe los valores de las placas para continuar!",
                        "Placas nos identificadas",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            saveFileService.openFileChooser();
            if(saveFileService.getFilePath() == null) {
                return;
            }

            inputFileService.openFileChooser();
            if(inputFileService.getFilePath() == null) {
                return;
            }

            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    loadingDialog.open();
                    principalFrm.setEnabled(false);
                    executeTasks(platesUI);
                    return null;
                }

                @Override
                protected void done() {
                    loadingDialog.close();
                    principalFrm.setEnabled(true);
                    principalFrm.getTxtArea().setText("");
                    JOptionPane.showMessageDialog(principalFrm,
                            "Reports generated correctly",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            };
            worker.execute();
        });

    }


    private void executeTasks(String platesUI) {
        List<Optional<VehicleDTO>> vehicleList = new ArrayList<>();

        List<String> licencesPlate = mediatorPlate.separatePlates(platesUI);

        List<Optional<String>> responses = mediatorPlate.consultPlates(licencesPlate);

        if (responses.isEmpty()) {
            JOptionPane.showMessageDialog(principalFrm,
                    "Error al consultar las placas en el servidor",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        responses.forEach(opt -> {
            if (opt.isEmpty()) {
                LOG.log(Level.WARNING, "Request without information");
                return;
            }

            if (responses.contains("No se encontro ningun registro")) {
                LOG.log(Level.WARNING, "No se encontro registros");
                JOptionPane.showMessageDialog(principalFrm,
                        "No se encontro registros",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            vehicleList.add(mediatorPlate.parseVehicleFromHTML(opt.get()));
        });

        var vehicleListFinal = mediatorPlate.verifyRenovationDate(vehicleList);
        if(vehicleListFinal.isEmpty()) {
            LOG.log(Level.WARNING, "Error don't exist vehicles to generate the report");
            JOptionPane.showMessageDialog(principalFrm,
                    "No se han encontrado vehicles para generar el reporte",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            mediatorPlate.exportFiles(vehicleListFinal, saveFileService, inputFileService.getFilePath());
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error generating reports");
            JOptionPane.showMessageDialog(principalFrm,
                    "Error creando reportes PDF y TXT",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
