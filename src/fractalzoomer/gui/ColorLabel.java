package fractalzoomer.gui;

import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class ColorLabel extends JLabel {
    private int arcWidth;
    private int arcHeight;

    public ColorLabel() {
        super();
        this.arcWidth = 5;
        this.arcHeight = 5;

        if(!MainWindow.useCustomLaf) {
            setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        }

        setOpaque(true);
    }

    public ColorLabel(int arcWidth, int arcHeight) {
        super();
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;

        if(!MainWindow.useCustomLaf) {
            setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        }
        setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {

        if(!MainWindow.useCustomLaf) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        else {
            g.setColor(Constants.bg_color);
            g.fillRect(0, 0, getWidth(), getHeight());

            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color c = getBackground();
            g.setColor(c);

            int r1 = c.getRed();
            int g1 = c.getGreen();
            int b1 = c.getBlue();

            int r2 = Constants.bg_color.getRed();
            int g2 = Constants.bg_color.getGreen();
            int b2 = Constants.bg_color.getBlue();

            int d1 = r1 - r2;
            int d2 = g1 - g2;
            int d3 = b1 - b2;

            if(d1 * d1 + d2 * d2 + d3 * d3 < 9) {
                g.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
                g.setColor(Color.LIGHT_GRAY);
                g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arcWidth, arcHeight);
            }
            else {
                g.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
            }
        }
    }
}
