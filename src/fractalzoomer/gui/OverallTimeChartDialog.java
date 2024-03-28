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

public class OverallTimeChartDialog extends JDialog {

    private static PieDataset createDataset(String report) {
        DefaultPieDataset dataset = new DefaultPieDataset();

         //Should be aligned with the labels in TaskRender if we add new
        String[] labels = new String[] {
                TaskRender.REFERENCE_CALCULATION_ELAPSED_TIME_STRING_LABEL,
                TaskRender.JULIA_EXTRA_REFERENCE_CALCULATION_ELAPSED_TIME_STRING_LABEL,
                TaskRender.SA_CALCULATION_ELAPSED_TIME_LABEL,
                TaskRender.NANOMB1_CALCULATION_ELAPSED_TIME_LABEL,
                TaskRender.BLA_CALCULATION_ELAPSED_TIME_LABEL,
                TaskRender.PIXEL_CALCULATION_ELAPSED_TIME_STRING_LABEL,
                TaskRender.POST_PROCESSING_ELAPSED_TIME_STRING_LABEL,
                TaskRender.D3_RENDER_TIME_STRING_LABEL,
                TaskRender.IMAGE_FILTERS_TIME_STRING_LABEL
        };
        long[] times = new long[labels.length];

        String[] lines = report.split("<br>");
        for(String line : lines) {
            int index = -1;
            int i;
            for(i = 0; i < labels.length; i++) {
                index = line.indexOf(labels[i]);
                if(index != -1) {
                    break;
                }
            }

            if(index != -1) {
                line = line.substring(index + labels[i].length(), line.length());
                line = line.replaceAll("</b>", "");
                String[] tokens = line.split(" ");
                try {
                    times[i] += Long.parseLong(tokens[0]);
                }
                catch (Exception ex) {}
            }
        }

        for(int i = 0; i < labels.length; i++) {
            if(times[i] > 0) {
                String final_label = labels[i];
                final_label = final_label.replaceAll("<li>", "");
                final_label = final_label.replaceAll("<b>", "");
                final_label = final_label.replaceAll(":", "");
                dataset.setValue(final_label, times[i]);
            }
        }
        return dataset;
    }

    private static JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Overall Elapsed Time Statistics",  // chart title
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
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {2} ({1} ms)", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
        plot.setIgnoreZeroValues(true);

        return chart;
    }

    public OverallTimeChartDialog(JFrame ptra2, String report) {
        super();

        int chart_width = 700;
        int chart_height = 700;
        setSize(chart_width, chart_height);
        setTitle("Overall Elapsed Time Statistics");
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
