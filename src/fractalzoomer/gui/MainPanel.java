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

package fractalzoomer.gui;

import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 *
 * @author hrkalona2
 */
public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1059117419848617111L;
	private MainWindow ptr;
   
    public MainPanel(MainWindow ptr) {
        this.ptr = ptr;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        Graphics2D g2d = ((Graphics2D)g);
        //if(ptr.getOrbit()) {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //}
        //else {
        //    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        //}

        if(!ptr.getFirstPaint()) {
            g.drawImage(new BufferedImage(ThreadDraw.FAST_JULIA_IMAGE_SIZE, ThreadDraw.FAST_JULIA_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB), ptr.getScrollPane().getHorizontalScrollBar().getValue(), ptr.getScrollPane().getVerticalScrollBar().getValue(), null);
            ptr.getScrollPane().getHorizontalScrollBar().setValue((int)(ptr.getScrollPane().getHorizontalScrollBar().getMaximum() / 2.0 - ptr.getScrollPane().getHorizontalScrollBar().getSize().getWidth() / 2.0));
            ptr.getScrollPane().getVerticalScrollBar().setValue((int)(ptr.getScrollPane().getVerticalScrollBar().getMaximum() / 2.0 - ptr.getScrollPane().getVerticalScrollBar().getSize().getHeight() / 2.0));
            ptr.setFirstPaint();
        }


       if(ptr.getWholeImageDone()) {
            g.drawImage(ptr.getImage(), 0, 0, null);
            
            if(ptr.getOrbit()) {
                ptr.drawOrbit((Graphics2D)g);
            }
                
            if(ptr.getBoundaries()) {
                ptr.drawBoundaries((Graphics2D)g, true);
            }
                
            if(ptr.getGrid()) {
                ptr.drawGrid((Graphics2D)g, true);
            }
                
            if(ptr.getZoomWindow()) {
               ptr.drawZoomWindow((Graphics2D)g);
            }      
       }
       else {
            if(!ptr.getColorCycling()) {
                g.drawImage(ptr.getLastUsed(), 0, 0, null);
                    
                if(ptr.getBoundaries() && ptr.getLastUsed() != null) {
                    ptr.drawBoundaries((Graphics2D)g, true);
                }
                    
                if(ptr.getGrid() && ptr.getLastUsed() != null) {
                    ptr.drawGrid((Graphics2D)g, true);
                }
                    
                if(ptr.getZoomWindow() && ptr.getLastUsed() != null) {
                   ptr.drawZoomWindow((Graphics2D)g);
                }
           }
       }
            
    }
  
}
