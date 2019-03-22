/*
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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

import fractalzoomer.core.Complex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.BumpMapSettings;
import fractalzoomer.main.app_settings.ContourColoringSettings;
import fractalzoomer.main.app_settings.D3Settings;
import fractalzoomer.main.app_settings.DomainColoringSettings;
import fractalzoomer.main.app_settings.EntropyColoringSettings;
import fractalzoomer.main.app_settings.FakeDistanceEstimationSettings;
import fractalzoomer.main.app_settings.FiltersSettings;
import fractalzoomer.main.app_settings.FunctionSettings;
import fractalzoomer.main.app_settings.GreyscaleColoringSettings;
import fractalzoomer.main.app_settings.LightSettings;
import fractalzoomer.main.app_settings.OffsetColoringSettings;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.PaletteGradientMergingSettings;
import fractalzoomer.main.app_settings.RainbowPaletteSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.Pixel;
import fractalzoomer.utils.ExpandingQueue;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona2
 */
public class BoundaryTracing2Draw extends ThreadDraw {
    private boolean[] added;
    private ExpandingQueue<Pixel> pixels;
    private static final int INIT_QUEUE_SIZE = 6000;

    public BoundaryTracing2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset);
    }
    
    public BoundaryTracing2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset);
    }
    
    public BoundaryTracing2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, xJuliaCenter, yJuliaCenter);
    }
    
    public BoundaryTracing2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, xJuliaCenter, yJuliaCenter);
    }
    
    public BoundaryTracing2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset);
    }
    
    public BoundaryTracing2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, xJuliaCenter, yJuliaCenter);
    }

    public BoundaryTracing2Draw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, int color_cycling_location2, BumpMapSettings bms, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, int color_cycling_speed, FiltersSettings fs, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, boolean cycle_colors, boolean cycle_lights, boolean cycle_gradient, DomainColoringSettings ds, int gradient_offset) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, color_cycling_location2, bms, fdes, rps, color_cycling_speed, fs, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, cns, post_processing_order, ls, pbs, ots, cycle_colors, cycle_lights, cycle_gradient, ds, gradient_offset);
    }

    public BoundaryTracing2Draw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, BumpMapSettings bms, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, DomainColoringSettings ds, int gradient_offset) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs, bms, fdes, rps, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, cns, post_processing_order, ls, pbs, ots, ds, gradient_offset);
    }

    public BoundaryTracing2Draw(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean draw_action, MainWindow ptr, BufferedImage image, FiltersSettings fs,  int color_blending) {
        super(FROMx, TOx, FROMy, TOy, d3s, draw_action, ptr, image, fs,  color_blending);
    }

    private int calculate(int loc, double re, double im) {
        if(rgbs[loc] == 0) {
            image_iterations[loc] = iteration_algorithm.calculate(new Complex(re, im));
            escaped[loc] = iteration_algorithm.escaped();
            rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);
            drawing_done++;
            thread_calculated++;
        }
        return rgbs[loc];
    }
    
    private void add(int x, int y) {
        
        int loc = (y - FROMy) * (TOx - FROMx) + (x - FROMx);
        if(!added[loc]) {
            Pixel pixel = new Pixel();
            pixel.x = x;
            pixel.y = y;
            pixels.enqueue(pixel); 
            
            added[loc] = true;
        }
            
    }

    private void floodFill(int image_size, int pixel_percent) {
         
        int notCalculated = 0;
        int x, y, loc;
        
        for(y = FROMy + 1; y < TOy - 1; y++) {          
            for(x = FROMx + 1, loc = y * image_size + x; x < TOx - 1; x++, loc++) {
                if(rgbs[loc] == notCalculated) {
                    rgbs[loc] = getColorForSkippedPixels(rgbs[loc - 1], 0 + randomNumber);
                    image_iterations[loc] = image_iterations[loc - 1];
                    drawing_done++;
                }
            }
            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }
        }
        
    }
    
    @Override
    protected void drawIterations(int image_size) {

        added = new boolean[(TOy - FROMy) * (TOx - FROMx)];
        
        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = (image_size * image_size) / 100;

        int center_color;

        int loc, p1, p2, p3, p4;

        pixels = new ExpandingQueue(INIT_QUEUE_SIZE);

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

            currentPixel = pixels.dequeue();

            x = currentPixel.x;
            y = currentPixel.y;

            boolean ll = x >= FROMx + 1, rr = x < TOx - 1;
            boolean uu = y >= FROMy + 1, dd = y < TOy - 1;

            loc = y * image_size + x;           

            p1 = loc - 1;
            p2 = loc + 1;
            p3 = loc - image_size;
            p4 = loc + image_size;

            center_color = calculate(loc, temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y);

            boolean l = ll && calculate(p1, temp_xcenter_size + temp_size_image_size_x * (x - 1), temp_ycenter_size - temp_size_image_size_y * y) != center_color;
            boolean r = rr && calculate(p2, temp_xcenter_size + temp_size_image_size_x * (x + 1), temp_ycenter_size - temp_size_image_size_y * y) != center_color;
            boolean u = uu && calculate(p3, temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * (y - 1)) != center_color;
            boolean d = dd && calculate(p4, temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * (y + 1)) != center_color;

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
        
        postProcess(image_size);
    }

    @Override
    protected void drawIterationsPolar(int image_size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void drawIterationsPolarAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void drawIterationsAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void drawFastJulia(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void drawFastJuliaPolar(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void drawFastJuliaAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void drawFastJuliaPolarAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
