
package fractalzoomer.gui;

import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.ParserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.main.MainWindow.runsOnWindows;

/**
 *
 * @author hrkalona2
 */
public class InitialPerturbationDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public InitialPerturbationDialog(MainWindow ptr, Settings s) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Initial Perturbation");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());

        JTextField field_real = new JTextField(45);
        field_real.setText("" + s.fns.perturbation_vals[0]);

        JTextField field_imaginary = new JTextField(45);
        field_imaginary.setText("" + s.fns.perturbation_vals[1]);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension((runsOnWindows ? 550 : 630) + 50, 605));
        tabbedPane.setFocusable(false);

        JPanel panel11 = new JPanel();

        panel11.setLayout(new GridLayout(8, 1));

        panel11.add(new JLabel(""));
        panel11.add(new JLabel("Set the real and imaginary part of the initial perturbation."));
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

        JLabel html_label = CommonFunctions.createUserFormulaHtmlLabels("c, maxn, center, size, sizei, width, height, v1 - v30, point", "variable initial perturbation", "");
        panel21.add(html_label);

        JTextField field_formula = new JTextField(MainWindow.useCustomLaf ? 41 : 45);
        field_formula.setText("" + s.fns.perturbation_user_formula);

        JTabbedPane tabbedPane2 = new JTabbedPane();
        tabbedPane2.setPreferredSize(new Dimension((runsOnWindows ? 500 : 580) + 50, 190));
        tabbedPane2.setFocusable(false);

        panel22.add(new JLabel("z(0) = z(0) +"));
        panel22.add(field_formula);

        tabbedPane2.addTab("Normal", panel22);

        JPanel formula_panel_cond1 = new JPanel();
        formula_panel_cond1.setLayout(new GridLayout(2, 2));

        JTextField field_condition = new JTextField(MainWindow.useCustomLaf ? 22 : 24);
        field_condition.setText(s.fns.user_perturbation_conditions[0]);

        JTextField field_condition2 = new JTextField(MainWindow.useCustomLaf ? 22 : 24);
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

        JButton info_user = new MyButton("Help");
        info_user.setToolTipText("Shows the details of the user formulas.");
        info_user.setFocusable(false);
        info_user.setIcon(MainWindow.getIcon("help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(e -> ptra.showUserFormulaHelp());

        JButton code_editor = new MyButton("Edit User Code");
        code_editor.setToolTipText("<html>Opens the java code, containing the user defined functions,<br>with a text editor.</html>");
        code_editor.setFocusable(false);
        code_editor.setIcon(MainWindow.getIcon("code_editor2.png"));
        code_editor.setPreferredSize(new Dimension(160, 23));

        code_editor.addActionListener(e -> ptra.codeEditor());

        JButton compile_code = new MyButton("Compile User Code");
        compile_code.setToolTipText("Compiles the java code, containing the user defined functions.");
        compile_code.setFocusable(false);
        compile_code.setIcon(MainWindow.getIcon("compile2.png"));
        compile_code.setPreferredSize(new Dimension(180, 23));

        compile_code.addActionListener(e -> ptra.compileCode(true));

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


        JCheckBox pertur = new JCheckBox("Perturbation");
        pertur.setSelected(s.fns.perturbation);
        pertur.setFocusable(false);

        Object[] message = {
                " ",
                pertur,
                " ",
            tabbedPane
        };

        optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                                if (s.parser.foundNF() || s.parser.foundPixel() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundC0() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, c0, pixel, p, pp, nf, bail, cbail, r, stat, trap cannot be used in the z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else {
                                s.parser.parse(field_condition.getText());

                                if (s.parser.foundNF() || s.parser.foundPixel() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundC0() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, c0, pixel, p, pp, nf, bail, cbail, r, stat, trap cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                s.parser.parse(field_condition2.getText());

                                if (s.parser.foundNF() || s.parser.foundPixel() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundC0() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, c0, pixel, p, pp, nf, bail, cbail, r, stat, trap cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                s.parser.parse(field_formula_cond1.getText());

                                if (s.parser.foundNF() || s.parser.foundPixel() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundC0() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, c0, pixel, p, pp, nf, bail, cbail, r, stat, trap cannot be used in the left > right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                s.parser.parse(field_formula_cond2.getText());

                                if (s.parser.foundNF() || s.parser.foundPixel() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundC0() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, c0, pixel, p, pp, nf, bail, cbail, r, stat, trap cannot be used in the left < right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                s.parser.parse(field_formula_cond3.getText());

                                if (s.parser.foundNF() || s.parser.foundPixel() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundC0() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                    JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, c0, pixel, p, pp, nf, bail, cbail, r, stat, trap cannot be used in the left = right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
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
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setPerturbationPost(pertur.isSelected());
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
