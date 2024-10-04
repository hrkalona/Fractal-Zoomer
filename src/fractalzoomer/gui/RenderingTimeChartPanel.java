package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class RenderingTimeChartPanel extends JPanel implements ActionListener {
    public static  final  String[] labels = new String[] {
            TaskRender.TOTAL_ELAPSED_TIME_STRING_LABEL,
            TaskRender.REFERENCE_CALCULATION_ELAPSED_TIME_STRING_LABEL,
            TaskRender.JULIA_EXTRA_REFERENCE_CALCULATION_ELAPSED_TIME_STRING_LABEL,
            TaskRender.SA_CALCULATION_ELAPSED_TIME_LABEL,
            TaskRender.NANOMB1_CALCULATION_ELAPSED_TIME_LABEL,
            TaskRender.BLA_CALCULATION_ELAPSED_TIME_LABEL,
            TaskRender.PIXEL_CALCULATION_ELAPSED_TIME_STRING_LABEL,
            TaskRender.POST_PROCESSING_ELAPSED_TIME_STRING_LABEL,
            TaskRender.IMAGE_FILTERS_TIME_STRING_LABEL,
            "Image Save Elapsed Time"
    };
    private final XYSeries[] times;
    private final boolean[] has_data;
    private XYItemRenderer renderer;
    public RenderingTimeChartPanel(int maxCount) {
        super(new BorderLayout());

        XYSeriesCollection dataset = new XYSeriesCollection();
        times = new XYSeries[labels.length];
        has_data = new boolean[labels.length];
        for(int i = 0; i < times.length; i++) {

            String final_label = labels[i];
            final_label = final_label.replaceAll("<li>", "");
            final_label = final_label.replaceAll("<b>", "");
            final_label = final_label.replaceAll(":", "");

            times[i] = new XYSeries(final_label);
            times[i].setMaximumItemCount(maxCount);
            dataset.addSeries(times[i]);
        }

        NumberAxis domain = new NumberAxis("Render");
        NumberAxis range = new NumberAxis("Time (ms)");
        domain.setAutoRangeStickyZero(false);
        domain.setAutoRangeIncludesZero(false);
        range.setAutoRangeStickyZero(false);
        range.setAutoRangeIncludesZero(false);

        domain.setAutoRange(true);
        domain.setLowerMargin(0.0D);
        domain.setUpperMargin(0.0D);
        domain.setTickLabelsVisible(true);

        //range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        domain.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        range.setNumberFormatOverride(new DecimalFormat("#,##0"));
        renderer = new XYLineAndShapeRenderer(true, true);
        renderer.setDefaultToolTipGenerator(new StandardXYToolTipGenerator());

        XYPlot plot = new XYPlot(dataset, domain, range, renderer);
        plot.setDomainGridlinesVisible(true);

        JFreeChart chart = new JFreeChart("Time Trend", plot);

        ChartUtils.applyCurrentTheme(chart);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.BLACK)));
        JPanel boxPanel = new JPanel();

        for(int i = 0; i < times.length; i++) {
            JCheckBox box1 = new JCheckBox("" + (i + 1));
            box1.setActionCommand("" + (i + 1));
            box1.addActionListener(this);
            box1.setSelected(true);
            boxPanel.add(box1);
            box1.setFocusable(false);
            box1.setForeground((Color) renderer.getItemPaint(i, 0));
            box1.setFont(box1.getFont().deriveFont(Font.BOLD));
            box1.setToolTipText(times[i].getKey().toString());
        }
        this.add(chartPanel);
        this.add(boxPanel, "South");
    }

    public void addTimeData(int id, long render, long y) {
        if(y != 0 || has_data[id]) {
            times[id].add(render, y);
            has_data[id] = true;
        }
        else if(!has_data[id]) {
            times[id].add(render, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int series = Integer.parseInt(e.getActionCommand()) - 1;

            if (series >= 0) {
                boolean visible = renderer.getItemVisible(series, 0);
                renderer.setSeriesVisible(series, !visible);
            }
        }
        catch (Exception ex) {}
    }
}
