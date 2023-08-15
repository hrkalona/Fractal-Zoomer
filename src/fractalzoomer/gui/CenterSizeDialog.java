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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class CenterSizeDialog extends JDialog {
    public static final JTextField TEMPLATE_TFIELD = new JTextField();

    private MainWindow ptra;
    private JOptionPane optionPane;

    public static void disableKeys(InputMap inputMap) {
        String[] keys = {"ENTER"};
        for (String key : keys) {
            inputMap.put(KeyStroke.getKeyStroke(key), "none");
        }
    }

    public CenterSizeDialog(MainWindow ptr, Settings s) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Center and Size");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextArea field_real = new JTextArea(6, 50);
        field_real.setLineWrap(true);
        field_real.setFont(TEMPLATE_TFIELD.getFont());

        JScrollPane scrollReal = new JScrollPane (field_real,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        disableKeys(field_real.getInputMap());
        disableKeys(scrollReal.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        BigPoint p = MathUtils.rotatePointRelativeToPoint(new BigPoint(s.xCenter, s.yCenter), s.fns.rotation_vals, s.fns.rotation_center);

        if (p.x.compareTo(MyApfloat.ZERO) == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + p.x.toString(true));
        }

        JTextArea field_imaginary = new JTextArea(6, 50);
        field_imaginary.setLineWrap(true);
        field_imaginary.setFont(TEMPLATE_TFIELD.getFont());

        JScrollPane scrollImaginary = new JScrollPane (field_imaginary,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        disableKeys(field_imaginary.getInputMap());
        disableKeys(scrollImaginary.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));


        if (p.y.compareTo(MyApfloat.ZERO) == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + p.y.toString(true));
        }

        JTextArea field_size = new JTextArea(6, 50);
        field_size.setLineWrap(true);
        field_size.setFont(TEMPLATE_TFIELD.getFont());

        JScrollPane scrollSize = new JScrollPane (field_size,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        disableKeys(field_size.getInputMap());
        disableKeys(scrollSize.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));


        field_size.setText("" + s.size);

        JButton corners = new MyButton("Set Corners");
        corners.setToolTipText("An alternative center/size selection option.");
        corners.setFocusable(false);
        corners.setIcon(MainWindow.getIcon("corners.png"));

        JButton magnification = new MyButton("Set Magnification");
        magnification.setToolTipText("An alternative size option.");
        magnification.setFocusable(false);
        magnification.setIcon(MainWindow.getIcon("magnification.png"));

        JPanel cornersPanel = new JPanel();
        cornersPanel.add(corners);
        cornersPanel.add(magnification);

        corners.addActionListener(e -> new CornersDialog(ptr, s, field_real, field_imaginary, field_size));

        magnification.addActionListener(e -> new MagnificationDialog(ptr, s, field_size));

        SwingUtilities.invokeLater(() -> {
            scrollSize.getVerticalScrollBar().setValue(0);
            scrollReal.getVerticalScrollBar().setValue(0);
            scrollImaginary.getVerticalScrollBar().setValue(0);

        });

        Object[] message = {
            " ",
            "Set the real and imaginary part of the new center",
            "and the new size.",
            "Real:", scrollReal,
            "Imaginary:", scrollImaginary,
            "Size:", scrollSize,
            " ",
            cornersPanel,
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
                                long precision = MyApfloat.getAutomaticPrecision(new String[]{field_size.getText(), field_real.getText(), field_imaginary.getText()}, new boolean[] {true, false, false});

                                if (MyApfloat.shouldSetPrecision(precision, MyApfloat.alwaysCheckForDecrease)) {
                                    Fractal.clearReferences(true);
                                    MyApfloat.setPrecision(precision, s);
                                }
                            }

                            Apfloat tempReal = MyApfloat.fp.subtract(new MyApfloat(field_real.getText()), s.fns.rotation_center[0]);
                            Apfloat tempImaginary = MyApfloat.fp.subtract(new MyApfloat(field_imaginary.getText()), s.fns.rotation_center[1]);
                            Apfloat tempSize = new MyApfloat(field_size.getText());

                            if (tempSize.compareTo(MyApfloat.ZERO) <= 0) {
                                JOptionPane.showMessageDialog(ptra, "Size number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.size = tempSize;
                            /* Inverse rotation */
                            s.xCenter = MyApfloat.fp.add(MyApfloat.fp.add(MyApfloat.fp.multiply(tempReal, s.fns.rotation_vals[0]), MyApfloat.fp.multiply(tempImaginary, s.fns.rotation_vals[1])), s.fns.rotation_center[0]);
                            s.yCenter = MyApfloat.fp.add(MyApfloat.fp.add(MyApfloat.fp.multiply(tempReal.negate(), s.fns.rotation_vals[1]), MyApfloat.fp.multiply(tempImaginary, s.fns.rotation_vals[0])), s.fns.rotation_center[1]);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setCenterSizePost();
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
