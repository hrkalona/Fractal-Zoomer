package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
import fractalzoomer.main.MainWindow;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

public class OverallPixelDistributionChartDialog extends JDialog {

    private static PieDataset createDataset(String report) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        String[] labels = new String[] {
                TaskRender.PIXELS_CALCULATED_COUNT_LABEL,
                TaskRender.PIXELS_GUESSED_COUNT_LABEL,
                TaskRender.IMAGE_SIZE_LABEL,
        };
        long[] count = new long[labels.length];

        //Incase there are two passes, accumulate only the second pass counts
        boolean hasTwoPasses = report.contains(TaskRender.FIRST_PASS_STRING_LABEL);
        boolean foundSecondPass = false;

        String[] lines = report.split("<br>");
        for(String line : lines) {
            int index = -1;
            int i = 0;

            if(!hasTwoPasses || foundSecondPass) {
                for (i = 0; i < labels.length; i++) {
                    index = line.indexOf(labels[i]);
                    if (index != -1) {
                        break;
                    }
                }
            }
            else if(hasTwoPasses) {
                foundSecondPass = line.contains(TaskRender.SECOND_PASS_STRING_LABEL);
            }

            if(index != -1) {

                line = line.substring(index + labels[i].length(), line.length());
                line = line.replaceAll("</b>", "");
                String[] tokens = line.split(" ");

                if(labels[i].equals(TaskRender.IMAGE_SIZE_LABEL)) {
                    String[] tokens2 = tokens[0].split("x");
                    try {
                        long val = Long.parseLong(tokens2[0]) * Long.parseLong(tokens2[1]);
                        if(tokens2.length == 3) {
                            val *= Long.parseLong(tokens2[2]);
                        }
                        count[i] += val;
                    } catch (Exception ex) {
                    }
                }
                else {
                    try {
                        count[i] += Long.parseLong(tokens[0]);
                    } catch (Exception ex) {
                    }
                }
            }
        }

        long total = 0;
        for(int i = 0; i < count.length; i++) {
            if(!labels[i].equals(TaskRender.IMAGE_SIZE_LABEL)) {
                total += count[i];
            }
        }

        for(int i = 0; i < labels.length; i++) {
            if(count[i] > 0) {
                String final_label = labels[i];

                if(final_label.equals(TaskRender.IMAGE_SIZE_LABEL)) {
                    if(total == 0) {
                        dataset.setValue("Pixels Completed", count[i] - total);
                    }
                    else {
                        dataset.setValue("Pixels Reused", count[i] - total);
                    }
                }
                else {
                    final_label = final_label.replaceAll("<li>", "");
                    final_label = final_label.replaceAll("<b>", "");
                    final_label = final_label.replaceAll(":", "");
                    dataset.setValue(final_label, count[i]);
                }

            }
        }


        return dataset;
    }

    private static JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart( // createRingChart
                "Pixel Distribution Statistics",  // chart title
                dataset,            // data
                true,              // no legend
                true,               // tooltips
                false               // no URL generation
        );

        PiePlot plot = (PiePlot) chart.getPlot();

        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);


        plot.setLabelLinkStroke(new BasicStroke(2.0f));

        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setCircular(true);
        plot.setIgnoreZeroValues(true);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {2} ({1})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));

        return chart;
    }

    public OverallPixelDistributionChartDialog(JFrame ptra2, String report) {
        super();

        int chart_width = 700;
        int chart_height = 700;
        setSize(chart_width, chart_height);
        setTitle("Pixel Distribution Statistics");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (chart_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (chart_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                dispose();

            }
        });

        JFreeChart chart = createChart(createDataset(report));
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart, false);
        panel.setMouseWheelEnabled(true);
        //chartPanel.setPreferredSize(new Dimension(chart_width, chart_height));
        setContentPane(panel);
        //pack();
    }

}
