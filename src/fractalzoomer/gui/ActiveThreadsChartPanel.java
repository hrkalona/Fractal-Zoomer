package fractalzoomer.gui;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class ActiveThreadsChartPanel extends JPanel {
    private final TimeSeries active_threads;

    public ActiveThreadsChartPanel(int maxAge) {
        super(new BorderLayout());
        active_threads = new TimeSeries("Active Threads");
        this.active_threads.setMaximumItemAge(maxAge);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(this.active_threads);
        DateAxis domain = new DateAxis("Time");
        NumberAxis range = new NumberAxis("Thread Count");
        //domain.setTickLabelFont(new Font("SansSerif", 0, 12));
        //range.setTickLabelFont(new Font("SansSerif", 0, 12));
        //domain.setLabelFont(new Font("SansSerif", 0, 14));
        //range.setLabelFont(new Font("SansSerif", 0, 14));
        range.setNumberFormatOverride(new DecimalFormat("#,##0"));
        //XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        //XYAreaRenderer2 renderer = new XYAreaRenderer2();
        XYAreaRenderer renderer = new XYAreaRenderer();//2

        XYPlot plot = new XYPlot(dataset, domain, range, renderer);
        domain.setAutoRange(true);
        domain.setLowerMargin(0.0D);
        domain.setUpperMargin(0.0D);
        domain.setTickLabelsVisible(true);
        plot.setDomainGridlinesVisible(true);
        plot.setForegroundAlpha(0.55F);
        range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        JFreeChart chart = new JFreeChart("Active Threads", plot);

        ChartUtils.applyCurrentTheme(chart);

        Color green = new Color(102,2,60);
        double coef = 0.6;
        int r = (int)(green.getRed() * coef + 255 * (1 - coef));
        int g = (int)(green.getGreen() * coef + 255 * (1 - coef));
        int b = (int)(green.getBlue() * coef + 255 * (1 - coef));

        ((XYAreaRenderer)plot.getRenderer()).setOutline(true);
        ((XYAreaRenderer)plot.getRenderer()).setUseFillPaint(true);
        ((XYAreaRenderer)plot.getRenderer()).setSeriesPaint(0, new Color(r, g, b), true);
        ((XYAreaRenderer)plot.getRenderer()).setSeriesOutlinePaint(0, green, true);
        ((XYAreaRenderer)plot.getRenderer()).setSeriesStroke(0, new BasicStroke(3.0F, 0, 2));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.BLACK)));
        this.add(chartPanel);
    }

    public void addActiveThreadsObservation(RegularTimePeriod p, double y) {
        active_threads.add(p, y);
    }

}
