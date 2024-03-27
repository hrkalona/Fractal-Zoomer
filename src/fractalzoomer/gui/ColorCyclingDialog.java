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
import fractalzoomer.main.app_settings.ColorCyclingSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

/**
 *
 * @author hrkalona2
 */
public class ColorCyclingDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public ColorCyclingDialog(MainWindow ptr, ColorCyclingSettings ccs) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Color Cycling Options");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JSlider speed_slid = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1000 - ccs.color_cycling_speed);

        speed_slid.setPreferredSize(new Dimension(200, 35));

        speed_slid.setToolTipText("Sets the color cycling speed.");

        speed_slid.setPaintLabels(true);
        speed_slid.setFocusable(false);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();

        labelTable.put(speed_slid.getMinimum(), new JLabel("Slow"));
        labelTable.put(speed_slid.getMaximum(), new JLabel("Fast"));
        speed_slid.setLabelTable(labelTable);

        JTextField field_color_cycle_adjust = new JTextField();
        field_color_cycle_adjust.addAncestorListener(new RequestFocusListener());
        field_color_cycle_adjust.setText("" + ccs.color_cycling_adjusting_value);

        JTextField field_gradient_cycle_adjust = new JTextField();
        field_gradient_cycle_adjust.setText("" + ccs.gradient_cycling_adjusting_value);

        JTextField field_light_cycle_adjust = new JTextField();
        field_light_cycle_adjust.setText("" + ccs.light_cycling_adjusting_value);

        JTextField field_bump_cycle_adjust = new JTextField();
        field_bump_cycle_adjust.setText("" + ccs.bump_cycling_adjusting_value);

        JTextField field_slope_cycle_adjust = new JTextField();
        field_slope_cycle_adjust.setText("" + ccs.slope_cycling_adjusting_value);

        Object[] message3 = {
            " ",
            "Set the color cycling speed.",
                "Speed:",
            speed_slid,
                " ",
                "Set the cycling adjusting values.",
                "Color Cycling:",
                field_color_cycle_adjust,
                "Gradient Cycling:",
                field_gradient_cycle_adjust,
                "Light Cycling:",
                field_light_cycle_adjust,
                "Bump-Mapping Cycling:",
                field_bump_cycle_adjust,
                "Slope Cycling:",
                field_slope_cycle_adjust,
            " ",};

        optionPane = new JOptionPane(message3, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            int temp = Integer.parseInt(field_color_cycle_adjust.getText());
                            int temp2 = Integer.parseInt(field_gradient_cycle_adjust.getText());
                            int temp3 = Integer.parseInt(field_light_cycle_adjust.getText());
                            int temp4 = Integer.parseInt(field_bump_cycle_adjust.getText());
                            int temp5 = Integer.parseInt(field_slope_cycle_adjust.getText());

                            if(temp < -50 || temp > 50) {
                                JOptionPane.showMessageDialog(ptra, "The color cycling adjusting value must be in the range of [-50, 50].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp2 < -50 || temp2 > 50) {
                                JOptionPane.showMessageDialog(ptra, "The gradient cycling adjusting value must be in the range of [-50, 50].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp3 < -50 || temp3 > 50) {
                                JOptionPane.showMessageDialog(ptra, "The light cycling adjusting value must be in the range of [-50, 50].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp4 < -50 || temp4 > 50) {
                                JOptionPane.showMessageDialog(ptra, "The bump-mapping cycling adjusting value must be in the range of [-50, 50].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(temp5 < -50 || temp5 > 50) {
                                JOptionPane.showMessageDialog(ptra, "The slope cycling adjusting value must be in the range of [-50, 50].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            ccs.color_cycling_adjusting_value = temp;
                            ccs.gradient_cycling_adjusting_value = temp2;
                            ccs.light_cycling_adjusting_value = temp3;
                            ccs.bump_cycling_adjusting_value = temp4;
                            ccs.slope_cycling_adjusting_value = temp5;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
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
