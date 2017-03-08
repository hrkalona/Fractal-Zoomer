/*
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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

import fractalzoomer.app_updater.AppUpdater;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona2
 */
public class SplashThread extends Thread {
    private BufferedImage image;
    private SplashLabel l1;
    private int skip_end;
    private int version;
    
    public SplashThread(BufferedImage image, SplashLabel l1, int version, int skip_end) {
        
        this.image = image;
        this.l1 = l1;
        this.skip_end = skip_end;
        this.version = version;
        
    }
    
    @Override
    public void run() {
        
        int width = l1.getCustomWidth();
        int height = l1.getCustomHeight();
        for(int i = 0; i + width + skip_end < image.getWidth(); i+= 1) {

            l1.setImage(image.getSubimage(i, 0, width, height));
            l1.repaint();
            try {
                Thread.sleep(3);
            }
            catch(InterruptedException ex) {
                
            }
        }
    
        
        l1.drawText("version " + AppUpdater.convertVersion(version), width - 90, 50, new Color(27, 32, 99), 13);
        l1.drawText("Fractal Zoomer", width - 180, 30, Color.BLACK, 26);
        l1.repaint();
            
        try {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex) {
                
        }

    }
    
    
    
}
