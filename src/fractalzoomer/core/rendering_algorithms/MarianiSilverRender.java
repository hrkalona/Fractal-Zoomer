
package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.PixelExtraData;
import fractalzoomer.core.antialiasing.AntialiasingAlgorithm;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MinimalRendererWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.Square;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

/**
 *
 * @author hrkalona2
 */
public class MarianiSilverRender extends QueueBasedRender {
    public static final int MAX_TILE_SIZE = 6;

    public MarianiSilverRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilverRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilverRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public MarianiSilverRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public MarianiSilverRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilverRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    @Override
    protected void render(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int loc;

        //ptr.setWholeImageDone(true);

        boolean escaped_val;
        double f_val;

        task_completed = 0;

        createQueueSquare();

        ArrayList<Square> squares = new ArrayList<>();

        long time = System.currentTimeMillis();

        int iteration = 0;
        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }

            squares.add(currentSquare);

            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            int x = FROMx;
            if (!perform_initial_work_stealing) {
                location.precalculateX(x);
            }
            for (int y = FROMy; y < TOy; y++) {
                loc = y * image_width + x;
                if (perform_initial_work_stealing) {
                    firstIndexesLocal.add(loc);
                    continue;
                }

                if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                    escaped[loc] = escaped_val = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(f_val, escaped_val);
                    task_calculated++;
                    task_completed++;
                }
                if(perform_work_stealing_and_wait) {
                    rendering_done_per_task[taskId]++;
                }
                else {
                    rendering_done++;
                }

            }

            if (TOx == image_width) {
                x = TOx - 1;
                if (!perform_initial_work_stealing) {
                    location.precalculateX(x);
                }
                for (int y = FROMy + 1; y < TOy; y++) {
                    loc = y * image_width + x;
                    if (perform_initial_work_stealing) {
                        firstIndexesLocal.add(loc);
                        continue;
                    }

                    if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                        image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                        escaped[loc] = escaped_val = iteration_algorithm.escaped();
                        rgbs[loc] = getFinalColor(f_val, escaped_val);
                        task_calculated++;
                        task_completed++;
                    }
                    if(perform_work_stealing_and_wait) {
                        rendering_done_per_task[taskId]++;
                    }
                    else {
                        rendering_done++;
                    }

                }
            }

            int y = FROMy;
            if (!perform_initial_work_stealing) {
                location.precalculateY(y);
            }
            for (x = FROMx + 1, loc = y * image_width + x; x < TOx; x++, loc++) {
                if (perform_initial_work_stealing) {
                    firstIndexesLocal.add(loc);
                    continue;
                }

                if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                    escaped[loc] = escaped_val = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(f_val, escaped_val);
                    task_calculated++;
                    task_completed++;
                }
                if(perform_work_stealing_and_wait) {
                    rendering_done_per_task[taskId]++;
                }
                else {
                    rendering_done++;
                }

            }

            if (TOy == image_height) {
                y = TOy - 1;

                int xLimit = TOx;

                if (TOx == image_width) {
                    xLimit--;
                }

                if (!perform_initial_work_stealing) {
                    location.precalculateY(y);
                }
                for (x = FROMx + 1, loc = y * image_width + x; x < xLimit; x++, loc++) {
                    if (perform_initial_work_stealing) {
                        firstIndexesLocal.add(loc);
                        continue;
                    }

                    if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                        image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                        escaped[loc] = escaped_val = iteration_algorithm.escaped();
                        rgbs[loc] = getFinalColor(f_val, escaped_val);
                        task_calculated++;
                        task_completed++;
                    }
                    if(perform_work_stealing_and_wait) {
                        rendering_done_per_task[taskId]++;
                    }
                    else {
                        rendering_done++;
                    }

                }
            }
            iteration++;
        } while (true);


        if(perform_initial_work_stealing) {
            firstRender(image_width, location);
        }

        if(perform_work_stealing_and_wait) {
            updateProgress();
        }
        else {
            if (rendering_done / progress_one_percent >= 1) {
                update(rendering_done);
                rendering_done = 0;
            }
        }

        for(Square square : squares) {
            enqueueSquare(sanitizeSquare(square, image_width, image_height));
        }

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;
        int slice_FROMxp1;
        int slice_FROMyp1;
        int x, y;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        boolean temp_starting_escaped;

        int skippedColor;

        do {

            Square currentSquare = getNextSquare();

            if (currentSquare == null) {
                break;
            }

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;
            slice_FROMxp1 = slice_FROMx + 1;
            slice_FROMyp1 = slice_FROMy + 1;

            int xLength = slice_TOx - slice_FROMx;
            int yLength = slice_TOy - slice_FROMy;

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_width + x;

            temp_starting_value = image_iterations[loc];
            temp_starting_pixel_color = rgbs[loc];
            temp_starting_escaped = escaped[loc];

            int loc2;
            int same_count = 0;
            for (loc = slice_FROMy * image_width + x, loc2 = slice_TOy * image_width + x; x <= slice_TOx; x++, loc++, loc2++) {
                if(PERIMETER_ACCURACY == 1) {
                    if (isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value)) {
                        whole_area = false;
                        break;
                    }
                }
                else {
                    same_count += (!isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) ? 1 : 0) + (!isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value) ? 1 : 0);
                }
            }

            if (whole_area) {
                for (y = slice_FROMyp1, loc = y * image_width + slice_FROMx, loc2 = y * image_width + slice_TOx; y <= slice_TOy - 1; y++, loc += image_width, loc2 += image_width) {
                    if(PERIMETER_ACCURACY == 1) {
                        if (isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value)) {
                            whole_area = false;
                            break;
                        }
                    }
                    else {
                        same_count += (!isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) ? 1 : 0) + (!isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value) ? 1 : 0);
                    }
                }
            }

            if(PERIMETER_ACCURACY < 1) {
                whole_area = same_count >= PERIMETER_ACCURACY * ((xLength + yLength) << 1);
            }

            if (!whole_area) {
                if (canSubDivide(xLength, yLength)) {
                    performSubDivision(currentSquare.iteration, slice_FROMx, slice_TOx, slice_FROMy, slice_TOy, xLength, yLength, location, image_width);
                } else {
                    normalRender(slice_FROMx, slice_TOx, slice_FROMy, slice_TOy, location, image_width);
                }
            } else {
                y = slice_FROMyp1;
                x = slice_FROMxp1;

                int chunk = slice_TOx - x;

                int currentIteration = currentSquare.iteration;
                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentIteration);

                int fill_count = 0;
                for (int k = y; k < slice_TOy; k++) {
                    int temploc = k * image_width;
                    int loc3 = temploc + x;
                    int loc4 = temploc + slice_TOx;

                    for (int index = loc3; index < loc4; index++) {
                        if (rgbs[index] >>> 24 != Constants.NORMAL_ALPHA) {
                            image_iterations[index] = temp_starting_value;
                            rgbs[index] = skippedColor;
                            escaped[index] = temp_starting_escaped;
                            task_completed++;
                            fill_count++;
                        }
                    }
                    rendering_done += chunk;
                }
                task_pixel_grouping[currentIteration] += fill_count;

                if(perform_work_stealing_and_wait) {
                    updateProgress();
                }
                else {
                    if (rendering_done / progress_one_percent >= 1) {
                        update(rendering_done);
                        rendering_done = 0;
                    }
                }

                 /*ptr.getMainPanel().repaint();
                     try {
                     Thread.sleep(1); //demo
                     }
                     catch (InterruptedException ex) {}*/

            }
