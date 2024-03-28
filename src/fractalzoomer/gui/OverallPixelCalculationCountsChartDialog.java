package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
import fractalzoomer.main.MainWindow;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OverallPixelCalculationCountsChartDialog extends JDialog {

    private static CategoryDataset createDataset(String report) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] labels = new String[] {
                TaskRender.PIXELS_CALCULATED_COUNT_LABEL,
                TaskRender.PIXELS_GUESSED_COUNT_LABEL,
                TaskRender.PIXELS_COMPLETED_COUNT_LABEL,
                TaskRender.PIXELS_POST_PROCESSED_COUNT_LABEL,
                TaskRender.EXTRA_PIXELS_CALCULATED_COUNT_LABEL,
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
            if(labels[i].equals(TaskRender.PIXELS_COMPLETED_COUNT_LABEL)) {
                total += count[i];
            }
        }

        for(int i = 0; i < labels.length; i++) {
            if(count[i] > 0) {
                String final_label = labels[i];

                if(final_label.equals(TaskRender.IMAGE_SIZE_LABEL)) {
                    if(count[i] - total > 0) {
                        dataset.addValue(count[i] - total, "Pixels Reused", "Overall");
                    }
                    dataset.addValue(count[i], "Pixels Total", "Overall");
                }
                else {
                    final_label = final_label.replaceAll("<li>", "");
                    final_label = final_label.replaceAll("<b>", "");
                    final_label = final_label.replaceAll(":", "");
                    dataset.addValue(count[i], final_label, "Overall");
                }

            }
        }


        return dataset;
    }

    private static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Pixel Count Statistics", null /* x-axis label*/,
                "Pixel Count" /* y-axis label */, dataset);
        chart.addSubtitle(new TextTitle("Overall"));
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);

        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());

        for(int i = 0; i < dataset.getRowCount(); i++) {
            renderer.setSeriesItemLabelsVisible(i, Boolean.TRUE);
        }



        //To make it flat
        //renderer.setBarPainter(new StandardBarPainter());
        return chart;
    }

    public OverallPixelCalculationCountsChartDialog(JFrame ptra2, String report) {
        super();

        int chart_width = 800;
        int chart_height = 500;
        setSize(chart_width, chart_height);
        setTitle("Pixel Count Statistics");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (chart_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (chart_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                dispose();

            }
        });

        CategoryDataset dataset = createDataset(report);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        //chartPanel.setPreferredSize(new Dimension(chart_width, chart_height));
        setContentPane(chartPanel);
        //pack();
    }

}
