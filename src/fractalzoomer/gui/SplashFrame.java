/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
package fractalzoomer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author hrkalona2
 */
public class SplashFrame extends JFrame {
    private SplashThread thread;
    

    public SplashFrame(int version) {

        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        setSize(400, 260);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BorderLayout());
        
        BufferedImage image = convertToBufferedImage(getIcon("/fractalzoomer/icons/splash.png").getImage());
    
        SplashLabel l1 = new SplashLabel(400, 260);

        add(l1, BorderLayout.PAGE_START); 
        
        thread = new SplashThread(image, l1, version, 155);
        thread.start();
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));
        
    }
    
    public boolean isAnimating() {
        
        return thread.isAlive();
        
    }
    
    private BufferedImage convertToBufferedImage(Image image) {
        BufferedImage newImage = new BufferedImage(
            image.getWidth(null), image.getHeight(null),
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
}
