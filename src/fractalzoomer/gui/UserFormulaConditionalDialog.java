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
import fractalzoomer.parser.ParserException;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author hrkalona2
 */
public class UserFormulaConditionalDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public UserFormulaConditionalDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("User Formula Conditional");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JPanel formula_panel_cond1 = new JPanel();
        formula_panel_cond1.setLayout(new GridLayout(2, 2));

        JTextField field_condition = new JTextField(24);
        field_condition.setText(s.fns.user_formula_conditions[0]);

        JTextField field_condition2 = new JTextField(24);
        field_condition2.setText(s.fns.user_formula_conditions[1]);

        formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(field_condition);
        formula_panel_cond1.add(field_condition2);

        JTextField field_formula_cond1 = new JTextField(50);
        field_formula_cond1.setText(s.fns.user_formula_condition_formula[0]);

        JTextField field_formula_cond2 = new JTextField(50);
        field_formula_cond2.setText(s.fns.user_formula_condition_formula[1]);

        JTextField field_formula_cond3 = new JTextField(50);
        field_formula_cond3.setText(s.fns.user_formula_condition_formula[2]);

        JPanel formula_panel_cond11 = new JPanel();

        formula_panel_cond11.add(new JLabel("left > right, z ="));
        formula_panel_cond11.add(field_formula_cond1);

        JPanel formula_panel_cond12 = new JPanel();

        formula_panel_cond12.add(new JLabel("left < right, z ="));
        formula_panel_cond12.add(field_formula_cond2);

        JPanel formula_panel_cond13 = new JPanel();

        formula_panel_cond13.add(new JLabel("left = right, z ="));
        formula_panel_cond13.add(field_formula_cond3);

        JLabel bail3 = new JLabel("Bailout Technique:");
        bail3.setFont(new Font("Arial", Font.BOLD, 11));

        String[] method43 = {"Escaping Algorithm", "Converging Algorithm"};

        JComboBox method43_choice = new JComboBox(method43);
        method43_choice.setSelectedIndex(s.fns.bail_technique);
        method43_choice.setToolTipText("Selects the bailout technique.");
        method43_choice.setFocusable(false);

        Object[] labels33 = ptra.createUserFormulaLabels("z, c, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

        Object[] message33 = {
            labels33,
            bail3,
            method43_choice,
            " ",
            "Insert your formulas, that will be evaluated under a specific condition.",
            formula_panel_cond1,
            formula_panel_cond11,
            formula_panel_cond12,
            formula_panel_cond13,};

        optionPane = new JOptionPane(message33, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        boolean temp_bool = false;
                        
                        s.parser.parse(field_condition.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.parser.parse(field_condition2.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.parser.parse(field_formula_cond1.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r cannot be used in the left > right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.parser.parse(field_formula_cond2.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r cannot be used in the left < right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.parser.parse(field_formula_cond3.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r cannot be used in the left = right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.fns.user_formula_condition_formula[0] = field_formula_cond1.getText();
                        s.fns.user_formula_condition_formula[1] = field_formula_cond2.getText();
                        s.fns.user_formula_condition_formula[2] = field_formula_cond3.getText();
                        s.fns.user_formula_conditions[0] = field_condition.getText();
                        s.fns.user_formula_conditions[1] = field_condition2.getText();
                        s.userFormulaHasC = temp_bool;
                        s.fns.bail_technique = method43_choice.getSelectedIndex();

                        ptra.setUserFormulaOptions(true);
                    } catch (ParserException ex) {
                        JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
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
