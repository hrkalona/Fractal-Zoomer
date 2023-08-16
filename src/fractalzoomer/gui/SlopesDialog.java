/*
 * Copyright (C) 2020 hrkalona2
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

import fractalzoomer.core.TaskDraw;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

/**
 *
 * @author hrkalona2
 */
public class SlopesDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public SlopesDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Slopes");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JCheckBox enable_slopes = new JCheckBox("Slopes");
        enable_slopes.setSelected(s.pps.ss.slopes);
        enable_slopes.setFocusable(false);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 3));

        JSlider direction_of_light = new JSlider(JSlider.HORIZONTAL, 0, 360, ((int) (s.pps.ss.SlopeAngle)));
        direction_of_light.setPreferredSize(new Dimension(200, 40));
        direction_of_light.setMajorTickSpacing(60);
        direction_of_light.setMinorTickSpacing(1);
        direction_of_light.setToolTipText("Sets the slope angle.");
        //color_blend.setPaintTicks(true);
        direction_of_light.setPaintLabels(true);
        //direction_of_light.setSnapToTicks(true);
        direction_of_light.setFocusable(false);

        JSlider ratio = new JSlider(JSlider.HORIZONTAL, 0, 99, ((int) (s.pps.ss.SlopeRatio * 100)));
        ratio.setPreferredSize(new Dimension(200, 40));
        ratio.setToolTipText("Sets the ratio of the slopes.");
        //color_blend.setPaintTicks(true);
        ratio.setPaintLabels(true);
        //depth.setSnapToTicks(true);
        ratio.setFocusable(false);

        Hashtable<Integer, JLabel> table3 = new Hashtable<>();
        table3.put(0, new JLabel("0.0"));
        table3.put(25, new JLabel("0.25"));
        table3.put(50, new JLabel("0.5"));
        table3.put(75, new JLabel("0.75"));
        table3.put(99, new JLabel("0.99"));
        ratio.setLabelTable(table3);


        JTextField power = new JTextField(20);
        power.setText("" + s.pps.ss.SlopePower);

        JPanel p333 = new JPanel();
        p333.add(power);


        p1.add(new JLabel("Angle:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Power:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Ratio:", SwingConstants.HORIZONTAL));
        p1.add(direction_of_light);
        p1.add(p333);
        p1.add(ratio);


        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.pps.ss.s_noise_reducing_factor);

        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(2, 5));

        p3.add(new JLabel("Transfer Function:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Transfer Factor:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Fractional Transfer:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Transfer Mode:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Fractional Smoothing:", SwingConstants.HORIZONTAL));

        JPanel p15 = new JPanel();
        p15.setLayout(new GridLayout(2, 2));
        p15.add(new JLabel("Color Mode:", SwingConstants.HORIZONTAL));
        p15.add(new JLabel("Color Blending:", SwingConstants.HORIZONTAL));

        JComboBox<String> transfer_combo = new JComboBox<>(Constants.lightTransfer);
        transfer_combo.setSelectedIndex(s.pps.ss.heightTransfer);
        transfer_combo.setFocusable(false);
        transfer_combo.setToolTipText("Sets the height transfer function.");

        JTextField tranfer_factor_field = new JTextField(20);
        tranfer_factor_field.setText("" + s.pps.ss.heightTransferFactor);

        final JComboBox<String> color_method_combo = new JComboBox<>(Constants.colorMethod);
        color_method_combo.setSelectedIndex(s.pps.ss.colorMode);
        color_method_combo.setFocusable(false);
        color_method_combo.setToolTipText("Sets the color mode.");

        final JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.pps.ss.slope_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        color_method_combo.addActionListener(e -> color_blend_opt.setEnabled(color_method_combo.getSelectedIndex() == 3));

        final JComboBox<String> fractional_transfer = new JComboBox<>(Constants.fractionalTransfer);
        fractional_transfer.setSelectedIndex(s.pps.ss.fractionalTransfer);
        fractional_transfer.setFocusable(false);
        fractional_transfer.setToolTipText("Sets the fractional transfer function.");

        final JComboBox<String> fractional_transfer_mode = new JComboBox<>(Constants.fractionalTransferMode);
        fractional_transfer_mode.setSelectedIndex(s.pps.ss.fractionalTransferMode);
        fractional_transfer_mode.setFocusable(false);
        fractional_transfer_mode.setToolTipText("Sets the fractional transfer mode.");

        fractional_transfer_mode.setEnabled(fractional_transfer.getSelectedIndex() != 0);
        fractional_transfer.addActionListener(e -> fractional_transfer_mode.setEnabled(fractional_transfer.getSelectedIndex() != 0));

        final JComboBox<String> fractional_smoothing = new JComboBox<>(Constants.FadeAlgs);
        fractional_smoothing.setSelectedIndex(s.pps.ss.fractionalSmoothing);
        fractional_smoothing.setFocusable(false);
        fractional_smoothing.setToolTipText("Sets the fractional smoothing function.");

        JPanel p4 = new JPanel();
        p4.add(transfer_combo);
        JPanel p5 = new JPanel();
        p5.add(tranfer_factor_field);
        JPanel p6 = new JPanel();
        p6.add(color_method_combo);

        JPanel p20 = new JPanel();
        p20.add(fractional_transfer);
        JPanel p22 = new JPanel();
        p22.add(fractional_transfer_mode);
        JPanel p21 = new JPanel();
        p21.add(fractional_smoothing);

        p3.add(p4);
        p3.add(p5);
        p3.add(p20);
        p3.add(p22);
        p3.add(p21);

        JPanel p16 = new JPanel();
        p16.add(color_blend_opt);

        p15.add(p6);
        p15.add(p16);

        color_blend_opt.setEnabled(s.pps.ss.colorMode == 3);

        Object[] message = {
            " ",
            enable_slopes,
            " ",
            "Set the slope angle, power, and ratio.",
            " ", p1,
            " ",
            "Set the height transfer and fractional transfer/smoothing.",
            p3,
            " ",
                "Set the color options.",
                p15,
                " ",
            "Set the image noise reduction factor.",
            "Noise Reduction Factor:",
            noise_factor_field,};

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
                            double temp = Double.parseDouble(noise_factor_field.getText());
                            double temp2 = Double.parseDouble(power.getText());
                            double temp6 = Double.parseDouble(tranfer_factor_field.getText());

                            if (temp <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The noise reduction factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The power must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.pps.ss.slopes = enable_slopes.isSelected();
                            s.pps.ss.SlopeAngle = direction_of_light.getValue();
                            s.pps.ss.SlopeRatio = ratio.getValue() / 100.0;
                            s.pps.ss.SlopePower = temp2;
                            s.pps.ss.slope_blending = color_blend_opt.getValue() / 100.0;
                            s.pps.ss.colorMode = color_method_combo.getSelectedIndex();
                            s.pps.ss.heightTransfer = transfer_combo.getSelectedIndex();
                            s.pps.ss.heightTransferFactor = temp6;

                            double lightAngleRadians = Math.toRadians(s.pps.ss.SlopeAngle);
                            s.pps.ss.lightVector[0] = Math.cos(lightAngleRadians);
                            s.pps.ss.lightVector[1] = Math.sin(lightAngleRadians);

                            s.pps.ss.s_noise_reducing_factor = temp;

                            s.pps.ss.fractionalTransfer = fractional_transfer.getSelectedIndex();
                            s.pps.ss.fractionalSmoothing = fractional_smoothing.getSelectedIndex();
                            s.pps.ss.fractionalTransferMode = fractional_transfer_mode.getSelectedIndex();

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        if (greedy_algorithm && !TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA && enable_slopes.isSelected() && !julia_map && !s.d3s.d3 && !s.ds.domain_coloring) {
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
