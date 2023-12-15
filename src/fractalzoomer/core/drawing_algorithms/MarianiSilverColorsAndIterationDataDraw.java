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

import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona2
 */
public class MarianiSilverColorsAndIterationDataDraw extends MarianiSilverDraw {

    public MarianiSilverColorsAndIterationDataDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilverColorsAndIterationDataDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilverColorsAndIterationDataDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public MarianiSilverColorsAndIterationDataDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public MarianiSilverColorsAndIterationDataDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilverColorsAndIterationDataDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio,  boolean polar_projection, double circle_period,   boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  int gradient_offset,  double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps,Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    protected boolean isNotTheSame(int loc, int pixel_color, double pixel_value) {
        return rgbs[loc] != pixel_color || image_iterations[loc] != pixel_value;
    }

    protected boolean isNotTheSameFastJulia(int loc, int pixel_color, double pixel_value) {
        return rgbs[loc] != pixel_color || image_iterations_fast_julia[loc] != pixel_value;
    }

}
