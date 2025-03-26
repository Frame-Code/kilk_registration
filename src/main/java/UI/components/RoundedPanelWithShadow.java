package UI.components;

/**
 *
 * @author Daniel Mora Cantillo
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RoundedPanelWithShadow extends JPanel {
    private final int cornerRadius;
    private final Color shadowColor;
    private final int shadowSize;

        public RoundedPanelWithShadow(int cornerRadius, Color shadowColor, int shadowSize) {
        this.cornerRadius = cornerRadius;
        this.shadowColor = shadowColor;
        this.shadowSize = shadowSize;
        setOpaque(false); // Hace que el fondo sea transparente para permitir sombras suaves
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Habilitar suavizado para sombras más realistas
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Ajustar la sombra para que siga mejor los bordes redondeados
        int shadowOffset = shadowSize / 3; // Reduce la separación de la sombra
        for (int i = shadowSize; i > 0; i--) {
            float alpha = (float) i / (shadowSize + 5); // Ajuste fino del degradado de sombra
            g2.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), (int) (alpha * 120)));
            g2.fillRoundRect(shadowOffset - i, shadowOffset - i, getWidth() - (shadowOffset - i) * 2, getHeight() - (shadowOffset - i) * 2, cornerRadius + i, cornerRadius + i);
        }

        // Dibujar el panel con bordes redondeados sin separación extra
        g2.setColor(getBackground());
        g2.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset * 2, getHeight() - shadowOffset * 2, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g); // Renderiza otros componentes hijos si los hay
    }
}
