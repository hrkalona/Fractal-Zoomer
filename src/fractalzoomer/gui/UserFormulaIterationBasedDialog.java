
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
public class UserFormulaIterationBasedDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public UserFormulaIterationBasedDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType, boolean wasMagnetPatakiType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("User Formula Iteration Based");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField field_formula_it_based1 = new JTextField(50);
        field_formula_it_based1.setText(s.fns.user_formula_iteration_based[0]);

        JTextField field_formula_it_based2 = new JTextField(50);
        field_formula_it_based2.setText(s.fns.user_formula_iteration_based[1]);

        JTextField field_formula_it_based3 = new JTextField(50);
        field_formula_it_based3.setText(s.fns.user_formula_iteration_based[2]);

        JTextField field_formula_it_based4 = new JTextField(50);
        field_formula_it_based4.setText(s.fns.user_formula_iteration_based[3]);

        JPanel formula_panel_it1 = new JPanel();

        formula_panel_it1.add(new JLabel("Every 1st iteration, z ="));
        formula_panel_it1.add(field_formula_it_based1);

        JPanel formula_panel_it2 = new JPanel();

        formula_panel_it2.add(new JLabel("Every 2nd iteration, z ="));
        formula_panel_it2.add(field_formula_it_based2);

        JPanel formula_panel_it3 = new JPanel();

        formula_panel_it3.add(new JLabel("Every 3rd iteration, z ="));
        formula_panel_it3.add(field_formula_it_based3);

        JPanel formula_panel_it4 = new JPanel();

        formula_panel_it4.add(new JLabel("Every 4th iteration, z ="));
        formula_panel_it4.add(field_formula_it_based4);

        JLabel bail2 = new JLabel("Bailout Technique:");
        bail2.setFont(new Font("Arial", Font.BOLD, 11));

        String[] method42 = {"Escaping Algorithm", "Converging Algorithm", "Escaping or Converging Algorithm"};

        JComboBox<String> method42_choice = new JComboBox<>(method42);
        method42_choice.setSelectedIndex(s.fns.bail_technique);
        method42_choice.setToolTipText("Selects the bailout technique.");
        method42_choice.setFocusable(false);

        Object[] labels32 = ptra.createUserFormulaLabels("z, c, s, c0, pixel, p, pp, n, maxn, center, size, sizei, width, height, v1 - v30, point");

        Object[] message32 = {
            labels32,
            bail2,
            method42_choice,
            " ",
            "Insert your formulas. Different formula will be evaluated in every iteration.",
            formula_panel_it1,
            formula_panel_it2,
            formula_panel_it3,
            formula_panel_it4,};

        optionPane = new JOptionPane(message32, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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

                        boolean temp_bool = false;

                        try {
                            s.parser.parse(field_formula_it_based1.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the 1st iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            temp_bool = temp_bool || s.parser.foundC();

                            s.parser.parse(field_formula_it_based2.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the 2nd iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            temp_bool = temp_bool || s.parser.foundC();

                            s.parser.parse(field_formula_it_based3.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the 3rd iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            temp_bool = temp_bool || s.parser.foundC();

                            s.parser.parse(field_formula_it_based4.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the 4th iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            temp_bool = temp_bool || s.parser.foundC();

                            s.fns.user_formula_iteration_based[0] = field_formula_it_based1.getText();
                            s.fns.user_formula_iteration_based[1] = field_formula_it_based2.getText();
                            s.fns.user_formula_iteration_based[2] = field_formula_it_based3.getText();
                            s.fns.user_formula_iteration_based[3] = field_formula_it_based4.getText();
                            s.userFormulaHasC = temp_bool;
                            s.fns.bail_technique = method42_choice.getSelectedIndex();

                            ptra.setUserFormulaOptions(true);
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
