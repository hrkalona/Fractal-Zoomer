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

import fractalzoomer.core.ThreadDraw;
import fractalzoomer.core.antialiasing.AntialiasingAlgorithm;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.BrokenBarrierException;

/**
 *
 * @author kaloch
 */
public class BoundaryTracingDraw extends ThreadDraw {

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
    }

    public BoundaryTracingDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
    }


    @Override
    protected void drawIterations(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (HIGH_PRECISION_CALCULATION || PERTURBATION_THEORY) && fractal.supportsPerturbationTheory());

        int pixel_percent = (image_size * image_size) / 100;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = Constants.MAGIC_ALPHA;// borderColor = 1;

        boolean startEscaped;
        
        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int[] delPix = {1, image_size, -1, -image_size};
        double start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int[] intX = {1, 0, -1, 0};
        int[] intY = {0, 1, 0, -1};

        int skippedColor;

        int last_drawing_done = 0;
        int totalPixels = (TOx - FROMx) * (TOy - FROMy);

        if(PERTURBATION_THEORY && fractal.supportsPerturbationTheory() && !HIGH_PRECISION_CALCULATION) {
            if (reference_calc_sync.getAndIncrement() == 0) {
                calculateReference(location);
            }

            try {
                reference_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }
            location.setReference(Fractal.refPoint);
        }

        Location location2 = Location.getCopy(location);

        boolean escaped_val;
        double f_val;

        //ptr.setWholeImageDone(true);

        done:
        for (y = FROMy; y < TOy; y++) {
            location.precalculateY(y);
            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                //Test if the pixel is not calculated by comparing if the Alpha component has the magic value of 0xFE
                if (rgbs[pix] >>> 24 != culcColor) {
                    continue;
                }

                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                image_iterations[pix] = start_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                escaped[pix]  = startEscaped = iteration_algorithm.escaped();
                rgbs[pix] = startColor = getFinalColor(start_val, startEscaped);
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

                        if ((nextColor = rgbs[nextPix]) >>> 24 == culcColor) {//Todo some exception here for index, cant reproduce

                            image_iterations[nextPix] = f_val = iteration_algorithm.calculate(location2.getComplex(next_ix, next_iy));
                            escaped[nextPix] = escaped_val = iteration_algorithm.escaped();
                            rgbs[nextPix] = nextColor = getFinalColor(f_val, escaped_val);
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

                                    if ((floodColor = rgbs[floodPix]) >>> 24 == culcColor) {
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
    protected void drawFastJuliaAntialiased(int image_size, boolean polar) {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        location.createAntialiasingSteps(aaMethod == 5);

        if(PERTURBATION_THEORY && fractal.supportsPerturbationTheory() && !HIGH_PRECISION_CALCULATION) {

            if (reference_calc_sync.getAndIncrement() == 0) {
                calculateReferenceFastJulia(location);
            }

            try {
                reference_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            location.setReference(Fractal.refPoint);
        }

        Location location2 = Location.getCopy(location);

        int color;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = Constants.MAGIC_ALPHA;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int[] delPix = {1, image_size, -1, -image_size};

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int[] intX = {1, 0, -1, 0};
        int[] intY = {0, 1, 0, -1};

        int skippedColor;

        double start_val;
        boolean startEscaped;

        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        boolean aaAvgWithMean = filters_options_vals[MainWindow.ANTIALIASING] / 100 == 1;
        int supersampling_num = (aaSamplesIndex == 0 ? 4 : 8 * aaSamplesIndex);

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(supersampling_num + 1, aaMethod, aaAvgWithMean);

        boolean escaped_val;
        double f_val;

        for (y = FROMy; y < TOy; y++) {
            location.precalculateY(y);
            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if (rgbs[pix] >>> 24 != culcColor) {
                    continue;
                }

                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                image_iterations_fast_julia[pix] = start_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                escaped_fast_julia[pix] = startEscaped = iteration_algorithm.escaped();
                color = getFinalColor(start_val, startEscaped);

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i));
                    color = getFinalColor(temp_result, iteration_algorithm.escaped());

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                startColor = rgbs[pix] = aa.getColor();

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

                        if ((nextColor = rgbs[nextPix]) >>> 24 == culcColor) {

                            image_iterations_fast_julia[nextPix] = f_val = iteration_algorithm.calculate(location2.getComplex(next_ix, next_iy));
                            escaped_fast_julia[nextPix] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location2.getAntialiasingComplex(i));
                                color = getFinalColor(temp_result, iteration_algorithm.escaped());

                                if(!aa.addSample(color)) {
                                    break;
                                }
                            }

                            nextColor = rgbs[nextPix] = aa.getColor();

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

                                    if ((floodColor = rgbs[floodPix]) >>> 24 == culcColor) {
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
    protected void drawIterationsAntialiased(int image_size, boolean polar) {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        location.createAntialiasingSteps(aaMethod == 5);

        int pixel_percent = (image_size * image_size) / 100;

        int color;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = Constants.MAGIC_ALPHA;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int[] delPix = {1, image_size, -1, -image_size};
        double start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int[] intX = {1, 0, -1, 0};
        int[] intY = {0, 1, 0, -1};

        int skippedColor;
        
        boolean startEscaped;

        if(PERTURBATION_THEORY  && fractal.supportsPerturbationTheory() && !HIGH_PRECISION_CALCULATION) {
            if (reference_calc_sync.getAndIncrement() == 0) {
                calculateReference(location);
            }

            try {
                reference_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            location.setReference(Fractal.refPoint);
        }

        Location location2 = Location.getCopy(location);

        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        boolean aaAvgWithMean = filters_options_vals[MainWindow.ANTIALIASING] / 100 == 1;
        int supersampling_num = (aaSamplesIndex == 0 ? 4 : 8 * aaSamplesIndex);

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(supersampling_num + 1, aaMethod, aaAvgWithMean);

        int last_drawing_done = 0;
        int totalPixels = (TOx - FROMx) * (TOy - FROMy);

        boolean escaped_val;
        double f_val;

        done:
        for (y = FROMy; y < TOy; y++) {
            location.precalculateY(y);
            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if (rgbs[pix] >>> 24 != culcColor) {
                    continue;
                }

                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                image_iterations[pix] =  start_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                escaped[pix] = startEscaped = iteration_algorithm.escaped();
                color = getFinalColor(start_val, startEscaped);

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i));
                    color = getFinalColor(temp_result, iteration_algorithm.escaped());

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                startColor = rgbs[pix] = aa.getColor();

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

                        if ((nextColor = rgbs[nextPix]) >>> 24 == culcColor) {
                            image_iterations[nextPix] = f_val = iteration_algorithm.calculate(location2.getComplex(next_ix, next_iy));
                            escaped[nextPix] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location2.getAntialiasingComplex(i));
                                color = getFinalColor(temp_result, iteration_algorithm.escaped());

                                if(!aa.addSample(color)) {
                                    break;
                                }
                            }

                            nextColor = rgbs[nextPix] = aa.getColor();

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

                                    if ((floodColor = rgbs[floodPix]) >>> 24 == culcColor) {
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
    protected void drawFastJulia(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        if(PERTURBATION_THEORY && fractal.supportsPerturbationTheory() && !HIGH_PRECISION_CALCULATION) {

            if (reference_calc_sync.getAndIncrement() == 0) {
                calculateReferenceFastJulia(location);
            }

            try {
                reference_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            location.setReference(Fractal.refPoint);
        }

        Location location2 = Location.getCopy(location);

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = Constants.MAGIC_ALPHA;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int[] delPix = {1, image_size, -1, -image_size};

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int[] intX = {1, 0, -1, 0};
        int[] intY = {0, 1, 0, -1};

        int skippedColor;
        
        boolean startEscaped;

        double start_val;

        boolean escaped_val;
        double f_val;


        for (y = FROMy; y < TOy; y++) {
            location.precalculateY(y);
            for (x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if (rgbs[pix] >>> 24 != culcColor) {
                    continue;
                }

                curPix = startPix = pix;
                curDir = dirRight;
                ix = x;
                iy = y;

                image_iterations_fast_julia[pix] = start_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                escaped_fast_julia[pix] = startEscaped = iteration_algorithm.escaped();
                rgbs[pix] = startColor = getFinalColor(start_val, startEscaped);

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

                        if ((nextColor = rgbs[nextPix]) >>> 24 == culcColor) {
                            image_iterations_fast_julia[nextPix] = f_val = iteration_algorithm.calculate(location2.getComplex(next_ix, next_iy));
                            escaped_fast_julia[nextPix] = escaped_val = iteration_algorithm.escaped();
                            rgbs[nextPix] = nextColor = getFinalColor(f_val, escaped_val);
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

                                    if ((floodColor = rgbs[floodPix]) >>> 24 == culcColor) {
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
        final int culcColor = Constants.MAGIC_ALPHA;

        for (int i = 0; i < floodCount; i++) {
            floodPix = currentCache[i];
            flood_ix = floodPix % image_size;

            while (true) {
                flood_ix++;

                if (flood_ix >= TOx) {
                    break;
                }

                floodPix++;

                if ((floodColor = rgbs[floodPix]) >>> 24 == culcColor) {
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
