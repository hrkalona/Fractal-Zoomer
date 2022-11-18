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
import fractalzoomer.main.app_settings.LyapunovSettings;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.Parser;
import fractalzoomer.parser.ParserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hrkalona2
 */
public class LyapunovDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public LyapunovDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Lyapunov");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField field_formula_a = new JTextField(25);
        field_formula_a.setText(s.fns.lpns.lyapunovA);

        JTextField field_formula_b = new JTextField(25);
        field_formula_b.setText(s.fns.lpns.lyapunovB);

        JTextField field_formula_c = new JTextField(25);
        field_formula_c.setText(s.fns.lpns.lyapunovC);

        JTextField field_formula_d = new JTextField(25);
        field_formula_d.setText(s.fns.lpns.lyapunovD);

        JTextField field_expression = new JTextField(50);
        field_expression.setText(s.fns.lpns.lyapunovExpression);
        field_expression.setToolTipText("The format is: $A;$B;$C;$D using any combination you want.");
        
        JTextField field_function = new JTextField(50);
        field_function.setText(s.fns.lpns.lyapunovFunction);
        
        JTextField field_exponent_function = new JTextField(50);
        field_exponent_function.setText(s.fns.lpns.lyapunovExponentFunction);
        
        JTextField initial_value = new JTextField(50);
        initial_value.setText(s.fns.lpns.lyapunovInitialValue);

        JPanel formula_panel_lyapunov = new JPanel();
        formula_panel_lyapunov.setLayout(new GridLayout(2, 2));
        
        JPanel formula_panel_lyapunov10 = new JPanel();
        formula_panel_lyapunov10.setLayout(new GridLayout(2, 2));
        
        JPanel formula_panel_lyapunov2 = new JPanel();

        formula_panel_lyapunov2.add(new JLabel("A ="));
        formula_panel_lyapunov2.add(field_formula_a);
               

        JPanel formula_panel_lyapunov3 = new JPanel();
        
        formula_panel_lyapunov3.add(new JLabel("B ="));
        formula_panel_lyapunov3.add(field_formula_b);


        JPanel formula_panel_lyapunov4 = new JPanel();
        formula_panel_lyapunov4.add(new JLabel("C ="));
        formula_panel_lyapunov4.add(field_formula_c);
        
        JPanel formula_panel_lyapunov5 = new JPanel();
        formula_panel_lyapunov5.add(new JLabel("D ="));
        formula_panel_lyapunov5.add(field_formula_d);
        
        final JCheckBox useLyapExponentCheck = new JCheckBox("Override In-Coloring");
        useLyapExponentCheck.setToolTipText("Enables the use of a default implementation with the lyapunov exponent, for the in-coloring mode.");
        useLyapExponentCheck.setSelected(s.fns.lpns.useLyapunovExponent);
        useLyapExponentCheck.setFocusable(false);
        
        List<String> variables = new ArrayList<>();
        
        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            variables.add("v" + (i + 1));
        }
        
        String[] variablesArr = new String[variables.size()];
        variablesArr = variables.toArray(variablesArr);

        JComboBox<String> variable_choice = new JComboBox<>(variablesArr);
        variable_choice.setSelectedIndex(s.fns.lpns.lyapunovVariableId);
        variable_choice.setToolTipText("Exposes the lyapunov exponent to the selected variable.");
        variable_choice.setFocusable(false);
        
        JPanel checkbox_panel = new JPanel();
        checkbox_panel.add(useLyapExponentCheck);
        
        JPanel variable_panel = new JPanel();
        variable_panel.add(new JLabel("Expose exponent to variable: "));
        variable_panel.add(variable_choice);        
        
        final JCheckBox skipBailout = new JCheckBox("Skip Bailout");
        skipBailout.setToolTipText("Disables the bailout condition check.");
        skipBailout.setSelected(s.fns.lpns.lyapunovskipBailoutCheck);
        skipBailout.setFocusable(false);
        
        JPanel bailout_panel = new JPanel();
        bailout_panel.add(skipBailout);
        
        JTextField initial_iterations = new JTextField(25);
        initial_iterations.setText("" + s.fns.lpns.lyapunovInitializationIteratons);
        
        JPanel iterations_panel = new JPanel();
        iterations_panel.add(new JLabel("Initial Iterations ="));
        iterations_panel.add(initial_iterations);
        
        formula_panel_lyapunov.add(formula_panel_lyapunov2);
        formula_panel_lyapunov.add(formula_panel_lyapunov3);
        formula_panel_lyapunov.add(formula_panel_lyapunov4);
        formula_panel_lyapunov.add(formula_panel_lyapunov5);
        
        formula_panel_lyapunov10.add(iterations_panel);
        formula_panel_lyapunov10.add(variable_panel); 
        formula_panel_lyapunov10.add(bailout_panel);
        formula_panel_lyapunov10.add(checkbox_panel);             
        
        JPanel formula_panel_lyapunov6 = new JPanel();

        formula_panel_lyapunov6.add(new JLabel("Expression: r ="));
        formula_panel_lyapunov6.add(field_expression);
        
        JPanel formula_panel_lyapunov7 = new JPanel();

        formula_panel_lyapunov7.add(new JLabel("Function ="));
        formula_panel_lyapunov7.add(field_function);
                     
                
        JPanel formula_panel_lyapunov9 = new JPanel();

        formula_panel_lyapunov9.add(new JLabel("Initial Value ="));
        formula_panel_lyapunov9.add(initial_value);
        
        JPanel formula_panel_lyapunov8 = new JPanel();

        formula_panel_lyapunov8.add(new JLabel("Exponent ="));
        formula_panel_lyapunov8.add(field_exponent_function);

        Object[] labels_lyap = ptra.createUserFormulaLabels("z, r, c, c0, s, pixel, p, pp, n, maxn, center, size, sizei, v1 - v30, point");


        Object[] message_lyap = {
            labels_lyap,
            " ",
            "Insert the lyapunov formula parameters.",
            formula_panel_lyapunov,
            formula_panel_lyapunov6,
            formula_panel_lyapunov7,
            formula_panel_lyapunov9,
            formula_panel_lyapunov8,
            formula_panel_lyapunov10};

        optionPane = new JOptionPane(message_lyap, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            boolean temp_bool = false;

                            s.parser.parse(field_formula_a.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the A formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_b.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the B formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_c.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the C formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_d.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the D formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            String expression = field_expression.getText();

                            String[] subExpressions = LyapunovSettings.getTokens(expression);

                            if (subExpressions == null) {
                                JOptionPane.showMessageDialog(ptra, "The expression does not contain any sub-expressions.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            subExpressions = LyapunovSettings.flatten(subExpressions, "$A", field_formula_a.getText());
                            subExpressions = LyapunovSettings.flatten(subExpressions, "$B", field_formula_b.getText());
                            subExpressions = LyapunovSettings.flatten(subExpressions, "$C", field_formula_c.getText());
                            subExpressions = LyapunovSettings.flatten(subExpressions, "$D", field_formula_d.getText());

                            for (String subExpression : subExpressions) {
                                s.parser.parse(subExpression);

                                if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the Expression formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                temp_bool = temp_bool || s.parser.foundC();
                            }

                            s.parser.parse(field_function.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, stat, trap cannot be used in the A formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            temp_bool = temp_bool || s.parser.foundC();

                            s.parser.parse(field_exponent_function.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, stat, trap cannot be used in the A formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            temp_bool = temp_bool || s.parser.foundC();


                            s.parser.parse(initial_value.getText());

                            if (s.parser.foundPixel() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundC0() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, c0, pixel, p, pp, bail, cbail, r, stat, trap cannot be used in the initial value formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            int temp;
                            try {
                                temp = Integer.parseInt(initial_iterations.getText());
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp < 0) {
                                JOptionPane.showMessageDialog(ptra, "Initial iterations number must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            } else if (temp > MainWindow.MAX_ITERATIONS_NUMBER) {
                                JOptionPane.showMessageDialog(ptra, "Initial iterations number must be less than 2147483648.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.fns.lpns.lyapunovA = field_formula_a.getText();
                            s.fns.lpns.lyapunovB = field_formula_b.getText();
                            s.fns.lpns.lyapunovC = field_formula_c.getText();
                            s.fns.lpns.lyapunovD = field_formula_d.getText();
                            s.fns.lpns.lyapunovExpression = field_expression.getText();
                            s.fns.lpns.lyapunovFinalExpression = subExpressions;
                            s.fns.lpns.useLyapunovExponent = useLyapExponentCheck.isSelected();
                            s.fns.lpns.lyapunovFunction = field_function.getText();
                            s.fns.lpns.lyapunovExponentFunction = field_exponent_function.getText();
                            s.fns.lpns.lyapunovVariableId = variable_choice.getSelectedIndex();
                            s.fns.lpns.lyapunovInitialValue = initial_value.getText();
                            s.fns.lpns.lyapunovskipBailoutCheck = skipBailout.isSelected();
                            s.fns.lpns.lyapunovInitializationIteratons = temp;

                            s.userFormulaHasC = temp_bool;

                            ptra.setUserFormulaOptions(false);
                        } catch (ParserException ex) {
                            JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        ptra.optionsEnableShortcut();
                        dispose();
                        ptra.setFunctionPost(wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType);
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
