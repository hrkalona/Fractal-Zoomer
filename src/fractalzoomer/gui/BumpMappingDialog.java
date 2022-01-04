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

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static fractalzoomer.main.Constants.bumpProcessingMethod;
import static fractalzoomer.main.Constants.bumpTransferNames;

/**
 *
 * @author hrkalona2
 */
public class BumpMappingDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public BumpMappingDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Bump Mapping");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        final JCheckBox enable_bump_map = new JCheckBox("Bump Mapping");
        enable_bump_map.setSelected(s.bms.bump_map);
        enable_bump_map.setFocusable(false);

        JSlider direction_of_light = new JSlider(JSlider.HORIZONTAL, -360, 360, ((int) (s.bms.lightDirectionDegrees)));
        direction_of_light.setPreferredSize(new Dimension(300, 40));
        direction_of_light.setMajorTickSpacing(90);
        direction_of_light.setMinorTickSpacing(1);
        direction_of_light.setToolTipText("Sets the direction of light.");
        //color_blend.setPaintTicks(true);
        direction_of_light.setPaintLabels(true);
        //direction_of_light.setSnapToTicks(true);
        direction_of_light.setFocusable(false);

        JSlider depth = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int) (s.bms.bumpMappingDepth)));
        depth.setPreferredSize(new Dimension(300, 40));
        depth.setMajorTickSpacing(25);
        depth.setMinorTickSpacing(1);
        depth.setToolTipText("Sets the depth of the effect.");
        //color_blend.setPaintTicks(true);
        depth.setPaintLabels(true);
        //depth.setSnapToTicks(true);
        depth.setFocusable(false);

        JSlider strength = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int) (s.bms.bumpMappingStrength)));
        strength.setPreferredSize(new Dimension(300, 40));
        strength.setMajorTickSpacing(25);
        strength.setMinorTickSpacing(1);
        strength.setToolTipText("Sets the strength of the effect.");
        //color_blend.setPaintTicks(true);
        strength.setPaintLabels(true);
        //strength.setSnapToTicks(true);
        strength.setFocusable(false);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.bms.bm_noise_reducing_factor);

        final JComboBox bump_transfer_functions_opt = new JComboBox(bumpTransferNames);
        bump_transfer_functions_opt.setSelectedIndex(s.bms.bump_transfer_function);
        bump_transfer_functions_opt.setFocusable(false);
        bump_transfer_functions_opt.setToolTipText("Sets the transfer function.");

        JTextField bump_transfer_factor_field = new JTextField(20);
        bump_transfer_factor_field.setText("" + s.bms.bump_transfer_factor);

        JPanel panel = new JPanel();
        panel.add(bump_transfer_functions_opt);
        panel.add(bump_transfer_factor_field);

        final JComboBox bump_processing_method_opt = new JComboBox(bumpProcessingMethod);
        bump_processing_method_opt.setSelectedIndex(s.bms.bumpProcessing);
        bump_processing_method_opt.setFocusable(false);
        bump_processing_method_opt.setToolTipText("Sets the image processing method.");
        bump_processing_method_opt.setPreferredSize(new Dimension(150, 20));

        final JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.bms.bump_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        color_blend_opt.setEnabled(s.bms.bumpProcessing == 1 || s.bms.bumpProcessing == 2);

        bump_processing_method_opt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color_blend_opt.setEnabled(bump_processing_method_opt.getSelectedIndex() == 1 || bump_processing_method_opt.getSelectedIndex() == 2);
            }

        });

        JPanel p2 = new JPanel();
        p2.add(bump_processing_method_opt);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 2));
        p1.add(new JLabel("Image Processing Method:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Color Blending:", SwingConstants.HORIZONTAL));
        p1.add(p2);
        p1.add(color_blend_opt);

        Object[] message = {
            " ",
            enable_bump_map,
            " ",
            "Set the direction of light in degrees, depth, and strength.",
            "Direction of light:", direction_of_light,
            " ",
            "Depth:", depth,
            " ",
            "Strength:", strength,
            " ",
            "Set the tranfer function and the factor.",
            "Transfer Function and Factor:",
            panel,
            " ",
            "Set the image processing method.",
            p1,
            " ",
            "Set the image noise reduction factor.",
            "Noise Reduction Factor:",
            noise_factor_field,
            " "};

        optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
            }
        });

        optionPane.addPropertyChangeListener(
                new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
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
                        double temp2 = Double.parseDouble(bump_transfer_factor_field.getText());

                        if (temp <= 0) {
                            JOptionPane.showMessageDialog(ptra, "The noise reduction factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (temp2 <= 0) {
                            JOptionPane.showMessageDialog(ptra, "The transfer factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.bms.bump_map = enable_bump_map.isSelected();
                        s.bms.lightDirectionDegrees = direction_of_light.getValue();
                        s.bms.bumpMappingDepth = depth.getValue();
                        s.bms.bumpMappingStrength = strength.getValue();
                        s.bms.bm_noise_reducing_factor = temp;
                        s.bms.bump_transfer_function = bump_transfer_functions_opt.getSelectedIndex();
                        s.bms.bump_transfer_factor = temp2;
                        s.bms.bumpProcessing = bump_processing_method_opt.getSelectedIndex();
                        s.bms.bump_blending = color_blend_opt.getValue() / 100.0;

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                    
                    if (greedy_algorithm && enable_bump_map.isSelected() && !julia_map && !s.d3s.d3 && !s.ds.domain_coloring) {
                        JOptionPane.showMessageDialog(ptra, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }
                    
                    if (!s.fns.smoothing && s.bms.bump_map && !s.ds.domain_coloring) {
                        JOptionPane.showMessageDialog(ptra, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }
                    
                    ptra.setPostProcessingPost();
                }
            }
        });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

}
