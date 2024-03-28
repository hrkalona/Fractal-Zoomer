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

import fractalzoomer.core.TaskRender;
import fractalzoomer.main.MainWindow;
import fractalzoomer.utils.SelectionRectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.function.Predicate;
import java.util.stream.IntStream;


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

    public void setTimer(boolean quickRenderAndRefinement, TaskRender[][] drawThreads) {
        if(timer == null) {
            timer = new Timer(REPAINT_SLEEP_TIME, this);
            timer.start();
        }
        else {
            timer.stop();
            timer = null;
            timer = new Timer(REPAINT_SLEEP_TIME, this);
        }

        ptr.setWholeImageDone(!quickRenderAndRefinement);
        if(!quickRenderAndRefinement) {
            boolean isJulia = drawThreads[0][0].isJulia();
            boolean isJuliaMap = drawThreads[0][0].isJuliaMap();
            boolean isDomainColoring = drawThreads[0][0].isDomainColoring();
            boolean isNonJulia = drawThreads[0][0].isNonJulia();

            if(isJulia || isNonJulia || isJuliaMap || isDomainColoring) {
                ptr.reloadTitle();
                TaskRender.updateMode(ptr, false, isJulia, isJuliaMap, isDomainColoring);
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

    private void handlePreviousImage(Graphics g) {
        if(MainWindow.ZOOM_TO_THE_SELECTED_AREA) {
            BufferedImage lastUsed = ptr.getLastUsed();
            final SelectionRectangle sr = ptr.getSelectionRectangle();
            boolean showRectangle = sr.isVisible() && (ptr.canSelectPointInstantly() || TaskRender.DONE);

            if(lastUsed != null){
                if (showRectangle) {
                    if (sr.isSelectionValid() && MainWindow.MASK_IMAGE_OUTSIDE_WINDOW > 0) {
                        maskImageSlow(lastUsed, g, point -> !sr.isInImageChangeArea(point) || !sr.isInRectangleArea(point));
                    } else {
                        g.drawImage(lastUsed, 0, 0, null);
                    }
                } else {
                    g.drawImage(lastUsed, 0, 0, null);
                }
            }

            if(ptr.getBoundaries() && lastUsed != null) {
                ptr.drawBoundaries((Graphics2D)g, true);
            }

            if(ptr.getGrid() && lastUsed != null) {
                ptr.drawGrid((Graphics2D)g, true);
            }

            if(showRectangle && sr.isSelectionValid() && lastUsed != null) {
                sr.draw((Graphics2D) g);
            }
        }
        else {
            BufferedImage lastUsed = ptr.getLastUsed();
            Point p = null;

            boolean zw = ptr.getZoomWindow();
            if(zw) {
                try {
                    p = getMousePosition();
                }
                catch (Exception ex) {
                }
            }

            if(lastUsed != null) {
                if (zw && MainWindow.MASK_IMAGE_OUTSIDE_WINDOW > 0) {
                    Rectangle2D.Double rect = ptr.getZoomWindowRectangle(p);
                    maskImageSlow(lastUsed, g, point -> rect != null && !rect.contains(point));
                } else {
                    g.drawImage(lastUsed, 0, 0, null);
                }
            }

            if(ptr.getBoundaries() && lastUsed != null) {
                ptr.drawBoundaries((Graphics2D)g, true);
            }

            if(ptr.getGrid() && lastUsed != null) {
                ptr.drawGrid((Graphics2D)g, true);
            }

            if (zw && lastUsed != null) {
                ptr.drawZoomWindow((Graphics2D) g,  p);
            }
        }
    }

    private void handleNewImage(Graphics g) {
        if(MainWindow.ZOOM_TO_THE_SELECTED_AREA) {

            final SelectionRectangle sr = ptr.getSelectionRectangle();
            boolean showRectangle = sr.isVisible() && (ptr.canSelectPointInstantly() || TaskRender.DONE);
            BufferedImage image = ptr.getImage();
            BufferedImage last_used = ptr.getLastUsed();

            if(showRectangle) {
                if (sr.isSelectionValid() && MainWindow.MASK_IMAGE_OUTSIDE_WINDOW > 0) {
                    maskImage(image, last_used, point -> !sr.isInImageChangeArea(point) || !sr.isInRectangleArea(point));
                    g.drawImage(last_used, 0, 0, null);
                } else {
                    g.drawImage(image, 0, 0, null);
                }
            }
            else {
                g.drawImage(image, 0, 0, null);
            }

            if (ptr.getOrbit()) {
                ptr.drawOrbit((Graphics2D) g);
            }

            if (ptr.getBoundaries()) {
                ptr.drawBoundaries((Graphics2D) g, true);
            }

            if (ptr.getGrid()) {
                ptr.drawGrid((Graphics2D) g, true);
            }

            if(showRectangle && sr.isSelectionValid() ) {
                sr.draw((Graphics2D) g);
            }
        }
        else {
            Point p = null;

            boolean zw = ptr.getZoomWindow();
            if(zw) {
                try {
                    p = getMousePosition();
                }
                catch (Exception ex) {
                }
            }

            BufferedImage image = ptr.getImage();
            if (zw && MainWindow.MASK_IMAGE_OUTSIDE_WINDOW > 0 &&  ptr.getLastUsed() != null) {
                Rectangle2D.Double rect = ptr.getZoomWindowRectangle(p);
                maskImage(image, ptr.getLastUsed(), point -> rect != null && !rect.contains(point));
                g.drawImage(ptr.getLastUsed(), 0, 0, null);
            }
            else {
                g.drawImage(image, 0, 0, null);
            }

            if (ptr.getOrbit()) {
                ptr.drawOrbit((Graphics2D) g);
            }

            if (ptr.getBoundaries()) {
                ptr.drawBoundaries((Graphics2D) g, true);
            }

            if (ptr.getGrid()) {
                ptr.drawGrid((Graphics2D) g, true);
            }

            if (zw) {
                ptr.drawZoomWindow((Graphics2D) g, p);
            }
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
            g.drawImage(new BufferedImage(TaskRender.FAST_JULIA_IMAGE_SIZE, TaskRender.FAST_JULIA_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB), ptr.getScrollPane().getHorizontalScrollBar().getValue(), ptr.getScrollPane().getVerticalScrollBar().getValue(), null);
            ptr.getScrollPane().getHorizontalScrollBar().setValue((int)(ptr.getScrollPane().getHorizontalScrollBar().getMaximum() / 2.0 - ptr.getScrollPane().getHorizontalScrollBar().getSize().getWidth() / 2.0));
            ptr.getScrollPane().getVerticalScrollBar().setValue((int)(ptr.getScrollPane().getVerticalScrollBar().getMaximum() / 2.0 - ptr.getScrollPane().getVerticalScrollBar().getSize().getHeight() / 2.0));
            ptr.setFirstPaint();
        }

       if(ptr.getWholeImageDone()) {
            handleNewImage(g);
       }
       else if(!ptr.getColorCycling()) {
           handlePreviousImage(g);
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

    private final static double MASK_COEF = 0.4;

    private void maskImage(BufferedImage image, BufferedImage image2, Predicate<Point> predicate) {

        synchronized (ptr.image_reset_mutex) {
            int[] rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
            int[] rgbs2 = ((DataBufferInt) image2.getRaster().getDataBuffer()).getData();
            int image_width = image.getWidth();
            double inv_coef = 1 - MASK_COEF;

            IntStream.range(0, rgbs.length).parallel().forEach(p -> {
                int y = p / image_width;
                int x = p % image_width;

                int color = rgbs[p];
                int red = (color >> 16) & 0xFF;
                int green = (color >> 8) & 0xFF;
                int blue = (color) & 0xFF;

                if (predicate.test(new Point(x, y))) {

                    if(MainWindow.MASK_IMAGE_OUTSIDE_WINDOW == 1) {
                        red = (int) (red * MASK_COEF + inv_coef * 255);
                        green = (int) (green * MASK_COEF + inv_coef * 255);
                        blue = (int) (blue * MASK_COEF + inv_coef * 255);
                        rgbs2[p] = 0xFF000000 | (red << 16) | (green << 8) | blue;
                    }
                    else {
                        red = (int) (red * MASK_COEF);
                        green = (int) (green * MASK_COEF);
                        blue = (int) (blue * MASK_COEF);
                        rgbs2[p] = 0xFF000000 | (red << 16) | (green << 8) | blue;
                    }

                } else {
                    rgbs2[p] = color;
                }
            });
        }
    }

    private void maskImageSlow(BufferedImage image, Graphics g, Predicate<Point> predicate) {

        synchronized (ptr.image_reset_mutex) {
            int[] rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
            int image_width = image.getWidth();
            double inv_coef = 1 - MASK_COEF;

            for(int p = 0; p < rgbs.length; p++) {
                int y = p / image_width;
                int x = p % image_width;

                int color = rgbs[p];
                int red = (color >> 16) & 0xFF;
                int green = (color >> 8) & 0xFF;
                int blue = (color) & 0xFF;

                if (predicate.test(new Point(x, y))) {

                    if(MainWindow.MASK_IMAGE_OUTSIDE_WINDOW == 1) {
                        red = (int) (red * MASK_COEF + inv_coef * 255);
                        green = (int) (green * MASK_COEF + inv_coef * 255);
                        blue = (int) (blue * MASK_COEF + inv_coef * 255);
                        g.setColor(new Color((0xff000000) | (red << 16) | (green << 8) | blue));
                    }
                    else {
                        red = (int) (red * MASK_COEF);
                        green = (int) (green * MASK_COEF);
                        blue = (int) (blue * MASK_COEF);
                        g.setColor(new Color((0xff000000) | (red << 16) | (green << 8) | blue));
                    }
                    g.drawRect(x, y, 1, 1);
                }
                else {
                    g.setColor(new Color(color));
                    g.drawRect(x, y, 1, 1);
                }
            }
        }
    }

    public String getIterationData(int x, int y, int width, boolean domain_coloring, boolean d3) {

        if(domain_coloring || d3 || !TaskRender.DONE || ptr.hasTransformedImage()) {
            return "N/A";
        }

        try {
            return "" + TaskRender.image_iterations[y * width + x];
        }
        catch (Exception ex) {
            return "N/A";
        }
    }
}
