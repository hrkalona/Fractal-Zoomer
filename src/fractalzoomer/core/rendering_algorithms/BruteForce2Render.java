
package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.antialiasing.AntialiasingAlgorithm;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MinimalRendererWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.Square;
import fractalzoomer.utils.StopExecutionException;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona2
 */
public class BruteForce2Render extends TaskRender {

    public BruteForce2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public BruteForce2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public BruteForce2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public BruteForce2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public BruteForce2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public BruteForce2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public BruteForce2Render(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, DomainColoringSettings ds, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, boolean banded) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,    color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,  post_processing_order,  pbs,  ds, gradient_offset,  contourFactor, gps, pps, banded);
    }

    public BruteForce2Render(int action, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, DomainColoringSettings ds, boolean banded) {
        super(action, FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,  color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, color_blending,  post_processing_order, pbs, gradient_offset,  contourFactor, gps, pps, ds, banded);
    }

    @Override
    protected void render(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException, StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        int x, y, loc, iteration = 0;

        initialize(location);

        long time = System.currentTimeMillis();

        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if(currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            for(y = FROMy; y < TOy; y++) {
                location.precalculateY(y);
                for(x = FROMx, loc = y * image_width + x; x < TOx; x++, loc++) {

                    if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                        image_iterations[loc] = iteration_algorithm.calculate(location.getComplexWithX(x));
                        escaped[loc] = iteration_algorithm.escaped();
                        rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);
                        task_calculated++;
                    }
                    rendering_done_per_task[taskId]++;

                }

                updateProgress();

            }
            iteration++;
        } while (true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_width, image_height, null, location);
    }

    @Override
    protected void renderAntialiased(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException, StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        int x, y, loc, iteration = 0;
        
        int color;

        double temp_result;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        initialize(location);

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        double f_val;
        boolean escaped_val;

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

            for (y = FROMy; y < TOy; y++) {
                location.precalculateY(y);
                for (x = FROMx, loc = y * image_width + x; x < TOx; x++, loc++) {

                    image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                    escaped[loc] = escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(f_val, escaped_val);

                    if (storeExtraData) {
                        pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                    }

                    aa.initialize(color);

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {
                        temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                        escaped_val = iteration_algorithm.escaped();
                        color = getFinalColor(temp_result, escaped_val);

                        if (storeExtraData) {
                            pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                        }

                        if (!aa.addSample(color)) {
                            break;
                        }
                    }

                    rgbs[loc] = aa.getColor();

                    rendering_done_per_task[taskId]++;
                    task_calculated++;
                }

                updateProgress();

            }
            iteration++;
        } while (true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_width, image_height, aa, location);
        
    }

    @Override
    protected void renderFastJulia(int image_size, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_size, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initializeFastJulia(location);

        int x, y, loc, iteration = 0;

        initializeRectangleAreasQueue(image_size, image_size);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            for (y = FROMy; y < TOy; y++) {
                location.precalculateY(y);
                for (x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(location.getComplexWithX(x));
                    escaped_fast_julia[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc], escaped_fast_julia[loc]);
                }

            }
            iteration++;
        } while (true);

        postProcessFastJulia(image_size, null, location);
        
    }

    @Override
    protected void renderFastJuliaAntialiased(int image_size, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_size, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        initializeFastJulia(location);

        int x, y, loc, iteration = 0;
        int color;

        double temp_result;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData_fast_julia != null;

        double f_val;
        boolean escaped_val;

        initializeRectangleAreasQueue(image_size, image_size);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            for (y = FROMy; y < TOy; y++) {
                location.precalculateY(y);
                for (x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {

                    image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                    escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(f_val, escaped_val);

                    if (storeExtraData) {
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

            }
            iteration++;
        } while (true);

        postProcessFastJulia(image_size, aa, location);
        
    }

    @Override
    protected void render3D(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, detail, detail, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        double[] temp;

        int image_size = Math.min(image_width, image_height);
        int w2x = (int)(image_width * 0.5);
        int w2y = (int)(image_height * 0.5);
        double w2 = image_size * 0.5;

        double d = image_size / (double) detail;

        boolean escaped_val;
        double f_val;
        int iteration = 0;

        long time = System.currentTimeMillis();

        initializeRectangleAreasQueue(detail, detail);

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
                for (int x = FROMx, loc = y * detail + x; x < TOx; x++, loc++) {
                    temp = iteration_algorithm.calculate3D(location.getComplexWithX(x));
                    image_iterations[loc] = f_val = temp[1];
                    vert[x][y] = fractional_transfer_3d(temp[0]);
                    escaped[loc] = escaped_val = iteration_algorithm.escaped();
                    vert_color[x][y] = getFinalColor(f_val, escaped_val);

                    rendering_done_per_task[taskId]++;
                    task_calculated++;
                }

                updateProgress();
            }
            iteration++;
        } while (true);

        heightProcessing();

        calculate3DVectors(d, w2);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(detail, detail, null, location);
        }
        catch (StopSuccessiveRefinementException ex) {}

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(image_size, w2x, w2y, true, 1);

        }

    }

    @Override
    protected void render3DAntialiased(int image_width, int image_height, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, detail, detail, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        initialize(location);

        double[] temp;

        int image_size = Math.min(image_width, image_height);
        int w2x = (int)(image_width * 0.5);
        int w2y = (int)(image_height * 0.5);
        double w2 = image_size * 0.5;

        double d = image_size / (double) detail;

        int iteration = 0;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int temp_samples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(temp_samples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        int color;

        double height;

        boolean escaped_val;
        double f_val;
        boolean storeExtraData = pixelData != null;

        long time = System.currentTimeMillis();

        initializeRectangleAreasQueue(detail, detail);

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
                for (int x = FROMx, loc = y * detail + x; x < TOx; x++, loc++) {
                    temp = iteration_algorithm.calculate3D(location.getComplexWithX(x));
                    image_iterations[loc] = f_val = temp[1];
                    height = temp[0];
                    escaped[loc] = escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(f_val, escaped_val);

                    if(storeExtraData) {
                        pixelData[loc].set(0, color, f_val, escaped_val, temp_samples);
                    }

                    aa.initialize(color);

                    //Supersampling
                    for (int k = 0; k < supersampling_num; k++) {
                        temp = iteration_algorithm.calculate3D(location.getAntialiasingComplex(k, loc));
                        escaped_val = iteration_algorithm.escaped();
                        color = getFinalColor(temp[1], escaped_val);

                        if(storeExtraData) {
                            pixelData[loc].set(k + 1, color, temp[1], escaped_val, temp_samples);
                        }

                        height += temp[0];

                        if(!aa.addSample(color)) {
                            break;
                        }
                    }

                    vert[x][y] = fractional_transfer_3d((height / temp_samples));
                    vert_color[x][y] = aa.getColor();

                    rendering_done_per_task[taskId]++;
                    task_calculated++;
                }

                updateProgress();
            }
            iteration++;
        } while (true);

        heightProcessing();

        calculate3DVectors(d, w2);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(detail, detail, aa, location);
        }catch (StopSuccessiveRefinementException ex) {}

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(image_size, w2x, w2y, true, 1);

        }

    }

    @Override
    protected void renderDomain3D(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, detail, detail, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);

        int image_size = Math.min(image_width, image_height);
        int w2x = (int)(image_width * 0.5);
        int w2y = (int)(image_height * 0.5);
        double w2 = image_size * 0.5;

        double d = image_size / (double) detail;

        int iteration = 0;

        long time = System.currentTimeMillis();

        initializeRectangleAreasQueue(detail, detail);

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
                for (int x = FROMx, loc = y * detail + x; x < TOx; x++, loc++) {
                    Complex a = iteration_algorithm.calculateDomain(location.getComplexWithX(x));
                    double height = getDomainHeight(a);

                    vert[x][y] = fractional_transfer_3d(calaculateDomainColoringHeight(height));
                    vert_color[x][y] = domain_color.getDomainColor(a);
                    image_iterations[loc] = scaleDomainHeight(height);

                    rendering_done_per_task[taskId]++;
                    task_calculated++;
                }

                updateProgress();
            }
            iteration++;
        } while (true);

        heightProcessing();

        calculate3DVectors(d, w2);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(detail, detail, null, location);
        }catch (StopSuccessiveRefinementException ex) {}

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(image_size, w2x, w2y, true, 1);

        }

    }

    @Override
    protected void renderDomain3DAntialiased(int image_width, int image_height, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, detail, detail, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        int image_size = Math.min(image_width, image_height);
        int w2x = (int)(image_width * 0.5);
        int w2y = (int)(image_height * 0.5);
        double w2 = image_size * 0.5;

        double d = image_size / (double) detail;

        int iteration = 0;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int temp_samples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(temp_samples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        int color;

        double height;

        double f_val;

        boolean storeExtraData = pixelData != null;

        long time = System.currentTimeMillis();

        initializeRectangleAreasQueue(detail, detail);

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
                for (int x = FROMx, loc = y * detail + x; x < TOx; x++, loc++) {
                    Complex a = iteration_algorithm.calculateDomain(location.getComplexWithX(x));
                    double heightVal = getDomainHeight(a);
                    image_iterations[loc] = f_val = scaleDomainHeight(heightVal);

                    height = calaculateDomainColoringHeight(heightVal);
                    color = domain_color.getDomainColor(a);

                    if(storeExtraData) {
                        pixelData[loc].set(0, color, f_val, true, temp_samples);
                    }

                    aa.initialize(color);

                    //Supersampling
                    for (int k = 0; k < supersampling_num; k++) {
                        a = iteration_algorithm.calculateDomain(location.getAntialiasingComplex(k, loc));

                        color = domain_color.getDomainColor(a);
                        heightVal = getDomainHeight(a);
                        height += calaculateDomainColoringHeight(heightVal);

                        if(storeExtraData) {
                            f_val = scaleDomainHeight(heightVal);
                            pixelData[loc].set(k + 1, color, f_val, true, temp_samples);
                        }

                        if(!aa.addSample(color)) {
                            break;
                        }
                    }

                    vert[x][y] = fractional_transfer_3d((height / temp_samples));
                    vert_color[x][y] = aa.getColor();

                    rendering_done_per_task[taskId]++;
                    task_calculated++;
                }

                updateProgress();
            }
            iteration++;
        } while (true);

        heightProcessing();

        calculate3DVectors(d, w2);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(detail, detail, aa, location);
        }catch (StopSuccessiveRefinementException ex) {}

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(image_size, w2x, w2y, true, 1);

        }

    }
}
