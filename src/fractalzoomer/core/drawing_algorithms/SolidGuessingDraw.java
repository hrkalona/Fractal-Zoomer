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

@Deprecated
public class SolidGuessingDraw extends TaskDraw {

    private static final int SOLID_GUESSING_PIXEL_STEP = 8;

    @Override
    protected void drawIterations(int image_size, boolean polar) throws StopSuccessiveRefinementException {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int pixel_percent = (image_size * image_size) / 100;

        int loc;

        int prevColor = 0;
        int currentColor;
        double val;
        boolean esc;

        task_completed = 0;

        long time = System.currentTimeMillis();

        for(int y = FROMy; y < TOy; y++) {
            location.precalculateY(y);

            int step = SOLID_GUESSING_PIXEL_STEP;

            for(int x = FROMx; x < TOx; x += step) {

                loc = y * image_size + x;

                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = val = iteration_algorithm.calculate(location.getComplexWithX(x));
                    escaped[loc] = esc = iteration_algorithm.escaped();
                    rgbs[loc] = currentColor = getFinalColor(val, esc);
                    task_calculated++;
                    task_completed++;

                    if(x != FROMx && step > 1) {
                        int chunk = step - 1;

                        if(currentColor == prevColor) {
                            int skippedColor = getColorForSkippedPixels(currentColor, randomNumber);
                            int temp3 = loc - chunk;

                            for (int index = temp3; index < loc; index++) {
                                if (rgbs[index] >>> 24 != Constants.NORMAL_ALPHA) {
                                    image_iterations[index] = val;
                                    rgbs[index] = skippedColor;
                                    escaped[index] = esc;
                                    task_completed++;
                                }
                            }

                            drawing_done += chunk;

                        }
                        else {
                            for(int x1 = x - chunk, loc1 = y * image_size + x1 ; x1 < x; x1++, loc1++) {

                                if(rgbs[loc1] >>> 24 != Constants.NORMAL_ALPHA) {
                                    image_iterations[loc1] = val = iteration_algorithm.calculate(location.getComplexWithX(x1));
                                    escaped[loc1] = esc = iteration_algorithm.escaped();
                                    rgbs[loc1] = getFinalColor(val, esc);
                                    task_calculated++;
                                    task_completed++;
                                }
                                drawing_done++;

                            }
                        }
                    }

                    prevColor = currentColor;

                }

                if(x + step >= TOx) {
                    step = Math.max(x + step - TOx - 1, 1);
                }

                drawing_done++;

            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
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
