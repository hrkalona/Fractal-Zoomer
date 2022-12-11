package fractalzoomer.utils;

import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.MainWindow;

public class ImageRepainter extends Thread {
    private MainWindow ptr;
    private ThreadDraw [][] drawThreads;
    private static final int sleepTime = 300;

    public ImageRepainter(MainWindow ptr,  ThreadDraw [][] drawThreads) {
        super();
        this.ptr = ptr;
        this.drawThreads = drawThreads;
    }

    public void init() {
        ptr.setWholeImageDone(true);
        ptr.reloadTitle();
        ptr.getMainPanel().repaint();
    }

    @Override
    public void run() {

        while (!threadsAvailable()) {
            ptr.repaint();
            try {
                sleep(sleepTime);
            }
            catch (Exception ex) {

            }
        }

    }

    public boolean threadsAvailable() {

        synchronized(ptr) {
            try {
                for (int i = 0; i < drawThreads.length; i++) {
                    for (int j = 0; j < drawThreads[i].length; j++) {
                        if (drawThreads[i][j].isAlive() || !drawThreads[i][j].started()) {
                            return false;
                        }
                    }
                }
            } catch (Exception ex) {
                return false;
            }

            return true;
        }

    }
}
