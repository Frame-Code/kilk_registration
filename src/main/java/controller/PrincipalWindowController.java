package controller;

import UI.PrincipalWindow;

import dto.DocumentDataDTO;
import dto.VehicleDTO;

import lombok.RequiredArgsConstructor;

import service.interfaces.IDocumentCreatorService;
import service.interfaces.IMediatorPlateService;
import service.interfaces.ISaveFileService;

import javax.swing.*;

import java.net.CookieManager;
import java.net.http.HttpClient;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
    private final IDocumentCreatorService documentCreatorService;
    private final ISaveFileService saveFileService;

    public void addListeners(HttpClient client, CookieManager cookieManager) {
        principalFrm.getBtnGenerate().addActionListener(e -> {
            String value = principalFrm.getTxtArea().getText();
            if (value == null || value.isEmpty()) {
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

            List<Optional<VehicleDTO>> vehicleList = new ArrayList<>();

            List<String> licencesPlate = mediatorPlate.separatePlates(value);

            List<Optional<String>> responses = mediatorPlate.consultPlates(licencesPlate);

            if (responses.isEmpty()) {
                JOptionPane.showMessageDialog(principalFrm,
                        "Error consultando las placas",
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

            String platesWithNoveltiesPath = saveFileService.setFileName("Placas no encontradas.txt");
            String platesWithBadDatePath = saveFileService.setFileName("Placas sin renovacion.txt");
            if(platesWithNoveltiesPath == null || platesWithBadDatePath == null ){
                return;
            }
            var vehicleListFinal = mediatorPlate.verifyRenovationDate(vehicleList);

            documentCreatorService.createTXTReport(DocumentDataDTO.builder()
                            .outputPath(platesWithNoveltiesPath)
                            .rawContent(mediatorPlate.getPlatesWithNovelties())
                            .build());
            documentCreatorService.createTXTReport(DocumentDataDTO.builder()
                            .outputPath(platesWithBadDatePath)
                            .rawContent(mediatorPlate.getPlatesWithBeforeDate())
                            .build());
            AtomicInteger docNumber = new AtomicInteger();
            vehicleListFinal.forEach(auto -> {
                documentCreatorService.createPDFReport(DocumentDataDTO.builder()
                                .inputPath("C:\\Users\\Artist-Code\\Downloads\\Document 1 this.name.pdf")
                                .outputPath("C:\\Users\\Artist-Code\\Downloads\\documento_editado".
                                        concat(String.valueOf(docNumber.get())).concat(".pdf"))
                                .contentFields(Map.of(
                                        "nRevision", "000".concat(String.valueOf(new Random().nextInt(100) + 800)),
                                        "marca", auto.getMarca(),
                                        "modelo", auto.getModelo(),
                                        "year", "2004",
                                        "chasis", auto.getChasis(),
                                        "propietario", auto.getPropietario(),
                                        "placa", auto.getPlaca()
                                ))
                                .build());
                docNumber.getAndIncrement();
            });
            vehicleListFinal.forEach(System.out::println);
        });

        principalFrm.getBtnClean().addActionListener(e -> principalFrm.getTxtArea().setText(""));
        principalFrm.getBtnClose().addActionListener(e -> principalFrm.close());
    }

}
