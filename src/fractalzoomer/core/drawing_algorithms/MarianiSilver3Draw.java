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
import fractalzoomer.core.TaskDraw;
import fractalzoomer.core.antialiasing.AntialiasingAlgorithm;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.ExpandingQueueSquare;
import fractalzoomer.utils.Square;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

/**
 *
 * @author hrkalona2
 */
public class MarianiSilver3Draw extends MarianiSilverDraw {

    public MarianiSilver3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilver3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilver3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public MarianiSilver3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public MarianiSilver3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilver3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    @Override
    protected void drawIterations(int image_size, boolean polar) throws StopSuccessiveRefinementException {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int pixel_percent = (image_size * image_size) / 100;

        int loc;

        //ptr.setWholeImageDone(true);

        boolean escaped_val;
        double f_val;

        task_completed = 0;

        long time = System.currentTimeMillis();

        int x = FROMx;
        location.precalculateX(x);
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;

            if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
                task_calculated++;
                task_completed++;
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
                    task_calculated++;
                    task_completed++;
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
                task_calculated++;
                task_completed++;
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
                    task_calculated++;
                    task_completed++;
                }
                drawing_done++;

            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ExpandingQueueSquare squares = new ExpandingQueueSquare(RENDER_USING_DFS ? INIT_QUEUE_SIZE : INIT_QUEUE_SIZE2);

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

