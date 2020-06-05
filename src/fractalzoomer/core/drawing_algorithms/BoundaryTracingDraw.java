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

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author kaloch
 */
public class BoundaryTracingDraw extends ThreadDraw {

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, xJuliaCenter, yJuliaCenter);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, xJuliaCenter, yJuliaCenter);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, xJuliaCenter, yJuliaCenter);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, int color_cycling_location2, BumpMapSettings bms, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, int color_cycling_speed, FiltersSettings fs, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, boolean cycle_colors, boolean cycle_lights, boolean cycle_gradient, DomainColoringSettings ds, int gradient_offset, HistogramColoringSettings hss) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, color_cycling_location2, bms, fdes, rps, color_cycling_speed, fs, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, cns, post_processing_order, ls, pbs, ots, cycle_colors, cycle_lights, cycle_gradient, ds, gradient_offset, hss);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, BumpMapSettings bms, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, DomainColoringSettings ds, int gradient_offset, HistogramColoringSettings hss) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs, bms, fdes, rps, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, cns, post_processing_order, ls, pbs, ots, ds, gradient_offset, hss);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean draw_action, MainWindow ptr, BufferedImage image, FiltersSettings fs,  int color_blending) {
        super(FROMx, TOx, FROMy, TOy, d3s, draw_action, ptr, image, fs,  color_blending);
    }

    @Override
    protected void drawIterationsPolarAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int pixel_percent = (image_size * image_size) / 100;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        boolean startEscaped;
        
        double nextX, nextY, start_val;

        double sf3, cf3, r3;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y_sin = (double[]) (ret[1]);
        double[] antialiasing_y_cos = (double[]) (ret[2]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        int skippedColor;

        int last_drawing_done = 0;
        int totalPixels = (TOx - FROMx) * (TOy - FROMy);

        done:
        for (y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if (rgbs[pix] != culcColor) {
                    continue;
                }

                r = Math.exp(x * mulx + start);

                temp_x0 = xcenter + r * cf;
                temp_y0 = ycenter + r * sf;

                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                start_val = image_iterations[pix] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                startEscaped = escaped[pix] = iteration_algorithm.escaped();
                color = getFinalColor(start_val, startEscaped);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {

                    sf3 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf3 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r3 = r * antialiasing_x[i];

                    temp_x0 = xcenter + r3 * cf3;
                    temp_y0 = ycenter + r3 * sf3;

                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                    color = getFinalColor(temp_result, iteration_algorithm.escaped());

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                startColor = rgbs[pix] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
                thread_calculated++;

                while (iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                    curPix = startPix = startPix - image_size;
                    iy--;
                }

                temp_ix = ix;
                temp_iy = iy;

                do {                                            // tracing cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if ((nextColor = rgbs[nextPix]) == culcColor) {
                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            image_iterations[nextPix] = iteration_algorithm.calculate(new Complex(nextX, nextY));
                            escaped[nextPix] = iteration_algorithm.escaped();
                            color = getFinalColor(image_iterations[nextPix], escaped[nextPix]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {

                                sf3 = sf2 * antialiasing_y_cos[i] + cf2 * antialiasing_y_sin[i];
                                cf3 = cf2 * antialiasing_y_cos[i] - sf2 * antialiasing_y_sin[i];

                                r3 = r2 * antialiasing_x[i];

                                nextX = xcenter + r3 * cf3;
                                nextY = ycenter + r3 * sf3;

                                temp_result = iteration_algorithm.calculate(new Complex(nextX, nextY));
                                color = getFinalColor(temp_result, iteration_algorithm.escaped());

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            nextColor = rgbs[nextPix] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                            drawing_done++;
                            thread_calculated++;

                        }

                        if (nextColor == startColor) {
                            curDir = dir;
                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                curDir = dirRight;

                skippedColor = getColorForSkippedPixels(startColor, randomNumber);

                do {                                                 // 2nd cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if (rgbs[nextPix] == startColor) {           // flooding  
                            curDir = dir;
                            if (dir == dirUP) {
                                floodPix = curPix;
                                flood_ix = temp_ix;

                                while (true) {
                                    flood_ix++;

                                    if (flood_ix >= TOx) {
                                        break;
                                    }

                                    floodPix++;

                                    if ((floodColor = rgbs[floodPix]) == culcColor) {
                                        drawing_done++;
                                        rgbs[floodPix] = skippedColor;
                                        image_iterations[floodPix] = start_val;
                                        escaped[floodPix] = startEscaped;
                                    } else if (floodColor != startColor) {
                                        break;
                                    }

                                }
                            }

                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                int dif = drawing_done - last_drawing_done;
                if (dif / pixel_percent >= 1) {
                    update(dif);
                    last_drawing_done = drawing_done;
                }

                if (drawing_done == totalPixels) {
                    break done;
                }
            }

            int dif = drawing_done - last_drawing_done;
            if (dif / pixel_percent >= 1) {
                update(dif);
                last_drawing_done = drawing_done;
            }
        }

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        postProcess(image_size);

        int dif = drawing_done - last_drawing_done;
        if (dif != 0) {
            update(dif);
        }
        drawing_done = 0;
    }

    @Override
    protected void drawIterationsPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int pixel_percent = (image_size * image_size) / 100;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;
        
        boolean startEscaped;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        int skippedColor;

        int last_drawing_done = 0;
        int totalPixels = (TOx - FROMx) * (TOy - FROMy);

        done:
        for (y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if (rgbs[pix] != culcColor) {
                    continue;
                }

                r = Math.exp(x * mulx + start);

                temp_x0 = xcenter + r * cf;
                temp_y0 = ycenter + r * sf;

                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                start_val = image_iterations[pix] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                startEscaped = escaped[pix] = iteration_algorithm.escaped();
                startColor = rgbs[pix] = getFinalColor(start_val, startEscaped);
                drawing_done++;
                thread_calculated++;
                /*ptr.getMainPanel().repaint();
                     try {
                     Thread.sleep(1); //demo
                     }
                     catch (InterruptedException ex) {}*/

                while (iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                    curPix = startPix = startPix - image_size;
                    iy--;
                }

                temp_ix = ix;
                temp_iy = iy;

                do {                                            // tracing cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if ((nextColor = rgbs[nextPix]) == culcColor) {
                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            image_iterations[nextPix] = iteration_algorithm.calculate(new Complex(nextX, nextY));
                            escaped[nextPix] = iteration_algorithm.escaped();
                            nextColor = rgbs[nextPix] = getFinalColor(image_iterations[nextPix], escaped[nextPix]);
                            drawing_done++;
                            thread_calculated++;
                            /*ptr.getMainPanel().repaint();
                                 try {
                                 Thread.sleep(1); //demo
                                 }
                                 catch (InterruptedException ex) {}*/
                        }

                        if (nextColor == startColor) {
                            curDir = dir;
                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                curDir = dirRight;

                skippedColor = getColorForSkippedPixels(startColor, randomNumber);

                do {                                                 // 2nd cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if (rgbs[nextPix] == startColor) {           // flooding  
                            curDir = dir;
                            if (dir == dirUP) {
                                floodPix = curPix;
                                flood_ix = temp_ix;

                                while (true) {
                                    flood_ix++;

                                    if (flood_ix >= TOx) {
                                        break;
                                    }

                                    floodPix++;

                                    if ((floodColor = rgbs[floodPix]) == culcColor) {
                                        drawing_done++;
                                        rgbs[floodPix] = skippedColor;
                                        image_iterations[floodPix] = start_val;
                                        escaped[floodPix] = startEscaped;
                                        /*ptr.getMainPanel().repaint();
                                             try {
                                             Thread.sleep(1); //demo
                                             }
                                             catch (InterruptedException ex) {}*/
                                    } else if (floodColor != startColor) {
                                        break;
                                    }

                                }
                            }

                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                int dif = drawing_done - last_drawing_done;
                if (dif / pixel_percent >= 1) {
                    update(dif);
                    last_drawing_done = drawing_done;
                }

                if (drawing_done == totalPixels) {
                    break done;
                }
            }

            int dif = drawing_done - last_drawing_done;
            if (dif / pixel_percent >= 1) {
                update(dif);
                last_drawing_done = drawing_done;
            }
        }

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        postProcess(image_size);

        int dif = drawing_done - last_drawing_done;
        if (dif != 0) {
            update(dif);
        }
        drawing_done = 0;
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

        int pixel_percent = (image_size * image_size) / 100;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;

        boolean startEscaped;
        
        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        int skippedColor;

        int last_drawing_done = 0;
        int totalPixels = (TOx - FROMx) * (TOy - FROMy);

        done:
        for (y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size - y * temp_size_image_size_y;
            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if (rgbs[pix] != culcColor) {
                    continue;
                }

                temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;

                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                start_val = image_iterations[pix] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                startEscaped = escaped[pix] = iteration_algorithm.escaped();
                startColor = rgbs[pix] = getFinalColor(start_val, startEscaped);
                drawing_done++;
                thread_calculated++;
                /*ptr.getMainPanel().repaint();
                     try {
                     Thread.sleep(1); //demo
                     }
                     catch (InterruptedException ex) {}*/

                while (iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                    curPix = startPix = startPix - image_size;
                    iy--;
                }

                temp_ix = ix;
                temp_iy = iy;

                do {                                            // tracing cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if ((nextColor = rgbs[nextPix]) == culcColor) {
                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size - next_iy * temp_size_image_size_y;

                            image_iterations[nextPix] = iteration_algorithm.calculate(new Complex(nextX, nextY));
                            escaped[nextPix] = iteration_algorithm.escaped();
                            nextColor = rgbs[nextPix] = getFinalColor(image_iterations[nextPix], escaped[nextPix]);
                            drawing_done++;
                            thread_calculated++;
                            /*ptr.getMainPanel().repaint();
                                 try {
                                 Thread.sleep(1); //demo
                                 }
                                 catch (InterruptedException ex) {}*/
                        }

                        if (nextColor == startColor) {
                            curDir = dir;
                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                curDir = dirRight;

                skippedColor = getColorForSkippedPixels(startColor, randomNumber);

                do {                                                 // 2nd cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if (rgbs[nextPix] == startColor) {           // flooding  
                            curDir = dir;
                            if (dir == dirUP) {
                                floodPix = curPix;
                                flood_ix = temp_ix;

                                while (true) {
                                    flood_ix++;

                                    if (flood_ix >= TOx) {
                                        break;
                                    }

                                    floodPix++;

                                    if ((floodColor = rgbs[floodPix]) == culcColor) {
                                        drawing_done++;
                                        rgbs[floodPix] = skippedColor;
                                        image_iterations[floodPix] = start_val;
                                        escaped[floodPix] = startEscaped;
                                        /*ptr.getMainPanel().repaint();
                                             try {
                                             Thread.sleep(1); //demo
                                             }
                                             catch (InterruptedException ex) {}*/
                                    } else if (floodColor != startColor) {
                                        break;
                                    }

                                }
                            }

                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                int dif = drawing_done - last_drawing_done;
                if (dif / pixel_percent >= 1) {
                    update(dif);
                    last_drawing_done = drawing_done;
                }

                if (drawing_done == totalPixels) {
                    break done;
                }
            }

            int dif = drawing_done - last_drawing_done;
            if (dif / pixel_percent >= 1) {
                update(dif);
                last_drawing_done = drawing_done;
            }
        }

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        postProcess(image_size);

        int dif = drawing_done - last_drawing_done;
        if (dif != 0) {
            update(dif);
        }
        drawing_done = 0;

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

        int color;

        double temp_result;

        int red, green, blue;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        int skippedColor;

        double start_val;
        boolean startEscaped;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y = (double[]) (ret[1]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for (y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size - y * temp_size_image_size_y;
            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if (rgbs[pix] != culcColor) {
                    continue;
                }

                temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                start_val = image_iterations_fast_julia[pix] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                startEscaped = escaped_fast_julia[pix] = iteration_algorithm.escaped();
                color = getFinalColor(start_val, startEscaped);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getFinalColor(temp_result, iteration_algorithm.escaped());

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                startColor = rgbs[pix] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                while (iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                    curPix = startPix = startPix - image_size;
                    iy--;
                }

                temp_ix = ix;
                temp_iy = iy;

                do {                                            // tracing cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if ((nextColor = rgbs[nextPix]) == culcColor) {
                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size - next_iy * temp_size_image_size_y;

                            image_iterations_fast_julia[nextPix] = iteration_algorithm.calculate(new Complex(nextX, nextY));
                            escaped_fast_julia[nextPix] = iteration_algorithm.escaped();
                            color = getFinalColor(image_iterations_fast_julia[nextPix], escaped_fast_julia[nextPix]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(new Complex(nextX + antialiasing_x[i], nextY + antialiasing_y[i]));
                                color = getFinalColor(temp_result, iteration_algorithm.escaped());

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            nextColor = rgbs[nextPix] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                        }

                        if (nextColor == startColor) {
                            curDir = dir;
                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                curDir = dirRight;

                skippedColor = getColorForSkippedPixels(startColor, randomNumber);

                do {                                                 // 2nd cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if (rgbs[nextPix] == startColor) {           // flooding
                            curDir = dir;
                            if (dir == dirUP) {
                                floodPix = curPix;
                                flood_ix = temp_ix;

                                while (true) {
                                    flood_ix++;

                                    if (flood_ix >= TOx) {
                                        break;
                                    }

                                    floodPix++;

                                    if ((floodColor = rgbs[floodPix]) == culcColor) {
                                        rgbs[floodPix] = skippedColor;
                                        image_iterations_fast_julia[floodPix] = start_val;
                                        escaped_fast_julia[floodPix] = startEscaped;
                                    } else if (floodColor != startColor) {
                                        break;
                                    }

                                }
                            }

                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

            }
        }

        if (SKIPPED_PIXELS_ALG == 4) {
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

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        double sf3, cf3, r3;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        int skippedColor;
        
        boolean startEscaped;

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y_sin = (double[]) (ret[1]);
        double[] antialiasing_y_cos = (double[]) (ret[2]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        for (y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if (rgbs[pix] != culcColor) {
                    continue;
                }

                r = Math.exp(x * mulx + start);

                temp_x0 = xcenter + r * cf;
                temp_y0 = ycenter + r * sf;

                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                start_val = image_iterations_fast_julia[pix] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                startEscaped = escaped_fast_julia[pix] = iteration_algorithm.escaped();
                color = getFinalColor(start_val, startEscaped);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {

                    sf3 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf3 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r3 = r * antialiasing_x[i];

                    temp_x0 = xcenter + r3 * cf3;
                    temp_y0 = ycenter + r3 * sf3;

                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                    color = getFinalColor(temp_result, iteration_algorithm.escaped());

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                startColor = rgbs[pix] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                while (iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                    curPix = startPix = startPix - image_size;
                    iy--;
                }

                temp_ix = ix;
                temp_iy = iy;

                do {                                            // tracing cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if ((nextColor = rgbs[nextPix]) == culcColor) {
                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            image_iterations_fast_julia[nextPix] = iteration_algorithm.calculate(new Complex(nextX, nextY));
                            escaped_fast_julia[nextPix] = iteration_algorithm.escaped();
                            color = getFinalColor(image_iterations_fast_julia[nextPix], escaped_fast_julia[nextPix]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {

                                sf3 = sf2 * antialiasing_y_cos[i] + cf2 * antialiasing_y_sin[i];
                                cf3 = cf2 * antialiasing_y_cos[i] - sf2 * antialiasing_y_sin[i];

                                r3 = r2 * antialiasing_x[i];

                                nextX = xcenter + r3 * cf3;
                                nextY = ycenter + r3 * sf3;

                                temp_result = iteration_algorithm.calculate(new Complex(nextX, nextY));
                                color = getFinalColor(temp_result, iteration_algorithm.escaped());

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            nextColor = rgbs[nextPix] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                        }

                        if (nextColor == startColor) {
                            curDir = dir;
                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                curDir = dirRight;

                skippedColor = getColorForSkippedPixels(startColor, randomNumber);

                do {                                                 // 2nd cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if (rgbs[nextPix] == startColor) {           // flooding  
                            curDir = dir;
                            if (dir == dirUP) {
                                floodPix = curPix;
                                flood_ix = temp_ix;

                                while (true) {
                                    flood_ix++;

                                    if (flood_ix >= TOx) {
                                        break;
                                    }

                                    floodPix++;

                                    if ((floodColor = rgbs[floodPix]) == culcColor) {
                                        rgbs[floodPix] = skippedColor;
                                        image_iterations_fast_julia[floodPix] = start_val;
                                        escaped_fast_julia[floodPix] = startEscaped;
                                    } else if (floodColor != startColor) {
                                        break;
                                    }

                                }
                            }

                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);
            }
        }

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        postProcessFastJulia(image_size);

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

        int pixel_percent = (image_size * image_size) / 100;

        int color;

        double temp_result;

        int red, green, blue;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        int skippedColor;
        
        boolean startEscaped;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y = (double[]) (ret[1]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int last_drawing_done = 0;
        int totalPixels = (TOx - FROMx) * (TOy - FROMy);

        done:
        for (y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size - y * temp_size_image_size_y;
            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if (rgbs[pix] != culcColor) {
                    continue;
                }

                temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                start_val = image_iterations[pix] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                startEscaped = escaped[pix] = iteration_algorithm.escaped();
                color = getFinalColor(start_val, startEscaped);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getFinalColor(temp_result, iteration_algorithm.escaped());

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                startColor = rgbs[pix] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
                thread_calculated++;
                /*ptr.getMainPanel().repaint();
                     try {
                     Thread.sleep(1); //demo
                     }
                     catch (InterruptedException ex) {}*/

                while (iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                    curPix = startPix = startPix - image_size;
                    iy--;
                }

                temp_ix = ix;
                temp_iy = iy;

                do {                                            // tracing cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if ((nextColor = rgbs[nextPix]) == culcColor) {
                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size - next_iy * temp_size_image_size_y;

                            image_iterations[nextPix] = iteration_algorithm.calculate(new Complex(nextX, nextY));
                            escaped[nextPix] = iteration_algorithm.escaped();
                            color = getFinalColor(image_iterations[nextPix], escaped[nextPix]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(new Complex(nextX + antialiasing_x[i], nextY + antialiasing_y[i]));
                                color = getFinalColor(temp_result, iteration_algorithm.escaped());

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            nextColor = rgbs[nextPix] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                            drawing_done++;
                            thread_calculated++;
                            /*ptr.getMainPanel().repaint();
                                 try {
                                 Thread.sleep(1); //demo
                                 }
                                 catch (InterruptedException ex) {}*/
                        }

                        if (nextColor == startColor) {
                            curDir = dir;
                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                curDir = dirRight;

                skippedColor = getColorForSkippedPixels(startColor, randomNumber);

                do {                                                 // 2nd cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if (rgbs[nextPix] == startColor) {           // flooding
                            curDir = dir;
                            if (dir == dirUP) {
                                floodPix = curPix;
                                flood_ix = temp_ix;

                                while (true) {
                                    flood_ix++;

                                    if (flood_ix >= TOx) {
                                        break;
                                    }

                                    floodPix++;

                                    if ((floodColor = rgbs[floodPix]) == culcColor) {
                                        drawing_done++;
                                        rgbs[floodPix] = skippedColor;
                                        image_iterations[floodPix] = start_val;
                                        escaped[floodPix] = startEscaped;
                                        /*ptr.getMainPanel().repaint();
                                             try {
                                             Thread.sleep(1); //demo
                                             }
                                             catch (InterruptedException ex) {}*/
                                    } else if (floodColor != startColor) {
                                        break;
                                    }

                                }
                            }

                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                int dif = drawing_done - last_drawing_done;
                if (dif / pixel_percent >= 1) {
                    update(dif);
                    last_drawing_done = drawing_done;
                }

                if (drawing_done == totalPixels) {
                    break done;
                }
            }

            int dif = drawing_done - last_drawing_done;
            if (dif / pixel_percent >= 1) {
                update(dif);
                last_drawing_done = drawing_done;
            }
        }

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        postProcess(image_size);

        int dif = drawing_done - last_drawing_done;
        if (dif != 0) {
            update(dif);
        }
        drawing_done = 0;

    }

    @Override
    protected void drawFastJuliaPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        int skippedColor;
        
        boolean startEscaped;

        double start_val;

        for (y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if (rgbs[pix] != culcColor) {
                    continue;
                }

                r = Math.exp(x * mulx + start);

                temp_x0 = xcenter + r * cf;
                temp_y0 = ycenter + r * sf;

                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                start_val = image_iterations_fast_julia[pix] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                startEscaped = escaped_fast_julia[pix] = iteration_algorithm.escaped();
                startColor = rgbs[pix] = getFinalColor(start_val, startEscaped);

                while (iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                    curPix = startPix = startPix - image_size;
                    iy--;
                }

                temp_ix = ix;
                temp_iy = iy;

                do {                                            // tracing cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if ((nextColor = rgbs[nextPix]) == culcColor) {
                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            image_iterations_fast_julia[nextPix] = iteration_algorithm.calculate(new Complex(nextX, nextY));
                            escaped_fast_julia[nextPix] = iteration_algorithm.escaped();
                            nextColor = rgbs[nextPix] = getFinalColor(image_iterations_fast_julia[nextPix], escaped_fast_julia[nextPix]);
                        }

                        if (nextColor == startColor) {
                            curDir = dir;
                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                curDir = dirRight;

                skippedColor = getColorForSkippedPixels(startColor, randomNumber);

                do {                                                 // 2nd cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if (rgbs[nextPix] == startColor) {           // flooding
                            curDir = dir;
                            if (dir == dirUP) {
                                floodPix = curPix;
                                flood_ix = temp_ix;

                                while (true) {
                                    flood_ix++;

                                    if (flood_ix >= TOx) {
                                        break;
                                    }

                                    floodPix++;

                                    if ((floodColor = rgbs[floodPix]) == culcColor) {
                                        rgbs[floodPix] = skippedColor;
                                        image_iterations_fast_julia[floodPix] = start_val;
                                        escaped_fast_julia[floodPix] = startEscaped;
                                    } else if (floodColor != startColor) {
                                        break;
                                    }

                                }

                            }

                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);
            }

        }

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        postProcessFastJulia(image_size);
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

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        int skippedColor;
        
        boolean startEscaped;

        double start_val;

        for (y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size - y * temp_size_image_size_y;
            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if (rgbs[pix] != culcColor) {
                    continue;
                }

                temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                start_val = image_iterations_fast_julia[pix] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                startEscaped = escaped_fast_julia[pix] = iteration_algorithm.escaped();
                startColor = rgbs[pix] = getFinalColor(start_val, startEscaped);

                while (iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                    curPix = startPix = startPix - image_size;
                    iy--;
                }

                temp_ix = ix;
                temp_iy = iy;

                do {                                            // tracing cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if ((nextColor = rgbs[nextPix]) == culcColor) {
                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size - next_iy * temp_size_image_size_y;

                            image_iterations_fast_julia[nextPix] = iteration_algorithm.calculate(new Complex(nextX, nextY));
                            escaped_fast_julia[nextPix] = iteration_algorithm.escaped();
                            nextColor = rgbs[nextPix] = getFinalColor(image_iterations_fast_julia[nextPix], escaped_fast_julia[nextPix]);
                        }

                        if (nextColor == startColor) {
                            curDir = dir;
                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);

                curDir = dirRight;

                skippedColor = getColorForSkippedPixels(startColor, randomNumber);

                do {                                                 // 2nd cycle
                    for (Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                        dir = Dir & maskDir;

                        next_ix = temp_ix + intX[dir];
                        next_iy = temp_iy + intY[dir];

                        if (next_ix < FROMx || next_ix >= TOx || next_iy < FROMy || next_iy >= TOy) {
                            continue;
                        }

                        nextPix = curPix + delPix[dir];

                        if (rgbs[nextPix] == startColor) {           // flooding
                            curDir = dir;
                            if (dir == dirUP) {
                                floodPix = curPix;
                                flood_ix = temp_ix;

                                while (true) {
                                    flood_ix++;

                                    if (flood_ix >= TOx) {
                                        break;
                                    }

                                    floodPix++;

                                    if ((floodColor = rgbs[floodPix]) == culcColor) {
                                        rgbs[floodPix] = skippedColor;
                                        image_iterations_fast_julia[floodPix] = start_val;
                                        escaped_fast_julia[floodPix] = startEscaped;
                                    } else if (floodColor != startColor) {
                                        break;
                                    }

                                }

                            }

                            curPix = nextPix;
                            temp_ix = next_ix;
                            temp_iy = next_iy;
                            break;
                        }
                    }
                } while (curPix != startPix);
            }

        }

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        postProcessFastJulia(image_size);
    }

    /*private static Object[] floodFillCaches;
    private static final int INIT_CACHE_SIZE = 6000;
    private static final int MAX_IMAGE_SIZE_FOR_CACHING = 2000;
    private int floodCount;
    private int[] currentCache;*/
    
    /*private void insertToCache(int nextPix) {

        if (floodCount >= currentCache.length) {
            currentCache = Arrays.copyOf(currentCache, 2 * currentCache.length);
            floodFillCaches[threadId] = currentCache;
        }

        currentCache[floodCount] = nextPix; // if the direction was up, keep the current index in order to flood fill later
        floodCount++;
    }

    private void floodFillWithCache(int image_size, int skippedColor, int startColor, double start_val) {

        int floodPix;
        int flood_ix;
        int floodColor;
        final int culcColor = 0;

        for (int i = 0; i < floodCount; i++) {
            floodPix = currentCache[i];
            flood_ix = floodPix % image_size;

            while (true) {
                flood_ix++;

                if (flood_ix >= TOx) {
                    break;
                }

                floodPix++;

                if ((floodColor = rgbs[floodPix]) == culcColor) {
                    drawing_done++;
                    rgbs[floodPix] = skippedColor;
                    image_iterations[floodPix] = start_val;
                } else if (floodColor != startColor) {
                    break;
                }

            }

        }

        floodCount = 0;

    }
    
    public static void initFloodFillCaches(int n) {

        if (floodFillCaches != null && floodFillCaches.length == n) {
            return;
        }

        int sizePerThread = (int) (INIT_CACHE_SIZE / Math.sqrt(n) + 0.5);
        floodFillCaches = new Object[n];
        for (int i = 0; i < n; i++) {
            floodFillCaches[i] = new int[sizePerThread];
        }

    }*/
}
