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

import fractalzoomer.core.TaskDraw;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
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
@Deprecated
public class TiledGuessingDraw extends TaskDraw {

    private static final int TILED_GUESSING_SLICES = 10;

    @Override
    protected void drawIterations(int image_size, boolean polar) throws StopSuccessiveRefinementException {

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

        int x = 0;
        int y = 0;
        boolean whole_area;
        int step;

        double temp_starting_pixel_cicle;
        int temp_starting_pixel_color;
        boolean temp_starting_escaped;

        int thread_size_width = TOx - FROMx;
        int thread_size_height = TOy - FROMy;

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;
        int loc;

        int notCalculated = Constants.EMPTY_COLOR;

        int skippedColor;

        task_completed = 0;

        long time = System.currentTimeMillis();

        for (int i = 0; i < TILED_GUESSING_SLICES; i++) {
            slice_FROMy = FROMy + i * thread_size_height / TILED_GUESSING_SLICES;
            slice_TOy = FROMy + (i + 1) * (thread_size_height) / TILED_GUESSING_SLICES;
            for (int j = 0; j < TILED_GUESSING_SLICES; j++) {
                slice_FROMx = FROMx + j * (thread_size_width) / TILED_GUESSING_SLICES;
                slice_TOx = FROMx + (j + 1) * (thread_size_width) / TILED_GUESSING_SLICES;

                int temp = (slice_TOy - slice_FROMy + 1) / 2;

                for (y = slice_FROMy, whole_area = true, step = 0; step < temp; step++, whole_area = true) {

                    x = slice_FROMx + step;

                    loc = y * image_size + x;

                    temp_starting_pixel_cicle = image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                    temp_starting_escaped = escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = temp_starting_pixel_color = getFinalColor(temp_starting_pixel_cicle, temp_starting_escaped);

                    drawing_done++;
                    task_calculated++;
                    task_completed++;

                    for (x++, loc++; x < slice_TOx - step; x++, loc++) {
                        if (rgbs[loc] == notCalculated) {
                            image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithX(x));
                            escaped[loc] = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                            if (rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            task_calculated++;
                            task_completed++;
                        }

                    }

                    for (x--, y++; y < slice_TOy - step; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithY(y));
                            escaped[loc] = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                            if (rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            task_calculated++;
                            task_completed++;
                        }
                    }

                    for (y--, x--, loc = y * image_size + x; x >= slice_FROMx + step; x--, loc--) {
                        if (rgbs[loc] == notCalculated) {
                            image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithX(x));
                            escaped[loc] = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                            if (rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            task_calculated++;
                            task_completed++;
                        }
                    }

                    for (x++, y--; y > slice_FROMy + step; y--) {
                        loc = y * image_size + x;
                        if (rgbs[loc] == notCalculated) {
                            image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithY(y));
                            escaped[loc] = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                            if (rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            task_calculated++;
                            task_completed++;
                        }
                    }
                    y++;
                    x++;

                    if (drawing_done / pixel_percent >= 1) {
                        update(drawing_done);
                        drawing_done = 0;
                    }

                    if (whole_area) {
                        int temp6 = step + 1;
                        int temp1 = slice_TOx - temp6;
                        int temp2 = slice_TOy - temp6;

                        int temp3;
                        int temp4;

                        int chunk = temp1 - x;

                        skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, randomNumber + step);

                        for (int k = y; k < temp2; k++) {
                            int temploc = k * image_size;
                            temp3 = temploc + x;
                            temp4 = temploc + temp1;
                            Arrays.fill(image_iterations, temp3, temp4, temp_starting_pixel_cicle);
                            Arrays.fill(rgbs, temp3, temp4, skippedColor);
                            Arrays.fill(escaped, temp3, temp4, temp_starting_escaped);
                            drawing_done += chunk;
                            task_completed += chunk;
                        }

                        if (drawing_done / pixel_percent >= 1) {
                            update(drawing_done);
                            drawing_done = 0;
                        }
                        break;
                    }
                }
            }
        }

        if (SKIPPED_PIXELS_ALG == 4) {
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
