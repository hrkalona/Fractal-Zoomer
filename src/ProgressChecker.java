


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class ProgressChecker extends Thread {
  private MainWindow ptr;

    public ProgressChecker(MainWindow ptr) {

        this.ptr = ptr;
              
    }

    public synchronized void update(int new_percent) {

        //try {
            ptr.getProgressBar().setValue(ptr.getProgressBar().getValue() + new_percent);
        //}
        /*catch(Exception ex) {
            update(new_percent);
        }*/
              
    }

}