//            if(USE_NON_BLOCKING_RENDERING && STOP_RENDERING) {
//                break;
//            }

        } while (true);

//        if(USE_NON_BLOCKING_RENDERING) {
//            completed_per_task[taskId] = image_width * image_height;
//            try {
//                stop_rendering_lock.lockRead();
//            } catch (InterruptedException ex) {
//
//            }
//
//            try {
//                successive_refinement_rendering_algorithm_barrier.await();
//            } catch (InterruptedException ex) {
//
//            } catch (BrokenBarrierException ex) {
//            }
//
//            if (STOP_RENDERING) {
//                stop_rendering_lock.unlockRead();
//                throw new StopSuccessiveRefinementException();
//            }
//            stop_rendering_lock.unlockRead();
//        }

        if (SKIPPED_PIXELS_ALG == 4) {
            renderSquares(image_width, image_height);
        }

        pixel_calculation_time_per_task = System.currentTimeMillis() - time - totalPauseTimeNanos / 1000000;

        if(perform_initial_work_stealing && taskId == 0) {
            firstIndexes = null;
        }

        postProcess(image_width, image_height, null, location);
    }

    protected boolean canSubDivide(int xLength, int yLength) {
        if(usesSquareChunks) {
            return xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE;
        }

        return (xLength >= MAX_TILE_SIZE || yLength >= MAX_TILE_SIZE) && (xLength >= 3 && yLength >= 3);
    }

    private void firstRender(int image_width, Location location) {
        if (!firstIndexesLocal.isEmpty()) {
            synchronized (firstIndexes) {
                firstIndexes.addAll(firstIndexesLocal);
            }
        }
        firstIndexesLocal = null;

        try {
            initialize_jobs_sync2.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int x, y, loc;
        double f_val;
        boolean escaped_val;

        int condition = firstIndexes.size();
        int index;
        do {

            index = FIRST_INDEXES_CHUNK * mariani_silver_first_rendering.getAndIncrement();

            if(index >= condition) {
                break;
            }

            for(int count = 0; count < FIRST_INDEXES_CHUNK && index < condition; count++, index++) {

                loc = firstIndexes.get(index);

                x = loc % image_width;
                y = loc / image_width;

                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                    escaped[loc] = escaped_val = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(f_val, escaped_val);
                    task_calculated++;
                    task_completed++;
                }
                if(perform_work_stealing_and_wait) {
                    rendering_done_per_task[taskId]++;
                }
                else {
                    rendering_done++;
                }
            }

            if(perform_work_stealing_and_wait) {
                updateProgress();
            }
            else {
                if (rendering_done / progress_one_percent >= 1) {
                    update(rendering_done);
                    rendering_done = 0;
                }
            }

        } while(true);
    }

    private void firstRenderAntialiased(int image_width, Location location, AntialiasingAlgorithm aa, int supersampling_num, int totalSamples, boolean storeExtraData) {
        if (!firstIndexesLocal.isEmpty()) {
            synchronized (firstIndexes) {
                firstIndexes.addAll(firstIndexesLocal);
            }
        }
        firstIndexesLocal = null;

        try {
            initialize_jobs_sync2.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int y, x, loc, color;
        double f_val, temp_result;
        boolean escaped_val;

        int condition = firstIndexes.size();
        int index;
        do {

            index = FIRST_INDEXES_CHUNK * mariani_silver_first_rendering.getAndIncrement();

            if(index >= condition) {
                break;
            }

            for(int count = 0; count < FIRST_INDEXES_CHUNK && index < condition; count++, index++) {

                loc = firstIndexes.get(index);

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

                if(perform_work_stealing_and_wait) {
                    rendering_done_per_task[taskId]++;
                }
                else {
                    rendering_done++;
                }
                task_calculated++;
                task_completed++;
            }

            if(perform_work_stealing_and_wait) {
                updateProgress();
            }
            else {
                if (rendering_done / progress_one_percent >= 1) {
                    update(rendering_done);
                    rendering_done = 0;
                }
            }

        } while(true);
    }

    private void firstRenderFastJulia(int image_size, Location location) {
        if (!firstIndexesLocal.isEmpty()) {
            synchronized (firstIndexes) {
                firstIndexes.addAll(firstIndexesLocal);
            }
        }
        firstIndexesLocal = null;

        try {
            initialize_jobs_sync2.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int y, x, loc;
        double f_val;
        boolean escaped_val;
        int condition = firstIndexes.size();
        int index;
        do {

            index = FIRST_INDEXES_CHUNK * mariani_silver_first_rendering.getAndIncrement();

            if(index >= condition) {
                break;
            }

            for(int count = 0; count < FIRST_INDEXES_CHUNK && index < condition; count++, index++) {

                loc = firstIndexes.get(index);

                x = loc % image_size;
                y = loc / image_size;

                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
            }
        } while(true);
    }

    private void firstRenderFastJuliaAntialiased(int image_size, Location location, AntialiasingAlgorithm aa, int supersampling_num, int totalSamples, boolean storeExtraData) {
        if (!firstIndexesLocal.isEmpty()) {
            synchronized (firstIndexes) {
                firstIndexes.addAll(firstIndexesLocal);
            }
        }
        firstIndexesLocal = null;

        try {
            initialize_jobs_sync2.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int y, x, loc, color;
        double f_val, temp_result;
        boolean escaped_val;
        int condition = firstIndexes.size();
        int index;
        do {

            index = FIRST_INDEXES_CHUNK * mariani_silver_first_rendering.getAndIncrement();

            if(index >= condition) {
                break;
            }

            for(int count = 0; count < FIRST_INDEXES_CHUNK && index < condition; count++, index++) {

                loc = firstIndexes.get(index);

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

    private void normalRender(int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, Location location, int image_width) {
        int y, x, loc;
        double f_val;
        boolean escaped_val;

        for (y = slice_FROMy + 1; y < slice_TOy; y++) {
            for (x = slice_FROMx + 1, loc = y * image_width + x; x < slice_TOx; x++, loc++) {
                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                    escaped[loc] = escaped_val = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(f_val, escaped_val);
                    task_calculated++;
                    task_completed++;
                }
                if(perform_work_stealing_and_wait) {
                    rendering_done_per_task[taskId]++;
                }
                else {
                    rendering_done++;
                }
            }
        }

        if(perform_work_stealing_and_wait) {
            updateProgress();
        }
        else {
            if (rendering_done / progress_one_percent >= 1) {
                update(rendering_done);
                rendering_done = 0;
            }
        }
    }

    private void normalRenderAntialiased(int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, Location location, int image_width, AntialiasingAlgorithm aa, int supersampling_num, int totalSamples, boolean storeExtraData) {

        int y, x, loc, color;
        double f_val, temp_result;
        boolean escaped_val;

        for (y = slice_FROMy + 1; y < slice_TOy; y++) {
            for (x = slice_FROMx + 1, loc = y * image_width + x; x < slice_TOx; x++, loc++) {

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

                if(perform_work_stealing_and_wait) {
                    rendering_done_per_task[taskId]++;
                }
                else {
                    rendering_done++;
                }
                task_calculated++;
                task_completed++;
            }
        }

        if(perform_work_stealing_and_wait) {
            updateProgress();
        }
        else {
            if (rendering_done / progress_one_percent >= 1) {
                update(rendering_done);
                rendering_done = 0;
            }
        }
    }

    private void normalRenderFastJulia(int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, Location location, int image_size) {
        int y, x, loc;
        double f_val;
        boolean escaped_val;
        for (y = slice_FROMy + 1; y < slice_TOy; y++) {
            for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {
                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
            }
        }
    }

    private void normalRenderFastJuliaAntialiased(int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, Location location, int image_size, AntialiasingAlgorithm aa, int supersampling_num, int totalSamples, boolean storeExtraData) {

        int y, x, loc, color;
        double f_val, temp_result;
        boolean escaped_val;
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

    protected void performSubDivision(int currentIteration, int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, int xLength, int yLength, Location location, int image_width) {
        int halfY = slice_FROMy + (yLength >>> 1);
        int halfX = slice_FROMx + (xLength >>> 1);

        int loc;
        int x;
        boolean escaped_val;
        double f_val;

        int y = halfY;
        location.precalculateY(y);
        for (x = slice_FROMx + 1, loc = y * image_width + x; x < slice_TOx; x++, loc++) {

            if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
                task_calculated++;
                task_completed++;
            }
            if(perform_work_stealing_and_wait) {
                rendering_done_per_task[taskId]++;
            }
            else {
                rendering_done++;
            }
        }

        x = halfX;
        location.precalculateX(x);
        for (y = slice_FROMy + 1; y < slice_TOy; y++) {
            if (y != halfY) {
                loc = y * image_width + x;
                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                    escaped[loc] = escaped_val = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(f_val, escaped_val);
                    task_calculated++;
                    task_completed++;
                }
                if(perform_work_stealing_and_wait) {
                    rendering_done_per_task[taskId]++;
                }
                else {
                    rendering_done++;
                }
            }
        }

        int nextIter = currentIteration + 1;
        Square square1 = new Square(slice_FROMx, slice_FROMy, halfX, halfY,  nextIter);
        Square square2 = new Square(halfX, slice_FROMy, slice_TOx, halfY, nextIter);
        Square square3 = new Square(slice_FROMx, halfY, halfX, slice_TOy, nextIter);
        Square square4 = new Square(halfX, halfY, slice_TOx, slice_TOy, nextIter);
        enqueueSquare(square1, square2, square3, square4);
    }

    protected void performSubDivisionAntialiased(int currentIteration, int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, int xLength, int yLength, Location location, int image_width, AntialiasingAlgorithm aa, int supersampling_num, int totalSamples, boolean storeExtraData) {

        int halfY = slice_FROMy + (yLength >>> 1);
        int halfX = slice_FROMx + (xLength >>> 1);
        int x;
        int loc;
        double f_val, temp_result;
        boolean escaped_val;
        int color;

        int y = halfY;
        location.precalculateY(y);
        for (x = slice_FROMx + 1, loc = y * image_width + x; x < slice_TOx; x++, loc++) {

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

            if(perform_work_stealing_and_wait) {
                rendering_done_per_task[taskId]++;
            }
            else {
                rendering_done++;
            }
            task_calculated++;
            task_completed++;
        }

        x = halfX;
        location.precalculateX(x);
        for (y = slice_FROMy + 1; y < slice_TOy; y++) {
            if (y != halfY) {
                loc = y * image_width + x;

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

                if(perform_work_stealing_and_wait) {
                    rendering_done_per_task[taskId]++;
                }
                else {
                    rendering_done++;
                }
                task_calculated++;
                task_completed++;
            }
        }

        int nextIter = currentIteration + 1;
        Square square1 = new Square(slice_FROMx, slice_FROMy, halfX, halfY,  nextIter);
        Square square2 = new Square(halfX, slice_FROMy, slice_TOx, halfY, nextIter);
        Square square3 = new Square(slice_FROMx, halfY, halfX, slice_TOy, nextIter);
        Square square4 = new Square(halfX, halfY, slice_TOx, slice_TOy, nextIter);
        enqueueSquare(square1, square2, square3, square4);
    }

    protected void performSubDivisionFastJulia(int currentIteration, int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, int xLength, int yLength, Location location, int image_size) {
        int halfY = slice_FROMy + (yLength >>> 1);
        int halfX = slice_FROMx + (xLength >>> 1);
        int x;
        int loc;
        double f_val;
        boolean escaped_val;

        int y = halfY;
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

        int nextIter = currentIteration + 1;
        Square square1 = new Square(slice_FROMx, slice_FROMy, halfX, halfY,  nextIter);
        Square square2 = new Square(halfX, slice_FROMy, slice_TOx, halfY, nextIter);
        Square square3 = new Square(slice_FROMx, halfY, halfX, slice_TOy, nextIter);
        Square square4 = new Square(halfX, halfY, slice_TOx, slice_TOy, nextIter);
        enqueueSquare(square1, square2, square3, square4);
    }

    protected void performSubDivisionFastJuliaAntialiased(int currentIteration, int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, int xLength, int yLength, Location location, int image_size, AntialiasingAlgorithm aa, int supersampling_num, int totalSamples, boolean storeExtraData) {
        int halfY = slice_FROMy + (yLength >>> 1);
        int halfX = slice_FROMx + (xLength >>> 1);

        int x;
        int loc;
        double f_val, temp_result;
        boolean escaped_val;
        int color;

        int y = halfY;
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

        x = halfX;
        location.precalculateX(x);
        for (y = slice_FROMy + 1; y < slice_TOy; y++) {
            if (y != halfY) {
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

        int nextIter = currentIteration + 1;
        Square square1 = new Square(slice_FROMx, slice_FROMy, halfX, halfY,  nextIter);
        Square square2 = new Square(halfX, slice_FROMy, slice_TOx, halfY, nextIter);
        Square square3 = new Square(slice_FROMx, halfY, halfX, slice_TOy, nextIter);
        Square square4 = new Square(halfX, halfY, slice_TOx, slice_TOy, nextIter);
        enqueueSquare(square1, square2, square3, square4);
    }


    @Override
    protected void renderAntialiased(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, PERTURBATION_THEORY  && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        initialize(location);

        int loc;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR, fs.aaSigmaS);

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        int color;
        double temp_result;

        boolean escaped_val;
        double f_val;
        task_completed = 0;

        ArrayList<Square> squares = new ArrayList<>();

        createQueueSquare();

        long time = System.currentTimeMillis();

        int iteration = 0;
        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }

            squares.add(currentSquare);

            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;
            int x = FROMx;
            if (!perform_initial_work_stealing) {
                location.precalculateX(x);
            }
            for (int y = FROMy; y < TOy; y++) {
                loc = y * image_width + x;

                if (perform_initial_work_stealing) {
                    firstIndexesLocal.add(loc);
                    continue;
                }

                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
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

                if(perform_work_stealing_and_wait) {
                    rendering_done_per_task[taskId]++;
                }
                else {
                    rendering_done++;
                }
                task_calculated++;
                task_completed++;
            }

            if (TOx == image_width) {
                x = TOx - 1;
                if (!perform_initial_work_stealing) {
                    location.precalculateX(x);
                }
                for (int y = FROMy + 1; y < TOy; y++) {
                    loc = y * image_width + x;

                    if (perform_initial_work_stealing) {
                        firstIndexesLocal.add(loc);
                        continue;
                    }

                    image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
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

                    if(perform_work_stealing_and_wait) {
                        rendering_done_per_task[taskId]++;
                    }
                    else {
                        rendering_done++;
                    }
                    task_calculated++;
                    task_completed++;

                }
            }

            int y = FROMy;
            if (!perform_initial_work_stealing) {
                location.precalculateY(y);
            }
            for (x = FROMx + 1, loc = y * image_width + x; x < TOx; x++, loc++) {

                if (perform_initial_work_stealing) {
                    firstIndexesLocal.add(loc);
                    continue;
                }

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

                if(perform_work_stealing_and_wait) {
                    rendering_done_per_task[taskId]++;
                }
                else {
                    rendering_done++;
                }
                task_calculated++;
                task_completed++;
            }

            if (TOy == image_height) {
                y = TOy - 1;

                int xLimit = TOx;

                if (TOx == image_width) {
                    xLimit--;
                }

                if (!perform_initial_work_stealing) {
                    location.precalculateY(y);
                }

                for (x = FROMx + 1, loc = y * image_width + x; x < xLimit; x++, loc++) {

                    if (perform_initial_work_stealing) {
                        firstIndexesLocal.add(loc);
                        continue;
                    }

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

                    if(perform_work_stealing_and_wait) {
                        rendering_done_per_task[taskId]++;
                    }
                    else {
                        rendering_done++;
                    }
                    task_calculated++;
                    task_completed++;
                }
            }
            iteration++;
        } while (true);

        if(perform_initial_work_stealing) {
            firstRenderAntialiased(image_width, location, aa, supersampling_num, totalSamples, storeExtraData);
        }

        if(perform_work_stealing_and_wait) {
            updateProgress();
        }
        else {
            if (rendering_done / progress_one_percent >= 1) {
                update(rendering_done);
                rendering_done = 0;
            }
        }

        for(Square square : squares) {
            enqueueSquare(sanitizeSquare(square, image_width, image_height));
        }

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;
        int slice_FROMxp1;
        int slice_FROMyp1;
        int x, y;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        boolean temp_starting_escaped;
        PixelExtraData temp_starting_pixel_extra_data = null;

        int skippedColor;

        do {

            Square currentSquare = getNextSquare();

            if (currentSquare == null) {
                break;
            }

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;
            slice_FROMxp1 = slice_FROMx + 1;
            slice_FROMyp1 = slice_FROMy + 1;
            int xLength = slice_TOx - slice_FROMx;
            int yLength = slice_TOy - slice_FROMy;

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_width + x;

            temp_starting_value = image_iterations[loc];
            temp_starting_pixel_color = rgbs[loc];
            temp_starting_escaped = escaped[loc];
            if(storeExtraData) {
                temp_starting_pixel_extra_data = pixelData[loc];
            }

            int loc2;
            int same_count = 0;
            for (loc = slice_FROMy * image_width + x, loc2 = slice_TOy * image_width + x; x <= slice_TOx; x++, loc++, loc2++) {
                if(PERIMETER_ACCURACY == 1) {
                    if (isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value)) {
                        whole_area = false;
                        break;
                    }
                }
                else {
                    same_count += (!isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) ? 1 : 0) + (!isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value) ? 1 : 0);
                }
            }

            if (whole_area) {
                for (y = slice_FROMyp1, loc = y * image_width + slice_FROMx, loc2 = y * image_width + slice_TOx; y <= slice_TOy - 1; y++, loc += image_width, loc2 += image_width) {
                    if(PERIMETER_ACCURACY == 1) {
                        if (isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value)) {
                            whole_area = false;
                            break;
                        }
                    }
                    else {
                        same_count += (!isNotTheSame(loc, temp_starting_pixel_color, temp_starting_value) ? 1 : 0) + (!isNotTheSame(loc2, temp_starting_pixel_color, temp_starting_value) ? 1 : 0);
                    }
                }
            }

            if(PERIMETER_ACCURACY < 1) {
                whole_area = same_count >= PERIMETER_ACCURACY * ((xLength + yLength) << 1);
            }

            if (!whole_area) {
                if (canSubDivide(xLength, yLength)) {
                    performSubDivisionAntialiased(currentSquare.iteration, slice_FROMx, slice_TOx, slice_FROMy, slice_TOy, xLength, yLength, location, image_width, aa, supersampling_num, totalSamples, storeExtraData);
                } else {
                    normalRenderAntialiased(slice_FROMx, slice_TOx, slice_FROMy, slice_TOy, location, image_width, aa, supersampling_num, totalSamples, storeExtraData);
                }
            } else {
                y = slice_FROMyp1;
                x = slice_FROMxp1;

                int chunk = slice_TOx - x;

                int currentIteration = currentSquare.iteration;
                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentIteration);

                int fill_count = 0;

                for (int k = y; k < slice_TOy; k++) {
                    int temploc = k * image_width;
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
                        fill_count++;
                    }
                    rendering_done += chunk;
                }
                task_pixel_grouping[currentIteration] += fill_count;

                if(perform_work_stealing_and_wait) {
                    updateProgress();
                }
                else {
                    if (rendering_done / progress_one_percent >= 1) {
                        update(rendering_done);
                        rendering_done = 0;
                    }
                }

            }
