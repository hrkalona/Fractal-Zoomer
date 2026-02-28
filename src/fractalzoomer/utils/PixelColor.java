
package fractalzoomer.utils;

import fractalzoomer.gui.RoundedPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author hrkalona2
 */
public class PixelColor implements Runnable {
    private RoundedPanel ptr;
    private JLabel ptr2;
    private volatile boolean running;
    
    public PixelColor(RoundedPanel ptr, JLabel ptr2) {
        this.ptr = ptr;
        this.ptr2 = ptr2;
        running = true;
    }

    public void terminate() {
        running = false;
    }
    
    @Override
    public void run() {
        try {
            Robot robot = new Robot();

            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();

            Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

            double scaleX = ((double)width) / screenDimension.width;
            double scaleY = ((double)height) / screenDimension.height;

            if ("true".equals(System.getProperty("sun.java2d.dpiaware"))) {
                scaleX = 1;
                scaleY = 1;
            }

            while(running) {
                try {
                    int x = (int)(MouseInfo.getPointerInfo().getLocation().getX() * scaleX);
                    int y = (int)(MouseInfo.getPointerInfo().getLocation().getY() * scaleY);
                    Color color;
                    if (x < 0 || x >= width || y < 0 || y >= height) {
                        color = Color.BLACK;
                    } else {
                        color = robot.getPixelColor(x, y);
                    }

                    ptr.setBackground(color);
                    ptr2.setText("R: " + String.format("%3d", color.getRed()) + " G: " + String.format("%3d", color.getGreen()) + " B: " + String.format("%3d", color.getBlue()));
                    Thread.sleep(20);
                } catch (Exception ex) {}
            }

        } catch(Exception ex) {}
    }

}
