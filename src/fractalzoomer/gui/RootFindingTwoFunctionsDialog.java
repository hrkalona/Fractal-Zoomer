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
import static fractalzoomer.main.Constants.NEWTONFORMULA;
import static fractalzoomer.main.Constants.NEWTON_HINESFORMULA;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.ParserException;
import java.awt.FlowLayout;
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
public class RootFindingTwoFunctionsDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public RootFindingTwoFunctionsDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType) {

        super(ptr);
        
        ptra = ptr;
        
        String title = "";

        if (s.fns.function == NEWTONFORMULA) {
            title = "Newton Formula";
        } else if (s.fns.function == NEWTON_HINESFORMULA) {
            title = "Newton-Hines Formula";
        }

        setTitle(title);
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        String f1 = s.fns.user_fz_formula;
        String f2 = s.fns.user_dfz_formula;
        
        if (s.fns.function != oldSelected) {
            f1 = "z^3 - 1";
            f2 = "3*z^2";
        }

        JTextField field_fz_formula = new JTextField(50);
        field_fz_formula.setText(f1);

        JTextField field_dfz_formula = new JTextField(50);
        field_dfz_formula.setText(f2);

        JPanel formula_fz_panel = new JPanel();

        formula_fz_panel.add(new JLabel("f(z) ="));
        formula_fz_panel.add(field_fz_formula);

        JPanel formula_dfz_panel = new JPanel();

        formula_dfz_panel.add(new JLabel("f '(z) ="));
        formula_dfz_panel.add(field_dfz_formula);
        
        JPanel k_panel = new JPanel();
        JTextField k_real = new JTextField(30);
        k_real.setText("" + s.fns.newton_hines_k[0]);
        JTextField k_imag = new JTextField(30);
        k_imag.setText("" + s.fns.newton_hines_k[1]);
        k_panel.setLayout(new FlowLayout());
        k_panel.add(new JLabel("Newton-Hines k,  Re: "));
        k_panel.add(k_real);
        k_panel.add(new JLabel(" Im: "));
        k_panel.add(k_imag);

        JLabel imagelabel4 = new JLabel();
        
        if (s.fns.function == NEWTONFORMULA) {
            imagelabel4.setIcon(getIcon("/fractalzoomer/icons/newton.png"));
        } else if (s.fns.function == NEWTON_HINESFORMULA) {
            imagelabel4.setIcon(getIcon("/fractalzoomer/icons/newton_hines.png"));
        }

        JPanel imagepanel4 = new JPanel();
        imagepanel4.add(imagelabel4);
        
        JPanel derivativePanel = new JPanel();
        
        JComboBox derivative_choice = new JComboBox(Constants.derivativeMethod);
        derivative_choice.setSelectedIndex(s.fns.derivative_method);
        derivative_choice.setToolTipText("Selects the derivative method.");
        derivative_choice.setFocusable(false);
        
        derivativePanel.add(new JLabel("Derivative: "));
        derivativePanel.add(derivative_choice);
        
        formula_dfz_panel.setVisible(s.fns.derivative_method == Derivative.DISABLED);
        
        derivative_choice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formula_dfz_panel.setVisible(derivative_choice.getSelectedIndex() == Derivative.DISABLED);
                pack();
            }
            
        });

        Object[] labels4 = ptra.createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

        Object[] message4 = {
            labels4,
            " ",
            imagepanel4,
            " ",
            derivativePanel,
            "Insert the function and its derivative (if required).",
            formula_fz_panel,
            formula_dfz_panel,
            s.fns.function == NEWTON_HINESFORMULA ? " " : "",
            s.fns.function == NEWTON_HINESFORMULA ? k_panel : ""};

        optionPane = new JOptionPane(message4, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        double temp_re2 = Double.parseDouble(k_real.getText());
                        double temp_im2 = Double.parseDouble(k_imag.getText());
                        
                        s.parser.parse(field_fz_formula.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC() || s.parser.foundR()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: c, bail, cbail, r cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.parser.parse(field_dfz_formula.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC() || s.parser.foundR()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: c, bail, cbail, r cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.fns.user_fz_formula = field_fz_formula.getText();
                        s.fns.user_dfz_formula = field_dfz_formula.getText();
                        
                        s.fns.derivative_method = derivative_choice.getSelectedIndex();
                        Derivative.DERIVATIVE_METHOD = s.fns.derivative_method;
                        
                        if(s.fns.function == NEWTON_HINESFORMULA) {
                            s.fns.newton_hines_k[0] = temp_re2;
                            s.fns.newton_hines_k[1] = temp_im2;
                        }
                    } catch (ParserException ex) {
                        JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
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
