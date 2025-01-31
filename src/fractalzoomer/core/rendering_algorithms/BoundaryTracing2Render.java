
package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.location.Location;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.Square;
import fractalzoomer.utils.StopExecutionException;
import fractalzoomer.utils.queues.ExpandingQueuePixel;
import fractalzoomer.utils.Pixel;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;

import static fractalzoomer.core.rendering_algorithms.MarianiSilverRender.RENDER_USING_DFS;

/**
 *
 * @author hrkalona2
 */
@Deprecated
public class BoundaryTracing2Render extends TaskRender {
    public static boolean[] added;
    private ExpandingQueuePixel pixels;
    private static final int INIT_QUEUE_SIZE = 200;
    public static final int INIT_QUEUE_SIZE2 = 6000;

    public BoundaryTracing2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public static void initStatic(int width, int height) {
        added = new boolean[width * height];
    }

    private int calculate(int loc, GenericComplex pixel) {
        if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
            image_iterations[loc] = iteration_algorithm.calculate(pixel);
            escaped[loc] = iteration_algorithm.escaped();
            rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);
            rendering_done++;
            task_calculated++;
            task_completed++;
        }
        return rgbs[loc];
    }
    
    private void add(int x, int y, int image_width) {
        
        int loc = y * image_width + x;
        if(!added[loc]) {
            Pixel pixel = new Pixel(x, y);
            pixels.enqueue(pixel); 
            
            added[loc] = true;
        }
            
    }

    private void floodFill(int image_width, int pixel_percent, int FROMx, int TOx, int FROMy, int TOy) {

        int x, y, loc;

        long fill_count = 0;
        for(y = FROMy + 1; y < TOy - 1; y++) {          
            for(x = FROMx + 1, loc = y * image_width + x; x < TOx - 1; x++, loc++) {
                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
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
    protected void render(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException, StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int pixel_percent = (image_width * image_height) / 100;

        int center_color;

        int loc, p1, p2, p3, p4, iteration = 0;

        pixels = new ExpandingQueuePixel(RENDER_USING_DFS ? INIT_QUEUE_SIZE : INIT_QUEUE_SIZE2);

        task_completed = 0;

        long time = System.currentTimeMillis();

        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;
            int x;
            int y = FROMy;

            for (x = FROMx; x < TOx; x++) {
                add(x, y, image_width);
            }

            for (x--, y++; y < TOy; y++) {
                add(x, y, image_width);
            }

            for (y--, x--; x >= FROMx; x--) {
                add(x, y, image_width);
            }

            for (x++, y--; y > FROMy; y--) {
                add(x, y, image_width);
            }

            do {
                Pixel currentPixel = null;

                if (pixels.isEmpty()) {
                    break;
                }

                if (RENDER_USING_DFS) {
                    currentPixel = pixels.last();
                } else {
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

                if (l) {
                    add(x - 1, y, image_width);
                }
                if (r) {
                    add(x + 1, y, image_width);
                }
                if (u) {
                    add(x, y - 1, image_width);
                }
                if (d) {
                    add(x, y + 1, image_width);
                }

                // The corner pixels (nw,ne,sw,se) are also neighbors
                if ((uu && ll) && (l || u)) {
                    add(x - 1, y - 1, image_width);
                }
                if ((uu && rr) && (r || u)) {
                    add(x + 1, y - 1, image_width);
                }
                if ((dd && ll) && (l || d)) {
                    add(x - 1, y + 1, image_width);
                }
                if ((dd && rr) && (r || d)) {
                    add(x + 1, y + 1, image_width);
                }

                if (rendering_done / pixel_percent >= 1) {
                    update(rendering_done);
                    rendering_done = 0;
                }
            } while (true);

            floodFill(image_width, pixel_percent, FROMx, TOx, FROMy, TOy);
            iteration++;
        } while (true);
        
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
