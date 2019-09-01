/*
 * Copyright (C) 2019 hrkalona2
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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author hrkalona2
 */
public class UserFormulaNovaDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public UserFormulaNovaDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType) {

        super();

        ptra = ptr;

        setTitle("User Formula Nova");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        String f1 = s.fns.user_fz_formula;
        String f2 = s.fns.user_dfz_formula;
        String f3 = s.fns.user_ddfz_formula;

        if (s.fns.function != oldSelected) {
            f1 = "z^3 - 1";
            f2 = "3*z^2";
            f3 = "6*z";
        }

        JTextField field_fz_formula9 = new JTextField(50);
        field_fz_formula9.setText(f1);

        JTextField field_dfz_formula9 = new JTextField(50);
        field_dfz_formula9.setText(f2);

        JTextField field_ddfz_formula9 = new JTextField(50);
        field_ddfz_formula9.setText(f3);

        JPanel formula_fz_panel9 = new JPanel();

        formula_fz_panel9.add(new JLabel("f(z) ="));
        formula_fz_panel9.add(field_fz_formula9);

        JPanel formula_dfz_panel9 = new JPanel();

        formula_dfz_panel9.add(new JLabel("f '(z) ="));
        formula_dfz_panel9.add(field_dfz_formula9);

        JPanel formula_ddfz_panel9 = new JPanel();

        formula_ddfz_panel9.add(new JLabel("f ''(z) ="));
        formula_ddfz_panel9.add(field_ddfz_formula9);

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

        String[] method = {"Newton", "Halley", "Schroder", "Householder", "Secant", "Steffensen", "Muller", "Parhalley", "Laguerre", "Newton-Hines"};

        JComboBox method_choice = new JComboBox(method);
        method_choice.setSelectedIndex(s.fns.nova_method);
        method_choice.setToolTipText("Selects the root finding method for the Nova function.");
        method_choice.setFocusable(false);
        
        JPanel derivativePanel = new JPanel();
        
        JComboBox derivative_choice = new JComboBox(Constants.derivativeMethod);
        derivative_choice.setSelectedIndex(s.fns.derivative_method);
        derivative_choice.setToolTipText("Selects the derivative method.");
        derivative_choice.setFocusable(false);
        
        derivativePanel.add(new JLabel("Derivative: "));
        derivativePanel.add(derivative_choice);
        
        derivativePanel.setVisible(s.fns.nova_method != MainWindow.NOVA_SECANT && s.fns.nova_method != MainWindow.NOVA_STEFFENSEN && s.fns.nova_method != MainWindow.NOVA_MULLER);
        
        formula_dfz_panel9.setVisible(s.fns.derivative_method == Derivative.DISABLED);
        formula_ddfz_panel9.setVisible(s.fns.derivative_method == Derivative.DISABLED);
        
        derivative_choice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (method_choice.getSelectedIndex() == MainWindow.NOVA_HALLEY
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_SCHRODER
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_HOUSEHOLDER
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_PARHALLEY
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE) {
                    formula_ddfz_panel9.setVisible(derivative_choice.getSelectedIndex() == Derivative.DISABLED);
                }                
                
                if (method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_HALLEY
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_SCHRODER
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_HOUSEHOLDER
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_PARHALLEY
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON_HINES) {
                    formula_dfz_panel9.setVisible(derivative_choice.getSelectedIndex() == Derivative.DISABLED);
                }
                  
