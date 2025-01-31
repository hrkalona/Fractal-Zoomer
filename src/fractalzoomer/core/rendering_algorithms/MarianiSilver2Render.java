
package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.location.Location;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.Square;
import fractalzoomer.utils.StopExecutionException;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import fractalzoomer.utils.WaitOnCondition;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona2
 */
@Deprecated
public class MarianiSilver2Render extends MarianiSilverRender {
    private static final int MAX_TILE_SIZE = 3;

    public MarianiSilver2Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }
    
    @Override
    protected void render(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException, StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        task_completed = 0;

        createQueueSquare();

        int iteration = 0;
        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }

            enqueueSquare(currentSquare);

            iteration++;
        } while (true);

        WaitOnCondition.WaitOnCyclicBarrier(initialize_jobs_sync);

        int x = 0;
        int y = 0;
        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        boolean temp_starting_escaped;

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        int loc;

        int skippedColor;

        long time = System.currentTimeMillis();

        do {

            Square currentSquare = getNextSquare();

            if(currentSquare == null) {
                break;
            }

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_width + x;

            if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                temp_starting_value = image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                temp_starting_escaped = escaped[loc] = iteration_algorithm.escaped();
                temp_starting_pixel_color = rgbs[loc] = getFinalColor(temp_starting_value, temp_starting_escaped);
                task_calculated++;
                task_completed++;
            }
            else {
                temp_starting_value = image_iterations[loc];
                temp_starting_pixel_color = rgbs[loc];
                temp_starting_escaped = escaped[loc];
            }
            if(perform_work_stealing_and_wait) {
                rendering_done_per_task[taskId]++;
            }
            else {
                rendering_done++;
            }

            int same_count = 0;
            int perimeter_count = 0;

            for(x++, loc = y * image_width + x; x < slice_TOx; x++, loc++) {

                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(PERIMETER_ACCURACY == 1) {
                        if (rgbs[loc] != temp_starting_pixel_color) {
                            whole_area = false;
                        }
                    }
                    else {
                        if (rgbs[loc] == temp_starting_pixel_color) {
                            same_count++;
                        }
                        perimeter_count++;
                    }

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

            for(x--, y++; y < slice_TOy; y++) {
                loc = y * image_width + x;
                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(PERIMETER_ACCURACY == 1) {
                        if (rgbs[loc] != temp_starting_pixel_color) {
                            whole_area = false;
                        }
                    }
                    else {
                        if (rgbs[loc] == temp_starting_pixel_color) {
                            same_count++;
                        }
                        perimeter_count++;
                    }

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

            for(y--, x--, loc = y * image_width + x; x >= slice_FROMx; x--, loc--) {
                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(PERIMETER_ACCURACY == 1) {
                        if (rgbs[loc] != temp_starting_pixel_color) {
                            whole_area = false;
                        }
                    }
                    else {
                        if (rgbs[loc] == temp_starting_pixel_color) {
                            same_count++;
                        }
                        perimeter_count++;
                    }

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

            for(x++, y--; y > slice_FROMy; y--) {
                loc = y * image_width + x;
                if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                    image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(PERIMETER_ACCURACY == 1) {
                        if (rgbs[loc] != temp_starting_pixel_color) {
                            whole_area = false;
                        }
                    }
                    else {
                        if (rgbs[loc] == temp_starting_pixel_color) {
                            same_count++;
                        }
                        perimeter_count++;
                    }

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

            y++;
            x++;

            if(PERIMETER_ACCURACY < 1) {
                whole_area = same_count >= PERIMETER_ACCURACY * perimeter_count;
            }

            if(!whole_area) {

                slice_FROMx++;
                slice_TOx--;

                slice_FROMy++;
                slice_TOy--;

                int xLength = slice_TOx - slice_FROMx;
                int yLength = slice_TOy - slice_FROMy;

                if(canSubDivide(xLength, yLength)) {
                    int halfY = slice_FROMy + (yLength >>> 1);
                    int halfX = slice_FROMx + (xLength >>> 1);
                    int nextIter = currentSquare.iteration + 1;
                    Square square1 = new Square(slice_FROMx, slice_FROMy, halfX, halfY, nextIter);
                    Square square2 = new Square(halfX, slice_FROMy, slice_TOx, halfY, nextIter);
                    Square square3 = new Square(slice_FROMx, halfY, halfX, slice_TOy, nextIter);
                    Square square4 = new Square(halfX, halfY, slice_TOx, slice_TOy, nextIter);
                    enqueueSquare(square1, square2, square3, square4);
                }
                else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy; y < slice_TOy; y++) {
                        for (x = slice_FROMx, loc = y * image_width + x; x < slice_TOx; x++, loc++) {
                            if(rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                                image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                                escaped[loc] = iteration_algorithm.escaped();
                                rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);
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
                    }

                    if(perform_work_stealing_and_wait) {
                        updateProgress();
                    }
                    else {
                        if (rendering_done / progress_one_percent >= 1) {
                            update(rendering_done);
                            rendering_done = 0;
                        }
                    }
                }
            }
            else {
                int temp1 = slice_TOx - 1;
                int temp2 = slice_TOy - 1;

                int chunk = temp1 - x;

                int currentIteration = currentSquare.iteration;
                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                int fill_count = 0;
                
                for(int k = y; k < temp2; k++) {
                    int temploc = k * image_width;
                    int loc3 = temploc + x;
                    int loc4 = temploc + temp1;

                    for (int index = loc3; index < loc4; index++) {
                        if (rgbs[index] >>> 24 != Constants.NORMAL_ALPHA) {
                            image_iterations[index] = temp_starting_value;
                            rgbs[index] = skippedColor;
                            escaped[index] = temp_starting_escaped;
                            task_completed++;
                            fill_count++;
                        }
                    }
                    if(perform_work_stealing_and_wait) {
                        rendering_done_per_task[taskId] += chunk;
                    }
                    else {
                        rendering_done += chunk;
                    }
                }
                task_pixel_grouping[currentIteration] += fill_count;

                if(perform_work_stealing_and_wait) {
                    updateProgress();
                }
                else {
                    if (rendering_done / progress_one_percent >= 1) {
                        update(rendering_done);
                        rendering_done = 0;
                    }
                }
            }

        } while(true);

        if(SKIPPED_PIXELS_ALG == 4) {
            renderSquares(image_width, image_height);
        }

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;
        
        postProcess(image_width, image_height, null, location);

    }

    @Override
    protected void renderAntialiased(int image_width, int image_height, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void renderFastJulia(int image_size, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected void renderFastJuliaAntialiased(int image_size, boolean polar) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected boolean canSubDivide(int xLength, int yLength) {
        return xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE;
    }

}
