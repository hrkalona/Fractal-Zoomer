package fractalzoomer.core.drawing_algorithms;

import fractalzoomer.core.Complex;
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

public class CircularSuccessiveRefinementGuessingDraw extends SuccessiveRefinementGuessingDraw {
    public CircularSuccessiveRefinementGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public CircularSuccessiveRefinementGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public CircularSuccessiveRefinementGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public CircularSuccessiveRefinementGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public CircularSuccessiveRefinementGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public CircularSuccessiveRefinementGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps,Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public CircularSuccessiveRefinementGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs,  double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,      BlendingSettings color_blending,  int[] post_processing_order,   PaletteGradientMergingSettings pbs,  DomainColoringSettings ds, int gradient_offset,  double contourFactor, boolean smoothing, GeneratedPaletteSettings gps, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,    color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,  post_processing_order,  pbs,  ds, gradient_offset,  contourFactor, smoothing, gps, pps);
    }

    public CircularSuccessiveRefinementGuessingDraw(int action, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs,    double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,  int[] post_processing_order,   PaletteGradientMergingSettings pbs, int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, DomainColoringSettings ds) {
        super(action, FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,  color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, color_blending,  post_processing_order, pbs, gradient_offset,  contourFactor, gps, pps, ds);
    }

    public static Pixel[][] CoordinatesPerLevel;
    public static void initCoordinates(int image_size, boolean force) {

        if(force || CoordinatesPerLevel == null || coordinates.length != image_size * image_size) {

            CoordinatesPerLevel = new Pixel[SUCCESSIVE_REFINEMENT_EXPONENT + 1][];
            int current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;

            int x, y;
            for (int id = 0; id < CoordinatesPerLevel.length - 1; current_chunk_size >>= 1, id++) {
                ArrayList<Pixel> list = new ArrayList<>();

                if(Pixel.COMPARE_ALG == 16) {
                    for (int i = 0; i < coordinates.length; i++) {
                        Pixel pix = coordinates[i];
                        x = pix.x;
                        y = pix.y;

                        if (x % current_chunk_size != 0 || y % current_chunk_size != 0 || (y / current_chunk_size) % 2 == 1) {
                            continue;
                        }
                        list.add(pix);
                    }

                    for (int i = 0; i < coordinates.length; i++) {
                        Pixel pix = coordinates[i];
                        x = pix.x;
                        y = pix.y;

                        if (x % current_chunk_size != 0 || y % current_chunk_size != 0 || (y / current_chunk_size) % 2 == 0) {
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

                        if (x % current_chunk_size != 0 || y % current_chunk_size != 0) {
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
            CoordinatesPerLevelFastJulia = new Pixel[SUCCESSIVE_REFINEMENT_EXPONENT + 1][];
            int current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;

            int x, y;
            for (int id = 0; id < CoordinatesPerLevelFastJulia.length - 1; current_chunk_size >>= 1, id++) {

                ArrayList<Pixel> list = new ArrayList<>();

                if(Pixel.COMPARE_ALG == 16) {
                    for (int i = 0; i < coordinatesFastJulia.length; i++) {
                        Pixel pix = coordinatesFastJulia[i];
                        x = pix.x;
                        y = pix.y;

                        if (x % current_chunk_size != 0 || y % current_chunk_size != 0 || (y / current_chunk_size) % 2 == 1) {
                            continue;
                        }
                        list.add(pix);
                    }

                    for (int i = 0; i < coordinatesFastJulia.length; i++) {
                        Pixel pix = coordinatesFastJulia[i];
                        x = pix.x;
                        y = pix.y;

                        if (x % current_chunk_size != 0 || y % current_chunk_size != 0 || (y / current_chunk_size) % 2 == 0) {
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

                        if (x % current_chunk_size != 0 || y % current_chunk_size != 0) {
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

    @Override
    protected void drawIterations(int image_size, boolean polar) throws StopSuccessiveRefinementException {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (HIGH_PRECISION_CALCULATION || PERTURBATION_THEORY) && fractal.supportsPerturbationTheory());

        int pixel_percent = (image_size * image_size) / 100;


        initialize(location);


        int color, loc2, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;


        int current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;
        int prev_chunk_size = 0;
        int iteration = randomNumber;
        int min_chunk_size = TaskDraw.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskDraw.GUESS_BLOCKS_SELECTION - 1);
        min_chunk_size = TWO_PASS_SUCCESSIVE_REFINEMENT ? min_chunk_size << 1 : min_chunk_size;

        int current_chunk_size2 = 0;

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

        task_completed = 0;
        long nano_time = 0;

        for(int id = 0, id2 = 0; current_chunk_size >= 1; id++) {

            long time = System.nanoTime();

            AtomicInteger ai = successive_refinement_drawing_algorithm_pixel[id];

            int coordinatesLoc;

            Pixel[] coordinates = CoordinatesPerLevel[id2];
            condition = coordinates.length;
            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL[id2];
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

                        boolean check = performSecondPassActions && current_chunk_size <= min_chunk_size;
                        if(check) {
                            current_chunk_size2 = prev_chunk_size;

                            xstart = (x / current_chunk_size2) * current_chunk_size2;
                            ystart = (y / current_chunk_size2) * current_chunk_size2;
                            xend = xstart + current_chunk_size2;
                            yend = ystart + current_chunk_size2;

                            loca = ystart * image_size;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_size;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_size && yend < image_size && isTheSame(loca, locb, locc, locd)) {


                            tempx = Math.min(image_size, x + current_chunk_size);
                            tempy = Math.min(image_size, y + current_chunk_size);

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

                            tempx = Math.min(image_size, x + current_chunk_size);
                            tempy = Math.min(image_size, y + current_chunk_size);

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

            prev_chunk_size = current_chunk_size;
            if(performSecondPassActions) {
                current_chunk_size >>= 1;
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

        int current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;
        int prev_chunk_size = 0;
        int iteration = randomNumber;
        int min_chunk_size = TaskDraw.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskDraw.GUESS_BLOCKS_SELECTION - 1);
        min_chunk_size = TWO_PASS_SUCCESSIVE_REFINEMENT ? min_chunk_size << 1 : min_chunk_size;

        int current_chunk_size2 = 0;

        int xstart = 0;
        int ystart = 0;
        int xend = 0;
        int yend = 0;

        int loca = 0;
        int locb = 0;
        int locc = 0;
        int locd = 0;

        int condition;

        for(int id = 0, id2 = 0; current_chunk_size >= 1; id++) {
            AtomicInteger ai = successive_refinement_drawing_algorithm_pixel[id];

            Pixel[] coordinates = CoordinatesPerLevelFastJulia[id2];
            condition = coordinates.length;
            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL[id2];
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

                        boolean check = performSecondPassActions && current_chunk_size <= min_chunk_size;
                        if(check) {
                            current_chunk_size2 = prev_chunk_size;

                            xstart = (x / current_chunk_size2) * current_chunk_size2;
                            ystart = (y / current_chunk_size2) * current_chunk_size2;
                            xend = xstart + current_chunk_size2;
                            yend = ystart + current_chunk_size2;

                            loca = ystart * image_size;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_size;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_size && yend < image_size && isTheSameFastJulia(loca, locb, locc, locd)) {


                            tempx = Math.min(image_size, x + current_chunk_size);
                            tempy = Math.min(image_size, y + current_chunk_size);

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

            prev_chunk_size = current_chunk_size;
            if(performSecondPassActions) {
                current_chunk_size >>= 1;
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


        int current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;
        int prev_chunk_size = 0;
        int iteration = randomNumber;
        int min_chunk_size = TaskDraw.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskDraw.GUESS_BLOCKS_SELECTION - 1);
        min_chunk_size = TWO_PASS_SUCCESSIVE_REFINEMENT ? min_chunk_size << 1 : min_chunk_size;

        int current_chunk_size2 = 0;

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

        task_completed = 0;

        long nano_time = 0;

        for(int id = 0, id2 = 0; current_chunk_size >= 1; id++) {
            long time = System.nanoTime();
            AtomicInteger ai = successive_refinement_drawing_algorithm_pixel[id];

            Pixel[] coordinates = CoordinatesPerLevel[id2];
            condition = coordinates.length;
            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL[id2];
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

                        boolean check = performSecondPassActions && current_chunk_size <= min_chunk_size;
                        if(check) {
                            current_chunk_size2 = prev_chunk_size;

                            xstart = (x / current_chunk_size2) * current_chunk_size2;
                            ystart = (y / current_chunk_size2) * current_chunk_size2;
                            xend = xstart + current_chunk_size2;
                            yend = ystart + current_chunk_size2;

                            loca = ystart * image_size;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_size;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_size && yend < image_size && isTheSame(loca, locb, locc, locd)) {


                            tempx = Math.min(image_size, x + current_chunk_size);
                            tempy = Math.min(image_size, y + current_chunk_size);

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

                            tempx = Math.min(image_size, x + current_chunk_size);
                            tempy = Math.min(image_size, y + current_chunk_size);

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

            prev_chunk_size = current_chunk_size;
            if(performSecondPassActions) {
                current_chunk_size >>= 1;
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


        int current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;
        int prev_chunk_size = 0;
        int iteration = randomNumber;
        int min_chunk_size = TaskDraw.GUESS_BLOCKS_SELECTION == 0 ? 0 : 1 << (TaskDraw.GUESS_BLOCKS_SELECTION - 1);
        min_chunk_size = TWO_PASS_SUCCESSIVE_REFINEMENT ? min_chunk_size << 1 : min_chunk_size;

        int current_chunk_size2 = 0;

        int xstart = 0;
        int ystart = 0;
        int xend = 0;
        int yend = 0;

        int loca = 0;
        int locb = 0;
        int locc = 0;
        int locd = 0;

        int condition;

        for(int id = 0, id2 = 0; current_chunk_size >= 1; id++) {
            AtomicInteger ai = successive_refinement_drawing_algorithm_pixel[id];

            Pixel[] coordinates = CoordinatesPerLevelFastJulia[id2];
            condition = coordinates.length;
            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL[id2];
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

                        boolean check = performSecondPassActions && current_chunk_size <= min_chunk_size;
                        if(check) {
                            current_chunk_size2 = prev_chunk_size;

                            xstart = (x / current_chunk_size2) * current_chunk_size2;
                            ystart = (y / current_chunk_size2) * current_chunk_size2;
                            xend = xstart + current_chunk_size2;
                            yend = ystart + current_chunk_size2;

                            loca = ystart * image_size;
                            locb = loca + xend;
                            loca += xstart;

                            locc = yend * image_size;
                            locd = locc + xend;
                            locc += xstart;
                        }

                        if(check && xend < image_size && yend < image_size && isTheSameFastJulia(loca, locb, locc, locd)) {


                            tempx = Math.min(image_size, x + current_chunk_size);
                            tempy = Math.min(image_size, y + current_chunk_size);

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

            prev_chunk_size = current_chunk_size;
            if(performSecondPassActions) {
                current_chunk_size >>= 1;
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

    @Override
    public boolean hasCircularLogic() {
        return true;
    }

    @Override
    protected void applyPostProcessingPointFilter(int image_size, double[] image_iterations, boolean[] escaped, PixelExtraData[] pixelData, AntialiasingAlgorithm aa, Location location) {

        if(d3 || action == COLOR_CYCLING) {
            super.applyPostProcessingPointFilter(image_size, image_iterations, escaped, pixelData, aa, location);
            return;
        }

        double sizeCorr = 0, lightx = 0, lighty = 0;

        if (bms.bump_map) {
            double gradCorr = Math.pow(2, (bms.bumpMappingStrength - DEFAULT_BUMP_MAPPING_STRENGTH) * 0.05);
            sizeCorr = image_size / Math.pow(2, (MAX_BUMP_MAPPING_DEPTH - bms.bumpMappingDepth) * 0.16);
            double lightAngleRadians = Math.toRadians(bms.lightDirectionDegrees);
            lightx = Math.cos(lightAngleRadians) * gradCorr;
            lighty = Math.sin(lightAngleRadians) * gradCorr;
        }


        int[] modified = new int[1];

        if(aa != null) {
            modified = new int[aa.getTotalSamples()];
            aa.setNeedsPostProcessing(false);
        }

        int x, y, loc, coordinatesLoc;
        int condition = image_size * image_size;
        Pixel[] coord = !isFastJulia() ? coordinates : coordinatesFastJulia;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_post_processing.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coord[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

                applyPostProcessingOnPixel(loc, x, y, image_size, image_iterations, escaped, pixelData, aa, modified, sizeCorr, lightx, lighty, location);

            }

            if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT && !isFastJulia()) {
                try {
                    successive_refinement_lock.lockRead();
                } catch (InterruptedException ex) {

                }

                if (STOP_SUCCESSIVE_REFINEMENT) {
                    successive_refinement_lock.unlockRead();
                    break;
                }
                successive_refinement_lock.unlockRead();
            }

        } while(true);

    }

    @Override
    protected void changePalette(int image_size) throws StopSuccessiveRefinementException {

        int pixel_percent = (image_size * image_size) / 100;

        int x, y, loc, coordinatesLoc;
        int condition = image_size * image_size;
        task_completed = 0;

        long time = System.currentTimeMillis();

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_apply_palette.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coordinates[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

                if (domain_coloring) {
                    rgbs[loc] = domain_color.getDomainColor(new Complex(domain_image_data_re[loc], domain_image_data_im[loc]));
                } else {
                    rgbs[loc] = getStandardColor(image_iterations[loc], escaped[loc]);
                }

                drawing_done++;
                task_completed++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

            if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT) {
                try {
                    successive_refinement_lock.lockRead();
                } catch (InterruptedException ex) {

                }

                if (STOP_SUCCESSIVE_REFINEMENT) {
                    successive_refinement_lock.unlockRead();
                    break;
                }
                successive_refinement_lock.unlockRead();
            }

        } while(true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT) {
            try {
                successive_refinement_lock.lockRead();
            } catch (InterruptedException ex) {

            }

            try {
                successive_refinement_drawing_algorithm_barrier.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {
            }

            if (STOP_SUCCESSIVE_REFINEMENT) {
                successive_refinement_lock.unlockRead();
                throw new StopSuccessiveRefinementException();
            }
            successive_refinement_lock.unlockRead();
        }

        postProcess(image_size, null, null);

    }

    @Override
    protected void changePaletteWithAA(int image_size) throws StopSuccessiveRefinementException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        int totalSamples = supersampling_num + 1;
        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR, fs.aaSigmaS);

        aa.setNeedsPostProcessing(needsPostProcessing());

        int loc;
        int condition = image_size * image_size;

        int pixel_percent = (image_size * image_size) / 100;

        int color, x, y, coordinatesLoc;
        PixelExtraData data;
        task_completed = 0;

        long time = System.currentTimeMillis();

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_apply_palette.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coordinates[coordinatesLoc];
                x = pix.x;
                y = pix.y;
                loc = y * image_size + x;

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

                drawing_done++;
                task_completed++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

            if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT) {
                try {
                    successive_refinement_lock.lockRead();
                } catch (InterruptedException ex) {

                }

                if (STOP_SUCCESSIVE_REFINEMENT) {
                    successive_refinement_lock.unlockRead();
                    break;
                }
                successive_refinement_lock.unlockRead();
            }

        } while(true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        if(USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT) {
            try {
                successive_refinement_lock.lockRead();
            } catch (InterruptedException ex) {

            }

            try {
                successive_refinement_drawing_algorithm_barrier.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {
            }

            if (STOP_SUCCESSIVE_REFINEMENT) {
                successive_refinement_lock.unlockRead();
                throw new StopSuccessiveRefinementException();
            }
            successive_refinement_lock.unlockRead();
        }

        postProcess(image_size, aa, null);

    }

    @Override
    protected void quickDrawIterations(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int color, loc2, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;


        int current_chunk_size = tile;
        int min_chuck_size = tile;
        if(QUICKDRAW_SUCCESSIVE_REFINEMENT) {
            current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;
            min_chuck_size = prevPowerOf2(tile);
        }

        int condition = image_size * image_size;

        long nano_time = 0;

        for(int id = 0; current_chunk_size >= min_chuck_size; current_chunk_size >>= 1, id++) {
            long time = System.nanoTime();
            AtomicInteger ai = quick_draw_drawing_algorithm_pixel[id];

            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL[id];
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

                    if(x % current_chunk_size != 0 || y % current_chunk_size != 0) {
                        continue;
                    }

                    loc2 = y * image_size + x;

                    if(rgbs[loc2] >>> 24 != Constants.QUICKDRAW_CALCULATED_ALPHA) {
                        image_iterations[loc2] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                        escaped[loc2] = escaped_val = iteration_algorithm.escaped();
                        color = getFinalColor(f_val, escaped_val);

                        rgbs[loc2] = (color & 0xFFFFFF) | Constants.QUICKDRAW_CALCULATED_ALPHA_OFFSETED;

                        task_calculated++;

                        tempx = Math.min(x + current_chunk_size, image_size);
                        tempy = Math.min(y + current_chunk_size, image_size);

                        for (int i = y; i < tempy; i++) {
                            for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                                if (loc3 != loc2) {
                                    rgbs[loc3] = color;
                                }
                            }
                        }
                    }
                }

            } while (true);

            nano_time += System.nanoTime() - time;

            if(QUICKDRAW_SUCCESSIVE_REFINEMENT) {
                try {
                    quick_draw_drawing_algorithm_barrier.await();
                } catch (InterruptedException ex) {

                } catch (BrokenBarrierException ex) {

                }

                if(taskId == 0) {
                    if(id == 0) {
                        ptr.setWholeImageDone(true);
                        ptr.reloadTitle();
                        updateMode(ptr, false, iteration_algorithm.isJulia(), false, false);
                    }
                    ptr.getMainPanel().repaint();
                }
            }
        }

        pixel_calculation_time_per_task = nano_time / 1000000;

    }

    @Override
    protected void quickDrawIterationsDomain(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);

        int tempx, tempy;

        int color, loc2, x, y;

        int current_chunk_size = tile;
        int min_chuck_size = tile;
        if(QUICKDRAW_SUCCESSIVE_REFINEMENT) {
            current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;
            min_chuck_size = prevPowerOf2(tile);
        }
        int condition = image_size * image_size;


        long nano_time = 0;

        for(int id = 0; current_chunk_size >= min_chuck_size; current_chunk_size >>= 1, id++) {
            long time = System.nanoTime();
            AtomicInteger ai = quick_draw_drawing_algorithm_pixel[id];

            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL[id];
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

                    if(x % current_chunk_size != 0 || y % current_chunk_size != 0) {
                        continue;
                    }

                    loc2 = y * image_size + x;

                    if(rgbs[loc2] >>> 24 != Constants.QUICKDRAW_CALCULATED_ALPHA) {
                        Complex val = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                        image_iterations[loc2] = scaleDomainHeight(getDomainHeight(val));
                        color = domain_color.getDomainColor(val);
                        rgbs[loc2] = (color & 0xFFFFFF) | Constants.QUICKDRAW_CALCULATED_ALPHA_OFFSETED;

                        if (domain_image_data_re != null && domain_image_data_im != null) {
                            domain_image_data_re[loc2] = val.getRe();
                            domain_image_data_im[loc2] = val.getIm();
                        }

                        task_calculated++;

                        tempx = Math.min(x + current_chunk_size, image_size);
                        tempy = Math.min(y + current_chunk_size, image_size);

                        for (int i = y; i < tempy; i++) {
                            for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                                if (loc3 != loc2) {
                                    rgbs[loc3] = color;
                                }
                            }
                        }
                    }
                }

            } while (true);

            nano_time += System.nanoTime() - time;

            if(QUICKDRAW_SUCCESSIVE_REFINEMENT) {
                try {
                    quick_draw_drawing_algorithm_barrier.await();
                } catch (InterruptedException ex) {

                } catch (BrokenBarrierException ex) {

                }
                if(taskId == 0) {
                    if(id == 0) {
                        ptr.setWholeImageDone(true);
                        ptr.reloadTitle();
                        updateMode(ptr, false, iteration_algorithm.isJulia(), false, false);
                    }
                    ptr.getMainPanel().repaint();
                }
            }
        }

        pixel_calculation_time_per_task = nano_time / 1000000;

    }

    @Override
    protected void applySkippedColor(int image_size, boolean isFastJulia) {

        int x, y, loc, coordinatesLoc;
        int condition = image_size * image_size;
        Pixel[] coord = !isFastJulia() ? coordinates : coordinatesFastJulia;
        int[] colors = isFastJulia ? skipped_colors_fast_julia : skipped_colors;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * apply_skipped_color_pixel.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coord[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

                int c = colors[loc];

                if(c != 0) {
                    rgbs[loc] = c;
                }

            }

        } while(true);

    }

    @Override
    protected void drawSquares(int image_size) {

        int coordinatesLoc;
        int condition = image_size * image_size;
        int x, y, loc;

        int white = 0xffffffff;
        int grey = 0xffAAAAAA;

        int colA = white;
        int colB = grey;
        int length = 14;

        Pixel[] coord = !isFastJulia() ? coordinates : coordinatesFastJulia;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * draw_squares_pixel.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {

                Pixel pix = coord[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

                if (y % length < length / 2) {
                    colA = white;
                    colB = grey;
                } else {
                    colB = white;
                    colA = grey;
                }

                if (rgbs[loc] == 0x00ffffff) {
                    if (x % length < length / 2) {
                        rgbs[loc] = colA;
                    } else {
                        rgbs[loc] = colB;
                    }

                }

            }


        } while(true);

    }

    @Override
    protected void drawIterationsDomainAntialiased(int image_size, boolean polar) {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        int pixel_percent = (image_size * image_size) / 100;

        //better Brute force with antialiasing
        int x, y, loc, coordinatesLoc;
        int color;

        int condition = image_size * image_size;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR, fs.aaSigmaS);

        aa.setNeedsPostProcessing(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        double f_val;

        long time = System.currentTimeMillis();

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {

                Pixel pix = coordinates[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

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

                drawing_done++;
                task_calculated++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while (true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_size, aa, location);
        }
        catch (StopSuccessiveRefinementException ex) {}

    }

    @Override
    protected void drawIterationsDomain(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);

        int pixel_percent = (image_size * image_size) / 100;

        //Better brute force
        int x, y, coordinatesLoc, loc;

        int condition = image_size * image_size;

        long time = System.currentTimeMillis();

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {

                Pixel pix = coordinates[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

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

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while (true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_size, null, location);
        }
        catch (StopSuccessiveRefinementException ex) {}

    }

}
