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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class RootFindingThreeFunctionsDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public RootFindingThreeFunctionsDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType) {

        super(ptr);
        
        ptra = ptr;

        setTitle(FractalFunctionsMenu.functionNames[s.fns.function]);
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

        JTextField field_fz_formula2 = new JTextField(50);
        field_fz_formula2.setText(f1);

        JTextField field_dfz_formula2 = new JTextField(50);
        field_dfz_formula2.setText(f2);

        JTextField field_ddfz_formula2 = new JTextField(50);
        field_ddfz_formula2.setText(f3);

        JPanel formula_fz_panel2 = new JPanel();

        formula_fz_panel2.add(new JLabel("f(z) ="));
        formula_fz_panel2.add(field_fz_formula2);

        JPanel formula_dfz_panel2 = new JPanel();

        formula_dfz_panel2.add(new JLabel("f'(z) ="));
        formula_dfz_panel2.add(field_dfz_formula2);

        JPanel formula_ddfz_panel2 = new JPanel();

        formula_ddfz_panel2.add(new JLabel("f''(z) ="));
        formula_ddfz_panel2.add(field_ddfz_formula2);

        JLabel imagelabel41 = new JLabel();

        if (s.fns.function == HALLEYFORMULA) {
            imagelabel41.setIcon(getIcon("/fractalzoomer/icons/halley.png"));
        } else if (s.fns.function == SCHRODERFORMULA) {
            imagelabel41.setIcon(getIcon("/fractalzoomer/icons/schroder.png"));
        } else if (s.fns.function == HOUSEHOLDERFORMULA) {
            imagelabel41.setIcon(getIcon("/fractalzoomer/icons/householder.png"));
        } else if (s.fns.function == PARHALLEYFORMULA) {
            imagelabel41.setIcon(getIcon("/fractalzoomer/icons/parhalley.png"));
        }
        else if (s.fns.function == WHITTAKERFORMULA) {
            imagelabel41.setIcon(getIcon("/fractalzoomer/icons/whittaker.png"));
        }
        else if (s.fns.function == WHITTAKERDOUBLECONVEXFORMULA) {
            imagelabel41.setIcon(getIcon("/fractalzoomer/icons/whittaker_double_convex.png"));
        }
        else if (s.fns.function == SUPERHALLEYFORMULA) {
            imagelabel41.setIcon(getIcon("/fractalzoomer/icons/super_halley.png"));
        }

        JPanel imagepanel41 = new JPanel();
        imagepanel41.add(imagelabel41);
        
        JPanel derivativePanel = new JPanel();
        
        JComboBox derivative_choice = new JComboBox(Constants.derivativeMethod);
        derivative_choice.setSelectedIndex(s.fns.derivative_method);
        derivative_choice.setToolTipText("Selects the derivative method.");
        derivative_choice.setFocusable(false);
        
        derivativePanel.add(new JLabel("Derivative: "));
        derivativePanel.add(derivative_choice);
        
        formula_dfz_panel2.setVisible(s.fns.derivative_method == Derivative.DISABLED);
        formula_ddfz_panel2.setVisible(s.fns.derivative_method == Derivative.DISABLED);
        
        derivative_choice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formula_dfz_panel2.setVisible(derivative_choice.getSelectedIndex() == Derivative.DISABLED);
                formula_ddfz_panel2.setVisible(derivative_choice.getSelectedIndex() == Derivative.DISABLED);
                pack();
            }
            
        });

        Object[] labels41 = ptra.createUserFormulaLabels("z, s, pixel, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

        Object[] message41 = {
            labels41,
            " ",
            imagepanel41,
            " ",
            derivativePanel,
            "Insert the function and its derivatives (if required).",
            formula_fz_panel2,
            formula_dfz_panel2,
            formula_ddfz_panel2,};

        optionPane = new JOptionPane(message41, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        s.parser.parse(field_fz_formula2.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC() || s.parser.foundC0() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: c, c0, bail, cbail, r, stat, trap cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.parser.parse(field_dfz_formula2.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC() || s.parser.foundC0() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: c, c0, bail, cbail, r, stat, trap cannot be used in the f'(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.parser.parse(field_ddfz_formula2.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC() || s.parser.foundC0() ||  s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: c, c0, bail, cbail, r, stat, trap cannot be used in the f''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.fns.user_fz_formula = field_fz_formula2.getText();
                        s.fns.user_dfz_formula = field_dfz_formula2.getText();
                        s.fns.user_ddfz_formula = field_ddfz_formula2.getText();
                        
                        s.fns.derivative_method = derivative_choice.getSelectedIndex();
                        Derivative.DERIVATIVE_METHOD = s.fns.derivative_method;
                    } catch (ParserException ex) {
                        JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ptra.optionsEnableShortcut2();
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
