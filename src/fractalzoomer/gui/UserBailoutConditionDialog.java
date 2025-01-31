
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.ParserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class UserBailoutConditionDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public UserBailoutConditionDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] bailout_tests, boolean mode) {
        super(ptr);
        
        ptra = ptr;

        if(mode) {
            setTitle("User Bailout Condition");
        }
        else {
            setTitle("User Convergent Bailout Condition");
        }
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JPanel formula_panel_cond1 = new JPanel();
        formula_panel_cond1.setLayout(new GridLayout(2, 2));

        JTextField field_condition = new JTextField(24);

        if(mode) {
            field_condition.setText(s.fns.bailout_test_user_formula);
        }
        else {
            field_condition.setText(s.fns.cbs.convergent_bailout_test_user_formula);
        }

        JTextField field_condition2 = new JTextField(24);
        if(mode) {
            field_condition2.setText(s.fns.bailout_test_user_formula2);
        }
        else {
            field_condition2.setText(s.fns.cbs.convergent_bailout_test_user_formula2);
        }

        formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(field_condition);
        formula_panel_cond1.add(field_condition2);

        String[] test_options = {"Escaped", "Not Escaped"};
        String[] test_options2 = {"Converged", "Not Converged"};

        final JComboBox<String> combo_box_greater = new JComboBox<>(mode ? test_options : test_options2);
        final JComboBox<String> combo_box_less = new JComboBox<>(mode ? test_options : test_options2);

        combo_box_greater.setFocusable(false);
        combo_box_greater.setToolTipText("Sets the test option for the greater than case.");

        final JComboBox<String> combo_box_equal = new JComboBox<>(mode ? test_options : test_options2);
        combo_box_equal.setFocusable(false);
        combo_box_equal.setToolTipText("Sets the test option for the equal case.");

        combo_box_less.setFocusable(false);
        combo_box_less.setToolTipText("Sets the test option for the less than case.");

        int comparison = mode ? s.fns.bailout_test_comparison : s.fns.cbs.convergent_bailout_test_comparison;

        if (comparison == GREATER) { // >
            combo_box_greater.setSelectedIndex(0);
            combo_box_equal.setSelectedIndex(1);
            combo_box_less.setSelectedIndex(1);
        } else if (comparison == GREATER_EQUAL) { // >=
            combo_box_greater.setSelectedIndex(0);
            combo_box_equal.setSelectedIndex(0);
            combo_box_less.setSelectedIndex(1);
        } else if (comparison == LOWER) { // <
            combo_box_greater.setSelectedIndex(1);
            combo_box_equal.setSelectedIndex(1);
            combo_box_less.setSelectedIndex(0);
        } else if (comparison == LOWER_EQUAL) { // <=
            combo_box_greater.setSelectedIndex(1);
            combo_box_equal.setSelectedIndex(0);
            combo_box_less.setSelectedIndex(0);
        } else if (comparison == EQUAL) { // ==
            combo_box_greater.setSelectedIndex(1);
            combo_box_equal.setSelectedIndex(0);
            combo_box_less.setSelectedIndex(1);
        } else if (comparison == NOT_EQUAL) { // !=
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

        Object[] labels33 = ptra.createUserFormulaLabels(mode ? "z, c, s, c0, pixel, p, pp, n, maxn, bail, center, size, sizei, width, height, v1 - v30, point" : "z, c, s, c0, pixel, p, pp, n, maxn, cbail, center, size, sizei, width, height, v1 - v30, point");

        Object[] message33 = {
            labels33,
            " ",
            mode ? "Set the bailout condition." : "Set the convergent bailout condition.",
            formula_panel_cond1,
            formula_panel_cond11,
            formula_panel_cond12,
            formula_panel_cond13,};

        optionPane = new JOptionPane(message33, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            bailout_tests[oldSelected].setSelected(true);

                            if(mode) {
                                s.fns.bailout_test_algorithm = oldSelected;
                            }
                            else {
                                s.fns.cbs.convergent_bailout_test_algorithm = oldSelected;
                            }
                            dispose();
                            return;
                        }

                        try {
                            s.parser.parse(field_condition.getText());

                            if(mode) {
                                if (s.parser.foundNF() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: nf, cbail, r, stat, trap cannot be used in left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                            else {
                                if (s.parser.foundNF() || s.parser.foundBail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: nf, bail, r, stat, trap cannot be used in left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

                            s.parser.parse(field_condition2.getText());

                            if(mode) {
                                if (s.parser.foundNF() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: nf, cbail, r, stat, trap cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                            else {
                                if (s.parser.foundNF() || s.parser.foundBail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: nf, bail, r, stat, trap cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

                            if ((combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 0)
                                    || (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 1)) {

                                if(mode) {
                                    JOptionPane.showMessageDialog(ptra, "You cannot set all the outcomes to Escaped or Not Escaped.", "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                                else {
                                    JOptionPane.showMessageDialog(ptra, "You cannot set all the outcomes to Converged or Not Converged.", "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                                return;
                            }


                            if(mode) {
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
                            }
                            else {
                                s.fns.cbs.convergent_bailout_test_user_formula = field_condition.getText();
                                s.fns.cbs.convergent_bailout_test_user_formula2 = field_condition2.getText();

                                if (combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 1) { // >
                                    s.fns.cbs.convergent_bailout_test_comparison = GREATER;
                                } else if (combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 1) { // >=
                                    s.fns.cbs.convergent_bailout_test_comparison = GREATER_EQUAL;
                                } else if (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 0) { // <
                                    s.fns.cbs.convergent_bailout_test_comparison = LOWER;
                                } else if (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 0) { // <=
                                    s.fns.cbs.convergent_bailout_test_comparison = LOWER_EQUAL;
                                } else if (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 1) { // ==
                                    s.fns.cbs.convergent_bailout_test_comparison = EQUAL;
                                } else if (combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 0) { // !=
                                    s.fns.cbs.convergent_bailout_test_comparison = NOT_EQUAL;
                                }
                            }
                        } catch (ParserException ex) {
                            JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setBailoutTestPost();
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
