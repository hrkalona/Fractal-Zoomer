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

public class OverallGuessedPixelGroupingChartDialog extends JDialog {

    private static PieDataset createDataset(String report) {
        DefaultPieDataset dataset = new DefaultPieDataset();


        long[] counts = new long[TaskRender.MAX_GROUPING];
        String[] lines = report.split("<br>");
        for(String line : lines) {
            int index = -1;

            index = line.indexOf(TaskRender.PIXEL_GROUPING_STRING_LABEL);

            if(index != -1) {

                line = line.substring(index + TaskRender.PIXEL_GROUPING_STRING_LABEL.length(), line.length());
                line = line.trim();
                line = line.replaceAll("<b>", "");
                line = line.replaceAll("</b>", "");
                line = line.replaceAll(":", "");
                String[] tokens = line.split(" ");

                try {
                    int id = Integer.parseInt(tokens[0]) - 1;
                    long count = Long.parseLong(tokens[1]);
                    counts[id] = count;
                }
                catch (Exception ex) {}
            }
        }

        for(int i = 0; i < counts.length; i++) {
            if(counts[i] > 0) {
                dataset.setValue("Group " + (i + 1), counts[i]);
            }
        }

        return dataset;
    }

    private static JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Guessed Pixels Group Statistics",  // chart title
                dataset,            // data
                true,              // no legend
                true,               // tooltips
                false               // no URL generation
        );

        PiePlot plot = (PiePlot) chart.getPlot();

        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);

        for(int i = 0; i < dataset.getItemCount(); i++) {
           String key = dataset.getKey(i).toString();
           String[] tokens = key.split(" ");
           try {
               int group = Integer.parseInt(tokens[1]);
               plot.setSectionPaint(key, new Color(TaskRender.getGroupPixelColor(group - 1)));
           }
           catch (Exception ex) {}
        }

        plot.setLabelLinkStroke(new BasicStroke(2.0f));

        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setCircular(true);
        plot.setIgnoreZeroValues(true);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {2} ({1})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));

        return chart;
    }

    public OverallGuessedPixelGroupingChartDialog(JFrame ptra2, String report) {
        super();

        int chart_width = 700;
        int chart_height = 700;
        setSize(chart_width, chart_height);
        setTitle("Guessed Pixels Group Statistics");
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
