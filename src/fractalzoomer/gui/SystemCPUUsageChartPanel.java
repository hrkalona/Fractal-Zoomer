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

public class SystemCPUUsageChartPanel extends JPanel {
    private final TimeSeries cpu_usage;

    public SystemCPUUsageChartPanel(int maxAge) {
        super(new BorderLayout());
        cpu_usage = new TimeSeries("CPU Usage");
        this.cpu_usage.setMaximumItemAge(maxAge);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(this.cpu_usage);
        DateAxis domain = new DateAxis("Time");
        NumberAxis range = new NumberAxis("Usage (%)");
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
        JFreeChart chart = new JFreeChart("System CPU Usage", plot);

        ChartUtils.applyCurrentTheme(chart);

        Color col = new Color(253, 102, 35);
        double coef = 0.6;
        int r = (int)(col.getRed() * coef + 255 * (1 - coef));
        int g = (int)(col.getGreen() * coef + 255 * (1 - coef));
        int b = (int)(col.getBlue() * coef + 255 * (1 - coef));

        ((XYAreaRenderer)plot.getRenderer()).setOutline(true);
        ((XYAreaRenderer)plot.getRenderer()).setUseFillPaint(true);
        ((XYAreaRenderer)plot.getRenderer()).setSeriesPaint(0, new Color(r, g, b), true);
        ((XYAreaRenderer)plot.getRenderer()).setSeriesOutlinePaint(0, col, true);
        ((XYAreaRenderer)plot.getRenderer()).setSeriesStroke(0, new BasicStroke(3.0F, 0, 2));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.BLACK)));
        this.add(chartPanel);
    }

    public void addCPUUsageObservation(RegularTimePeriod p, double y) {
        cpu_usage.add(p, y);
    }

}
