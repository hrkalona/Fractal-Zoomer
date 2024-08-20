
package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.TaskRender;
import fractalzoomer.core.antialiasing.AntialiasingAlgorithm;
import fractalzoomer.core.location.Location;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MinimalRendererWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.Square;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona2
 */
public class MarianiSilver3Render extends MarianiSilverRender {

    public MarianiSilver3Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilver3Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilver3Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public MarianiSilver3Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public MarianiSilver3Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public MarianiSilver3Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    @Override
    protected boolean canSubDivide(int xLength, int yLength) {
        return (xLength >= MAX_TILE_SIZE || yLength >= MAX_TILE_SIZE) && (xLength >= 3 && yLength >= 3);
    }

    @Override
    protected void performSubDivision(int currentIteration, int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, int xLength, int yLength, Location location, int image_width) {
        int x, y;
        int loc;
        double f_val;
        boolean escaped_val;

        if(getSplitMode(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM, currentIteration) == 1) {
            int halfY = slice_FROMy + (yLength >>> 1);
            y = halfY;
            location.precalculateY(y);
            for (x = slice_FROMx + 1, loc = y * image_width + x; x < slice_TOx; x++, loc++) {

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

            int nextIter = currentIteration + 1;
            Square square1 = new Square(slice_FROMx, slice_FROMy, slice_TOx, halfY, nextIter);
            Square square3 = new Square(slice_FROMx, halfY, slice_TOx, slice_TOy, nextIter);
            enqueueSquare(square1, square3);
        }
        else {
            int halfX = slice_FROMx + (xLength >>> 1);
            x = halfX;
            location.precalculateX(x);
            for (y = slice_FROMy + 1; y < slice_TOy; y++) {
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

            int nextIter = currentIteration + 1;
            Square square2 = new Square(slice_FROMx, slice_FROMy, halfX, slice_TOy, nextIter);
            Square square4 = new Square(halfX, slice_FROMy, slice_TOx, slice_TOy, nextIter);
            enqueueSquare(square2, square4);
        }
    }

    @Override
    protected void performSubDivisionAntialiased(int currentIteration, int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, int xLength, int yLength, Location location, int image_width, AntialiasingAlgorithm aa, int supersampling_num, int totalSamples, boolean storeExtraData) {
        int x, y;
        int loc;
        int color;
        double f_val, temp_result;
        boolean escaped_val;

        if(getSplitMode(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM, currentIteration) == 1) {
            int halfY = slice_FROMy + (yLength >>> 1);
            y = halfY;
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

            int nextIter = currentIteration + 1;
            Square square1 = new Square(slice_FROMx, slice_FROMy, slice_TOx, halfY, nextIter);
            Square square3 = new Square(slice_FROMx, halfY, slice_TOx, slice_TOy, nextIter);
            enqueueSquare(square1, square3);
        }
        else {
            int halfX = slice_FROMx + (xLength >>> 1);
            x = halfX;
            location.precalculateX(x);
            for (y = slice_FROMy + 1; y < slice_TOy; y++) {
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

            int nextIter = currentIteration + 1;
            Square square2 = new Square(slice_FROMx, slice_FROMy, halfX, slice_TOy, nextIter);
            Square square4 = new Square(halfX, slice_FROMy, slice_TOx, slice_TOy, nextIter);
            enqueueSquare(square2, square4);
        }
    }

    @Override
    protected void performSubDivisionFastJulia(int currentIteration, int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, int xLength, int yLength, Location location, int image_size) {
        int y, x;
        int loc;
        double f_val;
        boolean escaped_val;
        if(getSplitMode(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM, currentIteration) == 1) {
            int halfY = slice_FROMy + (yLength >>> 1);
            y = halfY;
            location.precalculateY(y);
            for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {
                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithX(x));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
            }

            int nextIter = currentIteration + 1;
            Square square1 = new Square(slice_FROMx, slice_FROMy, slice_TOx, halfY, nextIter);
            Square square3 = new Square(slice_FROMx, halfY, slice_TOx, slice_TOy, nextIter);
            enqueueSquare(square1, square3);
        }
        else {
            int halfX = slice_FROMx + (xLength >>> 1);
            x = halfX;
            location.precalculateX(x);
            for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                loc = y * image_size + x;
                image_iterations_fast_julia[loc] = f_val = iteration_algorithm.calculate(location.getComplexWithY(y));
                escaped_fast_julia[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);
            }

            int nextIter = currentIteration + 1;
            Square square2 = new Square(slice_FROMx, slice_FROMy, halfX, slice_TOy, nextIter);
            Square square4 = new Square(halfX, slice_FROMy, slice_TOx, slice_TOy, nextIter);
            enqueueSquare(square2, square4);
        }
    }

    public static int getSplitMode(int split_algorithm, int iteration) {
        if(split_algorithm == 1) {
            return ((iteration + 1) % 2); //1, 0, 1, 0,...
        }
        else if(split_algorithm == 2) {
            return iteration % 2; // 0, 1, 0, 1,...
        }
        else if(split_algorithm == 3) {
            //1, 0, 0, 1, 1, 0, 0, 1, 1
            int iter = iteration + 2;
            int val = iter % 2;
            int val2 = iter % 4;
            if(val2 < 2) {
                return val;
            }
            else {
                return 1 - val;
            }
        }
        else if(split_algorithm == 4) {
            //0, 1, 1, 0, 0, 1, 1, 0, 0,...
            int val = iteration % 2;
            int val2 = iteration % 4;
            if(val2 < 2) {
                return val;
            }
            else {
                return 1 - val;
            }
        }
        return -1;
    }

    @Override
    protected void performSubDivisionFastJuliaAntialiased(int currentIteration, int slice_FROMx, int slice_TOx, int slice_FROMy, int slice_TOy, int xLength, int yLength, Location location, int image_size, AntialiasingAlgorithm aa, int supersampling_num, int totalSamples, boolean storeExtraData) {
        int y, x;
        int loc;
        double f_val, temp_result;
        boolean escaped_val;
        int color;

        if(getSplitMode(TaskRender.SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM, currentIteration) == 1) {
            int halfY = slice_FROMy + (yLength >>> 1);
            y = halfY;
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

            int nextIter = currentIteration + 1;
            Square square1 = new Square(slice_FROMx, slice_FROMy, slice_TOx, halfY, nextIter);
            Square square3 = new Square(slice_FROMx, halfY, slice_TOx, slice_TOy, nextIter);
            enqueueSquare(square1, square3);
        }
        else {
            int halfX = slice_FROMx + (xLength >>> 1);
            x = halfX;
            location.precalculateX(x);
            for (y = slice_FROMy + 1; y < slice_TOy; y++) {

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

                    if (storeExtraData) {
                        pixelData_fast_julia[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                    }

                    if (!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();
            }

            int nextIter = currentIteration + 1;
            Square square2 = new Square(slice_FROMx, slice_FROMy, halfX, slice_TOy, nextIter);
            Square square4 = new Square(halfX, slice_FROMy, slice_TOx, slice_TOy, nextIter);
            enqueueSquare(square2, square4);
        }
    }
}
