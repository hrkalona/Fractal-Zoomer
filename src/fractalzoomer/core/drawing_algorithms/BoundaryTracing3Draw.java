/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
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

import fractalzoomer.core.ThreadDraw;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.BrokenBarrierException;

/**
 *
 * @author hrkalona2
 */
@Deprecated
public class BoundaryTracing3Draw extends ThreadDraw {
    public BoundaryTracing3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js);
    }

    public BoundaryTracing3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js);
    }

    public BoundaryTracing3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
    }

    public BoundaryTracing3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
    }

    public BoundaryTracing3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js);
    }

    public BoundaryTracing3Draw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio, bms, polar_projection, circle_period, fdes, rps, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, gradient_offset, hss, contourFactor, gps, js, xJuliaCenter, yJuliaCenter);
    }

    public BoundaryTracing3Draw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, int color_cycling_location2, BumpMapSettings bms, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, int color_cycling_speed, FiltersSettings fs, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, boolean cycle_colors, boolean cycle_lights, boolean cycle_gradient, DomainColoringSettings ds, int gradient_offset, HistogramColoringSettings hss, double contourFactor, boolean smoothing, GeneratedPaletteSettings gps) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, color_cycling_location2, bms, fdes, rps, color_cycling_speed, fs, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, cns, post_processing_order, ls, pbs, ots, cycle_colors, cycle_lights, cycle_gradient, ds, gradient_offset, hss, contourFactor, smoothing, gps);
    }

    public BoundaryTracing3Draw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, BumpMapSettings bms, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, DomainColoringSettings ds, int gradient_offset, HistogramColoringSettings hss, double contourFactor, boolean smoothing, GeneratedPaletteSettings gps) {
        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, color_cycling_location2, fs, bms, fdes, rps, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, cns, post_processing_order, ls, pbs, ots, ds, gradient_offset, hss, contourFactor,  smoothing, gps);
    }

    public BoundaryTracing3Draw(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean draw_action, MainWindow ptr, BufferedImage image, FiltersSettings fs, int color_blending, double contourFactor, boolean smoothing, GeneratedPaletteSettings gps) {
        super(FROMx, TOx, FROMy, TOy, d3s, draw_action, ptr, image, fs,  color_blending, contourFactor, smoothing, gps);
    }


    private static final int RIGHT  = 0;
    private static final int LEFT  = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private void Trace(int x, int y, int pix, Location location, int image_size, int culcColor)
    {
        int sx,sy,nextColor,dir=RIGHT;
        boolean fill = false;

        double start_val;
        boolean startEscaped;
        int startColor;

        start_val = iteration_algorithm.calculate(location.getComplex(x, y));
        startEscaped = iteration_algorithm.escaped();
        startColor = getFinalColor(start_val, startEscaped);
        thread_calculated++;

        image_iterations[pix] = start_val;
        escaped[pix]  = startEscaped;
        rgbs[pix] = startColor;
        drawing_done++;

        int skippedColor = getColorForSkippedPixels(startColor, randomNumber);

        while (y - 1 >= FROMy && rgbs[pix - image_size] == startColor) {   // looking for boundary
            pix = pix - image_size;
            y--;
        }

        sx=x; sy=y;

        boolean tempEscaped;
        double tempVal;
        int tempPix;

        //this code is based on boundary.c with some improvements
        //technically

        int temp_x, temp_y;
        do
        {
            switch(dir)
            {
                case RIGHT:
                    /* check left */
                    temp_y = y - 1;
                    if(temp_y >= FROMy)
                    {
                        tempPix = pix - image_size;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go left */
                        {
                            dir=UP;
                            y = temp_y;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* check ahead */
                    temp_x = x + 1;
                    if(temp_x < TOx)
                    {
                        tempPix = pix + 1;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(temp_x, y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go ahead */
                        {
                            /* dir is still RIGHT */
                            x = temp_x;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* check right */
                    temp_y = y + 1;
                    if(temp_y < TOy)
                    {
                        tempPix = pix + image_size;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go right */
                        {
                            dir=DOWN;
                            y = temp_y;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* go back */
                    if(x==sx && y==sy) /* if direction is RIGHT and we're at the */
                        /* starting point, going back means return */
                        return;
                    dir=LEFT;
                    x--;
                    pix--;
                    break;

                case LEFT:
                    /* check left */
                    temp_y = y + 1;
                    if(temp_y < TOy)
                    {
                        tempPix = pix + image_size;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go left */
                        {
                            dir=DOWN;
                            y = temp_y;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* check ahead */
                    temp_x = x - 1;
                    if(temp_x >= FROMx)
                    {
                        tempPix = pix - 1;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(temp_x, y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go ahead */
                        {
                            /* dir is still LEFT */
                            x = temp_x;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* check right */
                    temp_y = y - 1;
                    if(temp_y >= FROMy)
                    {
                        tempPix = pix - image_size;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go right */
                        {
                            dir=UP;
                            y = temp_y;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* go back */
                    dir=RIGHT;
                    x++;
                    pix++;
                    break;

                case UP:
                    if(fill) /* fill current line by going right until we hit a */
                        /* pixel with a different color than c */
                    {
                        tempPix = pix;
                        int flood_ix = x;
                        int floodColor;
                        while (true) {
                            flood_ix++;

                            if (flood_ix >= TOx) {
                                break;
                            }

                            tempPix++;

                            if ((floodColor = rgbs[tempPix]) >>> 24 == culcColor) {
                                rgbs[tempPix] = skippedColor;
                                image_iterations[tempPix] = start_val;
                                escaped[tempPix] = startEscaped;
                                drawing_done++;
                            } else if (floodColor != startColor) {
                                break;
                            }

                        }
                    }
                    /* check left */
                    temp_x = x - 1;
                    if(temp_x >= FROMx)
                    {
                        tempPix = pix - 1;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(temp_x, y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go left */
                        {
                            dir=LEFT;
                            x = temp_x;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* check ahead */
                    temp_y = y - 1;
                    if(temp_y >= FROMy)
                    {
                        tempPix = pix - image_size;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go ahead */
                        {
                            /* dir is still UP */
                            y = temp_y;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* check right */
                    temp_x = x + 1;
                    if(temp_x < TOx)
                    {
                        tempPix = pix + 1;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(temp_x, y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go right */
                        {
                            dir=RIGHT;
                            x = temp_x;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* go back */
                    dir=DOWN;
                    y++;
                    pix += image_size;
                    break;

                case DOWN:
                    /* check left */
                    temp_x = x + 1;
                    if(temp_x < TOx)
                    {
                        tempPix = pix + 1;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(temp_x, y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go left */
                        {
                            dir=RIGHT;
                            x = temp_x;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* check ahead */
                    temp_y = y + 1;
                    if(temp_y < TOy)
                    {
                        tempPix = pix + image_size;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go ahead */
                        {
                            /* dir is still DOWN */
                            y = temp_y;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* check right */
                    temp_x = x - 1;
                    if(temp_x >= FROMx)
                    {
                        tempPix = pix - 1;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(temp_x, y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            thread_calculated++;
                            drawing_done++;
                        }
                        if(nextColor==startColor) /* go right */
                        {
                            dir=LEFT;
                            x = temp_x;
                            pix = tempPix;
                            break;
                        }
                    }
                    /* go back */
                    dir=UP;
                    y--;
                    pix -= image_size;
                    break;
            }

            if(x==sx&&y==sy) /* we're back where we started */
            {
                if(fill) /* we're done */
                    return;
                fill=true;  /* trace again, filling encircled area */
                dir=RIGHT;
            }
        }
        while(true);
    }
    
    @Override
    protected void drawIterations(int image_size, boolean polar) {


        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js, polar, PERTURBATION_THEORY && fractal.supportsPerturbationTheory());

        if(PERTURBATION_THEORY && fractal.supportsPerturbationTheory()) {
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

        int pixel_percent = (image_size * image_size) / 100;

        int totalPixels = (TOx - FROMx) * (TOy - FROMy);

        final int culcColor = Constants.MAGIC_ALPHA;

        int last_drawing_done = 0;

        stop:
        for(int y = FROMy; y < TOy; y++) {
            for (int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                if (rgbs[loc] != culcColor) {
                    continue;
                }
                Trace(x,y, loc, location, image_size, culcColor);

                int dif = drawing_done - last_drawing_done;
                if (dif / pixel_percent >= 1) {
                    update(dif);
                    last_drawing_done = drawing_done;
                }

                if (drawing_done == totalPixels) {
                    break stop;
                }
            }
        }

        int dif = drawing_done - last_drawing_done;
        if (dif > 0) {
            update(dif);
        }


        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }
        
        postProcess(image_size);
    }


    @Override
    protected void drawIterationsAntialiased(int image_size, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void drawFastJulia(int image_size, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void drawFastJuliaAntialiased(int image_size, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
