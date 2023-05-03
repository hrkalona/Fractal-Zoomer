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

import fractalzoomer.core.ThreadDraw;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class PeriodDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public PeriodDialog(MainWindow ptr, Settings s) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Period");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField field = new JTextField();
        field.addAncestorListener(new RequestFocusListener());
        field.setText("" + s.fns.period);

        JTextField field2 = new JTextField(10);
        field2.setText("" + s.max_iterations);


        Object[] message3 = {
            " ",
             "Period is only meant to be used along with Perturbation Theory\nand Bilinear Approximation or Nanomb1.",
                " ",
            "You are using " + s.fns.period + " as period.\nEnter the new period number.",
            field,
                " ",
                "You are using maximum " + s.max_iterations + " iterations.\nEnter the new maximum iterations number.",
                field2,
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
                            int temp = Integer.parseInt(field.getText());
                            int temp2 = Integer.parseInt(field2.getText());

                            if (temp < 0) {
                                JOptionPane.showMessageDialog(ptra, "Period number must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            } else if (temp >  MainWindow.MAX_ITERATIONS_NUMBER) {
                                JOptionPane.showMessageDialog(ptra, "Period number must be less than 2147483648.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 < 1) {
                                JOptionPane.showMessageDialog(ptra, "Maximum iterations number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            } else if (temp2 >  MainWindow.MAX_ITERATIONS_NUMBER) {
                                JOptionPane.showMessageDialog(ptra, "Maximum iterations number must be less than 2147483648.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.max_iterations = temp2;
                            
                            if(s.fns.period != temp && s.supportsPeriod() && (ThreadDraw.APPROXIMATION_ALGORITHM == 3 || (ThreadDraw.DETECT_PERIOD && ThreadDraw.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD))) {
                                Fractal.clearReferences(true);
                            }

                            s.fns.period = temp;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptr.setIterationsPost();
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