                pack();
            }
            
        });

        method_choice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (method_choice.getSelectedIndex() != MainWindow.NOVA_LAGUERRE) {
                    degree_panel.setVisible(false);
                } else {
                    degree_panel.setVisible(true);
                }
                
                if (method_choice.getSelectedIndex() != MainWindow.NOVA_NEWTON_HINES) {
                    k_panel.setVisible(false);
                } else {
                    k_panel.setVisible(true);
                }

                if (!(method_choice.getSelectedIndex() == MainWindow.NOVA_HALLEY
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_SCHRODER
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_HOUSEHOLDER
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_PARHALLEY
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE)) {
                    formula_ddfz_panel9.setVisible(false);
                } else if(derivative_choice.getSelectedIndex() == Derivative.DISABLED) {
                    formula_ddfz_panel9.setVisible(true);
                }

                if (!(method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_HALLEY
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_SCHRODER
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_HOUSEHOLDER
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_PARHALLEY
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE
                        || method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON_HINES)) {
                    formula_dfz_panel9.setVisible(false);
                } else if(derivative_choice.getSelectedIndex() == Derivative.DISABLED) {
                    formula_dfz_panel9.setVisible(true);
                }
                
                derivativePanel.setVisible(method_choice.getSelectedIndex() != MainWindow.NOVA_SECANT && method_choice.getSelectedIndex() != MainWindow.NOVA_STEFFENSEN && method_choice.getSelectedIndex() != MainWindow.NOVA_MULLER);

                pack();
            }

        });    
        
        Object[] labels3 = ptr.createUserFormulaLabels("z, c, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

        if (method_choice.getSelectedIndex() != MainWindow.NOVA_LAGUERRE) {
            degree_panel.setVisible(false);
        }
        
        if (method_choice.getSelectedIndex() != MainWindow.NOVA_NEWTON_HINES) {
            k_panel.setVisible(false);
        }

        if (!(method_choice.getSelectedIndex() == MainWindow.NOVA_HALLEY
                || method_choice.getSelectedIndex() == MainWindow.NOVA_SCHRODER
                || method_choice.getSelectedIndex() == MainWindow.NOVA_HOUSEHOLDER
                || method_choice.getSelectedIndex() == MainWindow.NOVA_PARHALLEY
                || method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE)) {
            formula_ddfz_panel9.setVisible(false);
        }

        if (!(method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON
                || method_choice.getSelectedIndex() == MainWindow.NOVA_HALLEY
                || method_choice.getSelectedIndex() == MainWindow.NOVA_SCHRODER
                || method_choice.getSelectedIndex() == MainWindow.NOVA_HOUSEHOLDER
                || method_choice.getSelectedIndex() == MainWindow.NOVA_PARHALLEY
                || method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE
                || method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON_HINES)) {
            formula_dfz_panel9.setVisible(false);
        }

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
            p1,
            degree_panel,
            k_panel};

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
                        fractal_functions[oldSelected].setSelected(true);
                        s.fns.function = oldSelected;
                        dispose();
                        return;
                    }

                    try {
                        s.parser.parse(field_fz_formula9.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        boolean temp_bool = s.parser.foundC();

                        if (method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_HALLEY
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_SCHRODER
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_HOUSEHOLDER
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_PARHALLEY
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON_HINES) {
                            s.parser.parse(field_dfz_formula9.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            temp_bool = temp_bool | s.parser.foundC();
                        }

                        if (method_choice.getSelectedIndex() == MainWindow.NOVA_HALLEY
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_SCHRODER
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_HOUSEHOLDER
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_PARHALLEY
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE) {
                            s.parser.parse(field_ddfz_formula9.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r cannot be used in the f ''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            temp_bool = temp_bool | s.parser.foundC();
                        }
                        
                        s.parser.parse(field_relaxation.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r cannot be used in the relaxation formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();
                        
                        s.parser.parse(field_addend.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r cannot be used in the addend formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

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

                        if (method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_HALLEY
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_SCHRODER
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_HOUSEHOLDER
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_PARHALLEY
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_NEWTON_HINES) {
                            s.fns.user_dfz_formula = field_dfz_formula9.getText();
                        }

                        if (method_choice.getSelectedIndex() == MainWindow.NOVA_HALLEY
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_SCHRODER
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_HOUSEHOLDER
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_PARHALLEY
                                || method_choice.getSelectedIndex() == MainWindow.NOVA_LAGUERRE) {
                            s.fns.user_ddfz_formula = field_ddfz_formula9.getText();
                        }                     

                        s.fns.nova_method = method_choice.getSelectedIndex();
                        s.fns.user_fz_formula = field_fz_formula9.getText();
                        s.userFormulaHasC = temp_bool;
                        s.fns.user_relaxation_formula = field_relaxation.getText();
                        s.fns.user_nova_addend_formula = field_addend.getText();
                        
                        s.fns.derivative_method = derivative_choice.getSelectedIndex();
                        Derivative.DERIVATIVE_METHOD = s.fns.derivative_method;

                        ptra.setUserFormulaOptions(true);
                    } catch (ParserException ex) {
                        JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ptra.optionsEnableShortcut();
                    dispose();
                    ptra.setFunctionPost(wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
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
