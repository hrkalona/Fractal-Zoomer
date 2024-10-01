
package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.TaskRender;
import fractalzoomer.core.location.Location;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.Square;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona2
 */
@Deprecated
public class TiledGuessingRender extends TaskRender {

    private static final int TILED_GUESSING_SLICES = 10;

    public TiledGuessingRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

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

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;
        int loc;

        int skippedColor, iteration = 0;

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

            int thread_size_width = TOx - FROMx;
            int thread_size_height = TOy - FROMy;

            for (int i = 0; i < TILED_GUESSING_SLICES; i++) {
                slice_FROMy = FROMy + i * thread_size_height / TILED_GUESSING_SLICES;
                slice_TOy = FROMy + (i + 1) * (thread_size_height) / TILED_GUESSING_SLICES;
                for (int j = 0; j < TILED_GUESSING_SLICES; j++) {
                    slice_FROMx = FROMx + j * (thread_size_width) / TILED_GUESSING_SLICES;
                    slice_TOx = FROMx + (j + 1) * (thread_size_width) / TILED_GUESSING_SLICES;

                    int temp = Math.min((slice_TOx - slice_FROMx + 1) >> 1, (slice_TOy - slice_FROMy + 1) >> 1);

                    for (y = slice_FROMy, whole_area = true, step = 0; step < temp; step++, whole_area = true) {

                        x = slice_FROMx + step;

                        int xstart = slice_FROMx + step;
                        int xend = slice_TOx - step;
                        int ystart = slice_FROMy + step;
                        int yend = slice_TOy - step;
                        int xlength = xend - xstart;
                        int ylength = yend - ystart;

                        loc = y * image_width + x;

                        if (ylength == 1) {
                            calculateHorizontalLine(loc, xstart, xend, y, location);
                            break;
                        }

                        if (xlength == 1) {
                            calculateVerticalLine(loc, ystart, yend, x, location, image_width);
                            break;
                        }

                        temp_starting_pixel_cicle = image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                        temp_starting_escaped = escaped[loc] = iteration_algorithm.escaped();
                        rgbs[loc] = temp_starting_pixel_color = getFinalColor(temp_starting_pixel_cicle, temp_starting_escaped);

                        rendering_done++;
                        task_calculated++;
                        task_completed++;

                        for (x++, loc++; x < xend; x++, loc++) {
                            if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                                image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithX(x));
                                escaped[loc] = iteration_algorithm.escaped();
                                rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                                if (rgbs[loc] != temp_starting_pixel_color) {
                                    whole_area = false;
                                }
                                task_calculated++;
                                task_completed++;
                            }
                            rendering_done++;
                        }

                        for (x--, y++; y < yend; y++) {
                            loc = y * image_width + x;

                            if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                                image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithY(y));
                                escaped[loc] = iteration_algorithm.escaped();
                                rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                                if (rgbs[loc] != temp_starting_pixel_color) {
                                    whole_area = false;
                                }
                                task_calculated++;
                                task_completed++;
                            }
                            rendering_done++;
                        }

                        for (y--, x--, loc = y * image_width + x; x >= xstart; x--, loc--) {
                            if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                                image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithX(x));
                                escaped[loc] = iteration_algorithm.escaped();
                                rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                                if (rgbs[loc] != temp_starting_pixel_color) {
                                    whole_area = false;
                                }

                                task_calculated++;
                                task_completed++;
                            }
                            rendering_done++;
                        }

                        for (x++, y--; y > ystart; y--) {
                            loc = y * image_width + x;
                            if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                                image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithY(y));
                                escaped[loc] = iteration_algorithm.escaped();
                                rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                                if (rgbs[loc] != temp_starting_pixel_color) {
                                    whole_area = false;
                                }

                                task_calculated++;
                                task_completed++;
                            }
                            rendering_done++;
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
                                    if (rgbs[index] >>> 24 != Constants.NORMAL_ALPHA) {
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
            iteration++;
        } while (true);

        if (SKIPPED_PIXELS_ALG == 4) {
            renderSquares(image_width, image_height);
        }

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_width, image_height, null, location);

    }

    private void calculateHorizontalLine(int loc, int xstart, int xend, int y, Location location) {
        location.precalculateY(y);
        for (int x = xstart; x < xend; x++, loc++) {
            if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithX(x));
                escaped[loc] = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);
                task_calculated++;
                task_completed++;
            }
            rendering_done++;
        }
    }

    private void calculateVerticalLine(int loc, int ystart, int yend, int x, Location location, int image_width) {
        location.precalculateX(x);
        for (int y = ystart; y < yend; y++, loc += image_width) {
            if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithY(y));
                escaped[loc] = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);
                task_calculated++;
                task_completed++;
            }
            rendering_done++;
        }
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
