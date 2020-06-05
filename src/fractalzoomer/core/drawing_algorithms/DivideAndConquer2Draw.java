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

import fractalzoomer.core.Complex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.ExpandingQueue;
import fractalzoomer.utils.Square;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author hrkalona2
 */
public class DivideAndConquer2Draw extends ThreadDraw {
    private static final int MAX_TILE_SIZE = 2;
    private static final int INIT_QUEUE_SIZE = 6000;

    public DivideAndConquer2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss);
    }

    public DivideAndConquer2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss);
    }

    public DivideAndConquer2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, xJuliaCenter, yJuliaCenter);
    }

    public DivideAndConquer2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, xJuliaCenter, yJuliaCenter);
    }

    public DivideAndConquer2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss);
    }

    public DivideAndConquer2Draw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, xJuliaCenter, yJuliaCenter);
    }

    public DivideAndConquer2Draw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, int color_cycling_location2, BumpMapSettings bms, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, int color_cycling_speed, FiltersSettings fs, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, boolean cycle_colors, boolean cycle_lights, boolean cycle_gradient, DomainColoringSettings ds, int gradient_offset, HistogramColoringSettings hss) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, color_cycling_location2, bms, fdes, rps, color_cycling_speed, fs, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, cns, post_processing_order, ls, pbs, ots, cycle_colors, cycle_lights, cycle_gradient, ds, gradient_offset, hss);
    }

    public DivideAndConquer2Draw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, BumpMapSettings bms, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, DomainColoringSettings ds, int gradient_offset, HistogramColoringSettings hss) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs, bms, fdes, rps, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, cns, post_processing_order, ls, pbs, ots, ds, gradient_offset, hss);
    }

    public DivideAndConquer2Draw(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean draw_action, MainWindow ptr, BufferedImage image, FiltersSettings fs,  int color_blending) {
        super(FROMx, TOx, FROMy, TOy, d3s, draw_action, ptr, image, fs,  color_blending);
    }
    
    @Override
    protected void drawIterations(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        ExpandingQueue<Square> squares = new ExpandingQueue(INIT_QUEUE_SIZE);

        Square square = new Square(FROMx, FROMy, TOx, TOy, randomNumber);

        squares.enqueue(square);

        int pixel_percent = (image_size * image_size) / 100;

        int x = 0;
        int y = 0;
        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        boolean temp_starting_escaped;

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        int loc;

        int notCalculated = 0;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if(squares.isEmpty()) {
                break;
            }

            currentSquare = squares.dequeue();

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            if(rgbs[loc] == notCalculated) {
                temp_starting_value = image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                temp_starting_escaped = escaped[loc] = iteration_algorithm.escaped();
                temp_starting_pixel_color = rgbs[loc] = getFinalColor(temp_starting_value, temp_starting_escaped);
                drawing_done++;
                thread_calculated++;
            }
            else {
                temp_starting_value = image_iterations[loc];
                temp_starting_pixel_color = rgbs[loc];
                temp_starting_escaped = escaped[loc];
            }

            for(x++, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                if(rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                    }

                    drawing_done++;
                    thread_calculated++;
                }
            }

            for(x--, y++; y < slice_TOy; y++) {
                loc = y * image_size + x;
                if(rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                    }

                    drawing_done++;
                    thread_calculated++;
                }
            }

            for(y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                if(rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                    }

                    drawing_done++;
                    thread_calculated++;
                }
            }

            for(x++, y--; y > slice_FROMy; y--) {
                loc = y * image_size + x;
                if(rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                    }

                    drawing_done++;
                    thread_calculated++;
                }
            }

            y++;
            x++;

            if(!whole_area) {

                slice_FROMx++;
                slice_TOx--;

                slice_FROMy++;
                slice_TOy--;

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if(xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {

                    Square square1 = new Square(slice_FROMx, slice_FROMy, slice_FROMx + xLength / 2, slice_FROMy + yLength / 2, currentSquare.iteration + 1);

                    squares.enqueue(square1);

                    Square square2 = new Square(slice_FROMx + xLength / 2, slice_FROMy, slice_TOx, slice_FROMy + yLength / 2, currentSquare.iteration + 1);

                    squares.enqueue(square2);

                    Square square3 = new Square(slice_FROMx, slice_FROMy + yLength / 2, slice_FROMx + xLength / 2, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square3);

                    Square square4 = new Square(slice_FROMx + xLength / 2, slice_FROMy + yLength / 2, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square4);

                }           
            }
            else {
                int temp1 = slice_TOx - 1;
                int temp2 = slice_TOy - 1;

                int chunk = temp1 - x;
                
                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for(int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations, k * image_size + x, k * image_size + temp1, temp_starting_value);
                    Arrays.fill(escaped, k * image_size + x, k * image_size + temp1, temp_starting_escaped);
                    drawing_done += chunk;
                }
                
                if(drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }
            }

        } while(true);

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
