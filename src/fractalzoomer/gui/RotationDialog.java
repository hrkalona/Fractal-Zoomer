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

import fractalzoomer.core.BigPoint;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.utils.MathUtils;
import org.apfloat.Apfloat;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.gui.CenterSizeDialog.TEMPLATE_TFIELD;

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
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

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

        rotation_slid.addChangeListener(e -> field_rotation.setText("" + ((double) rotation_slid.getValue())));

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

        JTextArea field_real = new JTextArea(6, 50);
        field_real.setFont(TEMPLATE_TFIELD.getFont());
        field_real.setLineWrap(true);

        JScrollPane scrollReal = new JScrollPane (field_real,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(field_real.getInputMap());
        CenterSizeDialog.disableKeys(scrollReal.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));


        if (s.fns.rotation_center[0].compareTo(MyApfloat.ZERO) == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + s.fns.rotation_center[0].toString(true));
        }

        JTextArea field_imaginary = new JTextArea(6, 50);
        field_imaginary.setFont(TEMPLATE_TFIELD.getFont());
        field_imaginary.setLineWrap(true);

        JScrollPane scrollImaginary = new JScrollPane (field_imaginary,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(field_imaginary.getInputMap());
        CenterSizeDialog.disableKeys(scrollImaginary.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        if (s.fns.rotation_center[1].compareTo(MyApfloat.ZERO) == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + s.fns.rotation_center[1].toString(true));
        }

        final JCheckBox current_center = new JCheckBox("Current Center");
        current_center.setSelected(false);
        current_center.setFocusable(false);

        current_center.addActionListener(e -> {


            if (!current_center.isSelected()) {
                if (s.fns.rotation_center[0].compareTo(MyApfloat.ZERO) == 0) {
                    field_real.setText("" + 0.0);
                } else {
                    field_real.setText("" + s.fns.rotation_center[0].toString(true));
                }

                field_real.setEnabled(true);

                if (s.fns.rotation_center[1].compareTo(MyApfloat.ZERO) == 0) {
                    field_imaginary.setText("" + 0.0);
                } else {
                    field_imaginary.setText("" + s.fns.rotation_center[1].toString(true));
                }
                field_imaginary.setEnabled(true);
            } else {
                BigPoint p = MathUtils.rotatePointRelativeToPoint(new BigPoint(s.xCenter, s.yCenter), s.fns.rotation_vals, s.fns.rotation_center);

                field_real.setText("" + p.x.toString(true));
                field_real.setEnabled(false);
                field_imaginary.setText("" + p.y.toString(true));
                field_imaginary.setEnabled(false);
            }
        });

        SwingUtilities.invokeLater(() -> {
            scrollReal.getVerticalScrollBar().setValue(0);
            scrollImaginary.getVerticalScrollBar().setValue(0);

        });

        JButton resetValues = new JButton("Reset");
        resetValues.setFocusable(false);
        resetValues.setIcon(MainWindow.getIcon("reset_small.png"));

        JPanel resetPanel = new JPanel();
        resetPanel.add(resetValues);

        resetValues.addActionListener(e -> {
            Settings defaultSettings = new Settings();

            field_rotation.setText("" + defaultSettings.fns.rotation);

            rotation_slid.setValue((int)defaultSettings.fns.rotation);

            if (defaultSettings.fns.rotation_center[0].compareTo(MyApfloat.ZERO) == 0) {
                field_real.setText("" + 0.0);
            } else {
                field_real.setText("" + defaultSettings.fns.rotation_center[0].toString(true));
            }

            if (defaultSettings.fns.rotation_center[1].compareTo(MyApfloat.ZERO) == 0) {
                field_imaginary.setText("" + 0.0);
            } else {
                field_imaginary.setText("" + defaultSettings.fns.rotation_center[1].toString(true));
            }

            current_center.setSelected(false);

            field_imaginary.setEnabled(true);
            field_real.setEnabled(true);
        });


        Object[] message = {
            " ",
            "Set the rotation angle in degrees.",
            "Rotation:", rotation_slid, field_rotation,
            " ",
            "Set the rotation center.",
            "Real:", scrollReal,
            "Imaginary:", scrollImaginary,
            current_center, " ",
                resetPanel,
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
                            if(MyApfloat.setAutomaticPrecision) {
                                long precision = MyApfloat.getAutomaticPrecision(new String[]{field_real.getText(), field_imaginary.getText()}, new boolean[] {false, false});

                                if (MyApfloat.shouldSetPrecision(precision, false)) {
                                    Fractal.clearReferences(true);
                                    MyApfloat.setPrecision(precision, s);
                                }
                            }

                            double temp = Double.parseDouble(field_rotation.getText());
                            Apfloat tempReal = new MyApfloat(field_real.getText());
                            Apfloat tempImaginary = new MyApfloat(field_imaginary.getText());

                            if (temp < -360) {
                                JOptionPane.showMessageDialog(ptra, "Rotation angle must be greater than -361.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            } else if (temp > 360) {
                                JOptionPane.showMessageDialog(ptra, "Rotation angle must be less than 361.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.fns.rotation = temp;

                            Apfloat tempRadians =  MyApfloat.fp.toRadians(new MyApfloat(s.fns.rotation));
                            s.fns.rotation_vals[0] = MyApfloat.cos(tempRadians);
                            s.fns.rotation_vals[1] = MyApfloat.sin(tempRadians);

                            s.fns.rotation_center[0] = tempReal;
                            s.fns.rotation_center[1] = tempImaginary;

                            Apfloat zero = MyApfloat.ZERO;

                            s.fns.rotation_center[0] = s.fns.rotation_center[0].compareTo(zero) == 0 ? zero : s.fns.rotation_center[0];
                            s.fns.rotation_center[1] = s.fns.rotation_center[1].compareTo(zero) == 0? zero : s.fns.rotation_center[1];

                            if(s.fns.rotation != 0 && s.fns.rotation != 360.0 && s.fns.rotation != -360.0) {
                                s.xCenter = s.fns.rotation_center[0];
                                s.yCenter = s.fns.rotation_center[1];
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setRotationPost();
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
