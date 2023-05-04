package fractalzoomer.gui;

import javax.swing.*;
import java.awt.*;

public class ImageRadioButtonMenuItem extends JRadioButtonMenuItem {

    public ImageRadioButtonMenuItem(String text, Icon icon) {
        super(text, icon, false);
    }

    @Override
    public void paintComponent(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        super.paintComponent(g);
    }
}
