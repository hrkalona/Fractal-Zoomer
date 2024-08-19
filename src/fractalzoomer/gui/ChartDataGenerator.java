package fractalzoomer.gui;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ChartDataGenerator extends Timer implements ActionListener {
    final ProcessCPUUsageChartPanel cpu_ptr;
    final SystemCPUUsageChartPanel syscpu_ptr;
    final MemoryUsageChartPanel mem_ptr;
    //final ActiveThreadsChartPanel threads_ptr;

    public ChartDataGenerator(ProcessCPUUsageChartPanel ptr, MemoryUsageChartPanel ptr2, SystemCPUUsageChartPanel ptr3, int interval) {
        super(interval, null);
        cpu_ptr = ptr;
        mem_ptr = ptr2;
        syscpu_ptr = ptr3;
        //threads_ptr = ptr4;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        RegularTimePeriod period = new Millisecond();
        try {
            mem_ptr.addAllocatedObservation(period, MemoryLabel.getAllocatedMemory());
        }
        catch (Exception ex) {}
        //MemoryUsageChartPanel.setMax(mem_ptr, (double)MemoryLabel.getMaxMemory());
        try {
            mem_ptr.addUsedObservation(period, MemoryLabel.getUsedMemory());
        }
        catch (Exception ex) {}
        try {
            cpu_ptr.addCPUUsageObservation(period, CpuLabel.getCpuLoad() * 100);
        }
        catch (Exception ex) {}

        try {

            syscpu_ptr.addCPUUsageObservation(period, CpuLabel.getSystemCpuLoad() * 100);
        }
        catch (Exception ex) {}

//        try {
//
//            threads_ptr.addActiveThreadsObservation(period, TaskRender.getActiveThreadCount());
//        }
//        catch (Exception ex) {}
    }
}
