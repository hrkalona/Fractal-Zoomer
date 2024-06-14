package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.utils.TaskStatistic;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
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
import java.util.ArrayList;

public class TaskPixelCalculationCountsChartDialog extends JDialog {

    private static CategoryDataset createDataset(ArrayList<TaskStatistic> taskStatistics) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(TaskStatistic stat : taskStatistics) {
            if(stat.getPixelsCalculated() >= 0) {
                dataset.addValue(stat.getPixelsCalculated(), "Pixels Calculated", "" + stat.getTaskId());
            }
            if(stat.getPixelsCalculated() >= 0 && stat.getPixelsCompleted() >= 0) {
                dataset.addValue(stat.getPixelsCompleted() - stat.getPixelsCalculated(), "Pixels Guessed", "" + stat.getTaskId());
            }
            if(stat.getPixelsCompleted() >= 0) {
                dataset.addValue(stat.getPixelsCompleted(), "Pixels Completed", "" + stat.getTaskId());
            }
            if(stat.getPixelsPostProcessed() >= 0) {
                dataset.addValue(stat.getPixelsPostProcessed(), "Pixels Post Processed", "" + stat.getTaskId());
            }
            if (stat.getExtraPixelsCalculated() > 0) {
                dataset.addValue(stat.getExtraPixelsCalculated(), "Extra Pixels Calculated", "" + stat.getTaskId());
            }
        }
        return dataset;
    }

    private static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Pixel Count Statistics", "Task" /* x-axis label*/,
                "Pixel Count" /* y-axis label */, dataset);
        chart.addSubtitle(new TextTitle("per Task"));
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);

        //renderer.setBarPainter(new StandardBarPainter());
        return chart;
    }

    public TaskPixelCalculationCountsChartDialog(JFrame ptra2, ArrayList<TaskStatistic> taskStatistics) {
        super();

        int chart_width = 1000;
        int chart_height = 600;
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

        CategoryDataset dataset = createDataset(taskStatistics);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        //chartPanel.setPreferredSize(new Dimension(chart_width, chart_height));
        setContentPane(chartPanel);
        //pack();
    }

}
