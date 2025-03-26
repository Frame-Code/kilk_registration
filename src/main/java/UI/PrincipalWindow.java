package UI;

import UI.components.RoundedPanelWithShadow;
import com.formdev.flatlaf.FlatDarkLaf;
import lombok.Getter;

import java.awt.Color;

/**
 *
 * @author Daniel Mora Cantillo
 */
public class PrincipalWindow extends javax.swing.JFrame {

    public PrincipalWindow() {
        FlatDarkLaf.setup();
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Generate report...");
    }

    public void open() {
        setVisible(true);
    }
    public void close(){
        setVisible(false);
        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        contentPane = new javax.swing.JPanel();
        pnlPrincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        jPanel2 = new RoundedPanelWithShadow(25, new Color(94, 93, 98, 100), 3);
        jLabel3 = new javax.swing.JLabel();
        pnlPrincipal1 = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        btnGenerate = new javax.swing.JButton();
        btnClean = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        contentPane.setBackground(new java.awt.Color(32, 28, 28));

        pnlPrincipal.setBackground(new java.awt.Color(32, 28, 28));
        pnlPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Escribe aquí las matriculas separadas por coma!", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        txtArea.setBackground(new java.awt.Color(42, 39, 39));
        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        txtArea.setLineWrap(true);
        txtArea.setRows(5);
        txtArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtArea);

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(32, 28, 28));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("REPORT GENERATOR");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );

        pnlPrincipal1.setBackground(new java.awt.Color(32, 28, 28));
        pnlPrincipal1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Actions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        btnClose.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("Close");

        btnGenerate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGenerate.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerate.setText("Generate");

        btnClean.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnClean.setForeground(new java.awt.Color(255, 255, 255));
        btnClean.setText("Clean");

        javax.swing.GroupLayout pnlPrincipal1Layout = new javax.swing.GroupLayout(pnlPrincipal1);
        pnlPrincipal1.setLayout(pnlPrincipal1Layout);
        pnlPrincipal1Layout.setHorizontalGroup(
            pnlPrincipal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipal1Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(btnClean, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );
        pnlPrincipal1Layout.setVerticalGroup(
            pnlPrincipal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipal1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPrincipal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClean, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout contentPaneLayout = new javax.swing.GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPrincipal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(pnlPrincipal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    @Getter
    private javax.swing.JButton btnClean;
    @Getter
    private javax.swing.JButton btnClose;
    @Getter
    private javax.swing.JButton btnGenerate;
    private javax.swing.JPanel contentPane;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    @Getter
    private javax.swing.JTextArea txtArea;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlPrincipal1;
    // End of variables declaration//GEN-END:variables
}
