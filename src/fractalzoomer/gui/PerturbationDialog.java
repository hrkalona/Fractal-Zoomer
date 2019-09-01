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

import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.MainWindow;
import static fractalzoomer.main.MainWindow.runsOnWindows;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.ParserException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author hrkalona2
 */
public class PerturbationDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public PerturbationDialog(MainWindow ptr, Settings s, JCheckBoxMenuItem perturbation_opt) {
        
        super();

        ptra = ptr;

        setTitle("Perturbation");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());

        JTextField field_real = new JTextField(45);
        field_real.setText("" + s.fns.perturbation_vals[0]);

        JTextField field_imaginary = new JTextField(45);
        field_imaginary.setText("" + s.fns.perturbation_vals[1]);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension((runsOnWindows ? 550 : 630) + 50, 595));
        tabbedPane.setFocusable(false);

        JPanel panel11 = new JPanel();

        panel11.setLayout(new GridLayout(8, 1));

        panel11.add(new JLabel(""));
        panel11.add(new JLabel("Set the real and imaginary part of the perturbation."));
        panel11.add(new JLabel(""));
        panel11.add(new JLabel("Real:"));
        panel11.add(field_real);
        panel11.add(new JLabel(""));
        panel11.add(new JLabel("Imaginary:"));
        panel11.add(field_imaginary);

        panel1.add(panel11);

        JPanel panel21 = new JPanel();
        panel21.setLayout(new FlowLayout());
        JPanel panel22 = new JPanel();
        panel22.setLayout(new FlowLayout());

        JLabel html_label = CommonFunctions.createUserFormulaHtmlLabels("c, maxn, center, size, sizei, v1 - v30, point", "variable perturbation", "");
        panel21.add(html_label);

        JTextField field_formula = new JTextField(45);
        field_formula.setText("" + s.fns.perturbation_user_formula);

        JTabbedPane tabbedPane2 = new JTabbedPane();
        tabbedPane2.setPreferredSize(new Dimension((runsOnWindows ? 500 : 580) + 50, 190));
        tabbedPane2.setFocusable(false);

        panel22.add(new JLabel("z(0) = z(0) +"));
        panel22.add(field_formula);

        tabbedPane2.addTab("Normal", panel22);

        JPanel formula_panel_cond1 = new JPanel();
        formula_panel_cond1.setLayout(new GridLayout(2, 2));

        JTextField field_condition = new JTextField(24);
        field_condition.setText(s.fns.user_perturbation_conditions[0]);

        JTextField field_condition2 = new JTextField(24);
        field_condition2.setText(s.fns.user_perturbation_conditions[1]);

        formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(field_condition);
        formula_panel_cond1.add(field_condition2);

        JTextField field_formula_cond1 = new JTextField(37);
        field_formula_cond1.setText(s.fns.user_perturbation_condition_formula[0]);

        JTextField field_formula_cond2 = new JTextField(37);
        field_formula_cond2.setText(s.fns.user_perturbation_condition_formula[1]);

        JTextField field_formula_cond3 = new JTextField(37);
        field_formula_cond3.setText(s.fns.user_perturbation_condition_formula[2]);

        JPanel formula_panel_cond11 = new JPanel();

        formula_panel_cond11.add(new JLabel("left > right, z(0) = z(0) +"));
        formula_panel_cond11.add(field_formula_cond1);

        JPanel formula_panel_cond12 = new JPanel();

        formula_panel_cond12.add(new JLabel("left < right, z(0) = z(0) +"));
        formula_panel_cond12.add(field_formula_cond2);

        JPanel formula_panel_cond13 = new JPanel();

        formula_panel_cond13.add(new JLabel("left = right, z(0) = z(0) +"));
        formula_panel_cond13.add(field_formula_cond3);

        JPanel panel_cond = new JPanel();

        panel_cond.setLayout(new FlowLayout());

        panel_cond.add(formula_panel_cond1);
        panel_cond.add(formula_panel_cond11);
        panel_cond.add(formula_panel_cond12);
        panel_cond.add(formula_panel_cond13);

        tabbedPane2.addTab("Conditional", panel_cond);

        JButton info_user = new JButton("Help");
        info_user.setToolTipText("Shows the details of the user formulas.");
        info_user.setFocusable(false);
        info_user.setIcon(getIcon("/fractalzoomer/icons/help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptra.showUserFormulaHelp();

            }

        });

        JButton code_editor = new JButton("Edit User Code");
        code_editor.setToolTipText("<html>Opens the java code, containing the user defined functions,<br>with a text editor.</html>");
        code_editor.setFocusable(false);
        code_editor.setIcon(getIcon("/fractalzoomer/icons/code_editor2.png"));
        code_editor.setPreferredSize(new Dimension(160, 23));

        code_editor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptra.codeEditor();

            }

        });

        JButton compile_code = new JButton("Compile User Code");
        compile_code.setToolTipText("Compiles the java code, containing the user defined functions.");
        compile_code.setFocusable(false);
        compile_code.setIcon(getIcon("/fractalzoomer/icons/compile2.png"));
        compile_code.setPreferredSize(new Dimension(180, 23));

        compile_code.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptra.compileCode(true);

            }

        });

        JPanel info_panel = new JPanel();
        info_panel.setLayout(new FlowLayout());
        info_panel.add(info_user);
        info_panel.add(code_editor);
        info_panel.add(compile_code);
        info_panel.setPreferredSize(new Dimension(500, 28));

        panel2.add(info_panel);
        panel2.add(panel21);
        panel2.add(tabbedPane2);

        tabbedPane.addTab("Static value", panel1);
        tabbedPane.addTab("Variable value", panel2);

        if (s.fns.variable_perturbation) {
            tabbedPane.setSelectedIndex(1);
            tabbedPane2.setSelectedIndex(s.fns.user_perturbation_algorithm);
        } else {
            tabbedPane.setSelectedIndex(0);
        }

        Object[] message = {
            tabbedPane
        };

        optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        perturbation_opt.setSelected(false);
                        dispose();
                        return;
                    }

                    try {
                        double temp = 0, temp2 = 0;
                        if (tabbedPane.getSelectedIndex() == 0) {
                            temp = Double.parseDouble(field_real.getText());
                            temp2 = Double.parseDouble(field_imaginary.getText());
                        } else if (tabbedPane2.getSelectedIndex() == 0) {
                            s.parser.parse(field_formula.getText());
                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, p, pp, bail, cbail, r cannot be used in the z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } else {
                            s.parser.parse(field_condition.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, p, pp, bail, cbail, r cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_condition2.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, p, pp, bail, cbail, r cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_cond1.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, p, pp, bail, cbail, r cannot be used in the left > right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_cond2.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, p, pp, bail, cbail, r cannot be used in the left < right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_cond3.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, p, pp, bail, cbail, r cannot be used in the left = right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }

                        if (tabbedPane.getSelectedIndex() == 0) {
                            s.fns.perturbation_vals[0] = temp == 0.0 ? 0.0 : temp;
                            s.fns.perturbation_vals[1] = temp2 == 0.0 ? 0.0 : temp2;
                            s.fns.variable_perturbation = false;
                        } else {
                            s.fns.variable_perturbation = true;

                            s.fns.user_perturbation_algorithm = tabbedPane2.getSelectedIndex();

                            if (s.fns.user_perturbation_algorithm == 0) {
                                s.fns.perturbation_user_formula = field_formula.getText();
                            } else {
                                s.fns.user_perturbation_conditions[0] = field_condition.getText();
                                s.fns.user_perturbation_conditions[1] = field_condition2.getText();
                                s.fns.user_perturbation_condition_formula[0] = field_formula_cond1.getText();
                                s.fns.user_perturbation_condition_formula[1] = field_formula_cond2.getText();
                                s.fns.user_perturbation_condition_formula[2] = field_formula_cond3.getText();
                            }
                        }
                    } catch (ParserException ex) {
                        JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                    ptra.setPerturbationPost();
                    ptra.defaultFractalSettings();
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
