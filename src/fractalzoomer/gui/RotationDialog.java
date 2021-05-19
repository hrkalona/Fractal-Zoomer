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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
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
public class RotationDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public RotationDialog(MainWindow ptr, Settings s) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Rotation");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        final JSlider rotation_slid = new JSlider(JSlider.HORIZONTAL, -360, 360, ((int) (s.fns.rotation)));
        rotation_slid.setPreferredSize(new Dimension(300, 35));
        rotation_slid.setMajorTickSpacing(90);
        rotation_slid.setMinorTickSpacing(1);
        rotation_slid.setToolTipText("Sets the rotation.");
        //color_blend.setPaintTicks(true);
        rotation_slid.setPaintLabels(true);
        //rotation_slid.setSnapToTicks(true);
        rotation_slid.setFocusable(false);

        final JTextField field_rotation = new JTextField();
        field_rotation.setText("" + s.fns.rotation);

        rotation_slid.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                field_rotation.setText("" + ((double) rotation_slid.getValue()));
            }

        });

        field_rotation.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    double temp = Double.parseDouble(field_rotation.getText());

                    rotation_slid.setValue(((int) (temp + 0.5)));
                } catch (Exception ex) {

                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    double temp = Double.parseDouble(field_rotation.getText());

                    rotation_slid.setValue(((int) (temp + 0.5)));
                } catch (Exception ex) {

                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    double temp = Double.parseDouble(field_rotation.getText());

                    rotation_slid.setValue(((int) (temp + 0.5)));
                } catch (Exception ex) {

                }
            }

        });

        final JTextField field_real = new JTextField();

        if (s.fns.rotation_center[0] == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + s.fns.rotation_center[0]);
        }

        final JTextField field_imaginary = new JTextField();

        if (s.fns.rotation_center[1] == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + s.fns.rotation_center[1]);
        }

        final JCheckBox current_center = new JCheckBox("Current Center");
        current_center.setSelected(false);
        current_center.setFocusable(false);

        current_center.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!current_center.isSelected()) {
                    if (s.fns.rotation_center[0] == 0) {
                        field_real.setText("" + 0.0);
                    } else {
                        field_real.setText("" + s.fns.rotation_center[0]);
                    }

                    field_real.setEditable(true);

                    if (s.fns.rotation_center[1] == 0) {
                        field_imaginary.setText("" + 0.0);
                    } else {
                        field_imaginary.setText("" + s.fns.rotation_center[1]);
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

        Object[] message = {
            " ",
            "Set the rotation angle in degrees.",
            "Rotation:", rotation_slid, field_rotation,
            " ",
            "Set the rotation center.",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            current_center, " "};

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
                        double temp = Double.parseDouble(field_rotation.getText());
                        double tempReal = Double.parseDouble(field_real.getText());
                        double tempImaginary = Double.parseDouble(field_imaginary.getText());

                        if (temp < -360) {            
                            JOptionPane.showMessageDialog(ptra, "Rotation angle must be greater than -361.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else if (temp > 360) {
                            JOptionPane.showMessageDialog(ptra, "Rotation angle must be less than 361.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.fns.rotation = temp;

                        s.fns.rotation_vals[0] = Math.cos(Math.toRadians(s.fns.rotation));
                        s.fns.rotation_vals[1] = Math.sin(Math.toRadians(s.fns.rotation));

                        s.fns.rotation_center[0] = tempReal;
                        s.fns.rotation_center[1] = tempImaginary;

                        s.fns.rotation_center[0] = s.fns.rotation_center[0] == 0 ? 0 : s.fns.rotation_center[0];
                        s.fns.rotation_center[1] = s.fns.rotation_center[1] == 0 ? 0 : s.fns.rotation_center[1];

                        s.xCenter = s.fns.rotation_center[0];
                        s.yCenter = s.fns.rotation_center[1];
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                    ptra.setRotationPost();
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
