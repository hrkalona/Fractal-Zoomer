
package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.PixelExtraData;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.antialiasing.AntialiasingAlgorithm;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MinimalRendererWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.StopExecutionException;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author kaloch
 */
public class BruteForceInterleavedRender extends TaskRender {

    public BruteForceInterleavedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public BruteForceInterleavedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public BruteForceInterleavedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public BruteForceInterleavedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public BruteForceInterleavedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public BruteForceInterleavedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public BruteForceInterleavedRender(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, int color_cycling_location2, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, FiltersSettings fs, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, DomainColoringSettings ds, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, ColorCyclingSettings ccs, boolean banded) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, color_cycling_location2, fs, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,  post_processing_order,  pbs, ds, gradient_offset,  contourFactor, gps, pps, ccs, banded);
    }

    public BruteForceInterleavedRender(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, DomainColoringSettings ds, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, boolean banded) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,    color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,  post_processing_order,  pbs,  ds, gradient_offset,  contourFactor, gps, pps, banded);
    }

    public BruteForceInterleavedRender(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean render_action, MainWindow ptr, BufferedImage image, FiltersSettings fs, BlendingSettings color_blending, double contourFactor) {
        super(FROMx, TOx, FROMy, TOy, d3s, render_action, ptr, image, fs,  color_blending, contourFactor);
    }

    public BruteForceInterleavedRender(int action, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, DomainColoringSettings ds, boolean banded) {
        super(action, FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,  color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, color_blending,  post_processing_order, pbs, gradient_offset,  contourFactor, gps, pps, ds, banded);
    }

    private void renderInternal(int condition, int thread_chunk_size, int image_width, AtomicInteger dCount, Location location, int a, int b) {
        int x, y, loc;
        double f_val;
        boolean escaped_val;
        do {

            loc = thread_chunk_size * (a * dCount.getAndIncrement() + b);

            if(loc >= condition) {
                break;
            }

            for(int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {

                x = loc % image_width;
                y = loc / image_width;

                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                    escaped[loc] = escaped_val = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(f_val, escaped_val);
                    task_calculated++;
                }

                rendering_done_per_task[taskId]++;
            }

            updateProgress();

        } while(true);
    }

    @Override
    protected void render(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException, StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        int condition = image_width * image_height;

        initialize(location);

        int thread_chunk_size = getThreadChunkSize(image_width, true);

        long time = System.currentTimeMillis();
        renderInternal(condition, thread_chunk_size, image_width, normal_rendering_algorithm_pixel, location, 2, 0);
        renderInternal(condition, thread_chunk_size, image_width, normal_rendering_algorithm_pixel2, location, 2, 1);
        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_width, image_height, null, location);
    }

    private void renderAntialiasedInternal(int condition, int thread_chunk_size, int image_width, boolean storeExtraData, AntialiasingAlgorithm aa, int supersampling_num, int totalSamples, AtomicInteger dCount, Location location, int a, int b) {
        int x, y, loc;
        double f_val, temp_result;
        boolean escaped_val;
        int color;

        do {

            loc = thread_chunk_size * (a * dCount.getAndIncrement() + b);

            if(loc >= condition) {
                break;
            }

            for(int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                x = loc % image_width;
                y = loc / image_width;

                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(f_val, escaped_val);

                if(storeExtraData) {
                    pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for(int i = 0; i < supersampling_num; i++) {
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

                rendering_done_per_task[taskId]++;
                task_calculated++;
            }

            updateProgress();

        } while(true);
    }

    @Override
    protected void renderAntialiased(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException, StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        int condition = image_width * image_height;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;
        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        initialize(location);

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        int thread_chunk_size = getThreadChunkSize(image_width, true);

        long time = System.currentTimeMillis();
        renderAntialiasedInternal(condition, thread_chunk_size, image_width, storeExtraData, aa, supersampling_num, totalSamples, normal_rendering_algorithm_pixel, location, 2, 0);
        renderAntialiasedInternal(condition, thread_chunk_size, image_width, storeExtraData, aa, supersampling_num, totalSamples, normal_rendering_algorithm_pixel2, location, 2, 1);
        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_width, image_height, aa, location);
    }

    private void renderFastJuliaInternal(int condition, int thread_chunk_size, int image_size, AtomicInteger dCount, Location location, int a, int b) {
        boolean escaped_val;
        double f_val;
        int loc, x, y;

        do {

            loc = thread_chunk_size * (a * dCount.getAndIncrement() + b);

            if(loc >= condition) {
                break;
            }

            for(int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                x = loc % image_size;
                y = loc / image_size;

                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
            }

        } while(true);
    }

    @Override
    protected void renderFastJulia(int image_size, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_size, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initializeFastJulia(location);

        int condition = image_size * image_size;

        int thread_chunk_size = getThreadChunkSize(image_size, true);

        renderFastJuliaInternal(condition, thread_chunk_size, image_size, normal_rendering_algorithm_pixel, location, 2, 0);
        renderFastJuliaInternal(condition, thread_chunk_size, image_size, normal_rendering_algorithm_pixel2, location, 2, 1);

        postProcessFastJulia(image_size, null, location);

    }

    private void renderFastJuliaAntialiasedInternal(int condition, int thread_chunk_size, int image_size, boolean storeExtraData, AntialiasingAlgorithm aa, int supersampling_num, int totalSamples, AtomicInteger dCount, Location location, int a, int b) {
        int loc, x, y, color;
        double f_val, temp_result;
        boolean escaped_val;
        do {

            loc = thread_chunk_size * (a * dCount.getAndIncrement() + b);

            if(loc >= condition) {
                break;
            }

            for(int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                x = loc % image_size;
                y = loc / image_size;

                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(f_val, escaped_val);

                if(storeExtraData) {
                    pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for(int i = 0; i < supersampling_num; i++) {
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
        } while(true);
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

        int condition = image_size * image_size;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData_fast_julia != null;

        int thread_chunk_size = getThreadChunkSize(image_size, true);

        renderFastJuliaAntialiasedInternal(condition, thread_chunk_size, image_size, storeExtraData, aa, supersampling_num, totalSamples, normal_rendering_algorithm_pixel, location, 2, 0);
        renderFastJuliaAntialiasedInternal(condition, thread_chunk_size, image_size, storeExtraData, aa, supersampling_num, totalSamples, normal_rendering_algorithm_pixel2, location, 2, 1);

        postProcessFastJulia(image_size, aa, location);
    }

    private void applyPostProcessingPointFilterInternal(double[] image_iterations, boolean[] escaped, PixelExtraData[] pixelData, int condition, int thread_chunk_size, int image_width, int image_height, AtomicInteger dCount, Location location, double sizeCorr, double lightx, double lighty, AntialiasingAlgorithm aa, int[] modified, int a, int b) {
        int loc, x, y;
        do {

            loc = thread_chunk_size * (a * dCount.getAndIncrement() + b);

            if(loc >= condition) {
                break;
            }

            for(int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                x = loc % image_width;
                y = loc / image_width;

                applyPostProcessingOnPixel(loc, x, y, image_width, image_height, image_iterations, escaped, pixelData, aa, modified, sizeCorr, lightx, lighty, location);

            }

        } while(true);
    }

    @Override
    protected void applyPostProcessingPointFilter(int image_width, int image_height, double[] image_iterations, boolean[] escaped, PixelExtraData[] pixelData, AntialiasingAlgorithm aa, Location location) throws StopExecutionException {

        if(d3 || action == COLOR_CYCLING) {
            super.applyPostProcessingPointFilter(image_width, image_height, image_iterations, escaped, pixelData, aa, location);
            return;
        }

        double sizeCorr = 0, lightx = 0, lighty = 0;

        if (bms.bump_map) {
            double gradCorr = Math.pow(2, (bms.bumpMappingStrength - DEFAULT_BUMP_MAPPING_STRENGTH) * 0.05);
            sizeCorr = Math.min(image_width, image_height) / Math.pow(2, (MAX_BUMP_MAPPING_DEPTH - bms.bumpMappingDepth) * 0.16);
            double lightAngleRadians = Math.toRadians(bms.lightDirectionDegrees);
            lightx = Math.cos(lightAngleRadians) * gradCorr;
            lighty = Math.sin(lightAngleRadians) * gradCorr;
        }


        int[] modified = new int[1];

        if(aa != null) {
            modified = new int[aa.getTotalSamples()];
            aa.setNeedsAllSamples(false);
        }

        int condition = image_width * image_height;

        int thread_chunk_size = getThreadChunkSize(image_width, true);

        applyPostProcessingPointFilterInternal(image_iterations, escaped, pixelData, condition, thread_chunk_size, image_width, image_height, normal_rendering_algorithm_post_processing, location, sizeCorr, lightx, lighty, aa, modified, 2, 0);
        applyPostProcessingPointFilterInternal(image_iterations, escaped, pixelData, condition, thread_chunk_size, image_width, image_height, normal_rendering_algorithm_post_processing2, location, sizeCorr, lightx, lighty, aa, modified, 2, 1);

    }

    private void changePaletteWithAAInternal(int condition, int thread_chunk_size, AntialiasingAlgorithm aa, AtomicInteger dCount, int a, int b) {
        int loc, color;
        PixelExtraData data;
        do {

            loc = thread_chunk_size * (a * dCount.getAndIncrement() + b);

            if(loc >= condition) {
                break;
            }

            for(int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                data = pixelData[loc];
                data.update_rgb(0, color = getStandardColor(data.values[0], data.escaped[0]));

                aa.initialize(color);

                //Supersampling
                int length = data.getActualLength() - 1;
                for(int i = 0; i < length; i++) {
                    data.update_rgb(i + 1, color = getFinalColor(data.values[i + 1], data.escaped[i + 1]));

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();

                rendering_done_per_task[taskId]++;
                task_completed++;
            }

            updateProgress();

        } while(true);
    }

    @Override
    protected void changePaletteWithAA(int image_width, int image_height) throws StopSuccessiveRefinementException, StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        int totalSamples = supersampling_num + 1;
        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(true);

        int condition = image_width * image_height;

        task_completed = 0;

        int thread_chunk_size = getThreadChunkSize(image_width, true);

        long time = System.currentTimeMillis();

        changePaletteWithAAInternal(condition, thread_chunk_size, aa, normal_rendering_algorithm_apply_palette, 2, 0);
        changePaletteWithAAInternal(condition, thread_chunk_size, aa, normal_rendering_algorithm_apply_palette2, 2, 1);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_width, image_height, aa, null);

    }

    private void changePaletteInternal(int condition, int thread_chunk_size, AtomicInteger dCount, int a, int b) {
        int loc;
        do {

            loc = thread_chunk_size * (a * dCount.getAndIncrement() + b);

            if(loc >= condition) {
                break;
            }

            for(int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                if (domain_coloring) {
                    rgbs[loc] = domain_color.getDomainColor(new Complex(domain_image_data_re[loc], domain_image_data_im[loc]));
                } else {
                    rgbs[loc] = getStandardColor(image_iterations[loc], escaped[loc]);
                }

                rendering_done_per_task[taskId]++;
                task_completed++;
            }

            updateProgress();

        } while(true);
    }

    @Override
    protected void changePalette(int image_width, int image_height) throws StopSuccessiveRefinementException, StopExecutionException {

        int condition = image_width * image_height;
        task_completed = 0;

        int thread_chunk_size = getThreadChunkSize(image_width, true);

        long time = System.currentTimeMillis();

        changePaletteInternal(condition, thread_chunk_size, normal_rendering_algorithm_apply_palette, 2, 0);
        changePaletteInternal(condition, thread_chunk_size, normal_rendering_algorithm_apply_palette2, 2, 1);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_width, image_height,null, null);

    }

    private void renderDomainInternal(int condition, int thread_chunk_size, int image_width, AtomicInteger dCount, Location location, int a, int b) {
        int loc, x, y;
        do {

            loc = thread_chunk_size * (a * dCount.getAndIncrement() + b);

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                x = loc % image_width;
                y = loc / image_width;

                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    Complex val = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                    image_iterations[loc] = scaleDomainHeight(getDomainHeight(val));
                    rgbs[loc] = domain_color.getDomainColor(val);

                    if (domain_image_data_re != null && domain_image_data_im != null) {
                        domain_image_data_re[loc] = val.getRe();
                        domain_image_data_im[loc] = val.getIm();
                    }
                    task_calculated++;
                }

                rendering_done_per_task[taskId]++;
            }

            updateProgress();

        } while (true);
    }

    @Override
    protected void renderDomain(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);

        int condition = image_width * image_height;

        int thread_chunk_size = getThreadChunkSize(image_width, true);

        long time = System.currentTimeMillis();

        renderDomainInternal(condition, thread_chunk_size, image_width, normal_rendering_algorithm_pixel, location, 2, 0);
        renderDomainInternal(condition, thread_chunk_size, image_width, normal_rendering_algorithm_pixel2, location, 2, 1);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_width, image_height, null, location);
        }
        catch (StopSuccessiveRefinementException ex) {}

    }

    private void renderDomainAntialiasedInternal(int condition, int thread_chunk_size, int image_size, boolean storeExtraData, AntialiasingAlgorithm aa, int supersampling_num, int totalSamples, AtomicInteger dCount, Location location, int a, int b) {
        int loc, x, y, color;
        double f_val;
        do {

            loc = thread_chunk_size * (a * dCount.getAndIncrement() + b);

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                x = loc % image_size;
                y = loc / image_size;

                Complex val = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                image_iterations[loc] = f_val = scaleDomainHeight(getDomainHeight(val));
                color = domain_color.getDomainColor(val);

                if(storeExtraData) {
                    pixelData[loc].set(0, color, f_val, true, totalSamples);
                }

                if (domain_image_data_re != null && domain_image_data_im != null) {
                    domain_image_data_re[loc] = val.getRe();
                    domain_image_data_im[loc] = val.getIm();
                }

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    val = iteration_algorithm.calculateDomain(location.getAntialiasingComplex(i, loc));
                    color = domain_color.getDomainColor(val);

                    if(storeExtraData) {
                        f_val = scaleDomainHeight(getDomainHeight(val));
                        pixelData[loc].set(i + 1, color, f_val, true, totalSamples);
                    }

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();

                rendering_done_per_task[taskId]++;
                task_calculated++;
            }

            updateProgress();

        } while (true);
    }

    @Override
    protected void renderDomainAntialiased(int image_width, int image_height, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        int condition = image_width * image_height;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        int thread_chunk_size = getThreadChunkSize(image_width, true);

        long time = System.currentTimeMillis();

        renderDomainAntialiasedInternal(condition, thread_chunk_size, image_width, storeExtraData, aa, supersampling_num, totalSamples, normal_rendering_algorithm_pixel, location, 2, 0);
        renderDomainAntialiasedInternal(condition, thread_chunk_size, image_width, storeExtraData, aa, supersampling_num, totalSamples, normal_rendering_algorithm_pixel2, location, 2, 1);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_width, image_height, aa, location);
        } catch (StopSuccessiveRefinementException ex) {}

    }

}
