package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.utils.TaskStatistic;
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
import java.util.ArrayList;
import java.util.HashMap;

public class TaskCalculatedPixelChartDialog extends JDialog {
    private HashMap<Integer, Color> colors = new HashMap<>();

    private PieDataset createDataset(ArrayList<TaskStatistic> taskStatistics) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for(TaskStatistic stat : taskStatistics) {
            if(stat.getPixelsCalculated() >= 0) {
                dataset.setValue("Task " + stat.getTaskId(), stat.getPixelsCalculated());
                colors.put(stat.getTaskId(), stat.getThreadColor());
            }
        }
        return dataset;

    }

    private JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Calculated Pixels Statistics",  // chart title
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
               int taskId = Integer.parseInt(tokens[1]);
               plot.setSectionPaint(key, colors.get(taskId));
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

    public TaskCalculatedPixelChartDialog(JFrame ptra2, ArrayList<TaskStatistic> taskStatistics) {
        super();

        int chart_width = 700;
        int chart_height = 700;
        setSize(chart_width, chart_height);
        setTitle("Calculated Pixels Statistics");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (chart_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (chart_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                dispose();

            }
        });

        JFreeChart chart = createChart(createDataset(taskStatistics));
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart, false);
        panel.setMouseWheelEnabled(true);
        //chartPanel.setPreferredSize(new Dimension(chart_width, chart_height));
        setContentPane(panel);
        //pack();
    }

}
