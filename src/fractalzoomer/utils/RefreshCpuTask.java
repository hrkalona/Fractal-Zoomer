
package fractalzoomer.utils;

import fractalzoomer.gui.CpuLabel;

import java.util.TimerTask;

/**
 *
 * @author hrkalona2
 */
public class RefreshCpuTask extends TimerTask {
    private CpuLabel cpuLabel;

    public RefreshCpuTask(CpuLabel cpuLabel) {
        super();
        this.cpuLabel = cpuLabel;
    }

    @Override
    public void run() {
        cpuLabel.refresh();
    }
    
}
