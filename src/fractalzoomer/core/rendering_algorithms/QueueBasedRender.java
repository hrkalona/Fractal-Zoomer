package fractalzoomer.core.rendering_algorithms;

import fractalzoomer.core.TaskRender;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.MinimalRendererWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.lists.ArrayListInt;
import fractalzoomer.utils.Square;
import fractalzoomer.utils.queues.ExpandingQueueSquare;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public abstract class QueueBasedRender extends TaskRender {
    public static final int INIT_QUEUE_SIZE = 200;
    public static final int INIT_QUEUE_SIZE2 = 6000;

    public static boolean RENDER_USING_DFS = true;
    public static boolean INITIAL_WORK_STEALING_ENABLED = true;
    public static boolean WORK_STEALING_ENABLED = true;
    public static int WORK_STEAL_ALGORITHM = 1;
    public static boolean WAIT_AND_STEAL = true;
    public static double PERIMETER_ACCURACY = 1;
    private static int notify4_count;
    private static int notify2_count;
    protected static int FIRST_INDEXES_CHUNK = 50;
    protected static ArrayListInt firstIndexes;
    protected static ExpandingQueueSquare[] allQueuesSquare;

    protected static volatile int threadsWaiting;
    protected static volatile boolean keepWaiting;
    private boolean perform_work_stealing;

    protected boolean perform_work_stealing_and_wait;
    protected boolean perform_initial_work_stealing;
    private ExpandingQueueSquare localQueueSquare;

    protected ArrayListInt firstIndexesLocal;
    protected long totalPauseTimeNanos;

    private static int numTasksm1;

    public static void initStatic(int num_tasks) {
        allQueuesSquare = new ExpandingQueueSquare[num_tasks];
        firstIndexes = new ArrayListInt();
        threadsWaiting = 0;
        keepWaiting = true;
        numTasksm1 = num_tasks - 1;
        notify4_count = Math.min(4, numTasksm1);
        notify2_count = Math.min(2, numTasksm1);
    }

    public QueueBasedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public QueueBasedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public QueueBasedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, d3s, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public QueueBasedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    public QueueBasedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, image, fs, periodicity_checking, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps);
    }

    public QueueBasedRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fs, color_cycling_location, color_cycling_location2, exterior_de, exterior_de_factor, height_ratio,  polar_projection, circle_period,   inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring,    color_blending,   post_processing_order,  pbs,  gradient_offset,  contourFactor, gps, js, pps, xJuliaCenter, yJuliaCenter);
    }

    protected void createQueueSquare() {
        localQueueSquare = new ExpandingQueueSquare(RENDER_USING_DFS ? INIT_QUEUE_SIZE : INIT_QUEUE_SIZE2);
        allQueuesSquare[taskId] = localQueueSquare;
        perform_work_stealing = WORK_STEALING_ENABLED && allQueuesSquare.length > 1;
        perform_initial_work_stealing = perform_work_stealing && INITIAL_WORK_STEALING_ENABLED;
        firstIndexesLocal = new ArrayListInt();
        totalPauseTimeNanos = 0;
        perform_work_stealing_and_wait = perform_work_stealing && WAIT_AND_STEAL;
    }

    private Square getNextSquareFrom(ExpandingQueueSquare queue) {
        if (RENDER_USING_DFS) {
            return queue.last();
        }
        return queue.dequeue();
    }

    public Square getNextSquare() {
        if(perform_work_stealing) {
            synchronized (WORK_STEAL_ALGORITHM == 2 ? allQueuesSquare : localQueueSquare) {
                if (!localQueueSquare.isEmpty()) {
                    return getNextSquareFrom(localQueueSquare);
                }
            }

            do {
                if (WORK_STEAL_ALGORITHM == 0) { //Steal from first and onwards
                    for (int i = 0; i < allQueuesSquare.length; i++) {
                        if (i == taskId) {
                            continue;
                        }
                        ExpandingQueueSquare queue = allQueuesSquare[i];
                        synchronized (queue) {
                            if (!queue.isEmpty()) {
                                return getNextSquareFrom(queue);
                            }
                        }
                    }
                } else if (WORK_STEAL_ALGORITHM == 1) { //Steal from next and onwards
                    int length = allQueuesSquare.length - 1;
                    for (int i = 0; i < length; i++) {
                        int index = (i + taskId + 1) % allQueuesSquare.length;
                        ExpandingQueueSquare queue = allQueuesSquare[index];
                        synchronized (queue) {
                            if (!queue.isEmpty()) {
                                return getNextSquareFrom(queue);
                            }
                        }
                    }
                } else {
                    synchronized (allQueuesSquare) { //Steal from the one with the max amount of items
                        int max_size = 0;
                        int max_index = -1;
                        for (int i = 0; i < allQueuesSquare.length; i++) {
                            if (i == taskId) {
                                continue;
                            }
                            int size = allQueuesSquare[i].size();
                            if (size > max_size) {
                                max_size = size;
                                max_index = i;
                            }
                        }

                        if (max_index != -1) {
                            return getNextSquareFrom(allQueuesSquare[max_index]);
                        }
                    }
                }
            } while (WAIT_AND_STEAL && keepWaiting && pause());

            stop();

            return null;
        }

        if (localQueueSquare.isEmpty()) {
            return null;
        }
        if (RENDER_USING_DFS) {
            return localQueueSquare.last();
        }
        return localQueueSquare.dequeue();
    }

    private void stop() {

        boolean shouldNotify = false;
        synchronized (allQueuesSquare) {
            if(WAIT_AND_STEAL && keepWaiting) {
                keepWaiting = false;
                if(threadsWaiting > 0) {
                    shouldNotify = true;
                }
            }
        }

        if(shouldNotify) {
            while (true) {
                synchronized (allQueuesSquare) {
                    if(threadsWaiting == 0) {
                        break;
                    }
                    allQueuesSquare.notifyAll();
                }
                try {
                    TimeUnit.MICROSECONDS.sleep(10);
                }
                catch (Exception ex) {

                }
            }
        }
    }

    private boolean pause() {

        long time = System.nanoTime();

        synchronized (allQueuesSquare) {
            if(threadsWaiting == numTasksm1) {
                return false;
            }
            threadsWaiting++;
            try {
                allQueuesSquare.wait();
            }
            catch (Exception ex) {

            }
            threadsWaiting--;
        }

        totalPauseTimeNanos += System.nanoTime() - time;

        return true;
    }

    protected void enqueueSquare(Square square) {
        if(perform_work_stealing) {
            synchronized (WORK_STEAL_ALGORITHM == 2 ? allQueuesSquare : localQueueSquare) {
                localQueueSquare.enqueue(square);
            }
        }
        else {
            localQueueSquare.enqueue(square);
        }
    }

    protected void enqueueSquare(Square square1, Square square2, Square square3, Square square4) {
        if(perform_work_stealing) {
            if(WORK_STEAL_ALGORITHM == 2) {
                synchronized (allQueuesSquare) {
                    localQueueSquare.enqueue(square1);
                    localQueueSquare.enqueue(square2);
                    localQueueSquare.enqueue(square3);
                    localQueueSquare.enqueue(square4);

                    int notify_count = Math.min(threadsWaiting, notify4_count);
                    for(int i = 0; i < notify_count; i++) {
                        allQueuesSquare.notify();
                    }
//                    if(threadsWaiting > 0) {
//                        allQueuesSquare.notifyAll();
//                    }
                }
            }
            else {
                synchronized (localQueueSquare) {
                    localQueueSquare.enqueue(square1);
                    localQueueSquare.enqueue(square2);
                    localQueueSquare.enqueue(square3);
                    localQueueSquare.enqueue(square4);
                }
                synchronized (allQueuesSquare) {
                    int notify_count = Math.min(threadsWaiting, notify4_count);
                    for(int i = 0; i < notify_count; i++) {
                        allQueuesSquare.notify();
                    }
//                    if(threadsWaiting > 0) {
//                        allQueuesSquare.notifyAll();
//                    }
                }
            }
        }
        else {
            localQueueSquare.enqueue(square1);
            localQueueSquare.enqueue(square2);
            localQueueSquare.enqueue(square3);
            localQueueSquare.enqueue(square4);
        }
    }

    protected void enqueueSquare(Square square1, Square square2) {
        if(perform_work_stealing) {
            if(WORK_STEAL_ALGORITHM == 2) {
                synchronized (allQueuesSquare) {
                    localQueueSquare.enqueue(square1);
                    localQueueSquare.enqueue(square2);

                    int notify_count = Math.min(threadsWaiting, notify2_count);
                    for(int i = 0; i < notify_count; i++) {
                        allQueuesSquare.notify();
                    }
//                    if(threadsWaiting > 0) {
//                        allQueuesSquare.notifyAll();
//                    }
                }
            }
            else {
                synchronized (localQueueSquare) {
                    localQueueSquare.enqueue(square1);
                    localQueueSquare.enqueue(square2);
                }
                synchronized (allQueuesSquare) {
                    int notify_count = Math.min(threadsWaiting, notify2_count);
                    for(int i = 0; i < notify_count; i++) {
                        allQueuesSquare.notify();
                    }
//                    if(threadsWaiting > 0) {
//                        allQueuesSquare.notifyAll();
//                    }
                }
            }
        }
        else {
            localQueueSquare.enqueue(square1);
            localQueueSquare.enqueue(square2);
        }
    }
}
