
package fractalzoomer.utils;

import fractalzoomer.main.MainWindow;

import java.util.TimerTask;

/**
 *
 * @author kaloch
 */
public class CompleteImageTask extends TimerTask {
    private MainWindow ptr;
    private boolean d3;

    private boolean preview;
    private boolean zoomToCursor;
    
    public CompleteImageTask(MainWindow ptr, boolean d3, boolean preview, boolean zoomToCursor) {
        super();
        this.ptr = ptr;
        this.d3 = d3;
        this.preview = preview;
        this.zoomToCursor = zoomToCursor;
    }

    @Override
    public void run() {
        ptr.taskCompleteImage(d3, preview, zoomToCursor);
    }
    
}
