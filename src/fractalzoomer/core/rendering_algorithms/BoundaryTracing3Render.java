
package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.TaskRender;
import fractalzoomer.core.location.Location;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.Square;
import fractalzoomer.utils.StopExecutionException;
import fractalzoomer.utils.StopSuccessiveRefinementException;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona2
 */
@Deprecated
public class BoundaryTracing3Render extends TaskRender {


    private static final int RIGHT  = 0;
    private static final int LEFT  = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    public BoundaryTracing3Render(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    private void Trace(int x, int y, int pix, Location location, int image_width, int culcColor, int FROMx, int TOx, int FROMy, int TOy)
    {
        int sx,sy,nextColor,dir=RIGHT;
        boolean fill = false;

        double start_val;
        boolean startEscaped;
        int startColor;

        start_val = iteration_algorithm.calculate(location.getComplex(x, y));
        startEscaped = iteration_algorithm.escaped();
        startColor = getFinalColor(start_val, startEscaped);
        task_calculated++;
        task_completed++;

        image_iterations[pix] = start_val;
        escaped[pix]  = startEscaped;
        rgbs[pix] = startColor;
        rendering_done++;

        int skippedColor = getColorForSkippedPixels(startColor, 0);

        while (y - 1 >= FROMy) {   // looking for boundary
            int startPix_image_size = pix - image_width;
            if(rgbs[startPix_image_size] != startColor) {
                break;
            }
            pix = startPix_image_size;
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
                        tempPix = pix - image_width;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                        tempPix = pix + image_width;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                        tempPix = pix + image_width;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                        tempPix = pix - image_width;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                        long fill_count = 0;
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
                                rendering_done++;
                                task_completed++;
                                fill_count++;
                            } else if (floodColor != startColor) {
                                break;
                            }

                        }

                        task_pixel_grouping[0] += fill_count;
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
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                        tempPix = pix - image_width;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                    pix += image_width;
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
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                        tempPix = pix + image_width;
                        if((nextColor=rgbs[tempPix]) >>> 24 == culcColor)
                        {
                            image_iterations[tempPix] = tempVal = iteration_algorithm.calculate(location.getComplex(x, temp_y));
                            escaped[tempPix] = tempEscaped = iteration_algorithm.escaped();
                            rgbs[tempPix] = nextColor = getFinalColor(tempVal, tempEscaped);
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                            task_calculated++;
                            rendering_done++;
                            task_completed++;
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
                    pix -= image_width;
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
    protected void render(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException, StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int pixel_percent = (image_width * image_height) / 100;

        int totalPixels = (TOx - FROMx) * (TOy - FROMy);

        final int culcColor = Constants.EMPTY_ALPHA;

        int last_rendering_done = 0, iteration = 0;

        task_completed = 0;

        long time = System.currentTimeMillis();

        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            stop:
            for (int y = FROMy; y < TOy; y++) {
                for (int x = FROMx, loc = y * image_width + x; x < TOx; x++, loc++) {
                    if (rgbs[loc] >>> 24 != culcColor) {
                        continue;
                    }
                    Trace(x, y, loc, location, image_width, culcColor, FROMx, TOx, FROMy, TOy);

                    int dif = rendering_done - last_rendering_done;
                    if (dif / pixel_percent >= 1) {
                        update(dif);
                        last_rendering_done = rendering_done;
                    }

                    if (!SPLIT_INTO_RECTANGLE_AREAS && rendering_done == totalPixels) {
                        break stop;
                    }
                }
            }
            iteration++;
        } while (true);

        int dif = rendering_done - last_rendering_done;
        if (dif > 0) {
            update(dif);
        }


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
