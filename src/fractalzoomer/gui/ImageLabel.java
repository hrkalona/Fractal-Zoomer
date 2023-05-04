package fractalzoomer.gui;

import javax.swing.*;
import java.awt.*;

public class ImageLabel extends JLabel {

    public ImageLabel() {
        super();
    }

    public ImageLabel(Icon image, int horizontalAlignment) {
        super(null, image, horizontalAlignment);
    }

    public ImageLabel(Icon image) {
        super(image);
    }

    @Override
    public void paintComponent(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        super.paintComponent(g);
    }
}
