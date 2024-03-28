package fractalzoomer.gui;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer2;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class MemoryUsageChartPanel extends JPanel {
    private final TimeSeries max;
    private final TimeSeries allocated;
    private final TimeSeries used;

    public MemoryUsageChartPanel(int maxAge) {
        super(new BorderLayout());
        max = new TimeSeries("Max Memory");
        this.max.setMaximumItemAge(maxAge);
        this.allocated = new TimeSeries("Allocated Memory");
        this.allocated.setMaximumItemAge(maxAge);
        this.used = new TimeSeries("Used Memory");
        this.used.setMaximumItemAge(maxAge);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        //dataset.addSeries(this.max);
        dataset.addSeries(this.used);
        dataset.addSeries(this.allocated);

        DateAxis domain = new DateAxis("Time");
        NumberAxis range = new NumberAxis("Memory (bytes)");
        //domain.setTickLabelFont(new Font("SansSerif", 0, 12));
        //range.setTickLabelFont(new Font("SansSerif", 0, 12));
        //domain.setLabelFont(new Font("SansSerif", 0, 14));
        //range.setLabelFont(new Font("SansSerif", 0, 14));
        range.setNumberFormatOverride(new DecimalFormat("#,##0"));
        //XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        XYAreaRenderer renderer = new XYAreaRenderer();

        XYPlot plot = new XYPlot(dataset, domain, range, renderer);
        domain.setAutoRange(true);
        plot.setDomainGridlinesVisible(true);
        plot.setForegroundAlpha(0.55F);
        domain.setLowerMargin(0.0D);
        domain.setUpperMargin(0.0D);
        domain.setTickLabelsVisible(true);
        range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        JFreeChart chart = new JFreeChart("JVM Memory Usage", plot);

        ChartUtils.applyCurrentTheme(chart);

        ((XYAreaRenderer)plot.getRenderer()).setOutline(true);
        ((XYAreaRenderer)plot.getRenderer()).setUseFillPaint(true);

        double coef = 0.6;
        int r = (int)(Color.RED.getRed() * coef + 255 * (1 - coef));
        int g = (int)(Color.RED.getGreen() * coef + 255 * (1 - coef));
        int b = (int)(Color.RED.getBlue() * coef + 255 * (1 - coef));

        ((XYAreaRenderer)plot.getRenderer()).setSeriesPaint(0, new Color(r, g, b), true);
        ((XYAreaRenderer)plot.getRenderer()).setSeriesOutlinePaint(0, Color.RED, true);
        ((XYAreaRenderer)plot.getRenderer()).setSeriesStroke(0, new BasicStroke(3.0F, 0, 2));

        r = (int)(Color.BLUE.getRed() * coef + 255 * (1 - coef));
        g = (int)(Color.BLUE.getGreen() * coef + 255 * (1 - coef));
        b = (int)(Color.BLUE.getBlue() * coef + 255 * (1 - coef));


        ((XYAreaRenderer)plot.getRenderer()).setSeriesPaint(1, new Color(r, g, b), true);
        ((XYAreaRenderer)plot.getRenderer()).setSeriesOutlinePaint(1, Color.BLUE, true);
        ((XYAreaRenderer)plot.getRenderer()).setSeriesStroke(1, new BasicStroke(3.0F, 0, 2));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.BLACK)));
        this.add(chartPanel);
    }

    public void addAllocatedObservation(RegularTimePeriod p, double y) { allocated.add(p, y);
    }

    public void addMaxObservation(RegularTimePeriod p, double y) {
        max.add(p, y);
    }

    public void addUsedObservation(RegularTimePeriod p, double y) {
        used.add(p, y);
    }

}
