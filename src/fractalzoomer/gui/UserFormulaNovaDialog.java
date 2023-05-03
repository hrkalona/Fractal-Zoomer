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

import fractalzoomer.core.Derivative;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.ParserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class UserFormulaNovaDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public UserFormulaNovaDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType, boolean wasMagnetPatakiType) {

        super(ptr);

        ptra = ptr;

        setTitle("User Formula Nova");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        String f1 = s.fns.user_fz_formula;
        String f2 = s.fns.user_dfz_formula;
        String f3 = s.fns.user_ddfz_formula;
        String f4 = s.fns.user_dddfz_formula;

        if (s.fns.function != oldSelected) {
            f1 = "z^3 - 1";
            f2 = "3*z^2";
            f3 = "6*z";
            f4 = "6";
        }

        JTextField field_fz_formula9 = new JTextField(50);
        field_fz_formula9.setText(f1);

        JTextField field_dfz_formula9 = new JTextField(50);
        field_dfz_formula9.setText(f2);

        JTextField field_ddfz_formula9 = new JTextField(50);
        field_ddfz_formula9.setText(f3);

        JTextField field_dddfz_formula9 = new JTextField(50);
        field_dddfz_formula9.setText(f4);

        JPanel formula_fz_panel9 = new JPanel();

        formula_fz_panel9.add(new JLabel("f(z) ="));
        formula_fz_panel9.add(field_fz_formula9);

        JPanel formula_dfz_panel9 = new JPanel();

        formula_dfz_panel9.add(new JLabel("f'(z) ="));
        formula_dfz_panel9.add(field_dfz_formula9);

        JPanel formula_ddfz_panel9 = new JPanel();

        formula_ddfz_panel9.add(new JLabel("f''(z) ="));
        formula_ddfz_panel9.add(field_ddfz_formula9);

        JPanel formula_dddfz_panel9 = new JPanel();

        formula_dddfz_panel9.add(new JLabel("f'''(z) ="));
        formula_dddfz_panel9.add(field_dddfz_formula9);

        JTextField field_relaxation = new JTextField(20);
        field_relaxation.setText(s.fns.user_relaxation_formula);
        JTextField field_addend = new JTextField(20);
        field_addend.setText(s.fns.user_nova_addend_formula);

        JPanel p1 = new JPanel();
        p1.add(new JLabel("Relaxation = "));
        p1.add(field_relaxation);
        p1.add(new JLabel(" Addend = "));
        p1.add(field_addend);
    
        JTextField field_real8 = new JTextField(20);
        field_real8.setText("" + s.fns.laguerre_deg[0]);

        JTextField field_imaginary8 = new JTextField(20);
        field_imaginary8.setText("" + s.fns.laguerre_deg[1]);

        JPanel degree_panel = new JPanel();
        degree_panel.add(new JLabel("Degree = "));
        degree_panel.add(new JLabel("Real:"));
        degree_panel.add(field_real8);
        degree_panel.add(new JLabel(" Imaginary:"));
        degree_panel.add(field_imaginary8);
        
        
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

        JLabel root = new JLabel("Root Finding Method:");
        root.setFont(new Font("Arial", Font.BOLD, 11));

        JComboBox<String> method_choice = new JComboBox<>(Constants.novaMethods);
        method_choice.setSelectedIndex(s.fns.nova_method);
        method_choice.setToolTipText("Selects the root finding method for the Nova function.");
        method_choice.setFocusable(false);
        
        JPanel derivativePanel = new JPanel();
        
        JComboBox<String> derivative_choice = new JComboBox<>(Constants.derivativeMethod);
        derivative_choice.setSelectedIndex(s.fns.derivative_method);
        derivative_choice.setToolTipText("Selects the derivative method.");
        derivative_choice.setFocusable(false);
        
        derivativePanel.add(new JLabel("Derivative: "));
        derivativePanel.add(derivative_choice);
        
        derivativePanel.setVisible(!Settings.isOneFunctionsNovaFormula(s.fns.nova_method));
        
        formula_dfz_panel9.setVisible(s.fns.derivative_method == Derivative.DISABLED);
        formula_ddfz_panel9.setVisible(s.fns.derivative_method == Derivative.DISABLED);
        formula_dddfz_panel9.setVisible(s.fns.derivative_method == Derivative.DISABLED);
        
        derivative_choice.addActionListener(e -> {

            if (Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex())) {
                formula_dddfz_panel9.setVisible(derivative_choice.getSelectedIndex() == Derivative.DISABLED);
            }

            if (Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isThreeFunctionsNovaFormula(method_choice.getSelectedIndex())) {
                formula_ddfz_panel9.setVisible(derivative_choice.getSelectedIndex() == Derivative.DISABLED);
            }

            if (Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isThreeFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isTwoFunctionsNovaFormula(method_choice.getSelectedIndex())) {
                formula_dfz_panel9.setVisible(derivative_choice.getSelectedIndex() == Derivative.DISABLED);
            }

            pack();
        });

        method_choice.addActionListener(e -> {
            degree_panel.setVisible(method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE);
            k_panel.setVisible(method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON_HINES);


            if (!(Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()))) {
                formula_dddfz_panel9.setVisible(false);
            } else if(derivative_choice.getSelectedIndex() == Derivative.DISABLED) {
                formula_dddfz_panel9.setVisible(true);
            }

            if (!(Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isThreeFunctionsNovaFormula(method_choice.getSelectedIndex()))) {
                formula_ddfz_panel9.setVisible(false);
            } else if(derivative_choice.getSelectedIndex() == Derivative.DISABLED) {
                formula_ddfz_panel9.setVisible(true);
            }

            if (!(Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isThreeFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isTwoFunctionsNovaFormula(method_choice.getSelectedIndex()))) {
                formula_dfz_panel9.setVisible(false);
            } else if(derivative_choice.getSelectedIndex() == Derivative.DISABLED) {
                formula_dfz_panel9.setVisible(true);
            }

            derivativePanel.setVisible(!Settings.isOneFunctionsNovaFormula(method_choice.getSelectedIndex()));

            pack();
        });

        JPanel defaultInitPanel = new JPanel();

        JCheckBox defaultInit = new JCheckBox("Default Nova Initial Value");
        defaultInit.setSelected(s.fns.defaultNovaInitialValue);
        defaultInit.setToolTipText("Uses 1 + 0i for Nova initialization.");
        defaultInit.setFocusable(false);

        defaultInitPanel.add(defaultInit);
        
        Object[] labels3 = ptr.createUserFormulaLabels("z, c, s, c0, pixel, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

        if (method_choice.getSelectedIndex() != MainWindow.NOVA_LAGUERRE) {
            degree_panel.setVisible(false);
        }
        
        if (method_choice.getSelectedIndex() != MainWindow.NOVA_NEWTON_HINES) {
            k_panel.setVisible(false);
        }

        if (!(Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()))) {
            formula_dddfz_panel9.setVisible(false);
        }

        if (!(Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isThreeFunctionsNovaFormula(method_choice.getSelectedIndex()))) {
            formula_ddfz_panel9.setVisible(false);
        }

        if (!(Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isThreeFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isTwoFunctionsNovaFormula(method_choice.getSelectedIndex()))) {
            formula_dfz_panel9.setVisible(false);
        }

        JPanel globalMethodPanel = new JPanel();

        JCheckBox globalMethod = new JCheckBox("Global Method");
        globalMethod.setSelected(s.fns.useGlobalMethod);
        globalMethod.setToolTipText("Changes the relaxation value automatically to increase convergence.");
        globalMethod.setFocusable(false);

        JTextField global_real = new JTextField(10);
        global_real.setText("" + s.fns.globalMethodFactor[0]);

        JTextField global_imaginary = new JTextField(10);
        global_imaginary.setText("" + s.fns.globalMethodFactor[1]);

        globalMethodPanel.add(globalMethod);
        globalMethodPanel.add(new JLabel(" Factor = "));
        globalMethodPanel.add(new JLabel("Real:"));
        globalMethodPanel.add(global_real);
        globalMethodPanel.add(new JLabel(" Imaginary:"));
        globalMethodPanel.add(global_imaginary);

        global_real.setEnabled(globalMethod.isSelected());
        global_imaginary.setEnabled(globalMethod.isSelected());

        globalMethod.addActionListener(e -> {
            global_real.setEnabled(globalMethod.isSelected());
            global_imaginary.setEnabled(globalMethod.isSelected());
        });

        Object[] message3 = {
            labels3,
            root,
            method_choice,
            " ",
            derivativePanel,
            "Insert the function, and its derivatives (if required).",
            formula_fz_panel9,
            formula_dfz_panel9,
            formula_ddfz_panel9,
            formula_dddfz_panel9,
            p1,
                globalMethodPanel,
            defaultInitPanel,
            degree_panel,
            k_panel};

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
                            fractal_functions[oldSelected].setSelected(true);
                            s.fns.function = oldSelected;
                            dispose();
                            return;
                        }

                        try {
                            s.parser.parse(field_fz_formula9.getText());
                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            boolean temp_bool = s.parser.foundC();

                            if (Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isThreeFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isTwoFunctionsNovaFormula(method_choice.getSelectedIndex())) {
                                s.parser.parse(field_dfz_formula9.getText());

                                if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the f'(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                temp_bool = temp_bool || s.parser.foundC();
                            }

                            if (Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isThreeFunctionsNovaFormula(method_choice.getSelectedIndex())) {
                                s.parser.parse(field_ddfz_formula9.getText());

                                if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the f''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                temp_bool = temp_bool || s.parser.foundC();
                            }

                            if (Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex())) {
                                s.parser.parse(field_dddfz_formula9.getText());

                                if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the f'''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                temp_bool = temp_bool || s.parser.foundC();
                            }

                            s.parser.parse(field_relaxation.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the relaxation formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            temp_bool = temp_bool || s.parser.foundC();

                            s.parser.parse(field_addend.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the addend formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            temp_bool = temp_bool || s.parser.foundC();

                            double temp5 = 0, temp6 = 0;
                            if (method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE) {
                                temp5 = Double.parseDouble(field_real8.getText());
                                temp6 = Double.parseDouble(field_imaginary8.getText());
                            }

                            double temp7 = 0, temp8 = 0;
                            if (method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON_HINES) {
                                temp7 = Double.parseDouble(field_realk.getText());
                                temp8 = Double.parseDouble(field_imaginaryk.getText());
                            }

                            if (method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE) {
                                s.fns.laguerre_deg[0] = temp5;
                                s.fns.laguerre_deg[1] = temp6;
                            }

                            if (method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON_HINES) {
                                s.fns.newton_hines_k[0] = temp7;
                                s.fns.newton_hines_k[1] = temp8;
                            }

                            double temp9 = Double.parseDouble(global_real.getText());
                            double temp10 = Double.parseDouble(global_imaginary.getText());

                            if (Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isThreeFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isTwoFunctionsNovaFormula(method_choice.getSelectedIndex())) {
                                s.fns.user_dfz_formula = field_dfz_formula9.getText();
                            }

                            if (Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex()) || Settings.isThreeFunctionsNovaFormula(method_choice.getSelectedIndex())) {
                                s.fns.user_ddfz_formula = field_ddfz_formula9.getText();
                            }

                            if (Settings.isFourFunctionsNovaFormula(method_choice.getSelectedIndex())) {
                                s.fns.user_dddfz_formula = field_dddfz_formula9.getText();
                            }

                            s.fns.nova_method = method_choice.getSelectedIndex();
                            s.fns.user_fz_formula = field_fz_formula9.getText();
                            s.userFormulaHasC = temp_bool;
                            s.fns.user_relaxation_formula = field_relaxation.getText();
                            s.fns.user_nova_addend_formula = field_addend.getText();

                            s.fns.derivative_method = derivative_choice.getSelectedIndex();
                            Derivative.DERIVATIVE_METHOD = s.fns.derivative_method;

                            s.fns.defaultNovaInitialValue = defaultInit.isSelected();

                            s.fns.useGlobalMethod = globalMethod.isSelected();
                            s.fns.globalMethodFactor[0] = temp9;
                            s.fns.globalMethodFactor[1] = temp10;

                            ptra.setUserFormulaOptions(true);
                        } catch (ParserException ex) {
                            JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        ptra.optionsEnableShortcut();
                        dispose();
                        ptra.setFunctionPost(oldSelected, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
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
