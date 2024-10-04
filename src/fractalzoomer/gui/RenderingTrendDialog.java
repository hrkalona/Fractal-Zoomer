package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RenderingTrendDialog extends JDialog {
    private static RenderingTrendDialog instance;
    private RenderingTimeChartPanel p;
    private RenderingIterationsChartPanel p2;

    public static RenderingTrendDialog getInstance(JFrame ptr) {
        if(instance == null) {
            instance = new RenderingTrendDialog(ptr);
        }
        return instance;
    }

    public static void closeInstance() {
        if(instance != null) {
            instance.dispose();
        }
        instance = null;
    }

    private RenderingTrendDialog(JFrame ptra2) {

        int metrics_width = 700;
        int metrics_height = 700;
        setTitle("Rendering Trend");
        setSize(metrics_width, metrics_height);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (metrics_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (metrics_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                closeInstance();
            }
        });

        int maxCount = 75;
        p = new RenderingTimeChartPanel(maxCount);
        p2 = new RenderingIterationsChartPanel(maxCount);

        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(2, 1));
        p3.add(p);
        p3.add(p2);

        setContentPane(p3);

    }

    public void addSampleData(long render, String report, long imageWriteTime) {
        render++;

        String[] lines = report.split("<br>");
        {
            String[] labels = RenderingTimeChartPanel.labels;

            int labelsLength = labels.length - 1;

            long[] times = new long[labelsLength];
            String[] labels2 = RenderingIterationsChartPanel.labels;
            double[] iterations = new double[labels2.length];

            for (String line : lines) {
                int index = -1;
                int i;
                for (i = 0; i < labelsLength; i++) {
                    index = line.indexOf(labels[i]);
                    if (index != -1) {
                        break;
                    }
                }

                if (index != -1) {
                    line = line.substring(index + labels[i].length(), line.length());
                    line = line.replaceAll("</b>", "");
                    String[] tokens = line.split(" ");
                    try {
                        times[i] += Long.parseLong(tokens[0]);
                    } catch (Exception ex) {
                    }
                }

                index = -1;
                for (i = 0; i < labels2.length; i++) {
                    index = line.indexOf(labels2[i]);
                    if (index != -1) {
                        break;
                    }
                }

                if (index != -1) {
                    line = line.substring(index + labels2[i].length(), line.length());
                    line = line.replaceAll("</b>", "");
                    String[] tokens = line.split(" ");
                    try {
                        iterations[i] += Double.parseDouble(tokens[0]);
                    } catch (Exception ex) {
                    }
                }
            }

            for (int i = 0; i < labelsLength; i++) {
                p.addTimeData(i, render, times[i]);
            }

            p.addTimeData(labelsLength, render, imageWriteTime);

            for (int i = 0; i < iterations.length; i++) {
                p2.addIterationData(i, render, iterations[i]);
            }
        }
    }
}
