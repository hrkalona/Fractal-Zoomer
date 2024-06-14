/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.utils;

import fractalzoomer.gui.RoundedPanel;
import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

        double scaleX = ((double)width) / screenDimension.width;
        double scaleY = ((double)height) / screenDimension.height;

        int v = CommonFunctions.getJavaVersion();

        if(v > 8) {
            scaleX = 1;
            scaleY = 1;
        }



        try {
            Robot robot = new Robot();
            //Rectangle screenRect = new Rectangle(width, height);

            while(running) {
                /*long time = System.currentTimeMillis();
                while(System.currentTimeMillis() - time < 20) {
                    yield();
                }*/

                
                Color color = robot.getPixelColor((int)(MouseInfo.getPointerInfo().getLocation().getX() * scaleX), (int)(MouseInfo.getPointerInfo().getLocation().getY() * scaleY));


//                BufferedImage capture = robot.createScreenCapture(screenRect);
//
//                Color color = new Color(capture.getRGB((int)(MouseInfo.getPointerInfo().getLocation().getX() * scaleX), (int)(MouseInfo.getPointerInfo().getLocation().getY() * scaleY)));
                ptr.setBackground(color);
                ptr2.setText("R: " + String.format("%3d", color.getRed()) + " G: " + String.format("%3d", color.getGreen()) + " B: " + String.format("%3d", color.getBlue()));

                Thread.sleep(20);
            }

        }
        catch(AWTException ex) {
            running = false;
        }
        catch(InterruptedException ex2) {
            running = false;
        }
    }

}
