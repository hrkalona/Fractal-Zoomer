/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.core.drawing_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.BumpMapSettings;
import fractalzoomer.main.app_settings.ContourColoringSettings;
import fractalzoomer.main.app_settings.D3Settings;
import fractalzoomer.main.app_settings.DomainColoringSettings;
import fractalzoomer.main.app_settings.EntropyColoringSettings;
import fractalzoomer.main.app_settings.FakeDistanceEstimationSettings;
import fractalzoomer.main.app_settings.FiltersSettings;
import fractalzoomer.main.app_settings.FunctionSettings;
import fractalzoomer.main.app_settings.GreyscaleColoringSettings;
import fractalzoomer.main.app_settings.OffsetColoringSettings;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.RainbowPaletteSettings;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author hrkalona2
 */
public class SolidGuessingDraw extends ThreadDraw {

    private static final int SOLID_GUESSING_SLICES = 10;

    public SolidGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns, post_processing_order);
    }

    public SolidGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns, post_processing_order);
    }

    public SolidGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns, post_processing_order, xJuliaCenter, yJuliaCenter);
    }

    public SolidGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns, post_processing_order, xJuliaCenter, yJuliaCenter);
    }

    public SolidGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns, post_processing_order);
    }

    public SolidGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, double xJuliaCenter, double yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, cns, post_processing_order, xJuliaCenter, yJuliaCenter);
    }

    public SolidGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, BumpMapSettings bms, double color_intensity, int transfer_function, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, int color_cycling_speed, FiltersSettings fs, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, bms, fdes, rps, color_cycling_speed, fs, color_intensity, transfer_function, ens, ofs, gss, color_blending, cns, post_processing_order);
    }

    public SolidGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, FiltersSettings fs, BumpMapSettings bms, double color_intensity, int transfer_function, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, fs, bms, fdes, rps, color_intensity, transfer_function, ens, ofs, gss, color_blending, cns, post_processing_order);
    }

    public SolidGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean draw_action, MainWindow ptr, BufferedImage image, FiltersSettings fs, int color_blending) {
        super(FROMx, TOx, FROMy, TOy, d3s, draw_action, ptr, image, fs, color_blending);
    }

    @Override
    protected void drawIterations(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = (image_size * image_size) / 100;

        int x = 0;
        int y = 0;
        boolean whole_area;
        int step;
        double temp_x0 = 0;
        double temp_y0;

        double temp_starting_pixel_cicle;
        int temp_starting_pixel_color;

        int thread_size_width = TOx - FROMx;
        int thread_size_height = TOy - FROMy;

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;
        int loc;

        int notCalculated = 0;

        int skippedColor;

        for (int i = 0; i < SOLID_GUESSING_SLICES; i++) {
            slice_FROMy = FROMy + i * thread_size_height / SOLID_GUESSING_SLICES;
            slice_TOy = FROMy + (i + 1) * (thread_size_height) / SOLID_GUESSING_SLICES;
            for (int j = 0; j < SOLID_GUESSING_SLICES; j++) {
                slice_FROMx = FROMx + j * (thread_size_width) / SOLID_GUESSING_SLICES;
                slice_TOx = FROMx + (j + 1) * (thread_size_width) / SOLID_GUESSING_SLICES;

                double temp = (slice_TOy - slice_FROMy + 1) / 2;

                for (y = slice_FROMy, whole_area = true, step = 0; step < temp; step++, whole_area = true) {

                    temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                    x = slice_FROMx + step;
                    temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;

                    loc = y * image_size + x;

                    temp_starting_pixel_cicle = image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));

                    rgbs[loc] = temp_starting_pixel_color = getFinalColor(temp_starting_pixel_cicle);

                    drawing_done++;
                    thread_calculated++;

                    for (x++, loc++; x < slice_TOx - step; x++, loc++) {
                        if (rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);

                            if (rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            thread_calculated++;
                        }

                    }

                    for (x--, y++; y < slice_TOy - step; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;
                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);

                            if (rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    for (y--, x--, loc = y * image_size + x; x >= slice_FROMx + step; x--, loc--) {
                        if (rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);

                            if (rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    for (x++, y--; y > slice_FROMy + step; y--) {
                        loc = y * image_size + x;
                        if (rgbs[loc] == notCalculated) {
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);

                            if (rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            thread_calculated++;
                        }
                    }
                    y++;
                    x++;

                    if (drawing_done / pixel_percent >= 1) {
                        update(drawing_done);
                        drawing_done = 0;
                    }

                    if (whole_area) {
                        int temp6 = step + 1;
                        int temp1 = slice_TOx - temp6;
                        int temp2 = slice_TOy - temp6;

                        int temp3;
                        int temp4;

                        int chunk = temp1 - x;

                        skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, randomNumber + step);

                        for (int k = y; k < temp2; k++) {
                            temp3 = k * image_size + x;
                            temp4 = k * image_size + temp1;
                            Arrays.fill(image_iterations, temp3, temp4, temp_starting_pixel_cicle);
                            Arrays.fill(rgbs, temp3, temp4, skippedColor);
                            drawing_done += chunk;
                        }

                        if (drawing_done / pixel_percent >= 1) {
                            update(drawing_done);
                            drawing_done = 0;
                        }
                        break;
                    }
                }
            }
        }

        postProcess(image_size);

    }

    @Override
    protected void drawIterationsPolar(int image_size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void drawIterationsPolarAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void drawIterationsAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void drawFastJulia(int image_size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void drawFastJuliaPolar(int image_size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void drawFastJuliaAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void drawFastJuliaPolarAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
