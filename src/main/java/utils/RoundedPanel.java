package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
    
    private int arcWidth = 20;  // Ancho del radio del borde
    private int arcHeight = 20; // Alto del radio del borde

    public RoundedPanel() {
        setOpaque(false);  // Asegúrate de que el panel sea transparente
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Configura la suavidad de los bordes
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Establece el color de fondo del borde redondeado
        g2d.setColor(new Color(0, 122, 255));
        
        // Dibuja el borde redondeado
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));
        
        g2d.setColor(Color.BLACK);  // Color del borde
        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));
    }

    // Método para ajustar el tamaño de los bordes
    public void setArcSize(int width, int height) {
        this.arcWidth = width;
        this.arcHeight = height;
        repaint();  // Redibuja el JPanel con los nuevos bordes redondeados
    }
}
