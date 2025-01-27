package demo_package;

import javax.swing.*;
import java.awt.*;

public class RoundedBorder extends JButton {

    public RoundedBorder(String text) {
        super(text);
        setFocusPainted(false); // Removes focus outline
        setContentAreaFilled(false); // Prevents default rectangular background painting
        setBorderPainted(false); // Prevents default rectangular border painting
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the button color based on its state (hover, pressed, etc.)
        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker()); // Darker shade when pressed
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter()); // Brighter shade when hovered
        } else {
            g2.setColor(getBackground());
        }

        // Draw the rounded rectangle for the button background
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Adjust the arc width and height

        // Paint the button's label (text)
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Set the border color explicitly to black
        g2.setColor(Color.BLACK); // Use black color for the border

        // Draw the rounded rectangle for the border
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Adjust the arc width and height
    }
}
