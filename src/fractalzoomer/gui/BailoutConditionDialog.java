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

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class BailoutConditionDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public BailoutConditionDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] bailout_tests) {
        super(ptr);
        
        ptra = ptr;

        setTitle("User Bailout Condition");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JPanel formula_panel_cond1 = new JPanel();
        formula_panel_cond1.setLayout(new GridLayout(2, 2));

        JTextField field_condition = new JTextField(24);
        field_condition.setText(s.fns.bailout_test_user_formula);

        JTextField field_condition2 = new JTextField(24);
        field_condition2.setText(s.fns.bailout_test_user_formula2);

        formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(field_condition);
        formula_panel_cond1.add(field_condition2);

        String[] test_options = {"Escaped", "Not Escaped"};

        final JComboBox combo_box_greater = new JComboBox(test_options);
        final JComboBox combo_box_less = new JComboBox(test_options);

        combo_box_greater.setFocusable(false);
        combo_box_greater.setToolTipText("Sets the test option for the greater than case.");

        final JComboBox combo_box_equal = new JComboBox(test_options);
        combo_box_equal.setFocusable(false);
        combo_box_equal.setToolTipText("Sets the test option for the equal case.");

        combo_box_less.setFocusable(false);
        combo_box_less.setToolTipText("Sets the test option for the less than case.");

        if (s.fns.bailout_test_comparison == GREATER) { // >
            combo_box_greater.setSelectedIndex(0);
            combo_box_equal.setSelectedIndex(1);
            combo_box_less.setSelectedIndex(1);
        } else if (s.fns.bailout_test_comparison == GREATER_EQUAL) { // >=
            combo_box_greater.setSelectedIndex(0);
            combo_box_equal.setSelectedIndex(0);
            combo_box_less.setSelectedIndex(1);
        } else if (s.fns.bailout_test_comparison == LOWER) { // <
            combo_box_greater.setSelectedIndex(1);
            combo_box_equal.setSelectedIndex(1);
            combo_box_less.setSelectedIndex(0);
        } else if (s.fns.bailout_test_comparison == LOWER_EQUAL) { // <=
            combo_box_greater.setSelectedIndex(1);
            combo_box_equal.setSelectedIndex(0);
            combo_box_less.setSelectedIndex(0);
        } else if (s.fns.bailout_test_comparison == EQUAL) { // ==
            combo_box_greater.setSelectedIndex(1);
            combo_box_equal.setSelectedIndex(0);
            combo_box_less.setSelectedIndex(1);
        } else if (s.fns.bailout_test_comparison == NOT_EQUAL) { // !=
            combo_box_greater.setSelectedIndex(0);
            combo_box_equal.setSelectedIndex(1);
            combo_box_less.setSelectedIndex(0);
        }

        JPanel formula_panel_cond11 = new JPanel();

        formula_panel_cond11.add(new JLabel("left > right"));
        formula_panel_cond11.add(combo_box_greater);

        JPanel formula_panel_cond12 = new JPanel();

        formula_panel_cond12.add(new JLabel("left < right"));
        formula_panel_cond12.add(combo_box_less);

        JPanel formula_panel_cond13 = new JPanel();

        formula_panel_cond13.add(new JLabel("left = right"));
        formula_panel_cond13.add(combo_box_equal);

        Object[] labels33 = ptra.createUserFormulaLabels("z, c, s, c0, p, pp, n, maxn, bail, center, size, sizei, v1 - v30, point");

        Object[] message33 = {
            labels33,
            " ",
            "Set the bailout condition.",
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
                        bailout_tests[oldSelected].setSelected(true);
                        s.fns.bailout_test_algorithm = oldSelected;
                        dispose();
                        return;
                    }

                    try {
                        s.parser.parse(field_condition.getText());

                        if (s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: cbail, r, stat, trap cannot be used in left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.parser.parse(field_condition2.getText());

                        if (s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: cbail, r, stat, trap cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if ((combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 0)
                                || (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 1)) {

                            JOptionPane.showMessageDialog(ptra, "You cannot set all the outcomes to Escaped or Not Escaped.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.fns.bailout_test_user_formula = field_condition.getText();
                        s.fns.bailout_test_user_formula2 = field_condition2.getText();

                        if (combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 1) { // >
                            s.fns.bailout_test_comparison = GREATER;
                        } else if (combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 1) { // >=
                            s.fns.bailout_test_comparison = GREATER_EQUAL;
                        } else if (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 0) { // <
                            s.fns.bailout_test_comparison = LOWER;
                        } else if (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 0) { // <=
                            s.fns.bailout_test_comparison = LOWER_EQUAL;
                        } else if (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 1) { // ==
                            s.fns.bailout_test_comparison = EQUAL;
                        } else if (combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 0) { // !=
                            s.fns.bailout_test_comparison = NOT_EQUAL;
                        }
                    } catch (ParserException ex) {
                        JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                    ptra.setBailoutTestPost();
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
