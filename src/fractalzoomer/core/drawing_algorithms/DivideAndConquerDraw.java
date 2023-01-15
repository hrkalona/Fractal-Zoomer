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

import fractalzoomer.core.PixelExtraData;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.core.antialiasing.AntialiasingAlgorithm;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.ExpandingQueueSquare;
import fractalzoomer.utils.Square;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

/**
 *
 * @author hrkalona2
 */
public class DivideAndConquerDraw extends ThreadDraw {

    private static final int MAX_TILE_SIZE = 6;
    private static final int INIT_QUEUE_SIZE = 200;

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js);
    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js);
    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
    }

    @Override
    protected void drawIterations(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

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

        int pixel_percent = (image_size * image_size) / 100;

        int loc;

        //ptr.setWholeImageDone(true);

        boolean escaped_val;
        double f_val;

        int x = FROMx;
        location.precalculateX(x);
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;

            if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
                thread_calculated++;
            }
            drawing_done++;

        }

        if (TOx == image_size) {
            x = TOx - 1;
            location.precalculateX(x);
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;

                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                    escaped[loc] = escaped_val = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(f_val, escaped_val);
                    thread_calculated++;
                }
                drawing_done++;

            }
        }

        int y = FROMy;
        location.precalculateY(y);
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {

            if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
                thread_calculated++;
            }
            drawing_done++;

        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            location.precalculateY(y);
            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {

                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                    escaped[loc] = escaped_val = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(f_val, escaped_val);
                    thread_calculated++;
                }
                drawing_done++;

            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ExpandingQueueSquare squares = new ExpandingQueueSquare(INIT_QUEUE_SIZE);

        Square square = new Square(FROMx, FROMy, TOx, TOy, randomNumber);

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
        boolean temp_starting_escaped;

        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.last();

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
            temp_starting_escaped = escaped[loc];

            int loc2;
            for (loc = slice_FROMy * image_size + x, loc2 = slice_TOy * image_size + x; x <= slice_TOx; x++, loc++, loc2++) {
                if (rgbs[loc] != temp_starting_pixel_color || rgbs[loc2] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (y = slice_FROMy + 1, loc = y * image_size + slice_FROMx, loc2 = y * image_size + slice_TOx; y <= slice_TOy - 1; y++, loc += image_size, loc2 += image_size) {
                    if (rgbs[loc] != temp_starting_pixel_color || rgbs[loc2] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    int halfY = slice_FROMy + (yLength >>> 1);
                    int halfX = slice_FROMx + (xLength >>> 1);
                    
                    y = halfY;
                    location.precalculateY(y);
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                            image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                            escaped[loc] = escaped_val = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(f_val, escaped_val);
                            thread_calculated++;
                        }
                        drawing_done++;
                    }

                    x = halfX;
                    location.precalculateX(x);
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        if (y != halfY) {
                            loc = y * image_size + x;
                            if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                                rgbs[loc] = getFinalColor(f_val, escaped_val);
                                thread_calculated++;
                            }
                            drawing_done++;
                        }
                    }

                    Square square1 = new Square(slice_FROMx, slice_FROMy, halfX, halfY,  currentSquare.iteration + 1);

                    squares.enqueue(square1);

                    Square square2 = new Square(halfX, slice_FROMy, slice_TOx, halfY, currentSquare.iteration + 1);

                    squares.enqueue(square2);

                    Square square3 = new Square(slice_FROMx, halfY, halfX, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square3);

                    Square square4 = new Square(halfX, halfY, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {
                            if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                                rgbs[loc] = getFinalColor(f_val, escaped_val);
                                thread_calculated++;
                            }
                            drawing_done++;
                        }
                    }

                    if (drawing_done / pixel_percent >= 1) {
                        update(drawing_done);
                        drawing_done = 0;
                    }
                }
            } else {
                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                int chunk = slice_TOx - x;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);

                for (int k = y; k < slice_TOy; k++) {
                    int temploc = k * image_size;
                    int loc3 = temploc + x;
                    int loc4 = temploc + slice_TOx;
                    Arrays.fill(rgbs, loc3, loc4, skippedColor);
                    Arrays.fill(image_iterations, loc3, loc4, temp_starting_value);
                    Arrays.fill(escaped, loc3, loc4, temp_starting_escaped);
                    drawing_done += chunk;
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }

                 /*ptr.getMainPanel().repaint();
                     try {
                     Thread.sleep(1); //demo
                     }
                     catch (InterruptedException ex) {}*/

            }

        } while (true);

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        postProcess(image_size, null, location);
    }


    @Override
    protected void drawIterationsAntialiased(int image_size, boolean polar) {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, PERTURBATION_THEORY  && fractal.supportsPerturbationTheory());
        location.createAntialiasingSteps(aaMethod == 5, useJitter);

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

        int pixel_percent = (image_size * image_size) / 100;

        int loc;

        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int supersampling_num = (aaSamplesIndex == 0 ? 4 : 8 * aaSamplesIndex);
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, location);


        boolean needsPostProcessing = needsPostProcessing();
        aa.setNeedsPostProcessing(needsPostProcessing);

        int color;
        double temp_result;

        boolean escaped_val;
        double f_val;

        int x = FROMx;
        location.precalculateX(x);
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;

            image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
            escaped[loc] = escaped_val = iteration_algorithm.escaped();
            color = getFinalColor(f_val, escaped_val);

            if(needsPostProcessing) {
                pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
            }

            aa.initialize(color);

            //Supersampling
            for (int i = 0; i < supersampling_num; i++) {
                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(temp_result, escaped_val);

                if(needsPostProcessing) {
                    pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                }

                if(!aa.addSample(color)) {
                    break;
                }
            }

            rgbs[loc] = aa.getColor();

            drawing_done++;
            thread_calculated++;
        }

        if (TOx == image_size) {
            x = TOx - 1;
            location.precalculateX(x);
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;

                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(f_val, escaped_val);

                if(needsPostProcessing) {
                    pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp_result, escaped_val);

                    if(needsPostProcessing) {
                        pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                    }

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();

                drawing_done++;
                thread_calculated++;

            }
        }

        int y = FROMy;
        location.precalculateY(y);
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {

            image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
            escaped[loc] = escaped_val = iteration_algorithm.escaped();
            color = getFinalColor(f_val, escaped_val);

            if(needsPostProcessing) {
                pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
            }

            aa.initialize(color);

            //Supersampling
            for (int i = 0; i < supersampling_num; i++) {
                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(temp_result, escaped_val);

                if(needsPostProcessing) {
                    pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                }

                if(!aa.addSample(color)) {
                    break;
                }
            }

            rgbs[loc] = aa.getColor();

            drawing_done++;
            thread_calculated++;
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            location.precalculateY(y);

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(f_val, escaped_val);

                if(needsPostProcessing) {
                    pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp_result, escaped_val);

                    if(needsPostProcessing) {
                        pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                    }

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();

                drawing_done++;
                thread_calculated++;
            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ExpandingQueueSquare squares = new ExpandingQueueSquare(INIT_QUEUE_SIZE);

        Square square = new Square(FROMx, FROMy, TOx, TOy, randomNumber);

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
        boolean temp_starting_escaped;
        PixelExtraData temp_starting_pixel_extra_data = null;

        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.last();

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
            temp_starting_escaped = escaped[loc];
            if(needsPostProcessing) {
                temp_starting_pixel_extra_data = pixelData[loc];
            }

            int loc2;
            for (loc = slice_FROMy * image_size + x, loc2 = slice_TOy * image_size + x; x <= slice_TOx; x++, loc++, loc2++) {
                if (rgbs[loc] != temp_starting_pixel_color || rgbs[loc2] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (y = slice_FROMy + 1, loc = y * image_size + slice_FROMx, loc2 = y * image_size + slice_TOx; y <= slice_TOy - 1; y++, loc += image_size, loc2 += image_size) {
                    if (rgbs[loc] != temp_starting_pixel_color || rgbs[loc2] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    int halfY = slice_FROMy + (yLength >>> 1);
                    int halfX = slice_FROMx + (xLength >>> 1);
                    
                    y = halfY;
                    location.precalculateY(y);
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                        escaped[loc] = escaped_val = iteration_algorithm.escaped();
                        color = getFinalColor(f_val, escaped_val);

                        if(needsPostProcessing) {
                            pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                        }

                        aa.initialize(color);

                        //Supersampling
                        for (int i = 0; i < supersampling_num; i++) {
                            temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                            escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(temp_result, escaped_val);

                            if(needsPostProcessing) {
                                pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                            }

                            if(!aa.addSample(color)) {
                                break;
                            }
                        }

                        rgbs[loc] = aa.getColor();

                        drawing_done++;
                        thread_calculated++;
                    }

                    x = halfX;
                    location.precalculateX(x);
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        if (y != halfY) {
                            loc = y * image_size + x;

                            image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                            escaped[loc] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            if(needsPostProcessing) {
                                pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if(needsPostProcessing) {
                                    pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                }

                                if(!aa.addSample(color)) {
                                    break;
                                }
                            }

                            rgbs[loc] = aa.getColor();

                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    Square square1 = new Square(slice_FROMx, slice_FROMy, halfX, halfY, currentSquare.iteration + 1);

                    squares.enqueue(square1);

                    Square square2 = new Square(halfX, slice_FROMy, slice_TOx, halfY, currentSquare.iteration + 1);

                    squares.enqueue(square2);

                    Square square3 = new Square(slice_FROMx, halfY, halfX, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square3);

                    Square square4 = new Square(halfX, halfY, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                            escaped[loc] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            if(needsPostProcessing) {
                                pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if(needsPostProcessing) {
                                    pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                }

                                if(!aa.addSample(color)) {
                                    break;
                                }
                            }

                            rgbs[loc] = aa.getColor();

                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    if (drawing_done / pixel_percent >= 1) {
                        update(drawing_done);
                        drawing_done = 0;
                    }
                }
            } else {
                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                int chunk = slice_TOx - x;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);

                for (int k = y; k < slice_TOy; k++) {
                    int temploc = k * image_size;
                    int loc3 = temploc + x;
                    int loc4 = temploc + slice_TOx;
                    Arrays.fill(rgbs, loc3, loc4, skippedColor);
                    Arrays.fill(image_iterations, loc3, loc4, temp_starting_value);
                    Arrays.fill(escaped, loc3, loc4, temp_starting_escaped);
                    if(needsPostProcessing) {
                        for(int n = loc3; n < loc4; n++) {
                            pixelData[n] = new PixelExtraData(temp_starting_pixel_extra_data, skippedColor);
                        }
                    }
                    drawing_done += chunk;
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }

            }

        } while (true);

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        postProcess(image_size, aa, location);
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

        int loc;

        boolean escaped_val;
        double f_val;

        int x = FROMx;
        location.precalculateX(x);
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
            rgbs[loc] = getFinalColor(f_val, escaped_val);
        }

        if (TOx == image_size) {
            x = TOx - 1;
            location.precalculateX(x);
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
            }
        }

        int y = FROMy;
        location.precalculateY(y);
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
            rgbs[loc] = getFinalColor(f_val, escaped_val);
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            location.precalculateY(y);
            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
            }
        }

        ExpandingQueueSquare squares = new ExpandingQueueSquare(INIT_QUEUE_SIZE);

        Square square = new Square(FROMx, FROMy, TOx, TOy, randomNumber);

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
        boolean temp_starting_escaped;

        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.last();

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
            temp_starting_escaped = escaped_fast_julia[loc];

            int loc2;
            for (loc = slice_FROMy * image_size + x, loc2 = slice_TOy * image_size + x; x <= slice_TOx; x++, loc++, loc2++) {
                if (rgbs[loc] != temp_starting_pixel_color || rgbs[loc2] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (y = slice_FROMy + 1, loc = y * image_size + slice_FROMx, loc2 = y * image_size + slice_TOx; y <= slice_TOy - 1; y++, loc += image_size, loc2 += image_size) {
                    if (rgbs[loc] != temp_starting_pixel_color || rgbs[loc2] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    int halfY = slice_FROMy + (yLength >>> 1);
                    int halfX = slice_FROMx + (xLength >>> 1);
                    
                    y = halfY;
                    location.precalculateY(y);
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {
                        image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                        escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                        rgbs[loc] = getFinalColor(f_val, escaped_val);
                    }

                    x = halfX;
                    location.precalculateX(x);
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        if (y != halfY) {
                            loc = y * image_size + x;
                            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(f_val, escaped_val);
                        }
                    }

                    Square square1 = new Square(slice_FROMx, slice_FROMy, halfX, halfY, currentSquare.iteration + 1);

                    squares.enqueue(square1);

                    Square square2 = new Square(halfX, slice_FROMy, slice_TOx, halfY, currentSquare.iteration + 1);

                    squares.enqueue(square2);

                    Square square3 = new Square(slice_FROMx, halfY, halfX, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square3);

                    Square square4 = new Square(halfX, halfY, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {
                            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(f_val, escaped_val);
                        }
                    }
                }

            } else {
                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);

                for (int k = y; k < slice_TOy; k++) {
                    int temploc = k * image_size;
                    int loc3 = temploc + x;
                    int loc4 = temploc + slice_TOx;
                    Arrays.fill(rgbs, loc3, loc4, skippedColor);
                    Arrays.fill(image_iterations_fast_julia, loc3, loc4, temp_starting_value);
                    Arrays.fill(escaped_fast_julia, loc3, loc4, temp_starting_escaped);
                }
            }

        } while (true);

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        postProcessFastJulia(image_size, null, location);

    }

    @Override
    protected void drawFastJuliaAntialiased(int image_size, boolean polar) {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        location.createAntialiasingSteps(aaMethod == 5, useJitter);

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

        int loc;

        int color;

        double temp_result;

        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int supersampling_num = (aaSamplesIndex == 0 ? 4 : 8 * aaSamplesIndex);
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, location);

        boolean escaped_val;
        double f_val;

        boolean needsPostProcessing = needsPostProcessing();
        aa.setNeedsPostProcessing(needsPostProcessing);

        int x = FROMx;
        location.precalculateX(x);
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
            color = getFinalColor(f_val, escaped_val);

            if(needsPostProcessing) {
                pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
            }

            aa.initialize(color);

            //Supersampling
            for (int i = 0; i < supersampling_num; i++) {
                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(temp_result, escaped_val);

                if(needsPostProcessing) {
                    pixelData_fast_julia[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                }

                if(!aa.addSample(color)) {
                    break;
                }
            }

            rgbs[loc] = aa.getColor();
        }

        if (TOx == image_size) {
            x = TOx - 1;
            location.precalculateX(x);
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;

                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(f_val, escaped_val);

                if(needsPostProcessing) {
                    pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp_result, escaped_val);

                    if(needsPostProcessing) {
                        pixelData_fast_julia[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                    }

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();
            }
        }

        int y = FROMy;
        location.precalculateY(y);
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
            color = getFinalColor(f_val, escaped_val);

            if(needsPostProcessing) {
                pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
            }

            aa.initialize(color);

            //Supersampling
            for (int i = 0; i < supersampling_num; i++) {
                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(temp_result, escaped_val);

                if(needsPostProcessing) {
                    pixelData_fast_julia[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                }

                if(!aa.addSample(color)) {
                    break;
                }
            }

            rgbs[loc] = aa.getColor();
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            location.precalculateY(y);
            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(f_val, escaped_val);

                if(needsPostProcessing) {
                    pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp_result, escaped_val);

                    if(needsPostProcessing) {
                        pixelData_fast_julia[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                    }

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();
            }
        }

        ExpandingQueueSquare squares = new ExpandingQueueSquare(INIT_QUEUE_SIZE);

        Square square = new Square(FROMx, FROMy, TOx, TOy, randomNumber);

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
        boolean temp_starting_escaped;
        PixelExtraData temp_starting_pixel_extra_data = null;

        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.last();

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
            temp_starting_escaped = escaped_fast_julia[loc];
            if(needsPostProcessing) {
                temp_starting_pixel_extra_data = pixelData_fast_julia[loc];
            }
            
            int loc2;
            for (loc = slice_FROMy * image_size + x, loc2 = slice_TOy * image_size + x; x <= slice_TOx; x++, loc++, loc2++) {
                if (rgbs[loc] != temp_starting_pixel_color || rgbs[loc2] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (y = slice_FROMy + 1, loc = y * image_size + slice_FROMx, loc2 = y * image_size + slice_TOx; y <= slice_TOy - 1; y++, loc += image_size, loc2 += image_size) {
                    if (rgbs[loc] != temp_starting_pixel_color || rgbs[loc2] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    int halfY = slice_FROMy + (yLength >>> 1);
                    int halfX = slice_FROMx + (xLength >>> 1);
                    
                    y = halfY;
                    location.precalculateY(y);
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                        escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                        color = getFinalColor(f_val, escaped_val);

                        if(needsPostProcessing) {
                            pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                        }

                        aa.initialize(color);

                        //Supersampling
                        for (int i = 0; i < supersampling_num; i++) {
                            temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                            escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(temp_result, escaped_val);

                            if(needsPostProcessing) {
                                pixelData_fast_julia[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                            }

                            if(!aa.addSample(color)) {
                                break;
                            }
                        }

                        rgbs[loc] = aa.getColor();
                    }

                    x = halfX;
                    location.precalculateX(x);
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        if (y != halfY) {
                            loc = y * image_size + x;

                            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            if(needsPostProcessing) {
                                pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if(needsPostProcessing) {
                                    pixelData_fast_julia[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                }

                                if(!aa.addSample(color)) {
                                    break;
                                }
                            }

                            rgbs[loc] = aa.getColor();
                        }
                    }

                    Square square1 = new Square(slice_FROMx, slice_FROMy, halfX, halfY, currentSquare.iteration + 1);

                    squares.enqueue(square1);

                    Square square2 = new Square(halfX, slice_FROMy, slice_TOx, halfY, currentSquare.iteration + 1);

                    squares.enqueue(square2);

                    Square square3 = new Square(slice_FROMx, halfY, halfX, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square3);

                    Square square4 = new Square(halfX, halfY, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {
                            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            if(needsPostProcessing) {
                                pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if(needsPostProcessing) {
                                    pixelData_fast_julia[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                }

                                if(!aa.addSample(color)) {
                                    break;
                                }
                            }

                            rgbs[loc] = aa.getColor();
                        }
                    }
                }

            } else {
                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);

                for (int k = y; k < slice_TOy; k++) {
                    int temploc = k * image_size;
                    int loc3 = temploc + x;
                    int loc4 = temploc + slice_TOx;
                    Arrays.fill(rgbs, loc3, loc4, skippedColor);
                    Arrays.fill(image_iterations_fast_julia, loc3, loc4, temp_starting_value);
                    Arrays.fill(escaped_fast_julia, loc3, loc4, temp_starting_escaped);
                    if(needsPostProcessing) {
                        for(int n = loc3; n < loc4; n++) {
                            pixelData_fast_julia[n] = new PixelExtraData(temp_starting_pixel_extra_data, skippedColor);
                        }
                    }
                }
            }

        } while (true);

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        postProcessFastJulia(image_size, aa, location);

    }

}