//            if(USE_NON_BLOCKING_RENDERING && STOP_RENDERING) {
//                break;
//            }

        } while (true);

//        if(USE_NON_BLOCKING_RENDERING) {
//            completed_per_task[taskId] = image_width * image_height;
//            try {
//                stop_rendering_lock.lockRead();
//            } catch (InterruptedException ex) {
//
//            }
//
//            try {
//                successive_refinement_rendering_algorithm_barrier.await();
//            } catch (InterruptedException ex) {
//
//            } catch (BrokenBarrierException ex) {
//            }
//
//            if (STOP_RENDERING) {
//                stop_rendering_lock.unlockRead();
//                throw new StopSuccessiveRefinementException();
//            }
//            stop_rendering_lock.unlockRead();
//        }

        if (SKIPPED_PIXELS_ALG == 4) {
            renderSquares(image_width, image_height);
        }

        pixel_calculation_time_per_task = System.currentTimeMillis() - time - totalPauseTimeNanos / 1000000;

        if(perform_initial_work_stealing && taskId == 0) {
            firstIndexes = null;
        }

        postProcess(image_width, image_height, aa, location);
    }

    @Override
    protected void renderFastJulia(int image_size, boolean polar) {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_size, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

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

        createQueueSquare();

        int loc;

        boolean escaped_val;
        double f_val;

        ArrayList<Square> squares = new ArrayList<>();

        int iteration = 0;
        initializeRectangleAreasQueue(image_size, image_size);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }

            squares.add(currentSquare);

            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            int x = FROMx;
            if (!perform_initial_work_stealing) {
                location.precalculateX(x);
            }
            for (int y = FROMy; y < TOy; y++) {
                loc = y * image_size + x;
                if (perform_initial_work_stealing) {
                    firstIndexesLocal.add(loc);
                    continue;
                }
                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
            }

            if (TOx == image_size) {
                x = TOx - 1;
                if (!perform_initial_work_stealing) {
                    location.precalculateX(x);
                }
                for (int y = FROMy + 1; y < TOy; y++) {
                    loc = y * image_size + x;
                    if (perform_initial_work_stealing) {
                        firstIndexesLocal.add(loc);
                        continue;
                    }
                    image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                    escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(f_val, escaped_val);
                }
            }

            int y = FROMy;
            if (!perform_initial_work_stealing) {
                location.precalculateY(y);
            }
            for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
                if (perform_initial_work_stealing) {
                    firstIndexesLocal.add(loc);
                    continue;
                }
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

                if (!perform_initial_work_stealing) {
                    location.precalculateY(y);
                }
                for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                    if (perform_initial_work_stealing) {
                        firstIndexesLocal.add(loc);
                        continue;
                    }
                    image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                    escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(f_val, escaped_val);
                }
            }
            iteration++;
        } while (true);

        if(perform_initial_work_stealing) {
            firstRenderFastJulia(image_size, location);
        }

        for(Square square : squares) {
            enqueueSquare(sanitizeSquare(square, image_size, image_size));
        }

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;
        int slice_FROMxp1;
        int slice_FROMyp1;
        int x, y;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        boolean temp_starting_escaped;

        int skippedColor;

        do {

            Square currentSquare = getNextSquare();

            if (currentSquare == null) {
                break;
            }

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;
            slice_FROMxp1 = slice_FROMx + 1;
            slice_FROMyp1 = slice_FROMy + 1;
            int xLength = slice_TOx - slice_FROMx;
            int yLength = slice_TOy - slice_FROMy;

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations_fast_julia[loc];
            temp_starting_pixel_color = rgbs[loc];
            temp_starting_escaped = escaped_fast_julia[loc];

            int loc2;
            int same_count = 0;
            for (loc = slice_FROMy * image_size + x, loc2 = slice_TOy * image_size + x; x <= slice_TOx; x++, loc++, loc2++) {
                if(PERIMETER_ACCURACY == 1) {
                    if (isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value)) {
                        whole_area = false;
                        break;
                    }
                }
                else {
                    same_count += (!isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) ? 1 : 0) + (!isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value) ? 1 : 0);
                }
            }

            if (whole_area) {
                for (y = slice_FROMyp1, loc = y * image_size + slice_FROMx, loc2 = y * image_size + slice_TOx; y <= slice_TOy - 1; y++, loc += image_size, loc2 += image_size) {
                    if(PERIMETER_ACCURACY == 1) {
                        if (isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value)) {
                            whole_area = false;
                            break;
                        }
                    }
                    else {
                        same_count += (!isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) ? 1 : 0) + (!isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value) ? 1 : 0);
                    }
                }
            }

            if(PERIMETER_ACCURACY < 1) {
                whole_area = same_count >= PERIMETER_ACCURACY * ((xLength + yLength) << 1);
            }

            if (!whole_area) {
                if (canSubDivide(xLength, yLength)) {
                    performSubDivisionFastJulia(currentSquare.iteration, slice_FROMx, slice_TOx, slice_FROMy, slice_TOy, xLength, yLength, location, image_size);
                } else {
                    normalRenderFastJulia(slice_FROMx, slice_TOx, slice_FROMy, slice_TOy, location, image_size);
                }
            } else {
                y = slice_FROMyp1;
                x = slice_FROMxp1;

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
            renderSquares(image_size, image_size);
        }

        if(perform_initial_work_stealing && taskId == 0) {
            firstIndexes = null;
        }

        postProcessFastJulia(image_size, null, location);

    }

    @Override
    protected void renderFastJuliaAntialiased(int image_size, boolean polar) {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_size, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
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

        createQueueSquare();

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

        ArrayList<Square> squares = new ArrayList<>();

        aa.setNeedsAllSamples(needsPostProcessing());

        int iteration = 0;
        initializeRectangleAreasQueue(image_size, image_size);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }

            squares.add(currentSquare);

            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            int x = FROMx;
            if (!perform_initial_work_stealing) {
                location.precalculateX(x);
            }
            for (int y = FROMy; y < TOy; y++) {
                loc = y * image_size + x;
                if (perform_initial_work_stealing) {
                    firstIndexesLocal.add(loc);
                    continue;
                }
                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
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

            if (TOx == image_size) {
                x = TOx - 1;
                if (!perform_initial_work_stealing) {
                    location.precalculateX(x);
                }
                for (int y = FROMy + 1; y < TOy; y++) {
                    loc = y * image_size + x;

                    if (perform_initial_work_stealing) {
                        firstIndexesLocal.add(loc);
                        continue;
                    }

                    image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
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

            int y = FROMy;
            if (!perform_initial_work_stealing) {
                location.precalculateY(y);
            }
            for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
                if (perform_initial_work_stealing) {
                    firstIndexesLocal.add(loc);
                    continue;
                }

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

            if (TOy == image_size) {
                y = TOy - 1;

                int xLimit = TOx;

                if (TOx == image_size) {
                    xLimit--;
                }

                if (!perform_initial_work_stealing) {
                    location.precalculateY(y);
                }
                for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                    if (perform_initial_work_stealing) {
                        firstIndexesLocal.add(loc);
                        continue;
                    }

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

        if(perform_initial_work_stealing) {
            firstRenderFastJuliaAntialiased(image_size, location, aa, supersampling_num, totalSamples, storeExtraData);
        }

        for(Square square : squares) {
            enqueueSquare(sanitizeSquare(square, image_size, image_size));
        }

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;
        int slice_FROMxp1;
        int slice_FROMyp1;
        int x, y;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        boolean temp_starting_escaped;
        PixelExtraData temp_starting_pixel_extra_data = null;

        int skippedColor;

        do {

            Square currentSquare = getNextSquare();

            if (currentSquare == null) {
                break;
            }

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;
            slice_FROMxp1 = slice_FROMx + 1;
            slice_FROMyp1 = slice_FROMy + 1;
            int xLength = slice_TOx - slice_FROMx;
            int yLength = slice_TOy - slice_FROMy;

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
            int same_count = 0;
            for (loc = slice_FROMy * image_size + x, loc2 = slice_TOy * image_size + x; x <= slice_TOx; x++, loc++, loc2++) {
                if(PERIMETER_ACCURACY == 1) {
                    if (isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value)) {
                        whole_area = false;
                        break;
                    }
                }
                else {
                    same_count += (!isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) ? 1 : 0) + (!isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value) ? 1 : 0);
                }
            }

            if (whole_area) {
                for (y = slice_FROMyp1, loc = y * image_size + slice_FROMx, loc2 = y * image_size + slice_TOx; y <= slice_TOy - 1; y++, loc += image_size, loc2 += image_size) {
                    if(PERIMETER_ACCURACY == 1) {
                        if (isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) || isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value)) {
                            whole_area = false;
                            break;
                        }
                    }
                    else {
                        same_count += (!isNotTheSameFastJulia(loc, temp_starting_pixel_color, temp_starting_value) ? 1 : 0) + (!isNotTheSameFastJulia(loc2, temp_starting_pixel_color, temp_starting_value) ? 1 : 0);
                    }
                }
            }

            if(PERIMETER_ACCURACY < 1) {
                whole_area = same_count >= PERIMETER_ACCURACY * ((xLength + yLength) << 1);
            }

            if (!whole_area) {
                if (canSubDivide(xLength, yLength)) {
                    performSubDivisionFastJuliaAntialiased(currentSquare.iteration, slice_FROMx, slice_TOx, slice_FROMy, slice_TOy, xLength, yLength, location, image_size, aa, supersampling_num, totalSamples, storeExtraData);
                } else {
                    normalRenderFastJuliaAntialiased(slice_FROMx, slice_TOx, slice_FROMy, slice_TOy, location, image_size, aa, supersampling_num, totalSamples, storeExtraData);
                }
            } else {
                y = slice_FROMyp1;
                x = slice_FROMxp1;

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
            renderSquares(image_size, image_size);
        }

        if(perform_initial_work_stealing && taskId == 0) {
            firstIndexes = null;
        }

        postProcessFastJulia(image_size, aa, location);

    }

    protected boolean isNotTheSame(int loc, int pixel_color, double pixel_value) {
        return rgbs[loc] != pixel_color;
    }

    protected boolean isNotTheSameFastJulia(int loc, int pixel_color, double pixel_value) {
        return rgbs[loc] != pixel_color;
    }

    private Square sanitizeSquare(Square s1, int image_width, int image_height) {
        if(s1.x2 == image_width && s1.y2 == image_height) {
            return new Square(s1.x1, s1.y1, image_width - 1, image_height - 1, s1.iteration);
        }
        else if(s1.x2 == image_width) {
            return new Square(s1.x1, s1.y1, image_width - 1, s1.y2, s1.iteration);
        }
        else if(s1.y2 == image_height) {
            return new Square(s1.x1, s1.y1, s1.x2, image_height - 1, s1.iteration);
        }

        return s1;
    }

}
