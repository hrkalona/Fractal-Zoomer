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
package fractalzoomer.core.drawing_algorithms;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.TaskDraw;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.ExpandingQueuePixel;
import fractalzoomer.utils.Pixel;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.BrokenBarrierException;

import static fractalzoomer.core.drawing_algorithms.MarianiSilverDraw.RENDER_USING_DFS;

/**
 *
 * @author hrkalona2
 */
@Deprecated
public class BoundaryTracing2Draw extends TaskDraw {
    private boolean[] added;
    private ExpandingQueuePixel pixels;
    private static final int INIT_QUEUE_SIZE = 200;
    public static final int INIT_QUEUE_SIZE2 = 6000;

    private int calculate(int loc, GenericComplex pixel) {
        if(rgbs[loc] == Constants.EMPTY_COLOR) {
            image_iterations[loc] = iteration_algorithm.calculate(pixel);
            escaped[loc] = iteration_algorithm.escaped();
            rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);
            drawing_done++;
            task_calculated++;
            task_completed++;
        }
        return rgbs[loc];
    }
    
    private void add(int x, int y) {
        
        int loc = (y - FROMy) * (TOx - FROMx) + (x - FROMx);
        if(!added[loc]) {
            Pixel pixel = new Pixel(x, y);
            pixels.enqueue(pixel); 
            
            added[loc] = true;
        }
            
    }

    private void floodFill(int image_size, int pixel_percent) {
         
        int notCalculated = Constants.EMPTY_COLOR;
        int x, y, loc;
        
        for(y = FROMy + 1; y < TOy - 1; y++) {          
            for(x = FROMx + 1, loc = y * image_size + x; x < TOx - 1; x++, loc++) {
                if(rgbs[loc] == notCalculated) {
                    rgbs[loc] = getColorForSkippedPixels(rgbs[loc - 1], randomNumber);
                    image_iterations[loc] = image_iterations[loc - 1];
                    drawing_done++;
                    task_completed++;
                }
            }
            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }
        }
        
    }
    
    @Override
    protected void drawIterations(int image_size, boolean polar) throws StopSuccessiveRefinementException {

        added = new boolean[(TOy - FROMy) * (TOx - FROMx)];

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int pixel_percent = (image_size * image_size) / 100;

        int center_color;

        int loc, p1, p2, p3, p4;

        pixels = new ExpandingQueuePixel(RENDER_USING_DFS ? INIT_QUEUE_SIZE : INIT_QUEUE_SIZE2);

        task_completed = 0;

        long time = System.currentTimeMillis();

        int x;
        int y = FROMy;

        for(x = FROMx; x < TOx; x++) {
            add(x, y);  
        }

        for(x--, y++; y < TOy; y++) {
            add(x, y);        
        }

        for(y--, x--; x >= FROMx; x--) {
            add(x, y);   
        }

        for(x++, y--; y > FROMy; y--) {
            add(x, y);      
        }

        do {
            Pixel currentPixel = null;

            if(pixels.isEmpty()) {
                break;
            }

            if(RENDER_USING_DFS) {
                currentPixel = pixels.last();
            }
            else {
                currentPixel = pixels.dequeue();
            }

            x = currentPixel.x;
            y = currentPixel.y;

            boolean ll = x >= FROMx + 1, rr = x < TOx - 1;
            boolean uu = y >= FROMy + 1, dd = y < TOy - 1;

            loc = y * image_size + x;           

            p1 = loc - 1;
            p2 = loc + 1;
            p3 = loc - image_size;
            p4 = loc + image_size;

            center_color = calculate(loc, location.getComplex(x, y));

            boolean l = ll && calculate(p1, location.getComplex(x - 1, y)) != center_color;
            boolean r = rr && calculate(p2, location.getComplex(x + 1, y)) != center_color;
            boolean u = uu && calculate(p3, location.getComplex(x, y - 1)) != center_color;
            boolean d = dd && calculate(p4, location.getComplex(x, y + 1)) != center_color;

            if(l) {
                add(x - 1, y);
            }
            if(r) {                
                add(x + 1, y);
            }
            if(u) {
                add(x, y - 1);
            }
            if(d) {
                add(x, y + 1);
            }

            // The corner pixels (nw,ne,sw,se) are also neighbors 
            if((uu && ll) && (l || u)) {
                add(x - 1, y - 1);
            }
            if((uu && rr) && (r || u)) {
                add(x + 1, y - 1);
            }
            if((dd && ll) && (l || d)) {
                add(x - 1, y + 1);
            }
            if((dd && rr) && (r || d)) {
                add(x + 1, y + 1);
            }
            
            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }
        } while(true);

        floodFill(image_size, pixel_percent);
        
        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;
        
        postProcess(image_size, null, location);
    }


    @Override
    protected void drawIterationsAntialiased(int image_size, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void drawFastJulia(int image_size, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void drawFastJuliaAntialiased(int image_size, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
