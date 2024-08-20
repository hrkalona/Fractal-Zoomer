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


@Deprecated
public class SolidGuessingRender extends TaskRender {

    private static final int SOLID_GUESSING_PIXEL_STEP = 8;

    public SolidGuessingRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    @Override
    protected void render(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException {


        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int pixel_percent = (image_width * image_height) / 100;

        int loc, iteration = 0;

        int prevColor = 0;
        int currentColor;
        double val;
        boolean esc;

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

            for (int y = FROMy; y < TOy; y++) {
                location.precalculateY(y);

                int step = SOLID_GUESSING_PIXEL_STEP;

                for (int x = FROMx; x < TOx; x += step) {

                    loc = y * image_width + x;

                    if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                        image_iterations[loc] = val = iteration_algorithm.calculate(location.getComplexWithX(x));
                        escaped[loc] = esc = iteration_algorithm.escaped();
                        rgbs[loc] = currentColor = getFinalColor(val, esc);
                        task_calculated++;
                        task_completed++;

                        if (x != FROMx && step > 1) {
                            int chunk = step - 1;

                            if (currentColor == prevColor) {
                                int skippedColor = getColorForSkippedPixels(currentColor, 0);
                                int temp3 = loc - chunk;

                                int fill_count = 0;
                                for (int index = temp3; index < loc; index++) {
                                    if (rgbs[index] >>> 24 != Constants.NORMAL_ALPHA) {
                                        image_iterations[index] = val;
                                        rgbs[index] = skippedColor;
                                        escaped[index] = esc;
                                        task_completed++;
                                        fill_count++;
                                    }
                                }

                                task_pixel_grouping[0] += fill_count;
                                rendering_done += chunk;

                            } else {
                                for (int x1 = x - chunk, loc1 = y * image_width + x1; x1 < x; x1++, loc1++) {

                                    if (rgbs[loc1] >>> 24 != Constants.NORMAL_ALPHA) {
                                        image_iterations[loc1] = val = iteration_algorithm.calculate(location.getComplexWithX(x1));
                                        escaped[loc1] = esc = iteration_algorithm.escaped();
                                        rgbs[loc1] = getFinalColor(val, esc);
                                        task_calculated++;
                                        task_completed++;
                                    }
                                    rendering_done++;

                                }
                            }
                        }

                        prevColor = currentColor;

                    }

                    if (x + step >= TOx) {
                        step = Math.max(TOx - x - 1, 1);
                    }

                    rendering_done++;

                }

                if (rendering_done / pixel_percent >= 1) {
                    update(rendering_done);
                    rendering_done = 0;
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
