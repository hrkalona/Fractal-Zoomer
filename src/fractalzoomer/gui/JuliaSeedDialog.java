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

import static fractalzoomer.gui.CenterSizeDialog.TEMPLATE_TFIELD;

/**
 *
 * @author hrkalona2
 */
public class JuliaSeedDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public JuliaSeedDialog(MainWindow ptr, Settings s) {
        
        super(ptr);

        ptra = ptr;

        if(s.fns.juliter) {
            setTitle("Julia Seed and Juliter");
        }
        else {
            setTitle("Julia Seed");
        }

        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());


        BigPoint p = MathUtils.rotatePointRelativeToPoint(new BigPoint(s.xCenter, s.yCenter), s.fns.rotation_vals, s.fns.rotation_center);

        JTextArea real_seed = new JTextArea(6, 50);
        real_seed.setFont(TEMPLATE_TFIELD.getFont());
        real_seed.setLineWrap(true);

        JScrollPane scrollRealSeed = new JScrollPane (real_seed,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(real_seed.getInputMap());
        CenterSizeDialog.disableKeys(real_seed.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        if (p.x.compareTo(MyApfloat.ZERO) == 0) {
            real_seed.setText("" + 0.0);
        } else {
            real_seed.setText("" + p.x.toString(true));
        }

        JTextArea imag_seed = new JTextArea(6, 50);
        imag_seed.setFont(TEMPLATE_TFIELD.getFont());
        imag_seed.setLineWrap(true);

        JScrollPane scrollImaginarySeed = new JScrollPane (imag_seed,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(imag_seed.getInputMap());
        CenterSizeDialog.disableKeys(imag_seed.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        if (p.y.compareTo(MyApfloat.ZERO) == 0) {
            imag_seed.setText("" + 0.0);
        } else {
            imag_seed.setText("" + p.y.toString(true));
        }

        Object[] message = null;

        JTextField juliterIterationsfield = new JTextField();
        juliterIterationsfield.addAncestorListener(new RequestFocusListener());
        juliterIterationsfield.setText("" + s.fns.juliterIterations);

        JCheckBox includePreStarting = new JCheckBox("Include pre-starting iterations");
        includePreStarting.setFocusable(false);
        includePreStarting.setSelected(s.fns.juliterIncludeInitialIterations);

        SwingUtilities.invokeLater(() -> {
            scrollRealSeed.getVerticalScrollBar().setValue(0);
            scrollImaginarySeed.getVerticalScrollBar().setValue(0);

        });

        if(s.fns.juliter) {
            Object[] temp = {
                    " ",
                    "Set the real and imaginary part of the Julia seed.",
                    "Real:", scrollRealSeed,
                    "Imaginary:", scrollImaginarySeed,
                    " ",
                    "Set the starting iterations of Juliter.",
                    "Starting Iterations:",
                    juliterIterationsfield,
                    " ",
                    includePreStarting,
                    " "};

            message = temp;
        }
        else {
            Object[] temp = {
                    " ",
                    "Set the real and imaginary part of the Julia seed.",
                    "Real:", scrollRealSeed,
                    "Imaginary:", scrollImaginarySeed,
                    " ",};

            message = temp;
        }


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
                                long precision = MyApfloat.getAutomaticPrecision(new String[]{real_seed.getText(), imag_seed.getText()}, new boolean[] {false, false}, false);

                                if (MyApfloat.shouldSetPrecision(precision, true)) {
                                    Fractal.clearReferences(true);
                                    MyApfloat.setPrecision(precision, s);
                                }
                            }

                            Apfloat tempReal = new MyApfloat(real_seed.getText());
                            Apfloat tempImaginary = new MyApfloat(imag_seed.getText());
                            s.xJuliaCenter = tempReal;
                            s.yJuliaCenter = tempImaginary;

                            if(s.fns.juliter) {
                                int temp = Integer.parseInt(juliterIterationsfield.getText());

                                if (temp < 0) {
                                    JOptionPane.showMessageDialog(ptra, "Juliter's starting iterations must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                s.fns.juliterIterations = temp;
                                s.fns.juliterIncludeInitialIterations = includePreStarting.isSelected();
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setJuliaSeedPost();
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
