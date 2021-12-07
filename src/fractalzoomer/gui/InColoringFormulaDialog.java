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

/**
 *
 * @author hrkalona2
 */
public class InColoringFormulaDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public InColoringFormulaDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] in_coloring_modes) {
        
        super(ptr);

        ptra = ptr;

        setTitle("User In Coloring Method");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JTabbedPane tabbedPane = new JTabbedPane();
        //tabbedPane.setPreferredSize(new Dimension(550, 210));
        tabbedPane.setPreferredSize(new Dimension(495, 190));
        tabbedPane.setFocusable(false);

        JTextField field_formula = new JTextField(MainWindow.runsOnWindows ? 50 : 45);//48
        field_formula.setText(s.fns.incoloring_formula);

        JPanel formula_panel = new JPanel();
        formula_panel.setLayout(new FlowLayout());

        formula_panel.add(new JLabel("in ="));
        formula_panel.add(field_formula);

        tabbedPane.addTab("Normal", formula_panel);

        JPanel formula_panel_cond1 = new JPanel();
        formula_panel_cond1.setLayout(new GridLayout(2, 2));

        JTextField field_condition = new JTextField(24);
        field_condition.setText(s.fns.user_incoloring_conditions[0]);

        JTextField field_condition2 = new JTextField(24);
        field_condition2.setText(s.fns.user_incoloring_conditions[1]);

        formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(field_condition);
        formula_panel_cond1.add(field_condition2);

        JTextField field_formula_cond1 = new JTextField(MainWindow.runsOnWindows ? 45 : 40);//35
        field_formula_cond1.setText(s.fns.user_incoloring_condition_formula[0]);

        JTextField field_formula_cond2 = new JTextField(MainWindow.runsOnWindows ? 45 : 40);//35
        field_formula_cond2.setText(s.fns.user_incoloring_condition_formula[1]);

        JTextField field_formula_cond3 = new JTextField(MainWindow.runsOnWindows ? 45 : 40);//35
        field_formula_cond3.setText(s.fns.user_incoloring_condition_formula[2]);

        JPanel formula_panel_cond11 = new JPanel();

        formula_panel_cond11.add(new JLabel("left > right, in ="));
        formula_panel_cond11.add(field_formula_cond1);

        JPanel formula_panel_cond12 = new JPanel();

        formula_panel_cond12.add(new JLabel("left < right, in ="));
        formula_panel_cond12.add(field_formula_cond2);

        JPanel formula_panel_cond13 = new JPanel();

        formula_panel_cond13.add(new JLabel("left = right, in ="));
        formula_panel_cond13.add(field_formula_cond3);

        JPanel panel_cond = new JPanel();

        panel_cond.setLayout(new FlowLayout());

        panel_cond.add(formula_panel_cond1);
        panel_cond.add(formula_panel_cond11);
        panel_cond.add(formula_panel_cond12);
        panel_cond.add(formula_panel_cond13);

        tabbedPane.addTab("Conditional", panel_cond);

        Object[] labels3 = ptra.createUserFormulaLabels("z, c, s, c0, pixel, p, pp, n, maxn, bail, cbail, center, size, sizei, v1 - v30, point");

        tabbedPane.setSelectedIndex(s.fns.user_in_coloring_algorithm);

        Object[] message3 = {
            labels3,
            " ",
            "Set the in coloring formula. Only the real component of the complex number will be used.",
            tabbedPane,};

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
                        in_coloring_modes[oldSelected].setSelected(true);
                        s.fns.in_coloring_algorithm = oldSelected;
                        dispose();
                        return;
                    }

                    try {
                        if (tabbedPane.getSelectedIndex() == 0) {
                            s.parser.parse(field_formula.getText());
                            
                            if(s.parser.foundR()|| s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: r, stat, trap cannot be used in the in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail() ) {
                                    JOptionPane.showMessageDialog(ptra, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(ptra, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } else {
                            s.parser.parse(field_condition.getText());
                            
                            if(s.parser.foundR()|| s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: r, stat, trap cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail()) {
                                    JOptionPane.showMessageDialog(ptra, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(ptra, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_condition2.getText());
                            
                            if(s.parser.foundR()|| s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: r, stat, trap cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail()) {
                                    JOptionPane.showMessageDialog(ptra, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(ptra, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_cond1.getText());
                            
                            if(s.parser.foundR()|| s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: r, stat, trap cannot be used in the left > right in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail()) {
                                    JOptionPane.showMessageDialog(ptra, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(ptra, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_cond2.getText());
                            
                            if(s.parser.foundR()|| s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: r, stat, trap cannot be used in the left < right in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail()) {
                                    JOptionPane.showMessageDialog(ptra, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(ptra, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_cond3.getText());
                            
                            if(s.parser.foundR()|| s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: r, stat, trap cannot be used in the left = right in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail()) {
                                    JOptionPane.showMessageDialog(ptra, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(ptra, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }

                        s.fns.user_in_coloring_algorithm = tabbedPane.getSelectedIndex();

                        if (s.fns.user_in_coloring_algorithm == 0) {
                            s.fns.incoloring_formula = field_formula.getText();
                        } else {
                            s.fns.user_incoloring_conditions[0] = field_condition.getText();
                            s.fns.user_incoloring_conditions[1] = field_condition2.getText();
                            s.fns.user_incoloring_condition_formula[0] = field_formula_cond1.getText();
                            s.fns.user_incoloring_condition_formula[1] = field_formula_cond2.getText();
                            s.fns.user_incoloring_condition_formula[2] = field_formula_cond3.getText();
                        }
                        
                        ptra.setUserInColoringModePost();

                    } catch (ParserException ex) {
                        JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                    ptra.setInColoringModePost();
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
