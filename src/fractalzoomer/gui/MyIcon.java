package fractalzoomer.gui;

import javax.swing.*;
import java.awt.*;

public class MyIcon implements Icon {
    Icon wrappedIcon;

    public MyIcon(Icon icon) {
        wrappedIcon = icon;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(wrappedIcon != null) {
            wrappedIcon.paintIcon(c, g, x, y);
        }
    }

    @Override
    public int getIconWidth() {
        if(wrappedIcon != null) {
            return wrappedIcon.getIconWidth();
        }
        return 0;
    }

    @Override
    public int getIconHeight() {
        if(wrappedIcon != null) {
            return wrappedIcon.getIconHeight();
        }
        return 0;
    }
}