            if(RENDER_USING_DFS) {
                currentSquare = squares.last();
            }
            else {
                currentSquare = squares.dequeue();
            }

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
                if (isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value)) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (y = slice_FROMy + 1, loc = y * image_size + slice_FROMx, loc2 = y * image_size + slice_TOx; y <= slice_TOy - 1; y++, loc += image_size, loc2 += image_size) {
                    if (isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value)) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (canSubDivide(xLength, yLength)) {
                    if(getSplitMode(currentSquare.iteration - randomNumber) == 1) {
                        int halfY = slice_FROMy + (yLength >>> 1);
                        y = halfY;
                        location.precalculateY(y);
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                                rgbs[loc] = getFinalColor(f_val, escaped_val);
                                task_calculated++;
                                task_completed++;
                            }
                            drawing_done++;
                        }

                        Square square1 = new Square(slice_FROMx, slice_FROMy, slice_TOx, halfY, currentSquare.iteration + 1);

                        squares.enqueue(square1);

                        Square square3 = new Square(slice_FROMx, halfY, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square3);
                    }
                    else {
                        int halfX = slice_FROMx + (xLength >>> 1);
                        x = halfX;
                        location.precalculateX(x);
                        for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                            loc = y * image_size + x;
                            if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                                rgbs[loc] = getFinalColor(f_val, escaped_val);
                                task_calculated++;
                                task_completed++;
                            }
                            drawing_done++;
                        }

                        Square square2 = new Square(slice_FROMx, slice_FROMy, halfX, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square2);

                        Square square4 = new Square(halfX, slice_FROMy, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square4);
                    }
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {
                            if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                                rgbs[loc] = getFinalColor(f_val, escaped_val);
                                task_calculated++;
                                task_completed++;
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

                    for (int index = loc3; index < loc4; index++) {
                        if (rgbs[index] >>> 24 != Constants.NORMAL_ALPHA) {
                            image_iterations[index] = temp_starting_value;
                            rgbs[index] = skippedColor;
                            escaped[index] = temp_starting_escaped;
                            task_completed++;
                        }
                    }

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

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_size, null, location);
    }

    @Override
    protected boolean canSubDivide(int xLength, int yLength) {
        return (xLength >= MAX_TILE_SIZE || yLength >= MAX_TILE_SIZE) && (xLength >= 3 && yLength >= 3);
    }

    private int getSplitMode(int iteration) {
        if(TaskDraw.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM == 1) {
            return ((iteration + 1) % 2); //1, 0, 1, 0,...
        }
        else if(TaskDraw.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM == 2) {
            return iteration % 2; // 0, 1, 0, 1,...
        }
        else if(TaskDraw.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM == 3) {
            //1, 0, 0, 1, 1, 0, 0, 1, 1
            int iter = iteration + 2;
            int val = iter % 2;
            int val2 = iter % 4;
            if(val2 < 2) {
                return val;
            }
            else {
                return 1 - val;
            }
        }
        else if(TaskDraw.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM == 4) {
            //0, 1, 1, 0, 0, 1, 1, 0, 0,...
            int val = iteration % 2;
            int val2 = iteration % 4;
            if(val2 < 2) {
                return val;
            }
            else {
                return 1 - val;
            }
        }
        return -1;
    }


    @Override
    protected void drawIterationsAntialiased(int image_size, boolean polar) throws StopSuccessiveRefinementException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, PERTURBATION_THEORY  && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        initialize(location);

        int pixel_percent = (image_size * image_size) / 100;

        int loc;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR, fs.aaSigmaS);

        aa.setNeedsPostProcessing(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        int color;
        double temp_result;

        boolean escaped_val;
        double f_val;
        task_completed = 0;

        long time = System.currentTimeMillis();

        int x = FROMx;
        location.precalculateX(x);
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;

            image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
            escaped[loc] = escaped_val = iteration_algorithm.escaped();
            color = getFinalColor(f_val, escaped_val);

            if(storeExtraData) {
                pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
            }

            aa.initialize(color);

            //Supersampling
            for (int i = 0; i < supersampling_num; i++) {
                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(temp_result, escaped_val);

                if(storeExtraData) {
                    pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                }

                if(!aa.addSample(color)) {
                    break;
                }
            }

            rgbs[loc] = aa.getColor();

            drawing_done++;
            task_calculated++;
            task_completed++;
        }

        if (TOx == image_size) {
            x = TOx - 1;
            location.precalculateX(x);
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;

                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(f_val, escaped_val);

                if(storeExtraData) {
                    pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp_result, escaped_val);

                    if(storeExtraData) {
                        pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                    }

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();

                drawing_done++;
                task_calculated++;
                task_completed++;

            }
        }

        int y = FROMy;
        location.precalculateY(y);
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {

            image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
            escaped[loc] = escaped_val = iteration_algorithm.escaped();
            color = getFinalColor(f_val, escaped_val);

            if(storeExtraData) {
                pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
            }

            aa.initialize(color);

            //Supersampling
            for (int i = 0; i < supersampling_num; i++) {
                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(temp_result, escaped_val);

                if(storeExtraData) {
                    pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                }

                if(!aa.addSample(color)) {
                    break;
                }
            }

            rgbs[loc] = aa.getColor();

            drawing_done++;
            task_calculated++;
            task_completed++;
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

                if(storeExtraData) {
                    pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp_result, escaped_val);

                    if(storeExtraData) {
                        pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                    }

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();

                drawing_done++;
                task_calculated++;
                task_completed++;
            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ExpandingQueueSquare squares = new ExpandingQueueSquare(RENDER_USING_DFS ? INIT_QUEUE_SIZE : INIT_QUEUE_SIZE2);

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

            if(RENDER_USING_DFS) {
                currentSquare = squares.last();
            }
            else {
                currentSquare = squares.dequeue();
            }

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
            if(storeExtraData) {
                temp_starting_pixel_extra_data = pixelData[loc];
            }

            int loc2;
            for (loc = slice_FROMy * image_size + x, loc2 = slice_TOy * image_size + x; x <= slice_TOx; x++, loc++, loc2++) {
                if (isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value)) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (y = slice_FROMy + 1, loc = y * image_size + slice_FROMx, loc2 = y * image_size + slice_TOx; y <= slice_TOy - 1; y++, loc += image_size, loc2 += image_size) {
                    if (isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value)) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (canSubDivide(xLength, yLength)) {

                    if(getSplitMode(currentSquare.iteration - randomNumber) == 1) {
                        int halfY = slice_FROMy + (yLength >>> 1);
                        y = halfY;
                        location.precalculateY(y);
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                            escaped[loc] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            if(storeExtraData) {
                                pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if(storeExtraData) {
                                    pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                }

                                if(!aa.addSample(color)) {
                                    break;
                                }
                            }

                            rgbs[loc] = aa.getColor();

                            drawing_done++;
                            task_calculated++;
                            task_completed++;
                        }

                        Square square1 = new Square(slice_FROMx, slice_FROMy, slice_TOx, halfY, currentSquare.iteration + 1);

                        squares.enqueue(square1);

                        Square square3 = new Square(slice_FROMx, halfY, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square3);
                    }
                    else {
                        int halfX = slice_FROMx + (xLength >>> 1);
                        x = halfX;
                        location.precalculateX(x);
                        for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                            loc = y * image_size + x;

                            image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                            escaped[loc] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            if(storeExtraData) {
                                pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if(storeExtraData) {
                                    pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                }

                                if(!aa.addSample(color)) {
                                    break;
                                }
                            }

                            rgbs[loc] = aa.getColor();

                            drawing_done++;
                            task_calculated++;
                            task_completed++;

                        }

                        Square square2 = new Square(slice_FROMx, slice_FROMy, halfX, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square2);

                        Square square4 = new Square(halfX, slice_FROMy, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square4);
                    }
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                            escaped[loc] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            if(storeExtraData) {
                                pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if(storeExtraData) {
                                    pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                }

                                if(!aa.addSample(color)) {
                                    break;
                                }
                            }

                            rgbs[loc] = aa.getColor();

                            drawing_done++;
                            task_calculated++;
                            task_completed++;
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

                    for (int index = loc3; index < loc4; index++) {
                        image_iterations[index] = temp_starting_value;
                        rgbs[index] = skippedColor;
                        escaped[index] = temp_starting_escaped;
                        if(storeExtraData) {
                            pixelData[index] = new PixelExtraData(temp_starting_pixel_extra_data, skippedColor);
                        }
                        task_completed++;
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

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

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

        ExpandingQueueSquare squares = new ExpandingQueueSquare(RENDER_USING_DFS ? INIT_QUEUE_SIZE : INIT_QUEUE_SIZE2);

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

            if(RENDER_USING_DFS) {
                currentSquare = squares.last();
            }
            else {
                currentSquare = squares.dequeue();
            }

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
                if (isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value)) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (y = slice_FROMy + 1, loc = y * image_size + slice_FROMx, loc2 = y * image_size + slice_TOx; y <= slice_TOy - 1; y++, loc += image_size, loc2 += image_size) {
                    if (isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value)) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (canSubDivide(xLength, yLength)) {

                    if(getSplitMode(currentSquare.iteration - randomNumber) == 1) {
                        int halfY = slice_FROMy + (yLength >>> 1);
                        y = halfY;
                        location.precalculateY(y);
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {
                            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(f_val, escaped_val);
                        }

                        Square square1 = new Square(slice_FROMx, slice_FROMy, slice_TOx, halfY, currentSquare.iteration + 1);

                        squares.enqueue(square1);

                        Square square3 = new Square(slice_FROMx, halfY, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square3);
                    }
                    else {
                        int halfX = slice_FROMx + (xLength >>> 1);
                        x = halfX;
                        location.precalculateX(x);
                        for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                            loc = y * image_size + x;
                            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(f_val, escaped_val);
                        }

                        Square square2 = new Square(slice_FROMx, slice_FROMy, halfX, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square2);

                        Square square4 = new Square(halfX, slice_FROMy, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square4);
                    }
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

                    for (int index = loc3; index < loc4; index++) {
                        image_iterations_fast_julia[index] = temp_starting_value;
                        rgbs[index] = skippedColor;
                        escaped_fast_julia[index] = temp_starting_escaped;
                    }
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
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

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

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR, fs.aaSigmaS);

        boolean escaped_val;
        double f_val;

        boolean storeExtraData = pixelData_fast_julia != null;

        aa.setNeedsPostProcessing(needsPostProcessing());

        int x = FROMx;
        location.precalculateX(x);
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
            color = getFinalColor(f_val, escaped_val);

            if(storeExtraData) {
                pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
            }

            aa.initialize(color);

            //Supersampling
            for (int i = 0; i < supersampling_num; i++) {
                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(temp_result, escaped_val);

                if(storeExtraData) {
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

                if(storeExtraData) {
                    pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp_result, escaped_val);

                    if(storeExtraData) {
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

            if(storeExtraData) {
                pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
            }

            aa.initialize(color);

            //Supersampling
            for (int i = 0; i < supersampling_num; i++) {
                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(temp_result, escaped_val);

                if(storeExtraData) {
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

                if(storeExtraData) {
                    pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp_result, escaped_val);

                    if(storeExtraData) {
                        pixelData_fast_julia[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                    }

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();
            }
        }

        ExpandingQueueSquare squares = new ExpandingQueueSquare(RENDER_USING_DFS ? INIT_QUEUE_SIZE : INIT_QUEUE_SIZE2);

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

            if(RENDER_USING_DFS) {
                currentSquare = squares.last();
            }
            else {
                currentSquare = squares.dequeue();
            }

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
            if(storeExtraData) {
                temp_starting_pixel_extra_data = pixelData_fast_julia[loc];
            }
            
            int loc2;
            for (loc = slice_FROMy * image_size + x, loc2 = slice_TOy * image_size + x; x <= slice_TOx; x++, loc++, loc2++) {
                if (isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value)) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (y = slice_FROMy + 1, loc = y * image_size + slice_FROMx, loc2 = y * image_size + slice_TOx; y <= slice_TOy - 1; y++, loc += image_size, loc2 += image_size) {
                    if (isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value)) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (canSubDivide(xLength, yLength)) {

                    if(getSplitMode(currentSquare.iteration - randomNumber) == 1) {
                        int halfY = slice_FROMy + (yLength >>> 1);
                        y = halfY;
                        location.precalculateY(y);
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            if(storeExtraData) {
                                pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if(storeExtraData) {
                                    pixelData_fast_julia[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                }

                                if(!aa.addSample(color)) {
                                    break;
                                }
                            }

                            rgbs[loc] = aa.getColor();
                        }

                        Square square1 = new Square(slice_FROMx, slice_FROMy, slice_TOx, halfY, currentSquare.iteration + 1);

                        squares.enqueue(square1);

                        Square square3 = new Square(slice_FROMx, halfY, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square3);
                    }
                    else {
                        int halfX = slice_FROMx + (xLength >>> 1);
                        x = halfX;
                        location.precalculateX(x);
                        for (y = slice_FROMy + 1; y < slice_TOy; y++) {

                            loc = y * image_size + x;

                            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            if(storeExtraData) {
                                pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if (storeExtraData) {
                                    pixelData_fast_julia[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                }

                                if (!aa.addSample(color)) {
                                    break;
                                }
                            }

                            rgbs[loc] = aa.getColor();
                        }

                        Square square2 = new Square(slice_FROMx, slice_FROMy, halfX, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square2);

                        Square square4 = new Square(halfX, slice_FROMy, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                        squares.enqueue(square4);
                    }
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {
                            image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                            escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            if(storeExtraData) {
                                pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if(storeExtraData) {
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

                    for (int index = loc3; index < loc4; index++) {
                        image_iterations_fast_julia[index] = temp_starting_value;
                        rgbs[index] = skippedColor;
                        escaped_fast_julia[index] = temp_starting_escaped;
                        if(storeExtraData) {
                            pixelData_fast_julia[index] = new PixelExtraData(temp_starting_pixel_extra_data, skippedColor);
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
