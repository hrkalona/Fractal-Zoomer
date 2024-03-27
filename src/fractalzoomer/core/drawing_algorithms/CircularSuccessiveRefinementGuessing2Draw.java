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
import fractalzoomer.utils.Pixel;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;

import static fractalzoomer.core.drawing_algorithms.CircularBruteForceDraw.coordinates;
import static fractalzoomer.core.drawing_algorithms.CircularBruteForceDraw.coordinatesFastJulia;

public class CircularSuccessiveRefinementGuessing2Draw extends CircularSuccessiveRefinementGuessingDraw {
    public CircularSuccessiveRefinementGuessing2Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public CircularSuccessiveRefinementGuessing2Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public CircularSuccessiveRefinementGuessing2Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public CircularSuccessiveRefinementGuessing2Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public CircularSuccessiveRefinementGuessing2Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public CircularSuccessiveRefinementGuessing2Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public CircularSuccessiveRefinementGuessing2Draw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, DomainColoringSettings ds, int gradient_offset, double contourFactor, boolean smoothing, GeneratedPaletteSettings gps, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,    color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,  post_processing_order,  pbs,  ds, gradient_offset,  contourFactor, smoothing, gps, pps);
    }

    public CircularSuccessiveRefinementGuessing2Draw(int action, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, DomainColoringSettings ds) {
        super(action, FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,  color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, color_blending,  post_processing_order, pbs, gradient_offset,  contourFactor, gps, pps, ds);
    }

    public static Pixel[][] CoordinatesPerLevel;
    public static void initCoordinates(int image_size, boolean force) {

        if(force || CoordinatesPerLevel == null || coordinates.length != image_size * image_size) {

            CoordinatesPerLevel = new Pixel[SUCCESSIVE_REFINEMENT_CHUNK_X.length][];
            int current_chunk_size_x, current_chunk_size_y;

            int x, y;
            for (int id = 0; id < CoordinatesPerLevel.length - 1; id++) {

                current_chunk_size_x = SUCCESSIVE_REFINEMENT_CHUNK_X[id];
                current_chunk_size_y = SUCCESSIVE_REFINEMENT_CHUNK_Y[id];

                ArrayList<Pixel> list = new ArrayList<>();

                if(Pixel.COMPARE_ALG == 16) {
                    for (int i = 0; i < coordinates.length; i++) {
                        Pixel pix = coordinates[i];
                        x = pix.x;
                        y = pix.y;

                        if (x % current_chunk_size_x != 0 || y % current_chunk_size_y != 0 || (y / current_chunk_size_y) % 2 == 1) {
                            continue;
                        }
                        list.add(pix);
                    }

                    for (int i = 0; i < coordinates.length; i++) {
                        Pixel pix = coordinates[i];
                        x = pix.x;
                        y = pix.y;

                        if (x % current_chunk_size_x != 0 || y % current_chunk_size_y != 0 || (y / current_chunk_size_y) % 2 == 0) {
                            continue;
                        }
                        list.add(pix);
                    }
                }
                else {
                    for (int i = 0; i < coordinates.length; i++) {
                        Pixel pix = coordinates[i];
                        x = pix.x;
                        y = pix.y;

                        if (x % current_chunk_size_x != 0 || y % current_chunk_size_y != 0) {
                            continue;
                        }
                        list.add(pix);
                    }
                }

                Pixel[] array = new Pixel[list.size()];
                CoordinatesPerLevel[id] = list.toArray(array);
            }

            CoordinatesPerLevel[CoordinatesPerLevel.length - 1] = coordinates;
        }
    }

    public static Pixel[][] CoordinatesPerLevelFastJulia;
    public static void initCoordinatesFastJulia(int image_size, boolean force) {

        if(force || CoordinatesPerLevelFastJulia == null || coordinatesFastJulia.length != image_size * image_size) {
            CoordinatesPerLevelFastJulia = new Pixel[SUCCESSIVE_REFINEMENT_CHUNK_X.length][];
            int current_chunk_size_x, current_chunk_size_y;

            int x, y;
            for (int id = 0; id < CoordinatesPerLevelFastJulia.length - 1; id++) {

                current_chunk_size_x = SUCCESSIVE_REFINEMENT_CHUNK_X[id];
                current_chunk_size_y = SUCCESSIVE_REFINEMENT_CHUNK_Y[id];

                ArrayList<Pixel> list = new ArrayList<>();

                if(Pixel.COMPARE_ALG == 16) {
                    for (int i = 0; i < coordinatesFastJulia.length; i++) {
                        Pixel pix = coordinatesFastJulia[i];
                        x = pix.x;
                        y = pix.y;

                        if (x % current_chunk_size_x != 0 || y % current_chunk_size_y != 0 || (y / current_chunk_size_y) % 2 == 1) {
                            continue;
                        }
                        list.add(pix);
                    }

                    for (int i = 0; i < coordinatesFastJulia.length; i++) {
                        Pixel pix = coordinatesFastJulia[i];
                        x = pix.x;
                        y = pix.y;

                        if (x % current_chunk_size_x != 0 || y % current_chunk_size_y != 0 || (y / current_chunk_size_y) % 2 == 0) {
                            continue;
                        }
                        list.add(pix);
                    }
                }
                else {
                    for (int i = 0; i < coordinatesFastJulia.length; i++) {
                        Pixel pix = coordinatesFastJulia[i];
                        x = pix.x;
                        y = pix.y;

                        if (x % current_chunk_size_x != 0 || y % current_chunk_size_y != 0) {
                            continue;
                        }
                        list.add(pix);
                    }
                }

                Pixel[] array = new Pixel[list.size()];
                CoordinatesPerLevelFastJulia[id] = list.toArray(array);
            }

            CoordinatesPerLevelFastJulia[CoordinatesPerLevelFastJulia.length - 1] = coordinatesFastJulia;
        }
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
    protected void drawIterations(int image_size, boolean polar) throws StopSuccessiveRefinementException {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (HIGH_PRECISION_CALCULATION || PERTURBATION_THEORY) && fractal.supportsPerturbationTheory());

        int pixel_percent = (image_size * image_size) / 100;


        initialize(location);


        int color, loc2, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;


        int current_chunk_size_x = 0;
        int current_chunk_size_y = 0;
        int prev_chunk_size_x;
        int prev_chunk_size_y;

        int iteration = randomNumber;
        int min_chunk_size = TaskDraw.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskDraw.GUESS_BLOCKS_SELECTION - 1);
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

        int condition;

        int stop_id = getStopAfterIter();
        int length = TWO_PASS_SUCCESSIVE_REFINEMENT ? SUCCESSIVE_REFINEMENT_CHUNK_X.length << 1 : SUCCESSIVE_REFINEMENT_CHUNK_X.length;

        task_completed = 0;
        long nano_time = 0;

        for(int id = 0, id2 = 0; id < length; id++) {

            long time = System.nanoTime();

            AtomicInteger ai = successive_refinement_drawing_algorithm2_pixel[id];

            prev_chunk_size_x = current_chunk_size_x;
            prev_chunk_size_y = current_chunk_size_y;
            current_chunk_size_x = SUCCESSIVE_REFINEMENT_CHUNK_X[id2];
            current_chunk_size_y = SUCCESSIVE_REFINEMENT_CHUNK_Y[id2];

            int coordinatesLoc;

            Pixel[] coordinates = CoordinatesPerLevel[id2];
            condition = coordinates.length;

            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL2[id2];
            boolean performSecondPassActions = !TWO_PASS_SUCCESSIVE_REFINEMENT || (id & 1) == 1;

            do {

                coordinatesLoc = chunk_size * ai.getAndIncrement();

                if (coordinatesLoc >= condition) {
                    break;
                }

                for (int count = 0; count < chunk_size && coordinatesLoc < condition; count++, coordinatesLoc++) {
                    Pixel pix = coordinates[coordinatesLoc];
                    x = pix.x;
                    y = pix.y;

                    loc2 = y * image_size + x;

                    if(!filled[loc2]) {

                        int aggregate_chunk = getAggregateChunk(current_chunk_size_x, current_chunk_size_y);
                        boolean check = performSecondPassActions && aggregate_chunk <= min_chunk_size;
                        if(check) {
                            current_chunk_size2x = prev_chunk_size_x;
                            current_chunk_size2y = prev_chunk_size_y;

                            xstart = (x / current_chunk_size2x) * current_chunk_size2x;
                            ystart = (y / current_chunk_size2y) * current_chunk_size2y;
                            xend = xstart + current_chunk_size2x;
                            yend = ystart + current_chunk_size2y;

                            loca = ystart * image_size;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_size;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_size && yend < image_size && isTheSame(loca, locb, locc, locd)) {


                            tempx = Math.min(image_size, x + current_chunk_size_x);
                            tempy = Math.min(image_size, y + current_chunk_size_y);

                            int start_color = rgbs[loca];
                            int skippedColor = getColorForSkippedPixels(start_color, iteration);
                            double start_iterations = image_iterations[loca];
                            boolean start_escaped = escaped[loca];

                            for (int i = y; i < tempy; i++) {
                                for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                                    if(!examined[loc3]) {
                                        if (rgbs[loc3] >>> 24 != Constants.NORMAL_ALPHA) {
                                            rgbs[loc3] = start_color;
                                            image_iterations[loc3] = start_iterations;
                                            escaped[loc3] = start_escaped;
                                            task_completed++;
                                        }
                                        setFilledAndExaminedWithSkippedColor(examined, filled, skipped_colors, loc3, skippedColor);
                                        drawing_done++;
                                    }
                                }
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
                            drawing_done++;

                            tempx = Math.min(image_size, x + current_chunk_size_x);
                            tempy = Math.min(image_size, y + current_chunk_size_y);

                            for (int i = y; i < tempy; i++) {
                                for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                                    if (rgbs[loc3] >>> 24 != Constants.NORMAL_ALPHA) {
                                        rgbs[loc3] = (color & 0xFFFFFF) | Constants.QUICKDRAW_CALCULATED_ALPHA_OFFSETED;
                                    }
                                }
                            }
                        }
                    }

                    if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT && id > stop_id && STOP_SUCCESSIVE_REFINEMENT) {
                        break;
                    }
                }

                if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT && id > stop_id && STOP_SUCCESSIVE_REFINEMENT) {
                    break;
                }

                if(drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }

            } while (true);

            nano_time += System.nanoTime() - time;

            if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT) {
                try {
                    successive_refinement_lock.lockRead();
                } catch (InterruptedException ex) {

                }
            }

            try {
                successive_refinement_drawing_algorithm_barrier.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            if(taskId == 0 && ptr != null) {
                if(id == 0) {
                    ptr.setWholeImageDone(true);
                    ptr.reloadTitle();
                    updateMode(ptr, false, iteration_algorithm.isJulia(), false, false);
                }
                ptr.getMainPanel().repaint();
            }

            if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT) {
                if (id > stop_id && STOP_SUCCESSIVE_REFINEMENT) {
                    successive_refinement_lock.unlockRead();
                    throw new StopSuccessiveRefinementException();
                }
                successive_refinement_lock.unlockRead();
            }

            if(performSecondPassActions) {
                iteration++;
                id2++;
            }
        }

        if(SKIPPED_PIXELS_ALG != 0) {
            applySkippedColor(image_size, false);
        }

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        pixel_calculation_time_per_task = nano_time / 1000000;

        postProcess(image_size, null, location);

    }

    @Override
    protected void drawFastJuliaAntialiased(int image_size, boolean polar) {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        initialize(location);

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR, fs.aaSigmaS);

        aa.setNeedsPostProcessing(needsPostProcessing());

        boolean storeExtraData = pixelData_fast_julia != null;

        int color, loc2, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;
        double temp_result;

        int current_chunk_size_x = 0;
        int current_chunk_size_y = 0;
        int prev_chunk_size_x;
        int prev_chunk_size_y;
        int iteration = randomNumber;
        int min_chunk_size = TaskDraw.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskDraw.GUESS_BLOCKS_SELECTION - 1);
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

        int condition;
        int length = TWO_PASS_SUCCESSIVE_REFINEMENT ? SUCCESSIVE_REFINEMENT_CHUNK_X.length << 1 : SUCCESSIVE_REFINEMENT_CHUNK_X.length;


        for(int id = 0, id2 = 0; id < length; id++) {
            AtomicInteger ai = successive_refinement_drawing_algorithm2_pixel[id];

            prev_chunk_size_x = current_chunk_size_x;
            prev_chunk_size_y = current_chunk_size_y;
            current_chunk_size_x = SUCCESSIVE_REFINEMENT_CHUNK_X[id2];
            current_chunk_size_y = SUCCESSIVE_REFINEMENT_CHUNK_Y[id2];

            Pixel[] coordinates = CoordinatesPerLevelFastJulia[id2];
            condition = coordinates.length;
            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL2[id2];
            boolean performSecondPassActions = !TWO_PASS_SUCCESSIVE_REFINEMENT || (id & 1) == 1;


            int coordinatesLoc;

            do {

                coordinatesLoc = chunk_size * ai.getAndIncrement();

                if (coordinatesLoc >= condition) {
                    break;
                }

                for (int count = 0; count < chunk_size && coordinatesLoc < condition; count++, coordinatesLoc++) {
                    Pixel pix = coordinates[coordinatesLoc];
                    x = pix.x;
                    y = pix.y;


                    loc2 = y * image_size + x;

                    if(!filled_fast_julia[loc2]) {

                        int aggregate_chunk = getAggregateChunk(current_chunk_size_x, current_chunk_size_y);
                        boolean check = performSecondPassActions && aggregate_chunk <= min_chunk_size;
                        if(check) {
                            current_chunk_size2x = prev_chunk_size_x;
                            current_chunk_size2y = prev_chunk_size_y;

                            xstart = (x / current_chunk_size2x) * current_chunk_size2x;
                            ystart = (y / current_chunk_size2y) * current_chunk_size2y;
                            xend = xstart + current_chunk_size2x;
                            yend = ystart + current_chunk_size2y;

                            loca = ystart * image_size;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_size;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_size && yend < image_size && isTheSameFastJulia(loca, locb, locc, locd)) {

                            tempx = Math.min(image_size, x + current_chunk_size_x);
                            tempy = Math.min(image_size, y + current_chunk_size_y);

                            int start_color = rgbs[loca];
                            int skippedColor = getColorForSkippedPixels(start_color, iteration);
                            double start_iterations = image_iterations_fast_julia[loca];
                            boolean start_escaped = escaped_fast_julia[loca];

                            PixelExtraData start_extra_data = null;
                            if(storeExtraData) {
                                start_extra_data = pixelData_fast_julia[loca];
                            }

                            for (int i = y; i < tempy; i++) {
                                for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                                    if(!examined_fast_julia[loc3]) {
                                        rgbs[loc3] = start_color;
                                        image_iterations_fast_julia[loc3] = start_iterations;
                                        escaped_fast_julia[loc3] = start_escaped;
                                        setFilledAndExaminedWithSkippedColor(examined_fast_julia, filled_fast_julia, skipped_colors_fast_julia, loc3, skippedColor);
                                        if(storeExtraData) {
                                            pixelData_fast_julia[loc3] = new PixelExtraData(start_extra_data, skippedColor);
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

            try {
                successive_refinement_drawing_algorithm_barrier.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            if(performSecondPassActions) {
                iteration++;
                id2++;
            }
        }


        if(SKIPPED_PIXELS_ALG != 0) {
            applySkippedColor(image_size, true);
        }


        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }


        postProcessFastJulia(image_size, aa, location);

    }

    @Override
    protected void drawIterationsAntialiased(int image_size, boolean polar) throws StopSuccessiveRefinementException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);
        int pixel_percent = (image_size * image_size) / 100;

        initialize(location);

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR, fs.aaSigmaS);

        aa.setNeedsPostProcessing(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        int color, loc2, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;
        double temp_result;


        int current_chunk_size_x = 0;
        int current_chunk_size_y = 0;
        int prev_chunk_size_x;
        int prev_chunk_size_y;
        int iteration = randomNumber;
        int min_chunk_size = TaskDraw.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskDraw.GUESS_BLOCKS_SELECTION - 1);
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

        int condition;

        task_completed = 0;

        int stop_id = getStopAfterIter();
        int length = TWO_PASS_SUCCESSIVE_REFINEMENT ? SUCCESSIVE_REFINEMENT_CHUNK_X.length << 1 : SUCCESSIVE_REFINEMENT_CHUNK_X.length;

        long nano_time = 0;

        for(int id = 0, id2 = 0; id < length; id++) {
            long time = System.nanoTime();
            AtomicInteger ai = successive_refinement_drawing_algorithm2_pixel[id];

            prev_chunk_size_x = current_chunk_size_x;
            prev_chunk_size_y = current_chunk_size_y;
            current_chunk_size_x = SUCCESSIVE_REFINEMENT_CHUNK_X[id2];
            current_chunk_size_y = SUCCESSIVE_REFINEMENT_CHUNK_Y[id2];

            Pixel[] coordinates = CoordinatesPerLevel[id2];
            condition = coordinates.length;
            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL2[id2];
            boolean performSecondPassActions = !TWO_PASS_SUCCESSIVE_REFINEMENT || (id & 1) == 1;

            int coordinatesLoc;

            do {

                coordinatesLoc = chunk_size * ai.getAndIncrement();

                if (coordinatesLoc >= condition) {
                    break;
                }

                for (int count = 0; count < chunk_size && coordinatesLoc < condition; count++, coordinatesLoc++) {
                    Pixel pix = coordinates[coordinatesLoc];
                    x = pix.x;
                    y = pix.y;


                    loc2 = y * image_size + x;

                    if(!filled[loc2]) {

                        int aggregate_chunk = getAggregateChunk(current_chunk_size_x, current_chunk_size_y);
                        boolean check = performSecondPassActions && aggregate_chunk <= min_chunk_size;
                        if(check) {
                            current_chunk_size2x = prev_chunk_size_x;
                            current_chunk_size2y = prev_chunk_size_y;

                            xstart = (x / current_chunk_size2x) * current_chunk_size2x;
                            ystart = (y / current_chunk_size2y) * current_chunk_size2y;
                            xend = xstart + current_chunk_size2x;
                            yend = ystart + current_chunk_size2y;

                            loca = ystart * image_size;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_size;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_size && yend < image_size && isTheSame(loca, locb, locc, locd)) {


                            tempx = Math.min(image_size, x + current_chunk_size_x);
                            tempy = Math.min(image_size, y + current_chunk_size_y);

                            int start_color = rgbs[loca];
                            int skippedColor = getColorForSkippedPixels(start_color, iteration);
                            double start_iterations = image_iterations[loca];
                            boolean start_escaped = escaped[loca];

                            PixelExtraData start_extra_data = null;
                            if(storeExtraData) {
                                start_extra_data = pixelData[loca];
                            }

                            for (int i = y; i < tempy; i++) {
                                for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                                    if(!examined[loc3]) {
                                        rgbs[loc3] = start_color;
                                        image_iterations[loc3] = start_iterations;
                                        escaped[loc3] = start_escaped;
                                        setFilledAndExaminedWithSkippedColor(examined, filled, skipped_colors, loc3, skippedColor);
                                        if(storeExtraData) {
                                            pixelData[loc3] = new PixelExtraData(start_extra_data, skippedColor);
                                        }
                                        task_completed++;
                                        drawing_done++;
                                    }
                                }
                            }
                        }
                        else if (!examined[loc2]){
                            image_iterations[loc2] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                            escaped[loc2] = escaped_val = iteration_algorithm.escaped();
                            color = getFinalColor(f_val, escaped_val);
                            task_calculated++;

                            examined[loc2] = true;
                            drawing_done++;
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

                            tempx = Math.min(image_size, x + current_chunk_size_x);
                            tempy = Math.min(image_size, y + current_chunk_size_y);

                            for (int i = y; i < tempy; i++) {
                                for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                                    rgbs[loc3] = color;
                                }
                            }
                        }
                    }

                    if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT && id > stop_id && STOP_SUCCESSIVE_REFINEMENT) {
                        break;
                    }
                }

                if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT && id > stop_id && STOP_SUCCESSIVE_REFINEMENT) {
                    break;
                }

                if(drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }

            } while (true);

            nano_time += System.nanoTime() - time;

            if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT) {
                try {
                    successive_refinement_lock.lockRead();
                } catch (InterruptedException ex) {

                }
            }

            try {
                successive_refinement_drawing_algorithm_barrier.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {
            }

            if(taskId == 0 && ptr != null) {
                if(id == 0) {
                    ptr.setWholeImageDone(true);
                    ptr.reloadTitle();
                    updateMode(ptr, false, iteration_algorithm.isJulia(), false, false);
                }
                ptr.getMainPanel().repaint();
            }

            if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT) {
                if (id > stop_id && STOP_SUCCESSIVE_REFINEMENT) {
                    successive_refinement_lock.unlockRead();
                    throw new StopSuccessiveRefinementException();
                }
                successive_refinement_lock.unlockRead();
            }

            if(performSecondPassActions) {
                iteration++;
                id2++;
            }
        }

        if(SKIPPED_PIXELS_ALG != 0) {
            applySkippedColor(image_size, false);
        }

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }

        pixel_calculation_time_per_task = nano_time / 1000000;

        postProcess(image_size, aa, location);

    }

    @Override
    protected void drawFastJulia(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (HIGH_PRECISION_CALCULATION || PERTURBATION_THEORY) && fractal.supportsPerturbationTheory());

        initialize(location);


        int loc2, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;


        int current_chunk_size_x = 0;
        int current_chunk_size_y = 0;
        int prev_chunk_size_x;
        int prev_chunk_size_y;
        int iteration = randomNumber;
        int min_chunk_size = TaskDraw.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskDraw.GUESS_BLOCKS_SELECTION - 1);
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

        int length = TWO_PASS_SUCCESSIVE_REFINEMENT ? SUCCESSIVE_REFINEMENT_CHUNK_X.length << 1 : SUCCESSIVE_REFINEMENT_CHUNK_X.length;

        int condition;

        for(int id = 0, id2 = 0; id < length; id++) {
            AtomicInteger ai = successive_refinement_drawing_algorithm2_pixel[id];

            prev_chunk_size_x = current_chunk_size_x;
            prev_chunk_size_y = current_chunk_size_y;
            current_chunk_size_x = SUCCESSIVE_REFINEMENT_CHUNK_X[id2];
            current_chunk_size_y = SUCCESSIVE_REFINEMENT_CHUNK_Y[id2];

            Pixel[] coordinates = CoordinatesPerLevelFastJulia[id2];
            condition = coordinates.length;
            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL2[id2];
            boolean performSecondPassActions = !TWO_PASS_SUCCESSIVE_REFINEMENT || (id & 1) == 1;


            int coordinatesLoc;

            do {

                coordinatesLoc = chunk_size * ai.getAndIncrement();

                if (coordinatesLoc >= condition) {
                    break;
                }

                for (int count = 0; count < chunk_size && coordinatesLoc < condition; count++, coordinatesLoc++) {
                    Pixel pix = coordinates[coordinatesLoc];
                    x = pix.x;
                    y = pix.y;

                    loc2 = y * image_size + x;

                    if(!filled_fast_julia[loc2]) {

                        int aggregate_chunk = getAggregateChunk(current_chunk_size_x, current_chunk_size_y);
                        boolean check = performSecondPassActions && aggregate_chunk <= min_chunk_size;
                        if(check) {
                            current_chunk_size2x = prev_chunk_size_x;
                            current_chunk_size2y = prev_chunk_size_y;

                            xstart = (x / current_chunk_size2x) * current_chunk_size2x;
                            ystart = (y / current_chunk_size2y) * current_chunk_size2y;
                            xend = xstart + current_chunk_size2x;
                            yend = ystart + current_chunk_size2y;

                            loca = ystart * image_size;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_size;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_size && yend < image_size && isTheSameFastJulia(loca, locb, locc, locd)) {


                            tempx = Math.min(image_size, x + current_chunk_size_x);
                            tempy = Math.min(image_size, y + current_chunk_size_y);

                            int start_color = rgbs[loca];
                            int skippedColor = getColorForSkippedPixels(start_color, iteration);
                            double start_iterations = image_iterations_fast_julia[loca];
                            boolean start_escaped = escaped_fast_julia[loca];

                            for (int i = y; i < tempy; i++) {
                                for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                                    if(!examined_fast_julia[loc3]) {
                                        rgbs[loc3] = start_color;
                                        image_iterations_fast_julia[loc3] = start_iterations;
                                        escaped_fast_julia[loc3] = start_escaped;
                                        setFilledAndExaminedWithSkippedColor(examined_fast_julia, filled_fast_julia, skipped_colors_fast_julia, loc3, skippedColor);
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

            try {
                successive_refinement_drawing_algorithm_barrier.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            if(performSecondPassActions) {
                iteration++;
                id2++;
            }
        }

        if(SKIPPED_PIXELS_ALG != 0) {
            applySkippedColor(image_size, true);
        }

        if (SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }


        postProcessFastJulia(image_size, null, location);

    }
}
