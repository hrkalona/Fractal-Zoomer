/*
 * Copyright (C) 2020 hrkalona
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.gui;

import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona
 */
public class HistogramColoringDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public HistogramColoringDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Histogram Coloring");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JComboBox<String> mapping = new JComboBox<>(Constants.histogramMapping);
        mapping.setSelectedIndex(s.hss.hmapping);
        mapping.setFocusable(false);
        mapping.setToolTipText("Sets the value mapping method.");

        JTextField granularity_field = new JTextField();
        granularity_field.setText("" + s.hss.histogramBinGranularity);
        granularity_field.setEnabled(mapping.getSelectedIndex() == 0);
        
        JTextField density_field = new JTextField();
        density_field.setText("" + s.hss.histogramDensity);
        density_field.setEnabled(mapping.getSelectedIndex() == 0);

        final JCheckBox enable_histogram_coloring = new JCheckBox("Histogram Coloring");
        enable_histogram_coloring.setSelected(s.hss.histogramColoring);
        enable_histogram_coloring.setFocusable(false);

        JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.hss.hs_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        RangeSlider scale_range = new RangeSlider(0, 100);
        scale_range.setValue((int)(s.hss.histogramScaleMin * 100));
        scale_range.setUpperValue((int)(s.hss.histogramScaleMax * 100));
        scale_range.setMajorTickSpacing(25);
        scale_range.setMinorTickSpacing(1);
        scale_range.setToolTipText("Sets the scaling range.");
        scale_range.setFocusable(false);
        scale_range.setPaintLabels(true);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.hss.hs_noise_reducing_factor);

        mapping.addActionListener(e -> {
            density_field.setEnabled(mapping.getSelectedIndex() == 0);
            granularity_field.setEnabled(mapping.getSelectedIndex() == 0);
        });

        Object[] message = {
            " ",
            enable_histogram_coloring,
            " ",
            "Set the mapping function.",
            "Mapping: ",
            mapping,
            " ",
            "Set the color blending percentage.",
            "Color Blending:", color_blend_opt,
            " ",
            "Set the histogram bin granularity.",
            "Bin Granularity:",
            granularity_field,
            " ",            
            "Set the histogram density.",
            "Density:",
            density_field,
            " ",
            "Set the scaling range.",
            "Scaling Range:",
            scale_range,
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

                            s.hss.histogramColoring = enable_histogram_coloring.isSelected();
                            s.hss.hs_noise_reducing_factor = temp2;
                            s.hss.hs_blending = color_blend_opt.getValue() / 100.0;
                            s.hss.histogramDensity = temp3;
                            s.hss.histogramBinGranularity = temp;
                            s.hss.histogramScaleMax = scale_range.getUpperValue() / 100.0;
                            s.hss.histogramScaleMin = scale_range.getValue() / 100.0;
                            s.hss.hmapping = mapping.getSelectedIndex();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        if (greedy_algorithm && !ThreadDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA && enable_histogram_coloring.isSelected() && !julia_map && !s.d3s.d3) {
                            JOptionPane.showMessageDialog(ptra, Constants.greedyWarning, "Warning!", JOptionPane.WARNING_MESSAGE);
                        }

                        ptra.setPostProcessingPost();
                    }
                });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }
    
}
