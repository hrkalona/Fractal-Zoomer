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
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.utils.MathUtils;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static fractalzoomer.gui.CenterSizeDialog.TEMPLATE_TFIELD;

/**
 *
 * @author hrkalona2
 */
public class PolarProjectionDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public PolarProjectionDialog(MainWindow ptr, Settings s, JButton polar_projection_button, JCheckBoxMenuItem polar_projection_opt, boolean mode) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Polar Projection");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JTextArea field_real = new JTextArea(3, 50);
        field_real.setFont(TEMPLATE_TFIELD.getFont());
        field_real.setLineWrap(true);

        JScrollPane scrollReal = new JScrollPane (field_real,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(field_real.getInputMap());
        CenterSizeDialog.disableKeys(scrollReal.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        BigPoint p = MathUtils.rotatePointRelativeToPoint(new BigPoint(s.xCenter, s.yCenter), s.fns.rotation_vals, s.fns.rotation_center);

        Apfloat zero = new MyApfloat(0.0);

        if (p.x.compareTo(zero) == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + p.x.toString(true));
        }

        JTextArea field_imaginary = new JTextArea(3, 50);
        field_imaginary.setFont(TEMPLATE_TFIELD.getFont());
        field_imaginary.setLineWrap(true);

        JScrollPane scrollImaginary = new JScrollPane (field_imaginary,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(field_imaginary.getInputMap());
        CenterSizeDialog.disableKeys(scrollImaginary.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        if (p.y.compareTo(zero) == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + p.y.toString(true));
        }

        JTextArea field_size = new JTextArea(3, 50);
        field_size.setFont(TEMPLATE_TFIELD.getFont());
        field_size.setLineWrap(true);

        JScrollPane scrollSize = new JScrollPane (field_size,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(field_size.getInputMap());
        CenterSizeDialog.disableKeys(scrollSize.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        field_size.setText("" + s.size);

        JTextField field_circle_period = new JTextField();
        field_circle_period.setText("" + s.circle_period);

        JButton corners = new JButton("Set Corners");
        corners.setToolTipText("An alternative center/size selection option.");
        corners.setFocusable(false);
        corners.setIcon(getIcon("/fractalzoomer/icons/corners.png"));

        corners.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new CornersDialog(ptr, s, field_real, field_imaginary, field_size);

            }

        });

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                scrollSize.getVerticalScrollBar().setValue(0);
                scrollReal.getVerticalScrollBar().setValue(0);
                scrollImaginary.getVerticalScrollBar().setValue(0);

            }
        });

        Object[] message3 = {
            " ",
            "Set the real and imaginary part of the polar projection center",
            "and the size.",
            "Real:", scrollReal,
            "Imaginary:", scrollImaginary,
            "Size:", scrollSize,
            " ",
            corners,
            " ",
            "Set the circle periods number.",
            "Circle Periods:",
            field_circle_period,
            ""};

        optionPane = new JOptionPane(message3, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        if(mode) {
                            polar_projection_opt.setSelected(false);
                            polar_projection_button.setSelected(false);
                        }
                        dispose();
                        return;
                    }

                    try {
                        Apfloat tempReal = new MyApfloat(field_real.getText()).subtract(s.fns.rotation_center[0]);
                        Apfloat tempImaginary = new MyApfloat(field_imaginary.getText()).subtract(s.fns.rotation_center[1]);
                        Apfloat tempSize = new MyApfloat(field_size.getText());
                        double temp_circle_period = Double.parseDouble(field_circle_period.getText());

                        Apfloat zero = new MyApfloat(0.0);
                        if (tempSize.compareTo(zero) <= 0) {
                            JOptionPane.showMessageDialog(ptra, "Size number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (temp_circle_period <= 0) {
                            JOptionPane.showMessageDialog(ptra, "The circle periods must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);                        
                            return;
                        }

                        s.size = tempSize;

                        s.xCenter = tempReal.multiply(s.fns.rotation_vals[0]).add(tempImaginary.multiply(s.fns.rotation_vals[1])).add(s.fns.rotation_center[0]);
                        s.yCenter = tempReal.negate().multiply(s.fns.rotation_vals[1]).add(tempImaginary.multiply(s.fns.rotation_vals[0])).add(s.fns.rotation_center[1]);

                        s.circle_period = temp_circle_period;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                    if(mode) {
                        ptra.setPolarProjectionPost();
                    }
                    else {
                        ptra.setPolarProjectionOptionsPost();
                    }
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
