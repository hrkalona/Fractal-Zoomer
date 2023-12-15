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

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class SkipBailoutDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public SkipBailoutDialog(MainWindow ptr, Settings s, boolean mode) {
        
        super(ptr);

        ptra = ptr;

        if(mode) {
            setTitle("Skip Bailout Condition Iterations");
        }
        else {
            setTitle("Skip Convergent Bailout Condition Iterations");
        }
        setModal(true);
        setIconImage(MainWindow.getIcon("skip_bailout.png").getImage());

        JTextField field = new JTextField();
        field.setText("" + (mode ? s.fns.skip_bailout_iterations : s.fns.skip_convergent_bailout_iterations));
        field.addAncestorListener(new RequestFocusListener());

        String message = mode ? ("You are using " + s.fns.skip_bailout_iterations + " skip bailout condition iterations.\nInsert the new skip bailout condition iterations number." ) :
                ("You are using " + s.fns.skip_convergent_bailout_iterations + " skip convergent bailout condition iterations.\nInsert the new skip convergent bailout condition iterations number." );

        Object[] message3 = {
            " ",
                message,
            field,
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

                            if (temp < 0) {
                                if(mode) {
                                    JOptionPane.showMessageDialog(ptra, "Skip bailout condition iterations number must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                                else {
                                    JOptionPane.showMessageDialog(ptra, "Skip convergent bailout condition iterations number must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                                return;
                            }

                            if(mode) {
                                s.fns.skip_bailout_iterations = temp;
                            }
                            else {
                                s.fns.skip_convergent_bailout_iterations = temp;
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptr.setSkipBailoutIterationsPost();
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
