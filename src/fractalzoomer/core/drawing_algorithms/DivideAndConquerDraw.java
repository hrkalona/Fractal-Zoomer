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
import fractalzoomer.main.app_settings.OffsetColoringSettings;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.RainbowPaletteSettings;
import fractalzoomer.utils.ExpandingQueue;
import fractalzoomer.utils.Square;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

/**
 *
 * @author hrkalona2
 */
public class DivideAndConquerDraw extends ThreadDraw {

    private static final int MAX_TILE_SIZE = 6;
    private static final int INIT_QUEUE_SIZE = 6000;

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns);
    }
    
    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns);
    }
    
    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns, xJuliaCenter, yJuliaCenter);
    }
    
    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns, xJuliaCenter, yJuliaCenter);
    }
    
    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns);
    }
    
    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns, xJuliaCenter, yJuliaCenter);
    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, BumpMapSettings bms, double color_intensity, int transfer_function, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, int color_cycling_speed, FiltersSettings fs, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, bms, fdes, rps, color_cycling_speed, fs, color_intensity, transfer_function, ens, ofs, gss, color_blending, cns);
    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, FiltersSettings fs, BumpMapSettings bms, double color_intensity, int transfer_function, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, fs, bms, fdes, rps, color_intensity, transfer_function, ens, ofs, gss, color_blending, cns);
    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean draw_action, MainWindow ptr, BufferedImage image, FiltersSettings fs,  int color_blending) {
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

        int pixel_percent = image_size * image_size / 100;

        int loc;

        int notCalculated = 0;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                rgbs[loc] = getFinalColor(image_iterations[loc]);
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    rgbs[loc] = getFinalColor(image_iterations[loc]);
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                rgbs[loc] = getFinalColor(image_iterations[loc]);
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    rgbs[loc] = getFinalColor(image_iterations[loc]);
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ExpandingQueue<Square> squares = new ExpandingQueue(INIT_QUEUE_SIZE);

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;
        
        squares.enqueue(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }
            
            currentSquare = squares.dequeue();

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                                rgbs[loc] = getFinalColor(image_iterations[loc]);
                                drawing_done++;
                                thread_calculated++;
                            }
                        }
                    }
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }
            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                int drawCount = 0;
                int chunk = temp1 - x;
                
                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);

                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations, k * image_size + x, k * image_size + temp1, temp_starting_value);
                    drawCount += chunk;
                }

                update(drawCount);

            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }
        
        postProcess(image_size);
    }

    @Override
    protected void drawIterationsPolar(int image_size) {

        double size = fractal.getSize();
        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int loc;

        int notCalculated = 0;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getFinalColor(image_iterations[loc]);
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    rgbs[loc] = getFinalColor(image_iterations[loc]);
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getFinalColor(image_iterations[loc]);
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    rgbs[loc] = getFinalColor(image_iterations[loc]);
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ExpandingQueue<Square> squares = new ExpandingQueue(INIT_QUEUE_SIZE);

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.enqueue(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.dequeue();

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                f = y * muly;
                                sf = Math.sin(f);
                                cf = Math.cos(f);

                                r = Math.exp(x * mulx + start);

                                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                                rgbs[loc] = getFinalColor(image_iterations[loc]);
                                drawing_done++;
                                thread_calculated++;
                            }
                        }
                    }
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }
            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                int drawCount = 0;
                int chunk = temp1 - x;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations, k * image_size + x, k * image_size + temp1, temp_starting_value);
                    drawCount += chunk;
                }

                update(drawCount);

            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }
        
        postProcess(image_size);
    }

    @Override
    protected void drawIterationsPolarAntialiased(int image_size) {

        double size = fractal.getSize();
        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int loc;

        int notCalculated = 0;

        int color;

        int red, green, blue;

        double temp_result;

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y_sin = (double[])(ret[1]);
        double[] antialiasing_y_cos = (double[])(ret[2]);   
        
        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = getFinalColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {

                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    color = getFinalColor(image_iterations[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {

                        sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r2 = r * antialiasing_x[i];

                        temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = getFinalColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {

                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    color = getFinalColor(image_iterations[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {

                        sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r2 = r * antialiasing_x[i];

                        temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ExpandingQueue<Square> squares = new ExpandingQueue(INIT_QUEUE_SIZE);

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.enqueue(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.dequeue();

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            color = getFinalColor(image_iterations[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {

                                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                r2 = r * antialiasing_x[i];

                                temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            color = getFinalColor(image_iterations[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {

                                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                r2 = r * antialiasing_x[i];

                                temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                f = y * muly;
                                sf = Math.sin(f);
                                cf = Math.cos(f);

                                r = Math.exp(x * mulx + start);

                                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                                color = getFinalColor(image_iterations[loc]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for (int i = 0; i < supersampling_num; i++) {

                                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                    r2 = r * antialiasing_x[i];

                                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                    color = getFinalColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                                drawing_done++;
                                thread_calculated++;
                            }
                        }
                    }
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }
            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                int drawCount = 0;
                int chunk = temp1 - x;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations, k * image_size + x, k * image_size + temp1, temp_starting_value);
                    drawCount += chunk;
                }

                update(drawCount);

            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }
        
        postProcess(image_size);

    }

    @Override
    protected void drawIterationsAntialiased(int image_size) {
        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int loc;

        int notCalculated = 0;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y = (double[])(ret[1]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        double temp_x0, temp_y0;
        int color, red, green, blue;
        double temp_result;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {

                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                color = getFinalColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                    temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                    color = getFinalColor(image_iterations[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {
                        temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                color = getFinalColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                    temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                    color = getFinalColor(image_iterations[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {
                        temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ExpandingQueue<Square> squares = new ExpandingQueue(INIT_QUEUE_SIZE);

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.enqueue(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.dequeue();

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            color = getFinalColor(image_iterations[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            color = getFinalColor(image_iterations[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                                color = getFinalColor(image_iterations[loc]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for (int i = 0; i < supersampling_num; i++) {
                                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                    color = getFinalColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                                drawing_done++;
                                thread_calculated++;
                            }
                        }
                    }
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }
            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                int drawCount = 0;
                int chunk = temp1 - x;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations, k * image_size + x, k * image_size + temp1, temp_starting_value);
                    drawCount += chunk;
                }

                update(drawCount);

            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }
        
        postProcess(image_size);
    }

    @Override
    protected void drawFastJulia(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int loc;

        int notCalculated = 0;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                }
            }
        }

        ExpandingQueue<Square> squares = new ExpandingQueue(INIT_QUEUE_SIZE);

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.enqueue(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.dequeue();

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations_fast_julia[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                            rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                            rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                            }
                        }
                    }
                }

            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations_fast_julia, k * image_size + x, k * image_size + temp1, temp_starting_value);
                }
            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }
        
        postProcessFastJulia(image_size);

    }

    @Override
    protected void drawFastJuliaPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int loc;

        int notCalculated = 0;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                }
            }
        }

        ExpandingQueue<Square> squares = new ExpandingQueue(INIT_QUEUE_SIZE);

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.enqueue(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.dequeue();

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations_fast_julia[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                f = y * muly;
                                sf = Math.sin(f);
                                cf = Math.cos(f);

                                r = Math.exp(x * mulx + start);

                                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                            }
                        }
                    }
                }

            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations_fast_julia, k * image_size + x, k * image_size + temp1, temp_starting_value);
                }
            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }
        
        postProcessFastJulia(image_size);

    }

    @Override
    protected void drawFastJuliaAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int loc;

        int notCalculated = 0;

        int color;

        double temp_result;
        double temp_x0, temp_y0;

        int red, green, blue;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y = (double[])(ret[1]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                color = getFinalColor(image_iterations_fast_julia[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                    temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                    color = getFinalColor(image_iterations_fast_julia[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {
                        temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                color = getFinalColor(image_iterations_fast_julia[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                    temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                    color = getFinalColor(image_iterations_fast_julia[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {
                        temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                }
            }
        }

        ExpandingQueue<Square> squares = new ExpandingQueue(INIT_QUEUE_SIZE);

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.enqueue(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.dequeue();

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations_fast_julia[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            color = getFinalColor(image_iterations_fast_julia[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            color = getFinalColor(image_iterations_fast_julia[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                                color = getFinalColor(image_iterations_fast_julia[loc]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for (int i = 0; i < supersampling_num; i++) {
                                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                    color = getFinalColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                            }
                        }
                    }
                }

            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations_fast_julia, k * image_size + x, k * image_size + temp1, temp_starting_value);
                }
            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }
        
        postProcessFastJulia(image_size);

    }

    @Override
    protected void drawFastJuliaPolarAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int loc;

        int notCalculated = 0;

        int color;
        
        double temp_result;

        int red, green, blue;

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y_sin = (double[])(ret[1]);
        double[] antialiasing_y_cos = (double[])(ret[2]);   
        
        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    color = getFinalColor(image_iterations_fast_julia[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {

                        sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r2 = r * antialiasing_x[i];

                        temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = getFinalColor(image_iterations_fast_julia[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {

                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    color = getFinalColor(image_iterations_fast_julia[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {

                        sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r2 = r * antialiasing_x[i];

                        temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                }
            }
        }

        ExpandingQueue<Square> squares = new ExpandingQueue(INIT_QUEUE_SIZE);

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.enqueue(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.dequeue();

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations_fast_julia[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            color = getFinalColor(image_iterations_fast_julia[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {

                                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                r2 = r * antialiasing_x[i];

                                temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            color = getFinalColor(image_iterations_fast_julia[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {

                                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                r2 = r * antialiasing_x[i];

                                temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                f = y * muly;
                                sf = Math.sin(f);
                                cf = Math.cos(f);

                                r = Math.exp(x * mulx + start);

                                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                                color = getFinalColor(image_iterations_fast_julia[loc]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for (int i = 0; i < supersampling_num; i++) {

                                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                    r2 = r * antialiasing_x[i];

                                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                    color = getFinalColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                            }
                        }
                    }
                }

            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                        
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations_fast_julia, k * image_size + x, k * image_size + temp1, temp_starting_value);
                }
            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }
        
        postProcessFastJulia(image_size);

    }
}
