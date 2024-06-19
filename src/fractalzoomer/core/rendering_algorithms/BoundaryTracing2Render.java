
package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.location.Location;
import fractalzoomer.main.Constants;
import fractalzoomer.utils.ExpandingQueuePixel;
import fractalzoomer.utils.Pixel;
import fractalzoomer.utils.StopSuccessiveRefinementException;

import static fractalzoomer.core.rendering_algorithms.MarianiSilverRender.RENDER_USING_DFS;

/**
 *
 * @author hrkalona2
 */
@Deprecated
public class BoundaryTracing2Render extends TaskRender {
    private boolean[] added;
    private ExpandingQueuePixel pixels;
    private static final int INIT_QUEUE_SIZE = 200;
    public static final int INIT_QUEUE_SIZE2 = 6000;

    private int calculate(int loc, GenericComplex pixel) {
        if(rgbs[loc] == Constants.EMPTY_COLOR) {
            image_iterations[loc] = iteration_algorithm.calculate(pixel);
            escaped[loc] = iteration_algorithm.escaped();
            rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);
            rendering_done++;
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

    private void floodFill(int image_width, int pixel_percent) {
         
        int notCalculated = Constants.EMPTY_COLOR;
        int x, y, loc;

        long fill_count = 0;
        for(y = FROMy + 1; y < TOy - 1; y++) {          
            for(x = FROMx + 1, loc = y * image_width + x; x < TOx - 1; x++, loc++) {
                if(rgbs[loc] == notCalculated) {
                    int locm1 = loc - 1;
                    rgbs[loc] = getColorForSkippedPixels(rgbs[locm1], 0);
                    image_iterations[loc] = image_iterations[locm1];
                    rendering_done++;
                    task_completed++;
                    fill_count++;
                }
            }
            if(rendering_done / pixel_percent >= 1) {
                update(rendering_done);
                rendering_done = 0;
            }
        }

        task_pixel_grouping[0] += fill_count;
        
    }
    
    @Override
    protected void render(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException {

        added = new boolean[(TOy - FROMy) * (TOx - FROMx)];

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int pixel_percent = (image_width * image_height) / 100;

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

            loc = y * image_width + x;

            p1 = loc - 1;
            p2 = loc + 1;
            p3 = loc - image_width;
            p4 = loc + image_width;

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
            
            if(rendering_done / pixel_percent >= 1) {
                update(rendering_done);
                rendering_done = 0;
            }
        } while(true);

        floodFill(image_width, pixel_percent);
        
        if(SKIPPED_PIXELS_ALG == 4) {
            renderSquares(image_width, image_height);
        }

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;
        
        postProcess(image_width, image_height, null, location);
    }


    @Override
    protected void renderAntialiased(int image_width, int image_height, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void renderFastJulia(int image_size, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void renderFastJuliaAntialiased(int image_size, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
