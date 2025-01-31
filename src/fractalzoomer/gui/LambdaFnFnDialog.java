
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.ParserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class LambdaFnFnDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public LambdaFnFnDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType, boolean wasMagnetPatakiType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Lambda(Fn || Fn)");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JPanel formula_panel_cond1 = new JPanel();
        formula_panel_cond1.setLayout(new GridLayout(2, 2));

        JTextField field_condition = new JTextField(24);
        field_condition.setText(s.fns.lfns.lambda_formula_conditions[0]);

        JTextField field_condition2 = new JTextField(24);
        field_condition2.setText(s.fns.lfns.lambda_formula_conditions[1]);

        formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
        formula_panel_cond1.add(field_condition);
        formula_panel_cond1.add(field_condition2);

        JTextField field_formula_cond1 = new JTextField(50);
        field_formula_cond1.setText(s.fns.lfns.lambda_formula_condition_formula[0]);

        JTextField field_formula_cond2 = new JTextField(50);
        field_formula_cond2.setText(s.fns.lfns.lambda_formula_condition_formula[1]);

        JTextField field_formula_cond3 = new JTextField(50);
        field_formula_cond3.setText(s.fns.lfns.lambda_formula_condition_formula[2]);

        JPanel formula_panel_cond11 = new JPanel();

        formula_panel_cond11.add(new JLabel("left > right, z = c * "));
        formula_panel_cond11.add(field_formula_cond1);

        JPanel formula_panel_cond12 = new JPanel();

        formula_panel_cond12.add(new JLabel("left < right, z = c * "));
        formula_panel_cond12.add(field_formula_cond2);

        JPanel formula_panel_cond13 = new JPanel();

        formula_panel_cond13.add(new JLabel("left = right, z = c * "));
        formula_panel_cond13.add(field_formula_cond3);
        Object[] labels33 = ptra.createUserFormulaLabels("z, c, s, c0, pixel, p, pp, n, maxn, center, size, sizei, width, height, v1 - v30, point");

        Object[] message33 = {
            labels33,
            " ",
            "Insert your formulas, that will be evaluated under a specific condition.",
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
                            fractal_functions[oldSelected].setSelected(true);
                            s.fns.function = oldSelected;
                            dispose();
                            return;
                        }

                        try {
                            s.parser.parse(field_condition.getText());

                            if (s.parser.foundNF() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: nf, bail, cbail, r, stat, trap cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_condition2.getText());

                            if (s.parser.foundNF() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: nf, bail, cbail, r, stat, trap cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_cond1.getText());

                            if (s.parser.foundNF() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: nf, bail, cbail, r, stat, trap cannot be used in the left > right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_cond2.getText());
                            if (s.parser.foundNF() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: nf, bail, cbail, r, stat, trap cannot be used in the left < right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_formula_cond3.getText());
                            if (s.parser.foundNF() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: nf, bail, cbail, r, stat, trap cannot be used in the left = right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.fns.lfns.lambda_formula_condition_formula[0] = field_formula_cond1.getText();
                            s.fns.lfns.lambda_formula_condition_formula[1] = field_formula_cond2.getText();
                            s.fns.lfns.lambda_formula_condition_formula[2] = field_formula_cond3.getText();
                            s.fns.lfns.lambda_formula_conditions[0] = field_condition.getText();
                            s.fns.lfns.lambda_formula_conditions[1] = field_condition2.getText();
                        } catch (ParserException ex) {
                            JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        ptra.optionsEnableShortcut();
                        dispose();
                        ptra.setFunctionPost(oldSelected, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
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
