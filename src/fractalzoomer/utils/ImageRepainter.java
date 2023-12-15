package fractalzoomer.utils;

import fractalzoomer.core.TaskDraw;
import fractalzoomer.main.MainWindow;

public class ImageRepainter implements Runnable {
    private MainWindow ptr;
    private TaskDraw[][] drawThreads;
    public static int REPAINT_SLEEP_TIME = 24;

    private volatile boolean stop;

    public ImageRepainter(MainWindow ptr,  TaskDraw[][] drawThreads) {
        super();
        this.ptr = ptr;
        this.drawThreads = drawThreads;
        stop = false;
    }

    public void init(boolean quickDrawAndRefinement) {

        ptr.setWholeImageDone(!quickDrawAndRefinement);
        if(!quickDrawAndRefinement) {
            boolean isJulia = drawThreads[0][0].isJulia();
            boolean isJuliaMap = drawThreads[0][0].isJuliaMap();
            boolean isDomainColoring = drawThreads[0][0].isDomainColoring();
            boolean isNonJulia = drawThreads[0][0].isNonJulia();

            if(isJulia || isNonJulia || isJuliaMap || isDomainColoring) {
                ptr.reloadTitle();
                TaskDraw.updateMode(ptr, false, isJulia, isJuliaMap, isDomainColoring);
            }
            ptr.getMainPanel().repaint();
        }
        stop = false;
    }

    public void stopIt() {
        stop = true;
    }

    @Override
    public void run() {

        while (!stop && TaskDraw.number_of_tasks.get() > 0) {

            ptr.repaint();

            try {
                Thread.sleep(REPAINT_SLEEP_TIME);
            } catch (Exception ex) {

            }

        }

    }
}
