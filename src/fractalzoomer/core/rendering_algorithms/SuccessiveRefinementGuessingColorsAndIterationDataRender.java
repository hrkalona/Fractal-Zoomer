package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.MinimalRendererWindow;
import fractalzoomer.main.app_settings.*;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuccessiveRefinementGuessingColorsAndIterationDataRender extends SuccessiveRefinementGuessingRender {

    public SuccessiveRefinementGuessingColorsAndIterationDataRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public SuccessiveRefinementGuessingColorsAndIterationDataRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public SuccessiveRefinementGuessingColorsAndIterationDataRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public SuccessiveRefinementGuessingColorsAndIterationDataRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public SuccessiveRefinementGuessingColorsAndIterationDataRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public SuccessiveRefinementGuessingColorsAndIterationDataRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    @Override
    protected boolean isTheSame(int loca, int locb, int locc, int locd) {
        if (pixelData != null) {
            if (!(rgbs[loca] == rgbs[locb] && rgbs[locb] == rgbs[locc] && rgbs[locc] == rgbs[locd])) {
                return false;
            }

            double loc_a_data = image_iterations[loca];
            double[] loc_a_values = pixelData[loca].values;
            for (int i = 0; i < loc_a_values.length; i++) {
                if (loc_a_data != loc_a_values[i]) {
                    return false;
                }
            }

            double[] loc_b_values = pixelData[locb].values;
            for (int i = 0; i < loc_b_values.length; i++) {
                if (loc_a_data != loc_b_values[i]) {
                    return false;
                }
            }

            double[] loc_c_values = pixelData[locc].values;
            for (int i = 0; i < loc_c_values.length; i++) {
                if (loc_a_data != loc_c_values[i]) {
                    return false;
                }
            }

            double[] loc_d_values = pixelData[locd].values;
            for (int i = 0; i < loc_d_values.length; i++) {
                if (loc_a_data != loc_d_values[i]) {
                    return false;
                }
            }

            return true;
        } else {
            return rgbs[loca] == rgbs[locb] && rgbs[locb] == rgbs[locc] && rgbs[locc] == rgbs[locd]
                    && image_iterations[loca] == image_iterations[locb] && image_iterations[locb] == image_iterations[locc] && image_iterations[locc] == image_iterations[locd];
        }
    }

    @Override
    protected boolean isTheSameFastJulia(int loca, int locb, int locc, int locd) {
        if (pixelData_fast_julia != null && pixelData_fast_julia[0].values != null) {
            if (!(rgbs[loca] == rgbs[locb] && rgbs[locb] == rgbs[locc] && rgbs[locc] == rgbs[locd])) {
                return false;
            }

            double loc_a_data = image_iterations_fast_julia[loca];
            double[] loc_a_values = pixelData_fast_julia[loca].values;
            for (int i = 0; i < loc_a_values.length; i++) {
                if (loc_a_data != loc_a_values[i]) {
                    return false;
                }
            }

            double[] loc_b_values = pixelData_fast_julia[locb].values;
            for (int i = 0; i < loc_b_values.length; i++) {
                if (loc_a_data != loc_b_values[i]) {
                    return false;
                }
            }

            double[] loc_c_values = pixelData_fast_julia[locc].values;
            for (int i = 0; i < loc_c_values.length; i++) {
                if (loc_a_data != loc_c_values[i]) {
                    return false;
                }
            }

            double[] loc_d_values = pixelData_fast_julia[locd].values;
            for (int i = 0; i < loc_d_values.length; i++) {
                if (loc_a_data != loc_d_values[i]) {
                    return false;
                }
            }

            return true;
        } else {
            return rgbs[loca] == rgbs[locb] && rgbs[locb] == rgbs[locc] && rgbs[locc] == rgbs[locd]
                    && image_iterations_fast_julia[loca] == image_iterations_fast_julia[locb] && image_iterations_fast_julia[locb] == image_iterations_fast_julia[locc] && image_iterations_fast_julia[locc] == image_iterations_fast_julia[locd];
        }
    }

    @Override
    protected boolean isTheSame(int loca, int locb) {
        if (pixelData != null) {
            if (rgbs[loca] != rgbs[locb]) {
                return false;
            }

            double loc_a_data = image_iterations[loca];
            double[] loc_a_values = pixelData[loca].values;
            for (int i = 0; i < loc_a_values.length; i++) {
                if (loc_a_data != loc_a_values[i]) {
                    return false;
                }
            }

            double[] loc_b_values = pixelData[locb].values;
            for (int i = 0; i < loc_b_values.length; i++) {
                if (loc_a_data != loc_b_values[i]) {
                    return false;
                }
            }

            return true;
        } else {
            return rgbs[loca] == rgbs[locb]
                    && image_iterations[loca] == image_iterations[locb];
        }
    }

    @Override
    protected boolean isTheSameFastJulia(int loca, int locb) {
        if (pixelData_fast_julia != null && pixelData_fast_julia[0].values != null) {
            if (rgbs[loca] != rgbs[locb]) {
                return false;
            }

            double loc_a_data = image_iterations_fast_julia[loca];
            double[] loc_a_values = pixelData_fast_julia[loca].values;
            for (int i = 0; i < loc_a_values.length; i++) {
                if (loc_a_data != loc_a_values[i]) {
                    return false;
                }
            }

            double[] loc_b_values = pixelData_fast_julia[locb].values;
            for (int i = 0; i < loc_b_values.length; i++) {
                if (loc_a_data != loc_b_values[i]) {
                    return false;
                }
            }

            return true;
        } else {
            return rgbs[loca] == rgbs[locb] &&
                    image_iterations_fast_julia[loca] == image_iterations_fast_julia[locb];
        }
    }

}
