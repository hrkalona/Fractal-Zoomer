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

import fractalzoomer.core.TaskDraw;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


/**
 *
 * @author hrkalona2
 */
public class MainPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1059117419848617111L;
	private MainWindow ptr;
    private Timer timer;

    public static int REPAINT_SLEEP_TIME = 24;
   
    public MainPanel(MainWindow ptr) {
        this.ptr = ptr;
    }

    public void setTimer(boolean quickDrawAndRefinement, TaskDraw[][] drawThreads) {
        if(timer == null) {
            timer = new Timer(REPAINT_SLEEP_TIME, this);
            timer.start();
        }
        else {
            timer.stop();
            timer = null;
            timer = new Timer(REPAINT_SLEEP_TIME, this);
        }

        ptr.setWholeImageDone(!quickDrawAndRefinement);
        if(!quickDrawAndRefinement) {
            boolean isJulia = drawThreads[0][0].isJulia();
            boolean isJuliaMap = drawThreads[0][0].isJuliaMap();
            boolean isDomainColoring = drawThreads[0][0].isDomainColoring();
            boolean isNonJulia = drawThreads[0][0].isNonJulia();

            if(isJulia || isNonJulia || isJuliaMap || isDomainColoring) {
                ptr.reloadTitle();
                TaskDraw.updateMode(ptr, false, isJulia, isJuliaMap, isDomainColoring);
            }
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void stopTimer() {
        if(timer != null) {
            timer.stop();
            timer = null;
        }
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
            g.drawImage(new BufferedImage(TaskDraw.FAST_JULIA_IMAGE_SIZE, TaskDraw.FAST_JULIA_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB), ptr.getScrollPane().getHorizontalScrollBar().getValue(), ptr.getScrollPane().getVerticalScrollBar().getValue(), null);
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

    public Color getColor(int x, int y) {
        try {
            if (ptr.getWholeImageDone()) {
                return new Color(ptr.getImage().getRGB(x, y));
            } else {
                return new Color(ptr.getLastUsed().getRGB(x, y));
            }
        }
        catch (Exception ex) {
            return new Color(0);
        }
    }

    public String getIterationData(int x, int y, int image_size, boolean domain_coloring, boolean d3) {

        if(domain_coloring || d3) {
            return "N/A";
        }

        try {
            if (TaskDraw.DONE) {
                return "" + TaskDraw.image_iterations[y * image_size + x];
            } else {
                return "N/A";
            }
        }
        catch (Exception ex) {
            return "N/A";
        }
    }
  
}
