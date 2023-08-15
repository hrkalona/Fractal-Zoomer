package fractalzoomer.core.drawing_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.PixelExtraData;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.core.antialiasing.AntialiasingAlgorithm;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.Pixel;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class CircularBruteForceDraw extends ThreadDraw {
    private static Pixel[] coordinates;
    private static boolean sorted_by_zoom_center;
    private static Pixel[] coordinatesFastJulia;
    protected static final int THREAD_CHUNK_SIZE = 250;

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, org.apfloat.Apfloat
        yCenter, org.apfloat.Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow
        ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings
        rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings
        ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js) {
            super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js);
        }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, org.apfloat.Apfloat
        yCenter, org.apfloat.Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js) {
            super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js);
        }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, org.apfloat.Apfloat
        yCenter, org.apfloat.Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, org.apfloat.Apfloat
        xJuliaCenter, org.apfloat.Apfloat yJuliaCenter) {
            super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
        }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, org.apfloat.Apfloat
        yCenter, org.apfloat.Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, org.apfloat.Apfloat
        xJuliaCenter, org.apfloat.Apfloat yJuliaCenter) {
            super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
        }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, org.apfloat.Apfloat
        yCenter, org.apfloat.Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js) {
            super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js);
        }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, org.apfloat.Apfloat
        yCenter, org.apfloat.Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, org.apfloat.Apfloat
        xJuliaCenter, org.apfloat.Apfloat yJuliaCenter) {
            super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
        }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, java.awt.image.BufferedImage
        image, int color_cycling_location, int color_cycling_location2, BumpMapSettings bms, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, int color_cycling_speed, FiltersSettings fs, fractalzoomer.main.app_settings.EntropyColoringSettings
        ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, boolean cycle_colors, boolean cycle_lights, boolean cycle_gradient, int color_cycling_adjusting_value, DomainColoringSettings ds, int gradient_offset, HistogramColoringSettings hss, double contourFactor, boolean smoothing, GeneratedPaletteSettings gps) {
            super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, color_cycling_location2, bms, fdes, rps, color_cycling_speed, fs, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, cns, post_processing_order, ls, pbs, ots, cycle_colors, cycle_lights, cycle_gradient, color_cycling_adjusting_value, ds, gradient_offset, hss, contourFactor, smoothing, gps);
        }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, java.awt.image.BufferedImage
        image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, fractalzoomer.main.app_settings.BumpMapSettings
        bms, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, fractalzoomer.main.app_settings.EntropyColoringSettings
        ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, BlendingSettings color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, fractalzoomer.main.app_settings.DomainColoringSettings
        ds, int gradient_offset, HistogramColoringSettings hss, double contourFactor, boolean smoothing, GeneratedPaletteSettings gps) {
            super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs, bms, fdes, rps, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, cns, post_processing_order, ls, pbs, ots, ds, gradient_offset, hss, contourFactor, smoothing, gps);
        }

    public CircularBruteForceDraw(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean draw_action, MainWindow ptr, java.awt.image.BufferedImage
        image, fractalzoomer.main.app_settings.FiltersSettings fs,  BlendingSettings color_blending, double contourFactor, boolean smoothing, GeneratedPaletteSettings gps) {
            super(FROMx, TOx, FROMy, TOy, d3s, draw_action, ptr, image, fs,  color_blending, contourFactor, smoothing, gps);
        }

    public static void initCoordinatesFastJulia(int fast_julia_image_size) {

        Pixel.COMPARE_ALG = ThreadDraw.CIRCULAR_BRUTE_FORCE_COMPARE_ALG;
        Pixel.n = ThreadDraw.CIRCULAR_BRUTE_FORCE_N;
        Pixel.nreciprocal = 1 / Pixel.n;
        Pixel.useCustom = false;

        if(coordinatesFastJulia == null || coordinatesFastJulia.length != fast_julia_image_size * fast_julia_image_size) {
            Pixel.midX = Pixel.midY = -fast_julia_image_size / 2;

            coordinatesFastJulia = new Pixel[fast_julia_image_size * fast_julia_image_size];

            int loc = 0;
            for (int y = 0; y < fast_julia_image_size; y++) {
                for (int x = 0; x < fast_julia_image_size; x++, loc++) {
                    coordinatesFastJulia[loc] = new Pixel(x, y);
                }
            }

            Arrays.sort(coordinatesFastJulia);
        }
    }

        public static void initCoordinates(int image_size, boolean zoom_to_cursor, boolean doSort) {

            Pixel.COMPARE_ALG = ThreadDraw.CIRCULAR_BRUTE_FORCE_COMPARE_ALG;
            Pixel.n = ThreadDraw.CIRCULAR_BRUTE_FORCE_N;
            Pixel.nreciprocal = 1 / Pixel.n;
            Pixel.useCustom = zoom_to_cursor;


            if(coordinates == null || coordinates.length != image_size * image_size) {
                Pixel.midX = Pixel.midY = -image_size / 2;

                coordinates = new Pixel[image_size * image_size];

                int loc = 0;
                for (int y = 0; y < image_size; y++) {
                    for (int x = 0; x < image_size; x++, loc++) {
                        coordinates[loc] = new Pixel(x, y);
                    }
                }

                Arrays.sort(coordinates);
                sorted_by_zoom_center = zoom_to_cursor;
            }
            else if(doSort && (zoom_to_cursor || zoom_to_cursor != sorted_by_zoom_center)) {
                Arrays.sort(coordinates);
                sorted_by_zoom_center = zoom_to_cursor;
            }

        }

        public static void clear() {
            coordinates = null;
        }

        public static void clearFastJulia() {
            coordinatesFastJulia = null;
        }

        @Override
        protected void drawIterations(int image_size, boolean polar) {

            Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

            int pixel_percent = (image_size * image_size) / 100;

            //Better brute force
            int x, y, coordinatesLoc, loc;

            int condition = image_size * image_size;

            if(PERTURBATION_THEORY && fractal.supportsPerturbationTheory() && !HIGH_PRECISION_CALCULATION) {

                if (reference_calc_sync.getAndIncrement() == 0) {
                    calculateReference(location);
                }

                try {
                    reference_sync.await();
                } catch (InterruptedException ex) {

                } catch (BrokenBarrierException ex) {

                }

                location.setReference(Fractal.refPoint);
            }

            boolean escaped_val;
            double f_val;

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
                        thread_calculated++;
                    }

                    drawing_done++;
                }

                if(drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }

            } while(true);

            postProcess(image_size, null, location);
        }

    @Override
    protected void drawIterationsAntialiased(int image_size, boolean polar) {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        location.createAntialiasingSteps(aaMethod == 5, useJitter);

        int pixel_percent = (image_size * image_size) / 100;

        //better Brute force with antialiasing
        int x, y, loc, coordinatesLoc;
        int color;

        double temp_result;

        int condition = image_size * image_size;

        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int supersampling_num = (aaSamplesIndex == 0 ? 4 : 8 * aaSamplesIndex);
        int totalSamples = supersampling_num + 1;
        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, location);

        if(PERTURBATION_THEORY && fractal.supportsPerturbationTheory() && !HIGH_PRECISION_CALCULATION) {
            if (reference_calc_sync.getAndIncrement() == 0) {
                calculateReference(location);
            }

            try {
                reference_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }
            location.setReference(Fractal.refPoint);
        }

        boolean escaped_val;
        double f_val;

        boolean needsPostProcessing = needsPostProcessing();
        aa.setNeedsPostProcessing(needsPostProcessing);

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

                if(needsPostProcessing) {
                    pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for(int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp_result, escaped_val);

                    if(needsPostProcessing) {
                        pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                    }

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();

                drawing_done++;
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

        postProcess(image_size, aa, location);
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
        location.createAntialiasingSteps(aaMethod == 5, useJitter);

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

        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int supersampling_num = (aaSamplesIndex == 0 ? 4 : 8 * aaSamplesIndex);
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, location);

        boolean escaped_val;
        double f_val;

        boolean needsPostProcessing = needsPostProcessing();
        aa.setNeedsPostProcessing(needsPostProcessing);

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

                if(needsPostProcessing) {
                    pixelData_fast_julia[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for(int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp_result, escaped_val);

                    if(needsPostProcessing) {
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
        Pixel[] coord = condition == coordinates.length ? coordinates : coordinatesFastJulia;

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
    protected void changePalette(int image_size) {

        int pixel_percent = (image_size * image_size) / 100;

        int x, y, loc, coordinatesLoc;
        int condition = image_size * image_size;

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
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        postProcess(image_size, null, null);

    }

    @Override
    protected void applyHistogram(PixelExtraData[] data, int image_size, int maxCount, int histogramGranularity, double histogramDensity, Location location, AntialiasingAlgorithm aa) {

        if(d3 || action == COLOR_CYCLING) {
            super.applyHistogram(data, image_size, maxCount, histogramGranularity, histogramDensity, location, aa);
            return;
        }

        int x, y, loc, coordinatesLoc;
        int condition = image_size * image_size;
        Pixel[] coord = condition == coordinates.length ? coordinates : coordinatesFastJulia;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_histogram.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coord[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

                applyHistogramToPixel(loc, x, y, image_size, data, maxCount, histogramGranularity, histogramDensity, location, aa);
            }

        } while(true);

    }

    @Override
    protected void applyRankOrderMapping(PixelExtraData[] data, int image_size, Location location, AntialiasingAlgorithm aa) {

        if(d3 || action == COLOR_CYCLING) {
            super.applyRankOrderMapping(data, image_size, location, aa);
            return;
        }

        int x, y, loc, coordinatesLoc;
        int condition = image_size * image_size;
        Pixel[] coord = condition == coordinates.length ? coordinates : coordinatesFastJulia;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_histogram.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coord[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

                applyRankOrderMappingToPixel(loc, x, y, image_size, data, location, aa);
            }

        } while(true);

    }

    @Override
    protected void applyHistogram(double[] image_iterations, boolean[] escaped, int image_size, int maxCount, int histogramGranularity, double histogramDensity, Location location, AntialiasingAlgorithm aa) {

        if(d3 || action == COLOR_CYCLING) {
            super.applyHistogram(image_iterations, escaped, image_size, maxCount, histogramGranularity, histogramDensity, location, aa);
            return;
        }

        int x, y, loc, coordinatesLoc;
        int condition = image_size * image_size;
        Pixel[] coord = condition == coordinates.length ? coordinates : coordinatesFastJulia;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_histogram.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coord[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

                applyHistogramToPixel(loc, x, y, image_size, image_iterations, escaped, maxCount, histogramGranularity, histogramDensity, location, aa);
            }

        } while(true);
    }

    @Override
    protected void applyRankOrderMapping(double[] image_iterations, boolean[] escaped, int image_size, Location location, AntialiasingAlgorithm aa) {

        if(d3 || action == COLOR_CYCLING) {
            super.applyRankOrderMapping(image_iterations, escaped, image_size, location, aa);
            return;
        }

        int x, y, loc, coordinatesLoc;
        int condition = image_size * image_size;
        Pixel[] coord = condition == coordinates.length ? coordinates : coordinatesFastJulia;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_histogram.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coord[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

                applyRankOrderMappingToPixel(loc, x, y, image_size, image_iterations, escaped, location, aa);
            }

        } while(true);
    }

    @Override
    protected void applyScaling(double[] image_iterations, boolean[] escaped, int mapping, int image_size, Location location, AntialiasingAlgorithm aa) {

        if(d3 || action == COLOR_CYCLING) {
            super.applyScaling(image_iterations, escaped, mapping, image_size, location, aa);
            return;
        }

        int x, y, loc, coordinatesLoc;
        int condition = image_size * image_size;
        Pixel[] coord = condition == coordinates.length ? coordinates : coordinatesFastJulia;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_histogram.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coord[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

                applyScalingToPixel(loc, x, y, image_size, image_iterations, escaped, mapping, location, aa);
            }

        } while(true);
    }

    @Override
    protected void applyScaling(PixelExtraData[] data, int mapping, int image_size, Location location, AntialiasingAlgorithm aa) {

        if(d3 || action == COLOR_CYCLING) {
            super.applyScaling(data, mapping, image_size, location, aa);
            return;
        }

        int x, y, loc, coordinatesLoc;
        int condition = image_size * image_size;
        Pixel[] coord = condition == coordinates.length ? coordinates : coordinatesFastJulia;

        do {

            coordinatesLoc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_histogram.getAndIncrement();

            if(coordinatesLoc >= condition) {
                break;
            }

            for(int count = 0; count < THREAD_CHUNK_SIZE && coordinatesLoc < condition; count++, coordinatesLoc++) {
                Pixel pix = coord[coordinatesLoc];
                x = pix.x;
                y = pix.y;

                loc = y * image_size + x;

                applyScalingToPixel(loc, x, y, image_size, data, mapping, location, aa);
            }

        } while(true);
    }

}
