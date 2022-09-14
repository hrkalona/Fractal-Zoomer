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

import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
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
public class NovaDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public NovaDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Nova (Method, Exponent, Relaxation)");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JLabel novazw = new JLabel();
        novazw.setIcon(getIcon("/fractalzoomer/icons/novazw.png"));

        JTextField field_real = new JTextField();
        field_real.setText("" + s.fns.z_exponent_nova[0]);

        JTextField field_imaginary = new JTextField();
        field_imaginary.setText("" + s.fns.z_exponent_nova[1]);

        JTextField field_real2 = new JTextField();
        field_real2.setText("" + s.fns.relaxation[0]);

        JTextField field_imaginary2 = new JTextField();
        field_imaginary2.setText("" + s.fns.relaxation[1]);
        
        JTextField field_realk = new JTextField(20);
        field_realk.setText("" + s.fns.newton_hines_k[0]);

        JTextField field_imaginaryk = new JTextField(20);
        field_imaginaryk.setText("" + s.fns.newton_hines_k[1]);

        JPanel k_panel = new JPanel();
        k_panel.add(new JLabel("k = "));
        k_panel.add(new JLabel("Real:"));
        k_panel.add(field_realk);
        k_panel.add(new JLabel(" Imaginary:"));
        k_panel.add(field_imaginaryk);

        JComboBox method_choice = new JComboBox(Constants.novaMethods);
        method_choice.setSelectedIndex(s.fns.nova_method);
        method_choice.setToolTipText("Selects the root finding method for the Nova function.");
        method_choice.setFocusable(false);

        JCheckBox defaultInit = new JCheckBox("Default Nova Initial Value");
        defaultInit.setSelected(s.fns.defaultNovaInitialValue);
        defaultInit.setToolTipText("Uses 1 + 0i for Nova initialization.");
        defaultInit.setFocusable(false);
        
        method_choice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (method_choice.getSelectedIndex() != MainWindow.NOVA_NEWTON_HINES) {
                    k_panel.setVisible(false);
                } else {
                    k_panel.setVisible(true);
                }
                
                pack();
            }
        });
        
        if (method_choice.getSelectedIndex() != MainWindow.NOVA_NEWTON_HINES) {
            k_panel.setVisible(false);
        }

        Object[] message2 = {
            " ",
            "Root Finding Method",
            method_choice,
            " ",
            novazw,
            "Set the real and imaginary part of the exponent.",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            " ",
            "Set the real and imaginary part of the relaxation.",
            "Real:", field_real2,
            "Imaginary:", field_imaginary2,
            " ",
                defaultInit,
                " ",
             k_panel};

        optionPane = new JOptionPane(message2, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        fractal_functions[oldSelected].setSelected(true);
                        s.fns.function = oldSelected;
                        dispose();
                        return;
                    }

                    try {
                        int temp7 = method_choice.getSelectedIndex();
                        double temp3 = Double.parseDouble(field_real.getText());
                        double temp4 = Double.parseDouble(field_imaginary.getText());
                        double temp5 = Double.parseDouble(field_real2.getText());
                        double temp6 = Double.parseDouble(field_imaginary2.getText());
                        
                        double temp8 = 0, temp9 = 0;
                        if (method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON_HINES) {
                            temp8 = Double.parseDouble(field_realk.getText());
                            temp9 = Double.parseDouble(field_imaginaryk.getText());
                        }
                        
                        if (method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON_HINES) {
                            s.fns.newton_hines_k[0] = temp8;
                            s.fns.newton_hines_k[1] = temp9;
                        }

                        s.fns.z_exponent_nova[0] = temp3 == 0.0 ? 0.0 : temp3;
                        s.fns.z_exponent_nova[1] = temp4 == 0.0 ? 0.0 : temp4;

                        s.fns.relaxation[0] = temp5 == 0.0 ? 0.0 : temp5;
                        s.fns.relaxation[1] = temp6 == 0.0 ? 0.0 : temp6;

                        s.fns.nova_method = temp7;

                        s.fns.defaultNovaInitialValue = defaultInit.isSelected();
                        
                        ptr.setFunctionPostNova();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ptra.optionsEnableShortcut();
                    dispose();
                    ptra.setFunctionPost(wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType);
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
