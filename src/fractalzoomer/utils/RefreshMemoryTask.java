
package fractalzoomer.utils;

import fractalzoomer.gui.MemoryLabel;

import java.util.TimerTask;

/**
 *
 * @author hrkalona2
 */
public class RefreshMemoryTask extends TimerTask {
    private MemoryLabel memory_label;
    
    public RefreshMemoryTask(MemoryLabel memory_label) {
        super();
        this.memory_label = memory_label;
    }

    @Override
    public void run() {
        memory_label.refresh();
    }
    
}
