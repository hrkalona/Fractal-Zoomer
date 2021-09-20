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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author hrkalona2
 */
public class CenterSizeDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public CenterSizeDialog(MainWindow ptr, Settings s) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Center and Size");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JTextField field_real = new JTextField();

        BigPoint p = MathUtils.rotatePointRelativeToPoint(new BigPoint(s.xCenter, s.yCenter), s.fns.rotation_vals, s.fns.rotation_center);

        Apfloat zero = new MyApfloat(0.0);
        if (p.x.compareTo(zero) == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + p.x.toString(true));
        }

        JTextField field_imaginary = new JTextField();

        if (p.y.compareTo(zero) == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + p.y.toString(true));
        }

        JTextField field_size = new JTextField();
        field_size.setText("" + s.size);

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

        Object[] message = {
            " ",
            "Set the real and imaginary part of the new center",
            "and the new size.",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            "Size:", field_size,
            " ",
            corners,
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
                        Apfloat tempReal = new MyApfloat(field_real.getText()).subtract(s.fns.rotation_center[0]);
                        Apfloat tempImaginary = new MyApfloat(field_imaginary.getText()).subtract(s.fns.rotation_center[1]);
                        Apfloat tempSize = new MyApfloat(field_size.getText());

                        Apfloat zero = new MyApfloat(0.0);
                        if (tempSize.compareTo(zero) <= 0) {
                            JOptionPane.showMessageDialog(ptra, "Size number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.size = tempSize;
                        /* Inverse rotation */
                        s.xCenter = tempReal.multiply(s.fns.rotation_vals[0]).add(tempImaginary.multiply(s.fns.rotation_vals[1])).add(s.fns.rotation_center[0]);
                        s.yCenter = tempReal.negate().multiply(s.fns.rotation_vals[1]).add(tempImaginary.multiply(s.fns.rotation_vals[0])).add(s.fns.rotation_center[1]);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                    ptra.setCenterSizePost();
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
