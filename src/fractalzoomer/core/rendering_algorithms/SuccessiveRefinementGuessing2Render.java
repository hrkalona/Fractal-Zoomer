package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.PixelExtraData;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.antialiasing.AntialiasingAlgorithm;
import fractalzoomer.core.location.Location;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MinimalRendererWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.StopExecutionException;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import fractalzoomer.utils.WaitOnCondition;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

public class SuccessiveRefinementGuessing2Render extends SuccessiveRefinementGuessingRender {

    public SuccessiveRefinementGuessing2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public SuccessiveRefinementGuessing2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public SuccessiveRefinementGuessing2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public SuccessiveRefinementGuessing2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public SuccessiveRefinementGuessing2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public SuccessiveRefinementGuessing2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public SuccessiveRefinementGuessing2Render(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, DomainColoringSettings ds, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, boolean banded) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,    color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,  post_processing_order,  pbs,  ds, gradient_offset,  contourFactor, gps, pps, banded);
    }

    public SuccessiveRefinementGuessing2Render(int action, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, DomainColoringSettings ds, boolean banded) {
        super(action, FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,  color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, color_blending,  post_processing_order, pbs, gradient_offset,  contourFactor, gps, pps, ds, banded);
    }

    protected int getAggregateChunk(int current_chunk_size_x, int current_chunk_size_y) {

        if(SQUARE_RECT_CHUNK_AGGERAGATION == 0) {
            return TWO_PASS_SUCCESSIVE_REFINEMENT ? Math.max(current_chunk_size_x, current_chunk_size_y) : Math.min(current_chunk_size_x, current_chunk_size_y);
        }
        else {
            return TWO_PASS_SUCCESSIVE_REFINEMENT ? Math.min(current_chunk_size_x, current_chunk_size_y) : Math.max(current_chunk_size_x, current_chunk_size_y);
        }

    }

    @Override
    protected void render(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException, StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (HIGH_PRECISION_CALCULATION || PERTURBATION_THEORY) && fractal.supportsPerturbationTheory());

        initialize(location);


        int color, loc2, loc, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;

        int iteration = 0;

        int min_chunk_size = TaskRender.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskRender.GUESS_BLOCKS_SELECTION - 1);
        min_chunk_size = TWO_PASS_SUCCESSIVE_REFINEMENT ? min_chunk_size << 1 : min_chunk_size;

        int current_chunk_size2x = 0;
        int current_chunk_size2y = 0;

        int xstart = 0;
        int ystart = 0;
        int xend = 0;
        int yend = 0;

        int loca = 0;
        int locb = 0;
        int locc = 0;
        int locd = 0;

        int image_width_m1 = image_width - 1;
        int image_height_m1 = image_height - 1;

        task_completed = 0;

        long nano_time = 0;

        int current_chunk_size_x = 0;
        int current_chunk_size_y = 0;
        int prev_chunk_size_x;
        int prev_chunk_size_y;

        int stop_id = getStopAfterIter();
        int length = TWO_PASS_SUCCESSIVE_REFINEMENT ? SUCCESSIVE_REFINEMENT_CHUNK_X.length << 1 : SUCCESSIVE_REFINEMENT_CHUNK_X.length;

        for(int id = 0, id2 = 0; id < length; id++) {
            long time = System.nanoTime();
            AtomicInteger ai = successive_refinement_rendering_algorithm2_pixel[id];

            prev_chunk_size_x = current_chunk_size_x;
            prev_chunk_size_y = current_chunk_size_y;
            current_chunk_size_x = SUCCESSIVE_REFINEMENT_CHUNK_X[id2];
            current_chunk_size_y = SUCCESSIVE_REFINEMENT_CHUNK_Y[id2];

            int image_size_tile_x = image_width % current_chunk_size_x  == 0 ? image_width / current_chunk_size_x : image_width / current_chunk_size_x + 1;
            int image_size_tile_y = image_height % current_chunk_size_y  == 0 ? image_height / current_chunk_size_y : image_height / current_chunk_size_y + 1;

            int condition = (image_size_tile_x) * (image_size_tile_y);
            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL2[id2];
            boolean performSecondPassActions = !TWO_PASS_SUCCESSIVE_REFINEMENT || (id & 1) == 1;

            do {

                loc = chunk_size * ai.getAndIncrement();

                if (loc >= condition) {
                    break;
                }

                for (int count = 0; count < chunk_size && loc < condition; count++, loc++) {
                    tempx = loc % image_size_tile_x;
                    tempy = loc / image_size_tile_x;

                    x = tempx * current_chunk_size_x;
                    y = tempy * current_chunk_size_y;

                    loc2 = y * image_width + x;

                    if(!filled[loc2]) {

                        int aggregate_chunk = getAggregateChunk(current_chunk_size_x, current_chunk_size_y);
                        boolean check = performSecondPassActions && aggregate_chunk <= min_chunk_size && prev_chunk_size_x != 0 && prev_chunk_size_y != 0;
                        if(check) {
                            current_chunk_size2x = prev_chunk_size_x;
                            current_chunk_size2y = prev_chunk_size_y;

                            xstart = (x / current_chunk_size2x) * current_chunk_size2x;
                            ystart = (y / current_chunk_size2y) * current_chunk_size2y;
                            xend = xstart == image_width_m1 ? image_width_m1 : xstart + current_chunk_size2x;
                            yend = ystart == image_height_m1 ? image_height_m1 : ystart + current_chunk_size2y;

                            loca = ystart * image_width;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_width;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_width && yend < image_height && isTheSame(loca, locb, locc, locd)) {

                            boolean fill = true;
                            if(TWO_PASS_SUCCESSIVE_REFINEMENT && TWO_PASS_CHECK_CENTER) {
                                int midX = (xstart + xend) >>> 1;
                                int midY = (ystart + yend) >>> 1;

                                int midLoc = midY * image_width + midX;

                                if(!examined[midLoc]) {
                                    if (rgbs[midLoc] >>> 24 != Constants.NORMAL_ALPHA) {
                                        image_iterations[midLoc] = f_val = iteration_algorithm.calculate(location.getComplex(midX, midY));
                                        escaped[midLoc] = escaped_val = iteration_algorithm.escaped();
                                        rgbs[midLoc] = getFinalColor(f_val, escaped_val);
                                        task_calculated++;
                                        task_completed++;
                                    }

                                    examined[midLoc] = true;
                                    rendering_done_per_task[taskId]++;
                                }

                                fill = isTheSame(loca, midLoc);
                            }

                            if(fill) {
                                tempx = Math.min(image_width, x + current_chunk_size_x);
                                tempy = Math.min(image_height, y + current_chunk_size_y);

                                int start_color = rgbs[loca];
                                int skippedColor = getColorForSkippedPixels(start_color, iteration);
                                double start_iterations = image_iterations[loca];
                                boolean start_escaped = escaped[loca];
                                long fill_count = 0;

                                for (int i = y; i < tempy; i++) {
                                    for (int j = x, loc3 = i * image_width + j; j < tempx; j++, loc3++) {
                                        if (!examined[loc3]) {
                                            if (rgbs[loc3] >>> 24 != Constants.NORMAL_ALPHA) {
                                                rgbs[loc3] = start_color;
                                                image_iterations[loc3] = start_iterations;
                                                escaped[loc3] = start_escaped;
                                                task_completed++;
                                                fill_count++;
                                            }
                                            setFilledAndExaminedWithSkippedColor(examined, filled, skipped_colors, loc3, skippedColor);
                                            rendering_done_per_task[taskId]++;
                                        }
                                    }
                                }

                                task_pixel_grouping[iteration] += fill_count;
                            }
                        }
                        else if (!examined[loc2]){
                            if (rgbs[loc2] >>> 24 != Constants.NORMAL_ALPHA) {
                                image_iterations[loc2] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                                escaped[loc2] = escaped_val = iteration_algorithm.escaped();
                                rgbs[loc2] = color = getFinalColor(f_val, escaped_val);
                                task_calculated++;
                                task_completed++;
                            } else {
                                color = rgbs[loc2];
                            }

                            examined[loc2] = true;
                            rendering_done_per_task[taskId]++;

                            tempx = Math.min(image_width, x + current_chunk_size_x);
                            tempy = Math.min(image_height, y + current_chunk_size_y);

                            for (int i = y; i < tempy; i++) {
                                for (int j = x, loc3 = i * image_width + j; j < tempx; j++, loc3++) {
                                    if (rgbs[loc3] >>> 24 != Constants.NORMAL_ALPHA) {
                                        rgbs[loc3] = (color & 0xFFFFFF) | Constants.QUICKRENDER_CALCULATED_ALPHA_OFFSETED;
                                    }
                                }
                            }
                        }
                    }

                    if(USE_NON_BLOCKING_RENDERING && id > stop_id && STOP_RENDERING) {
                        break;
                    }
                }

                if(USE_NON_BLOCKING_RENDERING && id > stop_id && STOP_RENDERING) {
                    break;
                }

                updateProgress();

            } while (true);


            nano_time += System.nanoTime() - time;

            if(USE_NON_BLOCKING_RENDERING) {
                WaitOnCondition.LockRead(stop_rendering_lock);
            }

            WaitOnCondition.WaitOnCyclicBarrier(successive_refinement_rendering_algorithm_barrier);

            if(taskId == 0 && ptr != null) {
                if(id == 0) {
                    ptr.setWholeImageDone(true);
                    ptr.reloadTitle();
                    updateMode(ptr, false, iteration_algorithm.isJulia(), false, false);
                }
                ptr.getMainPanel().repaint();
            }

            if(USE_NON_BLOCKING_RENDERING) {
                if (id > stop_id && STOP_RENDERING) {
                    WaitOnCondition.UnlockRead(stop_rendering_lock);
                    throw new StopSuccessiveRefinementException();
                }
                WaitOnCondition.UnlockRead(stop_rendering_lock);
            }

            if(performSecondPassActions) {
                iteration++;
                id2++;
            }
        }

        if(SKIPPED_PIXELS_ALG != 0) {
            applySkippedColor(image_width, image_height, false);
        }

        if (SKIPPED_PIXELS_ALG == 4) {
            renderSquares(image_width, image_height);
        }

        pixel_calculation_time_per_task = nano_time / 1000000;

        postProcess(image_width, image_height, null, location);

    }

    @Override
    protected void renderFastJuliaAntialiased(int image_size, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_size, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        initialize(location);

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData_fast_julia != null;

        int color, loc2, loc, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;
        double temp_result;

        int current_chunk_size_x = 0;
        int current_chunk_size_y = 0;
        int prev_chunk_size_x;
        int prev_chunk_size_y;
        int iteration = 0;
        int min_chunk_size = TaskRender.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskRender.GUESS_BLOCKS_SELECTION - 1);
        min_chunk_size = TWO_PASS_SUCCESSIVE_REFINEMENT ? min_chunk_size << 1 : min_chunk_size;

        int current_chunk_size2x = 0;
        int current_chunk_size2y = 0;

        int xstart = 0;
        int ystart = 0;
        int xend = 0;
        int yend = 0;

        int loca = 0;
        int locb = 0;
        int locc = 0;
        int locd = 0;

        int image_size_m1 = image_size - 1;

        int length = TWO_PASS_SUCCESSIVE_REFINEMENT ? SUCCESSIVE_REFINEMENT_CHUNK_X.length << 1 : SUCCESSIVE_REFINEMENT_CHUNK_X.length;

        for(int id = 0, id2 = 0; id < length; id++) {
            AtomicInteger ai = successive_refinement_rendering_algorithm2_pixel[id];

            prev_chunk_size_x = current_chunk_size_x;
            prev_chunk_size_y = current_chunk_size_y;
            current_chunk_size_x = SUCCESSIVE_REFINEMENT_CHUNK_X[id2];
            current_chunk_size_y = SUCCESSIVE_REFINEMENT_CHUNK_Y[id2];

            int image_size_tile_x = image_size % current_chunk_size_x  == 0 ? image_size / current_chunk_size_x : image_size / current_chunk_size_x + 1;
            int image_size_tile_y = image_size % current_chunk_size_y  == 0 ? image_size / current_chunk_size_y : image_size / current_chunk_size_y + 1;

            int condition = (image_size_tile_x) * (image_size_tile_y);

            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL2[id2];
            boolean performSecondPassActions = !TWO_PASS_SUCCESSIVE_REFINEMENT || (id & 1) == 1;

            do {

                loc = chunk_size * ai.getAndIncrement();

                if (loc >= condition) {
                    break;
                }

                for (int count = 0; count < chunk_size && loc < condition; count++, loc++) {
                    tempx = loc % image_size_tile_x;
                    tempy = loc / image_size_tile_x;

                    x = tempx * current_chunk_size_x;
                    y = tempy * current_chunk_size_y;

                    loc2 = y * image_size + x;

                    if(!filled_fast_julia[loc2]) {

                        int aggregate_chunk = getAggregateChunk(current_chunk_size_x, current_chunk_size_y);
                        boolean check = performSecondPassActions && aggregate_chunk <= min_chunk_size && prev_chunk_size_x != 0 && prev_chunk_size_y != 0;
                        if(check) {
                            current_chunk_size2x = prev_chunk_size_x;
                            current_chunk_size2y = prev_chunk_size_y;

                            xstart = (x / current_chunk_size2x) * current_chunk_size2x;
                            ystart = (y / current_chunk_size2y) * current_chunk_size2y;
                            xend = xstart == image_size_m1 ? image_size_m1 : xstart + current_chunk_size2x;
                            yend = ystart == image_size_m1 ? image_size_m1 : ystart + current_chunk_size2y;

                            loca = ystart * image_size;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_size;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_size && yend < image_size && isTheSameFastJulia(loca, locb, locc, locd)) {

                            boolean fill = true;
                            if(TWO_PASS_SUCCESSIVE_REFINEMENT && TWO_PASS_CHECK_CENTER) {
                                int midX = (xstart + xend) >>> 1;
                                int midY = (ystart + yend) >>> 1;

                                int midLoc = midY * image_size + midX;

                                if(!examined_fast_julia[midLoc]) {
                                    image_iterations_fast_julia[midLoc] = f_val = iteration_algorithm.calculate(location.getComplex(midX, midY));
                                    escaped_fast_julia[midLoc] = escaped_val = iteration_algorithm.escaped();
                                    color = getFinalColor(f_val, escaped_val);

                                    examined_fast_julia[midLoc] = true;

                                    if (storeExtraData) {
                                        pixelData_fast_julia[midLoc].set(0, color, f_val, escaped_val, totalSamples);
                                    }

                                    aa.initialize(color);

                                    //Supersampling
                                    for (int i = 0; i < supersampling_num; i++) {
                                        temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, midLoc));
                                        escaped_val = iteration_algorithm.escaped();
                                        color = getFinalColor(temp_result, escaped_val);

                                        if (storeExtraData) {
                                            pixelData_fast_julia[midLoc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                        }

                                        if (!aa.addSample(color)) {
                                            break;
                                        }
                                    }

                                    rgbs[midLoc] = aa.getColor();
                                }

                                fill = isTheSameFastJulia(loca, midLoc);
                            }

                            if(fill) {
                                tempx = Math.min(image_size, x + current_chunk_size_x);
                                tempy = Math.min(image_size, y + current_chunk_size_y);

                                int start_color = rgbs[loca];
                                int skippedColor = getColorForSkippedPixels(start_color, iteration);
                                double start_iterations = image_iterations_fast_julia[loca];
                                boolean start_escaped = escaped_fast_julia[loca];

                                PixelExtraData start_extra_data = null;
                                if (storeExtraData) {
                                    start_extra_data = pixelData_fast_julia[loca];
                                }

                                for (int i = y; i < tempy; i++) {
                                    for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                                        if (!examined_fast_julia[loc3]) {
                                            rgbs[loc3] = start_color;
                                            image_iterations_fast_julia[loc3] = start_iterations;
                                            escaped_fast_julia[loc3] = start_escaped;
                                            setFilledAndExaminedWithSkippedColor(examined_fast_julia, filled_fast_julia, skipped_colors_fast_julia, loc3, skippedColor);
                                            if (storeExtraData) {
                                                pixelData_fast_julia[loc3] = new PixelExtraData(start_extra_data, skippedColor);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else if (!examined_fast_julia[loc2]){

                            image_iterations_fast_julia[loc2] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                            escaped_fast_julia[loc2] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);

                            examined_fast_julia[loc2] = true;

                            if(storeExtraData) {
                                pixelData_fast_julia[loc2].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for(int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc2));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if(storeExtraData) {
                                    pixelData_fast_julia[loc2].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                }

                                if(!aa.addSample(color)) {
                                    break;
                                }
                            }

                            rgbs[loc2] = aa.getColor();
                        }
                    }
                }

            } while (true);

            WaitOnCondition.WaitOnCyclicBarrier(successive_refinement_rendering_algorithm_barrier);

            if(performSecondPassActions) {
                iteration++;
                id2++;
            }
        }


        if(SKIPPED_PIXELS_ALG != 0) {
            applySkippedColor(image_size, image_size, true);
        }


        if (SKIPPED_PIXELS_ALG == 4) {
            renderSquares(image_size, image_size);
        }


        postProcessFastJulia(image_size, aa, location);

    }

    @Override
    protected void renderAntialiased(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException, StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        initialize(location);

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        int color, loc2, loc, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;
        double temp_result;


        int current_chunk_size_x = 0;
        int current_chunk_size_y = 0;
        int prev_chunk_size_x;
        int prev_chunk_size_y;
        int iteration = 0;
        int min_chunk_size = TaskRender.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskRender.GUESS_BLOCKS_SELECTION - 1);
        min_chunk_size = TWO_PASS_SUCCESSIVE_REFINEMENT ? min_chunk_size << 1 : min_chunk_size;

        int current_chunk_size2x = 0, current_chunk_size2y = 0;

        int xstart = 0;
        int ystart = 0;
        int xend = 0;
        int yend = 0;

        int loca = 0;
        int locb = 0;
        int locc = 0;
        int locd = 0;

        int image_width_m1 = image_width - 1;
        int image_height_m1 = image_height - 1;

        task_completed = 0;

        long nano_time = 0;

        int stop_id = getStopAfterIter();
        int length = TWO_PASS_SUCCESSIVE_REFINEMENT ? SUCCESSIVE_REFINEMENT_CHUNK_X.length << 1 : SUCCESSIVE_REFINEMENT_CHUNK_X.length;

        for(int id = 0, id2 = 0; id < length; id++) {
            long time = System.nanoTime();
            AtomicInteger ai = successive_refinement_rendering_algorithm2_pixel[id];

            prev_chunk_size_x = current_chunk_size_x;
            prev_chunk_size_y = current_chunk_size_y;
            current_chunk_size_x = SUCCESSIVE_REFINEMENT_CHUNK_X[id2];
            current_chunk_size_y = SUCCESSIVE_REFINEMENT_CHUNK_Y[id2];

            int image_size_tile_x = image_width % current_chunk_size_x  == 0 ? image_width / current_chunk_size_x : image_width / current_chunk_size_x + 1;
            int image_size_tile_y = image_height % current_chunk_size_y  == 0 ? image_height / current_chunk_size_y : image_height / current_chunk_size_y + 1;

            int condition = (image_size_tile_x) * (image_size_tile_y);
            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL2[id2];
            boolean performSecondPassActions = !TWO_PASS_SUCCESSIVE_REFINEMENT || (id & 1) == 1;

            do {

                loc = chunk_size * ai.getAndIncrement();

                if (loc >= condition) {
                    break;
                }

                for (int count = 0; count < chunk_size && loc < condition; count++, loc++) {
                    tempx = loc % image_size_tile_x;
                    tempy = loc / image_size_tile_x;

                    x = tempx * current_chunk_size_x;
                    y = tempy * current_chunk_size_y;

                    loc2 = y * image_width + x;

                    if(!filled[loc2]) {

                        int aggregate_chunk = getAggregateChunk(current_chunk_size_x, current_chunk_size_y);
                        boolean check = performSecondPassActions && aggregate_chunk <= min_chunk_size && prev_chunk_size_x != 0 && prev_chunk_size_y != 0;
                        if(check) {
                            current_chunk_size2x = prev_chunk_size_x;
                            current_chunk_size2y = prev_chunk_size_y;

                            xstart = (x / current_chunk_size2x) * current_chunk_size2x;
                            ystart = (y / current_chunk_size2y) * current_chunk_size2y;
                            xend = xstart == image_width_m1 ? image_width_m1 : xstart + current_chunk_size2x;
                            yend = ystart == image_height_m1 ? image_height_m1 : ystart + current_chunk_size2y;

                            loca = ystart * image_width;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_width;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_width && yend < image_height && isTheSame(loca, locb, locc, locd)) {

                            boolean fill = true;
                            if(TWO_PASS_SUCCESSIVE_REFINEMENT && TWO_PASS_CHECK_CENTER) {
                                int midX = (xstart + xend) >>> 1;
                                int midY = (ystart + yend) >>> 1;

                                int midLoc = midY * image_width + midX;

                                if(!examined[midLoc]) {
                                    image_iterations[midLoc] = f_val = iteration_algorithm.calculate(location.getComplex(midX, midY));
                                    escaped[midLoc] = escaped_val = iteration_algorithm.escaped();
                                    color = getFinalColor(f_val, escaped_val);
                                    task_calculated++;

                                    examined[midLoc] = true;
                                    rendering_done_per_task[taskId]++;
                                    task_completed++;

                                    if (storeExtraData) {
                                        pixelData[midLoc].set(0, color, f_val, escaped_val, totalSamples);
                                    }

                                    aa.initialize(color);

                                    //Supersampling
                                    for (int i = 0; i < supersampling_num; i++) {
                                        temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, midLoc));
                                        escaped_val = iteration_algorithm.escaped();
                                        color = getFinalColor(temp_result, escaped_val);

                                        if (storeExtraData) {
                                            pixelData[midLoc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                        }

                                        if (!aa.addSample(color)) {
                                            break;
                                        }
                                    }

                                    rgbs[midLoc] = aa.getColor();
                                }

                                fill = isTheSame(loca, midLoc);
                            }

                            if(fill) {
                                tempx = Math.min(image_width, x + current_chunk_size_x);
                                tempy = Math.min(image_height, y + current_chunk_size_y);

                                int start_color = rgbs[loca];
                                int skippedColor = getColorForSkippedPixels(start_color, iteration);
                                double start_iterations = image_iterations[loca];
                                boolean start_escaped = escaped[loca];

                                PixelExtraData start_extra_data = null;
                                if (storeExtraData) {
                                    start_extra_data = pixelData[loca];
                                }

                                long fill_count = 0;
                                for (int i = y; i < tempy; i++) {
                                    for (int j = x, loc3 = i * image_width + j; j < tempx; j++, loc3++) {
                                        if (!examined[loc3]) {
                                            rgbs[loc3] = start_color;
                                            image_iterations[loc3] = start_iterations;
                                            escaped[loc3] = start_escaped;
                                            setFilledAndExaminedWithSkippedColor(examined, filled, skipped_colors, loc3, skippedColor);
                                            if (storeExtraData) {
                                                pixelData[loc3] = new PixelExtraData(start_extra_data, skippedColor);
                                            }
                                            task_completed++;
                                            rendering_done_per_task[taskId]++;
                                            fill_count++;
                                        }
                                    }
                                }

                                task_pixel_grouping[iteration] += fill_count;
                            }
                        }
                        else if (!examined[loc2]){
                            image_iterations[loc2] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                            escaped[loc2] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);
                            task_calculated++;

                            examined[loc2] = true;
                            rendering_done_per_task[taskId]++;
                            task_completed++;

                            if (storeExtraData) {
                                pixelData[loc2].set(0, color, f_val, escaped_val, totalSamples);
                            }

                            aa.initialize(color);

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc2));
                                escaped_val = iteration_algorithm.escaped();
                                color = getFinalColor(temp_result, escaped_val);

                                if (storeExtraData) {
                                    pixelData[loc2].set(i + 1, color, temp_result, escaped_val, totalSamples);
                                }

                                if (!aa.addSample(color)) {
                                    break;
                                }
                            }

                            rgbs[loc2] = color = aa.getColor();

                            tempx = Math.min(image_width, x + current_chunk_size_x);
                            tempy = Math.min(image_height, y + current_chunk_size_y);

                            for (int i = y; i < tempy; i++) {
                                for (int j = x, loc3 = i * image_width + j; j < tempx; j++, loc3++) {
                                    rgbs[loc3] = color;
                                }
                            }
                        }
                    }

                    if(USE_NON_BLOCKING_RENDERING && id > stop_id && STOP_RENDERING) {
                        break;
                    }
                }

                if(USE_NON_BLOCKING_RENDERING && id > stop_id && STOP_RENDERING) {
                    break;
                }

                updateProgress();

            } while (true);

            nano_time += System.nanoTime() - time;

            if(USE_NON_BLOCKING_RENDERING) {
                WaitOnCondition.LockRead(stop_rendering_lock);
            }

            WaitOnCondition.WaitOnCyclicBarrier(successive_refinement_rendering_algorithm_barrier);

            if(taskId == 0 && ptr != null) {
                if(id == 0) {
                    ptr.setWholeImageDone(true);
                    ptr.reloadTitle();
                    updateMode(ptr, false, iteration_algorithm.isJulia(), false, false);
                }
                ptr.getMainPanel().repaint();
            }

            if(USE_NON_BLOCKING_RENDERING) {
                if (id > stop_id && STOP_RENDERING) {
                    WaitOnCondition.UnlockRead(stop_rendering_lock);
                    throw new StopSuccessiveRefinementException();
                }
                WaitOnCondition.UnlockRead(stop_rendering_lock);
            }

            if(performSecondPassActions) {
                iteration++;
                id2++;
            }
        }

        if(SKIPPED_PIXELS_ALG != 0) {
            applySkippedColor(image_width, image_height, false);
        }

        if (SKIPPED_PIXELS_ALG == 4) {
            renderSquares(image_width, image_height);
        }

        pixel_calculation_time_per_task = nano_time / 1000000;

        postProcess(image_width, image_height, aa, location);

    }

    @Override
    protected void renderFastJulia(int image_size, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_size, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (HIGH_PRECISION_CALCULATION || PERTURBATION_THEORY) && fractal.supportsPerturbationTheory());

        initialize(location);


        int loc2, loc, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;


        int current_chunk_size_x = 0, current_chunk_size_y = 0;
        int prev_chunk_size_x, prev_chunk_size_y;
        int iteration = 0;
        int min_chunk_size = TaskRender.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskRender.GUESS_BLOCKS_SELECTION - 1);
        min_chunk_size = TWO_PASS_SUCCESSIVE_REFINEMENT ? min_chunk_size << 1 : min_chunk_size;

        int current_chunk_size2x = 0, current_chunk_size2y = 0;

        int xstart = 0;
        int ystart = 0;
        int xend = 0;
        int yend = 0;

        int loca = 0;
        int locb = 0;
        int locc = 0;
        int locd = 0;

        int image_size_m1 = image_size - 1;

        int length = TWO_PASS_SUCCESSIVE_REFINEMENT ? SUCCESSIVE_REFINEMENT_CHUNK_X.length << 1 : SUCCESSIVE_REFINEMENT_CHUNK_X.length;

        for(int id = 0, id2 = 0; id < length; id++) {
            AtomicInteger ai = successive_refinement_rendering_algorithm2_pixel[id];

            prev_chunk_size_x = current_chunk_size_x;
            prev_chunk_size_y = current_chunk_size_y;
            current_chunk_size_x = SUCCESSIVE_REFINEMENT_CHUNK_X[id2];
            current_chunk_size_y = SUCCESSIVE_REFINEMENT_CHUNK_Y[id2];

            int image_size_tile_x = image_size % current_chunk_size_x  == 0 ? image_size / current_chunk_size_x : image_size / current_chunk_size_x + 1;
            int image_size_tile_y = image_size % current_chunk_size_y  == 0 ? image_size / current_chunk_size_y : image_size / current_chunk_size_y + 1;

            int condition = (image_size_tile_x) * (image_size_tile_y);

            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL2[id2];
            boolean performSecondPassActions = !TWO_PASS_SUCCESSIVE_REFINEMENT || (id & 1) == 1;

            do {

                loc = chunk_size * ai.getAndIncrement();

                if (loc >= condition) {
                    break;
                }

                for (int count = 0; count < chunk_size && loc < condition; count++, loc++) {
                    tempx = loc % image_size_tile_x;
                    tempy = loc / image_size_tile_x;

                    x = tempx * current_chunk_size_x;
                    y = tempy * current_chunk_size_y;

                    loc2 = y * image_size + x;

                    if(!filled_fast_julia[loc2]) {

                        int aggregate_chunk = getAggregateChunk(current_chunk_size_x, current_chunk_size_y);
                        boolean check = performSecondPassActions && aggregate_chunk <= min_chunk_size && prev_chunk_size_x != 0 && prev_chunk_size_y != 0;
                        if(check) {
                            current_chunk_size2x = prev_chunk_size_x;
                            current_chunk_size2y = prev_chunk_size_y;

                            xstart = (x / current_chunk_size2x) * current_chunk_size2x;
                            ystart = (y / current_chunk_size2y) * current_chunk_size2y;
                            xend = xstart == image_size_m1 ? image_size_m1 : xstart + current_chunk_size2x;
                            yend = ystart == image_size_m1 ? image_size_m1 : ystart + current_chunk_size2y;

                            loca = ystart * image_size;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_size;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_size && yend < image_size && isTheSameFastJulia(loca, locb, locc, locd)) {
                            boolean fill = true;
                            if(TWO_PASS_SUCCESSIVE_REFINEMENT && TWO_PASS_CHECK_CENTER) {
                                int midX = (xstart + xend) >>> 1;
                                int midY = (ystart + yend) >>> 1;

                                int midLoc = midY * image_size + midX;

                                if(!examined_fast_julia[midLoc]) {
                                    image_iterations_fast_julia[midLoc] = f_val = iteration_algorithm.calculate(location.getComplex(midX, midY));
                                    escaped_fast_julia[midLoc] = escaped_val = iteration_algorithm.escaped();
                                    rgbs[midLoc] = getFinalColor(f_val, escaped_val);

                                    examined_fast_julia[midLoc] = true;
                                }

                                fill = isTheSameFastJulia(loca, midLoc);
                            }

                            if(fill) {
                                tempx = Math.min(image_size, x + current_chunk_size_x);
                                tempy = Math.min(image_size, y + current_chunk_size_y);

                                int start_color = rgbs[loca];
                                int skippedColor = getColorForSkippedPixels(start_color, iteration);
                                double start_iterations = image_iterations_fast_julia[loca];
                                boolean start_escaped = escaped_fast_julia[loca];

                                for (int i = y; i < tempy; i++) {
                                    for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                                        if (!examined_fast_julia[loc3]) {
                                            rgbs[loc3] = start_color;
                                            image_iterations_fast_julia[loc3] = start_iterations;
                                            escaped_fast_julia[loc3] = start_escaped;
                                            setFilledAndExaminedWithSkippedColor(examined_fast_julia, filled_fast_julia, skipped_colors_fast_julia, loc3, skippedColor);
                                        }
                                    }
                                }
                            }
                        }
                        else if (!examined_fast_julia[loc2]){
                            image_iterations_fast_julia[loc2] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                            escaped_fast_julia[loc2] = escaped_val = iteration_algorithm.escaped();
                            rgbs[loc2] = getFinalColor(f_val, escaped_val);

                            examined_fast_julia[loc2] = true;
                        }
                    }

                }

            } while (true);

            WaitOnCondition.WaitOnCyclicBarrier(successive_refinement_rendering_algorithm_barrier);

            if(performSecondPassActions) {
                iteration++;
                id2++;
            }
        }

        if(SKIPPED_PIXELS_ALG != 0) {
            applySkippedColor(image_size, image_size, true);
        }

        if (SKIPPED_PIXELS_ALG == 4) {
            renderSquares(image_size, image_size);
        }


        postProcessFastJulia(image_size, null, location);

    }
}
