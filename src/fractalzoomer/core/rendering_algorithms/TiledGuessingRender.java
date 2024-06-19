
package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.TaskRender;
import fractalzoomer.core.location.Location;
import fractalzoomer.main.Constants;
import fractalzoomer.utils.StopSuccessiveRefinementException;

/**
 *
 * @author hrkalona2
 */
@Deprecated
public class TiledGuessingRender extends TaskRender {

    private static final int TILED_GUESSING_SLICES = 10;

    @Override
    protected void render(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int pixel_percent = (image_width * image_height) / 100;

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

                    loc = y * image_width + x;

                    temp_starting_pixel_cicle = image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                    temp_starting_escaped = escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = temp_starting_pixel_color = getFinalColor(temp_starting_pixel_cicle, temp_starting_escaped);

                    rendering_done++;
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

                            rendering_done++;
                            task_calculated++;
                            task_completed++;
                        }

                    }

                    for (x--, y++; y < slice_TOy - step; y++) {
                        loc = y * image_width + x;

                        if (rgbs[loc] == notCalculated) {
                            image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithY(y));
                            escaped[loc] = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                            if (rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            rendering_done++;
                            task_calculated++;
                            task_completed++;
                        }
                    }

                    for (y--, x--, loc = y * image_width + x; x >= slice_FROMx + step; x--, loc--) {
                        if (rgbs[loc] == notCalculated) {
                            image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithX(x));
                            escaped[loc] = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                            if (rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            rendering_done++;
                            task_calculated++;
                            task_completed++;
                        }
                    }

                    for (x++, y--; y > slice_FROMy + step; y--) {
                        loc = y * image_width + x;
                        if (rgbs[loc] == notCalculated) {
                            image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithY(y));
                            escaped[loc] = iteration_algorithm.escaped();
                            rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                            if (rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            rendering_done++;
                            task_calculated++;
                            task_completed++;
                        }
                    }
                    y++;
                    x++;

                    if (rendering_done / pixel_percent >= 1) {
                        update(rendering_done);
                        rendering_done = 0;
                    }

                    if (whole_area) {
                        int temp6 = step + 1;
                        int temp1 = slice_TOx - temp6;
                        int temp2 = slice_TOy - temp6;

                        int temp3;
                        int temp4;

                        int chunk = temp1 - x;

                        skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, step);
                        int fill_count = 0;

                        for (int k = y; k < temp2; k++) {
                            int temploc = k * image_width;
                            temp3 = temploc + x;
                            temp4 = temploc + temp1;
                            for (int index = temp3; index < temp4; index++) {
                                if (rgbs[index] == notCalculated) {
                                    image_iterations[index] = temp_starting_pixel_cicle;
                                    rgbs[index] = skippedColor;
                                    escaped[index] = temp_starting_escaped;
                                    task_completed++;
                                    fill_count++;
                                }
                            }

                            task_pixel_grouping[step] += fill_count;
                            rendering_done += chunk;
                        }

                        if (rendering_done / pixel_percent >= 1) {
                            update(rendering_done);
                            rendering_done = 0;
                        }
                        break;
                    }
                }
            }
        }

        if (SKIPPED_PIXELS_ALG == 4) {
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
