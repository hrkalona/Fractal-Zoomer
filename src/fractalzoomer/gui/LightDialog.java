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

import fractalzoomer.main.Constants;
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
import java.util.Hashtable;

/**
 *
 * @author hrkalona2
 */
public class LightDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public LightDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Light");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        final JCheckBox enable_light = new JCheckBox("Light");
        enable_light.setSelected(s.ls.lighting);
        enable_light.setFocusable(false);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 3));

        JSlider direction_of_light = new JSlider(JSlider.HORIZONTAL, 0, 360, ((int) (s.ls.light_direction)));
        direction_of_light.setPreferredSize(new Dimension(200, 40));
        direction_of_light.setMajorTickSpacing(60);
        direction_of_light.setMinorTickSpacing(1);
        direction_of_light.setToolTipText("Sets the direction of light.");
        //color_blend.setPaintTicks(true);
        direction_of_light.setPaintLabels(true);
        //direction_of_light.setSnapToTicks(true);
        direction_of_light.setFocusable(false);

        JSlider depth = new JSlider(JSlider.HORIZONTAL, 0, 99, ((int) (s.ls.light_magnitude * 100)));
        depth.setPreferredSize(new Dimension(200, 40));
        depth.setToolTipText("Sets the magnitude of light.");
        //color_blend.setPaintTicks(true);
        depth.setPaintLabels(true);
        //depth.setSnapToTicks(true);
        depth.setFocusable(false);

        Hashtable<Integer, JLabel> table3 = new Hashtable<Integer, JLabel>();
        table3.put(0, new JLabel("0.0"));
        table3.put(25, new JLabel("0.25"));
        table3.put(50, new JLabel("0.5"));
        table3.put(75, new JLabel("0.75"));
        table3.put(99, new JLabel("0.99"));
        depth.setLabelTable(table3);

        final JComboBox light_mode_combo = new JComboBox(Constants.lightModes);
        light_mode_combo.setSelectedIndex(s.ls.lightMode);
        light_mode_combo.setFocusable(false);
        light_mode_combo.setToolTipText("Sets the light mode.");

        JPanel p7 = new JPanel();
        p7.add(light_mode_combo);

        p1.add(new JLabel("Direction:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Magnitude:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Light Mode:", SwingConstants.HORIZONTAL));
        p1.add(direction_of_light);
        p1.add(depth);
        p1.add(p7);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.ls.l_noise_reducing_factor);

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(2, 4));

        JTextField light_intensity_field = new JTextField();
        light_intensity_field.setText("" + s.ls.lightintensity);

        JTextField ambient_light_field = new JTextField();
        ambient_light_field.setText("" + s.ls.ambientlight);

        JTextField specular_intensity_field = new JTextField();
        specular_intensity_field.setText("" + s.ls.specularintensity);

        JTextField shininess_field = new JTextField();
        shininess_field.setText("" + s.ls.shininess);

        p2.add(new JLabel("Light Intensity:", SwingConstants.HORIZONTAL));
        p2.add(new JLabel("Ambient Light:", SwingConstants.HORIZONTAL));
        p2.add(new JLabel("Specular Intensity:", SwingConstants.HORIZONTAL));
        p2.add(new JLabel("Shininess:", SwingConstants.HORIZONTAL));
        p2.add(light_intensity_field);
        p2.add(ambient_light_field);
        p2.add(specular_intensity_field);
        p2.add(shininess_field);

        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(2, 4));

        p3.add(new JLabel("Transfer Function:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Transfer Factor:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Color Mode:", SwingConstants.HORIZONTAL));
        p3.add(new JLabel("Color Blending:", SwingConstants.HORIZONTAL));

        JComboBox transfer_combo = new JComboBox(Constants.lightTransfer);
        transfer_combo.setSelectedIndex(s.ls.heightTransfer);
        transfer_combo.setFocusable(false);
        transfer_combo.setToolTipText("Sets the height transfer function.");

        JTextField tranfer_factor_field = new JTextField(20);
        tranfer_factor_field.setText("" + s.ls.heightTransferFactor);

        final JComboBox color_method_combo = new JComboBox(Constants.colorMethod);
        color_method_combo.setSelectedIndex(s.ls.colorMode);
        color_method_combo.setFocusable(false);
        color_method_combo.setToolTipText("Sets the color mode.");

        final JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.ls.light_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        color_method_combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color_blend_opt.setEnabled(color_method_combo.getSelectedIndex() == 3);
            }
        });

        JPanel p4 = new JPanel();
        p4.add(transfer_combo);
        JPanel p5 = new JPanel();
        p5.add(tranfer_factor_field);
        JPanel p6 = new JPanel();
        p6.add(color_method_combo);

        p3.add(p4);
        p3.add(p5);
        p3.add(p6);
        p3.add(color_blend_opt);

        color_blend_opt.setEnabled(s.ls.colorMode == 3);

        Object[] message = {
            " ",
            enable_light,
            " ",
            "Set the light direction, magnitude, and light mode.",
            " ", p1,
            " ",
            "Set the light properties.",
            p2,
            " ",
            "Set the height transfer and color options.",
            p3,
            " ",
            "Set the image noise reduction factor.",
            "Noise Reduction Factor:",
            noise_factor_field,};

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
                        double temp2 = Double.parseDouble(light_intensity_field.getText());
                        double temp3 = Double.parseDouble(ambient_light_field.getText());
                        double temp4 = Double.parseDouble(specular_intensity_field.getText());
                        double temp5 = Double.parseDouble(shininess_field.getText());
                        double temp6 = Double.parseDouble(tranfer_factor_field.getText());

                        if (temp <= 0) {
                            JOptionPane.showMessageDialog(ptra, "The noise reduction factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.ls.lighting = enable_light.isSelected();
                        s.ls.light_direction = direction_of_light.getValue();
                        s.ls.light_magnitude = depth.getValue() / 100.0;
                        s.ls.lightintensity = temp2;
                        s.ls.ambientlight = temp3;
                        s.ls.specularintensity = temp4;
                        s.ls.shininess = temp5;
                        s.ls.light_blending = color_blend_opt.getValue() / 100.0;
                        s.ls.colorMode = color_method_combo.getSelectedIndex();
                        s.ls.heightTransfer = transfer_combo.getSelectedIndex();
                        s.ls.heightTransferFactor = temp6;
                        s.ls.lightMode = light_mode_combo.getSelectedIndex();

                        double lightAngleRadians = Math.toRadians(s.ls.light_direction);
                        s.ls.lightVector[0] = Math.cos(lightAngleRadians) * s.ls.light_magnitude;
                        s.ls.lightVector[1] = Math.sin(lightAngleRadians) * s.ls.light_magnitude;

                        s.ls.l_noise_reducing_factor = temp;

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();

                    if (greedy_algorithm && enable_light.isSelected() && !julia_map && !s.d3s.d3 && !s.ds.domain_coloring) {
                        JOptionPane.showMessageDialog(ptra, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }

                    if (!s.fns.smoothing && s.ls.lighting && !s.ds.domain_coloring) {
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
