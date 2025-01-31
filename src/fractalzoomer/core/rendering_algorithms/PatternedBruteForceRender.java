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
import fractalzoomer.utils.Pixel;
import fractalzoomer.utils.StopExecutionException;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import fractalzoomer.utils.WaitOnCondition;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PatternedBruteForceRender extends TaskRender {
    public static Pixel[] coordinates;
    public static int coordinates_width;
    public static int coordinates_height;
    private static boolean sorted_by_zoom_center;
    public static Pixel[] coordinatesFastJulia;
    protected static final int THREAD_CHUNK_SIZE = 250;

    public PatternedBruteForceRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public PatternedBruteForceRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public PatternedBruteForceRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public PatternedBruteForceRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public PatternedBruteForceRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public PatternedBruteForceRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }
    public PatternedBruteForceRender(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, DomainColoringSettings ds, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, boolean banded) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,    color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,  post_processing_order,  pbs,  ds, gradient_offset,  contourFactor, gps, pps, banded);
    }

    public PatternedBruteForceRender(int action, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, DomainColoringSettings ds, boolean banded) {
        super(action, FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs,  color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, color_blending,  post_processing_order, pbs, gradient_offset,  contourFactor, gps, pps, ds, banded);
    }

    public static void initCoordinatesFastJulia(int fast_julia_image_size, boolean hasSuccessiveRefinement) {

        Pixel.COMPARE_ALG = TaskRender.PATTERN_COMPARE_ALG;
        Pixel.REVERT = TaskRender.PATTERN_REVERT_ALG;
        Pixel.n = TaskRender.PATTERN_N;
        Pixel.nreciprocal = 1 / Pixel.n;
        Pixel.useCustom = false;
        Pixel.REPEAT = TaskRender.PATTERN_REPEAT_ALG;
        Pixel.SPACING = Math.PI / TaskRender.PATTERN_REPEAT_SPACING;
        Pixel.CENTER = TaskRender.PATTERN_CENTER;
        Pixel.WIDTH = fast_julia_image_size;

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
                    Arrays.parallelSort(coordinatesFastJulia);
                }
            }

            if(hasSuccessiveRefinement) {
                if(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                    PatternedSuccessiveRefinementGuessing2Render.initCoordinatesFastJulia(true);
                }
                else {
                    PatternedSuccessiveRefinementGuessingRender.initCoordinatesFastJulia(true);
                }
            }
        }
    }

        public static void initCoordinates(int width, int height, boolean zoom_to_cursor, boolean doSort, boolean hasSuccessiveRefinement) {

            Pixel.COMPARE_ALG = TaskRender.PATTERN_COMPARE_ALG;
            Pixel.REVERT = TaskRender.PATTERN_REVERT_ALG;
            Pixel.n = TaskRender.PATTERN_N;
            Pixel.nreciprocal = 1 / Pixel.n;
            Pixel.useCustom = zoom_to_cursor;
            Pixel.REPEAT = TaskRender.PATTERN_REPEAT_ALG;
            Pixel.SPACING = Math.PI / TaskRender.PATTERN_REPEAT_SPACING;
            Pixel.CENTER = TaskRender.PATTERN_CENTER;
            Pixel.WIDTH = width;


            if(coordinates == null || coordinates_width != width || coordinates_height != height) {
                Pixel.midX = -width / 2;
                Pixel.midY = -height / 2;

                coordinates_width = width;
                coordinates_height = height;

                coordinates = new Pixel[width * height];

                if(Pixel.COMPARE_ALG == 16) { //Interleave
                    int loc = 0;
                    for (int y = 0; y < height; y+= 2) {
                        for (int x = 0; x < width; x++, loc++) {
                            coordinates[loc] = new Pixel(x, y);
                        }
                    }

                    for (int y = 1; y < height; y+= 2) {
                        for (int x = 0; x < width; x++, loc++) {
                            coordinates[loc] = new Pixel(x, y);
                        }
                    }
                }
                else {
                    int loc = 0;
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++, loc++) {
                            coordinates[loc] = new Pixel(x, y);
                        }
                    }

                    if (Pixel.COMPARE_ALG == 4) {
                        shuffle(coordinates);
                    } else {
                        Arrays.parallelSort(coordinates);
                    }
                }

                sorted_by_zoom_center = zoom_to_cursor;

                if(hasSuccessiveRefinement) {
                    if(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                        PatternedSuccessiveRefinementGuessing2Render.initCoordinates(true);
                    }
                    else {
                        PatternedSuccessiveRefinementGuessingRender.initCoordinates(true);
                    }
                }
            }
            else if(doSort && (zoom_to_cursor || zoom_to_cursor != sorted_by_zoom_center)) {

                if(Pixel.COMPARE_ALG == 16) { //Interleave
                    int loc = 0;
                    for (int y = 0; y < height; y+= 2) {
                        for (int x = 0; x < width; x++, loc++) {
                            coordinates[loc] = new Pixel(x, y);
                        }
                    }

                    for (int y = 1; y < height; y+= 2) {
                        for (int x = 0; x < width; x++, loc++) {
                            coordinates[loc] = new Pixel(x, y);
                        }
                    }
                }
                else if(Pixel.COMPARE_ALG == 4) {
                    shuffle(coordinates);
                }
                else {
                    Arrays.parallelSort(coordinates);
                }
                sorted_by_zoom_center = zoom_to_cursor;

                if(hasSuccessiveRefinement) {
                    if(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM > 0) {
                        PatternedSuccessiveRefinementGuessing2Render.initCoordinates(true);
                    }
                    else {
                        PatternedSuccessiveRefinementGuessingRender.initCoordinates(true);
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
            PatternedSuccessiveRefinementGuessingRender.CoordinatesPerLevel = null;
            PatternedSuccessiveRefinementGuessing2Render.CoordinatesPerLevel = null;
        }

        public static void clearFastJulia() {
            coordinatesFastJulia = null;
            PatternedSuccessiveRefinementGuessingRender.CoordinatesPerLevelFastJulia = null;
            PatternedSuccessiveRefinementGuessing2Render.CoordinatesPerLevelFastJulia = null;
        }

        @Override
        protected void render(int image_width, int image_height, boolean polar) throws StopExecutionException {

            Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

            //Better brute force
            int x, y, coordinatesLoc, loc;

            int condition = image_width * image_height;

            initialize(location);

            boolean escaped_val;
            double f_val;

            long time = System.currentTimeMillis();

            do {

                coordinatesLoc = THREAD_CHUNK_SIZE * normal_rendering_algorithm_pixel.getAndIncrement();

                if(coordinatesLoc >= condition) {
                    break;
                }

                for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {

                    Pixel pix = coordinates[coordinatesLoc];
                    x = pix.x;
                    y = pix.y;

                    loc = y * image_width + x;

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

            pixel_calculation_time_per_task = System.currentTimeMillis() - time;

            try {
                postProcess(image_width, image_height, null, location);
            }
            catch (StopSuccessiveRefinementException ex) {

            }

        }

        @Override
    protected void renderDomainAntialiased(int image_width, int image_height, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        //better Brute force with antialiasing
        int x, y, loc, coordinatesLoc;
        int color;

        int condition = image_width * image_height;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        double f_val;

        long time = System.currentTimeMillis();

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_rendering_algorithm_pixel.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {

                Pixel pix = coordinates[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_width + x;

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

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_width, image_height, aa, location);
        }
        catch (StopSuccessiveRefinementException ex) {}

    }

    @Override
    protected void renderDomain(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);

        //Better brute force
        int x, y, coordinatesLoc, loc;

        int condition = image_width * image_height;

        long time = System.currentTimeMillis();

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_rendering_algorithm_pixel.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {

                Pixel pix = coordinates[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_width + x;

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

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_width, image_height, null, location);
        }
        catch (StopSuccessiveRefinementException ex) {}

    }

    @Override
    protected void renderAntialiased(int image_width, int image_height, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        //better Brute force with antialiasing
        int x, y, loc, coordinatesLoc;
        int color;

        double temp_result;

        int condition = image_width * image_height;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;
        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        initialize(location);

        boolean escaped_val;
        double f_val;

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        long time = System.currentTimeMillis();

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_rendering_algorithm_pixel.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {

                Pixel pix = coordinates[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_width + x;

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

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_width, image_height, aa, location);
        }catch (StopSuccessiveRefinementException ex) {}
    }

    @Override
    protected void renderFastJulia(int image_size, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_size, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initializeFastJulia(location);

        int x, y, loc, coordinatesLoc;

        int condition = image_size * image_size;

        boolean escaped_val;
        double f_val;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_rendering_algorithm_pixel.getAndIncrement();

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
    protected void renderFastJuliaAntialiased(int image_size, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_size, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        initializeFastJulia(location);

        int x, y, loc, coordinatesLoc;
        int color;

        double temp_result;

        int condition = image_size * image_size;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        boolean escaped_val;
        double f_val;

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData_fast_julia != null;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_rendering_algorithm_pixel.getAndIncrement();

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

        int x, y, loc, coordinatesLoc;
        int condition = image_width * image_height;
        Pixel[] coord = !isFastJulia() ? coordinates : coordinatesFastJulia;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_rendering_algorithm_post_processing.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coord[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_width + x;

                applyPostProcessingOnPixel(loc, x, y, image_width, image_height, image_iterations, escaped, pixelData, aa, modified, sizeCorr, lightx, lighty, location);

            }

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

        aa.setNeedsAllSamples(needsPostProcessing());

        int loc;
        int condition = image_width * image_height;

        int color, x, y, coordinatesLoc;
        PixelExtraData data;
        task_completed = 0;

        long time = System.currentTimeMillis();

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_rendering_algorithm_apply_palette.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coordinates[coordinatesLoc];
                x = pix.x;
                y = pix.y;
                loc = y * image_width + x;

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

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_width, image_height, aa, null);

    }

    @Override
    protected void changePalette(int image_width, int image_height) throws StopSuccessiveRefinementException, StopExecutionException {

        int x, y, loc, coordinatesLoc;
        int condition = image_width * image_height;
        task_completed = 0;

        long time = System.currentTimeMillis();

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_rendering_algorithm_apply_palette.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coordinates[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_width + x;

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

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_width, image_height, null, null);

    }

    @Override
    protected void quickRender(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int color, loc2, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;


        int current_chunk_size = tile;
        int min_chuck_size = tile;
        if(QUICKRENDER_SUCCESSIVE_REFINEMENT) {
            current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;
            min_chuck_size = prevPowerOf2(tile);
        }

        int condition = image_width * image_height;

        long nano_time = 0;

        for(int id = 0; current_chunk_size >= min_chuck_size; current_chunk_size >>= 1, id++) {
            long time = System.nanoTime();
            AtomicInteger ai = quick_render_rendering_algorithm_pixel[id];
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

                    loc2 = y * image_width + x;

                    if(rgbs[loc2] >>> 24 != Constants.QUICKRENDER_CALCULATED_ALPHA) {
                        image_iterations[loc2] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                        escaped[loc2] = escaped_val = iteration_algorithm.escaped();
                        color = getFinalColor(f_val, escaped_val);

                        rgbs[loc2] = (color & 0xFFFFFF) | Constants.QUICKRENDER_CALCULATED_ALPHA_OFFSETED;

                        task_calculated++;

                        tempx = Math.min(x + current_chunk_size, image_width);
                        tempy = Math.min(y + current_chunk_size, image_height);

                        for (int i = y; i < tempy; i++) {
                            for (int j = x, loc3 = i * image_width + j; j < tempx; j++, loc3++) {
                                if (loc3 != loc2) {
                                    rgbs[loc3] = color;
                                }
                            }
                        }
                    }
                }

            } while (true);

            nano_time += System.nanoTime() - time;

            if(QUICKRENDER_SUCCESSIVE_REFINEMENT) {
                WaitOnCondition.WaitOnCyclicBarrier(quick_render_rendering_algorithm_barrier);

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
    protected void quickRenderDomain(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);

        int tempx, tempy;

        int color, loc2, x, y;

        int current_chunk_size = tile;
        int min_chuck_size = tile;
        if(QUICKRENDER_SUCCESSIVE_REFINEMENT) {
            current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;
            min_chuck_size = prevPowerOf2(tile);
        }

        int condition = image_width * image_height;

        long nano_time = 0;

        for(int id = 0; current_chunk_size >= min_chuck_size; current_chunk_size >>= 1, id++) {
            long time = System.nanoTime();
            AtomicInteger ai = quick_render_rendering_algorithm_pixel[id];
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

                    loc2 = y * image_width + x;

                    if(rgbs[loc2] >>> 24 != Constants.QUICKRENDER_CALCULATED_ALPHA) {
                        Complex val = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                        image_iterations[loc2] = scaleDomainHeight(getDomainHeight(val));
                        color = domain_color.getDomainColor(val);
                        rgbs[loc2] = (color & 0xFFFFFF) | Constants.QUICKRENDER_CALCULATED_ALPHA_OFFSETED;

                        if (domain_image_data_re != null && domain_image_data_im != null) {
                            domain_image_data_re[loc2] = val.getRe();
                            domain_image_data_im[loc2] = val.getIm();
                        }

                        task_calculated++;

                        tempx = Math.min(x + current_chunk_size, image_width);
                        tempy = Math.min(y + current_chunk_size, image_height);

                        for (int i = y; i < tempy; i++) {
                            for (int j = x, loc3 = i * image_width + j; j < tempx; j++, loc3++) {
                                if (loc3 != loc2) {
                                    rgbs[loc3] = color;
                                }
                            }
                        }
                    }
                }

            } while (true);

            nano_time += System.nanoTime() - time;

            if(QUICKRENDER_SUCCESSIVE_REFINEMENT) {
                WaitOnCondition.WaitOnCyclicBarrier(quick_render_rendering_algorithm_barrier);
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
    public boolean hasPatternedLogic() {
        return true;
    }

}
