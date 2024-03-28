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
package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.TaskRender;
import fractalzoomer.core.location.Location;
import fractalzoomer.main.Constants;
import fractalzoomer.utils.ExpandingQueueSquare;
import fractalzoomer.utils.Square;
import fractalzoomer.utils.StopSuccessiveRefinementException;

import static fractalzoomer.core.rendering_algorithms.MarianiSilverRender.RENDER_USING_DFS;

/**
 *
 * @author hrkalona2
 */
@Deprecated
public class MarianiSilver2Render extends TaskRender {
    private static final int MAX_TILE_SIZE = 2;
    private static final int INIT_QUEUE_SIZE = 200;
    public static final int INIT_QUEUE_SIZE2 = 6000;
    
    @Override
    protected void render(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        ExpandingQueueSquare squares = new ExpandingQueueSquare(RENDER_USING_DFS ? INIT_QUEUE_SIZE : INIT_QUEUE_SIZE2);

        Square square = new Square(FROMx, FROMy, TOx, TOy, 0);

        squares.enqueue(square);

        int pixel_percent = (image_width * image_height) / 100;

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

        int notCalculated = Constants.EMPTY_COLOR;
        
        int skippedColor;

        long time = System.currentTimeMillis();

        do {

            Square currentSquare = null;

            if(squares.isEmpty()) {
                break;
            }

            if(RENDER_USING_DFS) {
                currentSquare = squares.last();
            }
            else {
                currentSquare = squares.dequeue();
            }

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_width + x;

            if(rgbs[loc] == notCalculated) {
                temp_starting_value = image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                temp_starting_escaped = escaped[loc] = iteration_algorithm.escaped();
                temp_starting_pixel_color = rgbs[loc] = getFinalColor(temp_starting_value, temp_starting_escaped);
                rendering_done++;
                task_calculated++;
                task_completed++;
            }
            else {
                temp_starting_value = image_iterations[loc];
                temp_starting_pixel_color = rgbs[loc];
                temp_starting_escaped = escaped[loc];
            }

            for(x++, loc = y * image_width + x; x < slice_TOx; x++, loc++) {

                if(rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                    }

                    rendering_done++;
                    task_calculated++;
                    task_completed++;
                }
            }

            for(x--, y++; y < slice_TOy; y++) {
                loc = y * image_width + x;
                if(rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                    }

                    rendering_done++;
                    task_calculated++;
                    task_completed++;
                }
            }

            for(y--, x--, loc = y * image_width + x; x >= slice_FROMx; x--, loc--) {
                if(rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                    }

                    rendering_done++;
                    task_calculated++;
                    task_completed++;
                }
            }

            for(x++, y--; y > slice_FROMy; y--) {
                loc = y * image_width + x;
                if(rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x, y));
                    escaped[loc] = iteration_algorithm.escaped();
                    rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                    if(rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                    }

                    rendering_done++;
                    task_calculated++;
                    task_completed++;
                }
            }

            y++;
            x++;

            if(!whole_area) {

                slice_FROMx++;
                slice_TOx--;

                slice_FROMy++;
                slice_TOy--;

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if(xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {

                    Square square1 = new Square(slice_FROMx, slice_FROMy, slice_FROMx + xLength / 2, slice_FROMy + yLength / 2, currentSquare.iteration + 1);

                    squares.enqueue(square1);

                    Square square2 = new Square(slice_FROMx + xLength / 2, slice_FROMy, slice_TOx, slice_FROMy + yLength / 2, currentSquare.iteration + 1);

                    squares.enqueue(square2);

                    Square square3 = new Square(slice_FROMx, slice_FROMy + yLength / 2, slice_FROMx + xLength / 2, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square3);

                    Square square4 = new Square(slice_FROMx + xLength / 2, slice_FROMy + yLength / 2, slice_TOx, slice_TOy, currentSquare.iteration + 1);

                    squares.enqueue(square4);

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
                        if (rgbs[index] == notCalculated) {
                            image_iterations[index] = temp_starting_value;
                            rgbs[index] = skippedColor;
                            escaped[index] = temp_starting_escaped;
                            task_completed++;
                            fill_count++;
                        }
                    }
                    rendering_done += chunk;
                }
                task_pixel_grouping[currentIteration] += fill_count;
                
                if(rendering_done / pixel_percent >= 1) {
                    update(rendering_done);
                    rendering_done = 0;
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

}
