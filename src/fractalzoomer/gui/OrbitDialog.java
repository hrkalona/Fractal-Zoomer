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
import fractalzoomer.utils.MathUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author hrkalona2
 */
public class OrbitDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public OrbitDialog(MainWindow ptr, Settings s, double[] orbit_vals) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Orbit");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        final JTextField field_real = new JTextField();

        if (orbit_vals[0] == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + orbit_vals[0]);
        }

        final JTextField field_imaginary = new JTextField();

        if (orbit_vals[1] == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + orbit_vals[1]);
        }

        final JCheckBox current_center = new JCheckBox("Current Center");
        current_center.setSelected(false);
        current_center.setFocusable(false);

        current_center.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!current_center.isSelected()) {
                    if (orbit_vals[0] == 0) {
                        field_real.setText("" + 0.0);
                    } else {
                        field_real.setText("" + orbit_vals[0]);
                    }

                    field_real.setEditable(true);

                    if (orbit_vals[1] == 0) {
                        field_imaginary.setText("" + 0.0);
                    } else {
                        field_imaginary.setText("" + orbit_vals[1]);
                    }
                    field_imaginary.setEditable(true);
                } else {

                    Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.fns.rotation_vals, s.fns.rotation_center);

                    field_real.setText("" + p.x);
                    field_real.setEditable(false);
                    field_imaginary.setText("" + p.y);
                    field_imaginary.setEditable(false);
                }
            }
        });

        JTextField sequence_points = new JTextField();
        sequence_points.setText("" + (s.max_iterations > 400 ? 400 : s.max_iterations));

        Object[] message = {
            " ",
            "Set the real and imaginary part of the new orbit.",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            current_center,
            " ",
            "Set the number of sequence points.",
            "Sequence Points:", sequence_points,
            " ",};

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
                    
                    double x_real = 0, y_imag = 0;
                    int seq_points = 0;
                    try {
                        double tempReal = Double.parseDouble(field_real.getText());
                        double tempImaginary = Double.parseDouble(field_imaginary.getText());
                        seq_points = Integer.parseInt(sequence_points.getText());

                        if (seq_points <= 0) {
                            JOptionPane.showMessageDialog(ptra, "Sequence points number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        orbit_vals[0] = tempReal;
                        orbit_vals[1] = tempImaginary;

                        tempReal -= s.fns.rotation_center[0];
                        tempImaginary -= s.fns.rotation_center[1];

                        /* Inversed Rotation */
                        x_real = tempReal * s.fns.rotation_vals[0] + tempImaginary * s.fns.rotation_vals[1] + s.fns.rotation_center[0];
                        y_imag = -tempReal * s.fns.rotation_vals[1] + tempImaginary * s.fns.rotation_vals[0] + s.fns.rotation_center[1];
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                    ptra.setOrbitPointPost(x_real, y_imag, seq_points);
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
