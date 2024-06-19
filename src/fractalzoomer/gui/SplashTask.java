
package fractalzoomer.gui;

/**
 *
 * @author hrkalona2
 */
public class SplashTask implements Runnable {
    private SplashFrame sf;

    public SplashTask(SplashFrame sf) {

        this.sf = sf;

    }

    @Override
    public void run() {

        sf.setVisible(true);

        try {
            Thread.sleep(2500);
        }
        catch(InterruptedException ex) {

        }

        sf.dispose();

    }



}