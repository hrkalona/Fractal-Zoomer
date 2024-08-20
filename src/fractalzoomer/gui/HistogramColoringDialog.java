
package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona
 */
public class HistogramColoringDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;
    private final JScrollPane scrollPane;

    public HistogramColoringDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {

        super(ptr);
        
        ptra = ptr;

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(600, 700));

        setTitle("Histogram Coloring");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JComboBox<String> mapping = new JComboBox<>(Constants.histogramMapping);
        mapping.setSelectedIndex(s.pps.hss.hmapping);
        mapping.setFocusable(false);
        mapping.setToolTipText("Sets the value mapping method.");

        JTextField granularity_field = new JTextField();
        granularity_field.setText("" + s.pps.hss.histogramBinGranularity);
        granularity_field.setEnabled(mapping.getSelectedIndex() == 0);
        
        JTextField density_field = new JTextField();
        density_field.setText("" + s.pps.hss.histogramDensity);
        density_field.setEnabled(mapping.getSelectedIndex() == 0);

        JPanel temp_p4 = new JPanel();
        temp_p4.setLayout(new GridLayout(2, 2));
        temp_p4.add(new JLabel("Bin Granularity:", SwingConstants.HORIZONTAL));
        temp_p4.add(new JLabel("Density:", SwingConstants.HORIZONTAL));
        temp_p4.add(granularity_field);
        temp_p4.add(density_field);

        final JCheckBox enable_histogram_coloring = new JCheckBox("Histogram Coloring");
        enable_histogram_coloring.setSelected(s.pps.hss.histogramColoring);
        enable_histogram_coloring.setFocusable(false);

        final JCheckBox removeOutliers = new JCheckBox("Remove Outliers");
        removeOutliers.setSelected(s.pps.hss.hs_remove_outliers);
        removeOutliers.setFocusable(false);

        JComboBox<String> outliersAlgorithm = new JComboBox<>(new String[] {"Tukey's Fences", "Z-score"});
        outliersAlgorithm.setSelectedIndex(s.pps.hss.hs_outliers_method);
        outliersAlgorithm.setFocusable(false);
        outliersAlgorithm.setToolTipText("Sets the outlier removal method.");

        JComboBox<String> rankOrderDigitGrouping = new JComboBox<>(new String[] {"1", "2", "3", "4", "5", "6", "7", "8"});
        rankOrderDigitGrouping.setSelectedIndex(s.pps.hss.rank_order_digits_grouping);
        rankOrderDigitGrouping.setFocusable(false);
        rankOrderDigitGrouping.setToolTipText("Sets the fractional digits grouping for rank order.");

        outliersAlgorithm.setEnabled(removeOutliers.isSelected());

        removeOutliers.addActionListener(e -> outliersAlgorithm.setEnabled(removeOutliers.isSelected()));

        JSlider color_blend_opt = new SliderGradient(JSlider.HORIZONTAL, 0, 100, (int) (s.pps.hss.hs_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        RangeSlider scale_range = new RangeSlider(0, 100);
        scale_range.setValue((int)(s.pps.hss.histogramScaleMin * 100));
        scale_range.setUpperValue((int)(s.pps.hss.histogramScaleMax * 100));
        scale_range.setMajorTickSpacing(25);
        scale_range.setMinorTickSpacing(1);
        scale_range.setToolTipText("Sets the scaling range.");
        scale_range.setFocusable(false);
        scale_range.setPaintLabels(true);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.pps.hss.hs_noise_reducing_factor);

        mapping.addActionListener(e -> {
            density_field.setEnabled(mapping.getSelectedIndex() == 0);
            granularity_field.setEnabled(mapping.getSelectedIndex() == 0);
            rankOrderDigitGrouping.setEnabled(mapping.getSelectedIndex() == 6);
        });

        rankOrderDigitGrouping.setEnabled(mapping.getSelectedIndex() == 6);

        JPanel blend_panel = new JPanel();

        final JComboBox<String> blend_modes = new JComboBox<>(Constants.blend_algorithms);
        blend_modes.setSelectedIndex(s.pps.hss.hs_color_blending);
        blend_modes.setFocusable(false);
        blend_modes.setToolTipText("Sets the blending mode mode.");

        final JCheckBox reverse_blending = new JCheckBox("Reverse Order of Colors");
        reverse_blending.setSelected(s.pps.hss.hs_reverse_color_blending);
        reverse_blending.setFocusable(false);
        reverse_blending.setToolTipText("Reverts the order of colors in the blending operation.");

        blend_panel.add(new JLabel("Blend Mode: "));
        blend_panel.add(blend_modes);
        blend_panel.add(reverse_blending);

        Object[] message = {
            " ",
            enable_histogram_coloring,
            " ",
            "Set the mapping function.",
            "Mapping: ",
            mapping,
                " ",
                "Set the color blending options.",
                blend_panel,
                " ",
            "Set the color blending percentage.",
            "Color Blending:", color_blend_opt,
            " ",
            "Set the histogram bin granularity/density.",
                temp_p4,
            " ",
                "Set the rank order fractional digits grouping.",
                "Fractional Digits Grouping:",
                rankOrderDigitGrouping,
                " ",
            "Set the scaling range.",
            "Scaling Range:",
            scale_range,
            " ",
                removeOutliers,
                "Outliers Removal Method:",
                outliersAlgorithm,
                " ",
            "Set the image noise reduction factor.",
            "Noise Reduction Factor:",
            noise_factor_field,
            " "};

        optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                optionPane.setValue(JOptionPane.CLOSED_OPTION);
            }
        });

        optionPane.addPropertyChangeListener(
                e -> {
                    String prop = e.getPropertyName();

                    if (isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {

                        Object value = optionPane.getValue();

                        if (value == JOptionPane.UNINITIALIZED_VALUE) {
                            //ignore reset
                            return;
                        }

                        //Reset the JOptionPane's value.
                        //If you don't do this, then if the user
                        //presses the same button next time, no
                        //property change event will be fired.
                        optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

                        if ((Integer) value == JOptionPane.CANCEL_OPTION || (Integer) value == JOptionPane.NO_OPTION || (Integer) value == JOptionPane.CLOSED_OPTION) {
                            dispose();
                            return;
                        }

                        try {
                            double temp2 = Double.parseDouble(noise_factor_field.getText());
                            double temp3 = Double.parseDouble(density_field.getText());
                            int temp = Integer.parseInt(granularity_field.getText());

                            if(temp < 1) {
                                JOptionPane.showMessageDialog(ptra, "The histogram bin granularity must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp > 50) {
                                JOptionPane.showMessageDialog(ptra, "The histogram bin granularity must be lower than 51.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The noise reduction factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp3 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The histogram density must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.pps.hss.histogramColoring = enable_histogram_coloring.isSelected();
                            s.pps.hss.hs_noise_reducing_factor = temp2;
                            s.pps.hss.hs_blending = color_blend_opt.getValue() / 100.0;
                            s.pps.hss.histogramDensity = temp3;
                            s.pps.hss.histogramBinGranularity = temp;
                            s.pps.hss.histogramScaleMax = scale_range.getUpperValue() / 100.0;
                            s.pps.hss.histogramScaleMin = scale_range.getValue() / 100.0;
                            s.pps.hss.hmapping = mapping.getSelectedIndex();
                            s.pps.hss.hs_remove_outliers = removeOutliers.isSelected();
                            s.pps.hss.hs_outliers_method = outliersAlgorithm.getSelectedIndex();
                            s.pps.hss.rank_order_digits_grouping = rankOrderDigitGrouping.getSelectedIndex();
                            s.pps.hss.hs_color_blending = blend_modes.getSelectedIndex();
                            s.pps.hss.hs_reverse_color_blending = reverse_blending.isSelected();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        if (greedy_algorithm && !TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA && enable_histogram_coloring.isSelected() && !julia_map && !s.d3s.d3) {
                            JOptionPane.showMessageDialog(ptra, Constants.greedyWarning, "Warning!", JOptionPane.WARNING_MESSAGE);
                        }

                        ptra.setPostProcessingPost();
                    }
                });

        //Make this dialog display it.
        scrollPane.setViewportView(optionPane);
        setContentPane(scrollPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }
    
}
