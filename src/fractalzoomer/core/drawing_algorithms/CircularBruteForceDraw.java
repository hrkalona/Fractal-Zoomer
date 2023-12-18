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
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;

public class CircularBruteForceDraw extends TaskDraw {
    public static Pixel[] coordinates;
    private static boolean sorted_by_zoom_center;
    public static Pixel[] coordinatesFastJulia;
    protected static final int THREAD_CHUNK_SIZE = 250;

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps,Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }
    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs,  double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,      BlendingSettings color_blending,  int[] post_processing_order,   PaletteGradientMergingSettings pbs,  DomainColoringSettings ds, int gradient_offset,  double contourFactor, boolean smoothing, GeneratedPaletteSettings gps, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,    color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,  post_processing_order,  pbs,  ds, gradient_offset,  contourFactor, smoothing, gps, pps);
    }

    public CircularBruteForceDraw(int action, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs,    double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,  int[] post_processing_order,   PaletteGradientMergingSettings pbs, int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, DomainColoringSettings ds) {
        super(action, FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,  color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, color_blending,  post_processing_order, pbs, gradient_offset,  contourFactor, gps, pps, ds);
    }

    public static void initCoordinatesFastJulia(int fast_julia_image_size, boolean hasSuccessiveRefinement) {

        Pixel.COMPARE_ALG = TaskDraw.CIRCULAR_COMPARE_ALG;
        Pixel.REVERT = TaskDraw.CIRCULAR_REVERT_ALG;
        Pixel.n = TaskDraw.CIRCULAR_N;
        Pixel.nreciprocal = 1 / Pixel.n;
        Pixel.useCustom = false;
        Pixel.REPEAT = TaskDraw.CIRCULAR_REPEAT_ALG;
        Pixel.SPACING = Math.PI / TaskDraw.CIRCULAR_REPEAT_SPACING;

        if(coordinatesFastJulia == null || coordinatesFastJulia.length != fast_julia_image_size * fast_julia_image_size) {
            Pixel.midX = Pixel.midY = -fast_julia_image_size / 2;

            coordinatesFastJulia = new Pixel[fast_julia_image_size * fast_julia_image_size];

            if(Pixel.COMPARE_ALG == 16) { //Interleave
                int loc = 0;
                for (int y = 0; y < fast_julia_image_size; y+= 2) {
                    for (int x = 0; x < fast_julia_image_size; x++, loc++) {
                        coordinatesFastJulia[loc] = new Pixel(x, y);
                    }
                }

                for (int y = 1; y < fast_julia_image_size; y+= 2) {
                    for (int x = 0; x < fast_julia_image_size; x++, loc++) {
                        coordinatesFastJulia[loc] = new Pixel(x, y);
                    }
                }
            }
            else {
                int loc = 0;
                for (int y = 0; y < fast_julia_image_size; y++) {
                    for (int x = 0; x < fast_julia_image_size; x++, loc++) {
                        coordinatesFastJulia[loc] = new Pixel(x, y);
                    }
                }

                if (Pixel.COMPARE_ALG == 4) {
                    shuffle(coordinatesFastJulia);
                } else {
                    Arrays.sort(coordinatesFastJulia);
                }
            }

            if(hasSuccessiveRefinement) {
                if(TaskDraw.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                    CircularSuccessiveRefinementGuessing2Draw.initCoordinatesFastJulia(fast_julia_image_size, true);
                }
                else {
                    CircularSuccessiveRefinementGuessingDraw.initCoordinatesFastJulia(fast_julia_image_size, true);
                }
            }
        }
    }

        public static void initCoordinates(int image_size, boolean zoom_to_cursor, boolean doSort, boolean hasSuccessiveRefinement) {

            Pixel.COMPARE_ALG = TaskDraw.CIRCULAR_COMPARE_ALG;
            Pixel.REVERT = TaskDraw.CIRCULAR_REVERT_ALG;
            Pixel.n = TaskDraw.CIRCULAR_N;
            Pixel.nreciprocal = 1 / Pixel.n;
            Pixel.useCustom = zoom_to_cursor;
            Pixel.REPEAT = TaskDraw.CIRCULAR_REPEAT_ALG;
            Pixel.SPACING = Math.PI / TaskDraw.CIRCULAR_REPEAT_SPACING;


            if(coordinates == null || coordinates.length != image_size * image_size) {
                Pixel.midX = Pixel.midY = -image_size / 2;

                coordinates = new Pixel[image_size * image_size];

                if(Pixel.COMPARE_ALG == 16) { //Interleave
                    int loc = 0;
                    for (int y = 0; y < image_size; y+= 2) {
                        for (int x = 0; x < image_size; x++, loc++) {
                            coordinates[loc] = new Pixel(x, y);
                        }
                    }

                    for (int y = 1; y < image_size; y+= 2) {
                        for (int x = 0; x < image_size; x++, loc++) {
                            coordinates[loc] = new Pixel(x, y);
                        }
                    }
                }
                else {
                    int loc = 0;
                    for (int y = 0; y < image_size; y++) {
                        for (int x = 0; x < image_size; x++, loc++) {
                            coordinates[loc] = new Pixel(x, y);
                        }
                    }

                    if (Pixel.COMPARE_ALG == 4) {
                        shuffle(coordinates);
                    } else {
                        Arrays.sort(coordinates);
                    }
                }

                sorted_by_zoom_center = zoom_to_cursor;

                if(hasSuccessiveRefinement) {
                    if(TaskDraw.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                        CircularSuccessiveRefinementGuessing2Draw.initCoordinates(image_size, true);
                    }
                    else {
                        CircularSuccessiveRefinementGuessingDraw.initCoordinates(image_size, true);
                    }
                }
            }
            else if(doSort && (zoom_to_cursor || zoom_to_cursor != sorted_by_zoom_center)) {

                if(Pixel.COMPARE_ALG == 16) { //Interleave
                    int loc = 0;
                    for (int y = 0; y < image_size; y+= 2) {
                        for (int x = 0; x < image_size; x++, loc++) {
                            coordinates[loc] = new Pixel(x, y);
                        }
                    }

                    for (int y = 1; y < image_size; y+= 2) {
                        for (int x = 0; x < image_size; x++, loc++) {
                            coordinates[loc] = new Pixel(x, y);
                        }
                    }
                }
                else if(Pixel.COMPARE_ALG == 4) {
                    shuffle(coordinates);
                }
                else {
                    Arrays.sort(coordinates);
                }
                sorted_by_zoom_center = zoom_to_cursor;

                if(hasSuccessiveRefinement) {
                    if(TaskDraw.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                        CircularSuccessiveRefinementGuessing2Draw.initCoordinates(image_size, true);
                    }
                    else {
                        CircularSuccessiveRefinementGuessingDraw.initCoordinates(image_size, true);
                    }
                }
            }

        }

        public static Random rand = new Random();

        public static void shuffle(Object[] array) { // mix-up the array
            for (int i = array.length - 1; i > 0; --i) {
                int j = rand.nextInt(i + 1);
                Object temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        public static void clear() {
            coordinates = null;
            CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevel = null;
            CircularSuccessiveRefinementGuessing2Draw.CoordinatesPerLevel = null;
        }

        public static void clearFastJulia() {
            coordinatesFastJulia = null;
            CircularSuccessiveRefinementGuessingDraw.CoordinatesPerLevelFastJulia = null;
            CircularSuccessiveRefinementGuessing2Draw.CoordinatesPerLevelFastJulia = null;
        }

        @Override
        protected void drawIterations(int image_size, boolean polar) {

            Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

            int pixel_percent = (image_size * image_size) / 100;

            //Better brute force
            int x, y, coordinatesLoc, loc;

            int condition = image_size * image_size;

            initialize(location);

            boolean escaped_val;
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

                    if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                        image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                        escaped[loc] = escaped_val = iteration_algorithm.escaped();
                        rgbs[loc] = getFinalColor(f_val, escaped_val);
                        task_calculated++;
                    }

                    drawing_done++;
                }

                if(drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }

            } while(true);

            pixel_calculation_time_per_task = System.currentTimeMillis() - time;

            try {
                postProcess(image_size, null, location);
            }
            catch (StopSuccessiveRefinementException ex) {

            }

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

    @Override
    protected void drawIterationsAntialiased(int image_size, boolean polar) {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        int pixel_percent = (image_size * image_size) / 100;

        //better Brute force with antialiasing
        int x, y, loc, coordinatesLoc;
        int color;

        double temp_result;

        int condition = image_size * image_size;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;
        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR, fs.aaSigmaS);

        initialize(location);

        boolean escaped_val;
        double f_val;

        aa.setNeedsPostProcessing(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

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

                drawing_done++;
                task_calculated++;
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_size, aa, location);
        }catch (StopSuccessiveRefinementException ex) {}
    }

    @Override
    protected void drawFastJulia(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

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

        int x, y, loc, coordinatesLoc;

        int condition = image_size * image_size;

        boolean escaped_val;
        double f_val;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coordinatesFastJulia[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
            }

        } while(true);

        postProcessFastJulia(image_size, null, location);

    }

    @Override
    protected void drawFastJuliaAntialiased(int image_size, boolean polar) {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
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

        int x, y, loc, coordinatesLoc;
        int color;

        double temp_result;

        int condition = image_size * image_size;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR, fs.aaSigmaS);

        boolean escaped_val;
        double f_val;

        aa.setNeedsPostProcessing(needsPostProcessing());

        boolean storeExtraData = pixelData_fast_julia != null;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coordinatesFastJulia[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

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

        postProcessFastJulia(image_size, aa, location);
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

        } while(true);
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

        } while(true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_size, aa, null);

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

        } while(true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_size, null, null);

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
                    ptr.setWholeImageDone(true);
                    if(id == 0) {
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
                    ptr.setWholeImageDone(true);
                    if(id == 0) {
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
    public boolean hasCircularLogic() {
        return true;
    }

}
