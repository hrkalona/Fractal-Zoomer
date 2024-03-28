package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MetricsDialog extends JDialog {
    private ChartDataGenerator gen;
    private static MetricsDialog instance;

    public static MetricsDialog getInstance(JFrame ptr) {
        if(instance == null) {
            instance = new MetricsDialog(ptr);
        }
        return instance;
    }

    private static void closeInstance() {
        if(instance != null) {
            instance.dispose();
        }
        instance = null;
    }

    private MetricsDialog(JFrame ptra2) {

        int metrics_width = 700;
        int metrics_height = 700;
        setTitle("Metrics");
        setSize(metrics_width, metrics_height);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (metrics_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (metrics_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                closeInstance();
            }
        });

        int maxAge = 180000;
        MemoryUsageChartPanel p = new MemoryUsageChartPanel(maxAge);
        ProcessCPUUsageChartPanel p2 = new ProcessCPUUsageChartPanel(maxAge);
        SystemCPUUsageChartPanel p4 = new SystemCPUUsageChartPanel(maxAge);
        //ActiveThreadsChartPanel p5 = new ActiveThreadsChartPanel(maxAge);

        gen = new ChartDataGenerator(p2, p, p4, 500);
        gen.start();

        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(3, 1));
        p3.add(p);
        //p3.add(p5);
        p3.add(p2);
        p3.add(p4);

        setContentPane(p3);

    }

    @Override
    public void dispose() {
        if(gen != null) {
            gen.stop();
            gen = null;
        }
        super.dispose();
    }
}
