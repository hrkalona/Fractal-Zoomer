
package fractalzoomer.gui;

import fractalzoomer.core.Derivative;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.parser.ParserException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class RootFindingThreeFunctionsDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public RootFindingThreeFunctionsDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType, boolean wasMagnetPatakiType) {

        super(ptr);
        
        ptra = ptr;

        setTitle(FractalFunctionsMenu.functionNames[s.fns.function]);
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());
        
        String f1 = s.fns.user_fz_formula;
        String f2 = s.fns.user_dfz_formula;
        String f3 = s.fns.user_ddfz_formula;
        
        if (s.fns.function != oldSelected) {
            f1 = "z^3 - 1";
            f2 = "3*z^2";
            f3 = "6*z";
        }

        JTextField field_fz_formula2 = new JTextField(50);
        field_fz_formula2.setText(f1);

        JTextField field_dfz_formula2 = new JTextField(50);
        field_dfz_formula2.setText(f2);

        JTextField field_ddfz_formula2 = new JTextField(50);
        field_ddfz_formula2.setText(f3);

        JPanel formula_fz_panel2 = new JPanel();

        formula_fz_panel2.add(new JLabel("f(z) ="));
        formula_fz_panel2.add(field_fz_formula2);

        JPanel formula_dfz_panel2 = new JPanel();

        formula_dfz_panel2.add(new JLabel("f'(z) ="));
        formula_dfz_panel2.add(field_dfz_formula2);

        JPanel formula_ddfz_panel2 = new JPanel();

        formula_ddfz_panel2.add(new JLabel("f''(z) ="));
        formula_ddfz_panel2.add(field_ddfz_formula2);

        JLabel imagelabel41 = new JLabel();

        if (s.fns.function == HALLEYFORMULA) {
            imagelabel41.setIcon(MainWindow.getIcon("halley.png"));
        } else if (s.fns.function == SCHRODERFORMULA) {
            imagelabel41.setIcon(MainWindow.getIcon("schroder.png"));
        } else if (s.fns.function == HOUSEHOLDERFORMULA) {
            imagelabel41.setIcon(MainWindow.getIcon("householder.png"));
        } else if (s.fns.function == PARHALLEYFORMULA) {
            imagelabel41.setIcon(MainWindow.getIcon("parhalley.png"));
        }
        else if (s.fns.function == WHITTAKERFORMULA) {
            imagelabel41.setIcon(MainWindow.getIcon("whittaker.png"));
        }
        else if (s.fns.function == WHITTAKERDOUBLECONVEXFORMULA) {
            imagelabel41.setIcon(MainWindow.getIcon("whittaker_double_convex.png"));
        }
        else if (s.fns.function == SUPERHALLEYFORMULA) {
            imagelabel41.setIcon(MainWindow.getIcon("super_halley.png"));
        }
        else if(s.fns.function == EULER_CHEBYSHEVFORMULA) {
            imagelabel41.setIcon(MainWindow.getIcon("euler_chebyshev.png"));
        }
        else if(s.fns.function == ABBASBANDY2FORMULA) {
            imagelabel41.setIcon(MainWindow.getIcon("abbasbandy2.png"));
        }
        else if(s.fns.function == POPOVSKI1FORMULA) {
            imagelabel41.setIcon(MainWindow.getIcon("popovski1.png"));
        }
        else if(s.fns.function == RAFIS_RAFIULLAHFORMULA) {
            imagelabel41.setIcon(MainWindow.getIcon("rafis_rafiullah.png"));
        }

        JPanel imagepanel41 = new JPanel();
        imagepanel41.add(imagelabel41);
        
        JPanel derivativePanel = new JPanel();
        
        JComboBox<String> derivative_choice = new JComboBox<>(Constants.derivativeMethod);
        derivative_choice.setSelectedIndex(s.fns.derivative_method);
        derivative_choice.setToolTipText("Selects the derivative method.");
        derivative_choice.setFocusable(false);
        
        derivativePanel.add(new JLabel("Derivative: "));
        derivativePanel.add(derivative_choice);
        
        formula_dfz_panel2.setVisible(s.fns.derivative_method == Derivative.DISABLED);
        formula_ddfz_panel2.setVisible(s.fns.derivative_method == Derivative.DISABLED);
        
        derivative_choice.addActionListener(e -> {
            formula_dfz_panel2.setVisible(derivative_choice.getSelectedIndex() == Derivative.DISABLED);
            formula_ddfz_panel2.setVisible(derivative_choice.getSelectedIndex() == Derivative.DISABLED);
            pack();
        });

        Object[] labels41 = ptra.createUserFormulaLabels("z, s, pixel, p, pp, n, maxn, center, size, sizei, width, height, v1 - v30, point");

        Object[] message41 = {
            labels41,
            " ",
            imagepanel41,
            " ",
            derivativePanel,
            "Insert the function and its derivatives (if required).",
            formula_fz_panel2,
            formula_dfz_panel2,
            formula_ddfz_panel2,};

        optionPane = new JOptionPane(message41, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            s.parser.parse(field_fz_formula2.getText());
                            if (s.parser.foundNF() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC() || s.parser.foundC0() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: c, c0, nf, bail, cbail, r, stat, trap cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_dfz_formula2.getText());

                            if (s.parser.foundNF() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC() || s.parser.foundC0() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: c, c0, nf, bail, cbail, r, stat, trap cannot be used in the f'(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_ddfz_formula2.getText());

                            if (s.parser.foundNF() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC() || s.parser.foundC0() ||  s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: c, c0, nf, bail, cbail, r, stat, trap cannot be used in the f''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.fns.user_fz_formula = field_fz_formula2.getText();
                            s.fns.user_dfz_formula = field_dfz_formula2.getText();
                            s.fns.user_ddfz_formula = field_ddfz_formula2.getText();

                            s.fns.derivative_method = derivative_choice.getSelectedIndex();
                            Derivative.DERIVATIVE_METHOD = s.fns.derivative_method;
                        } catch (ParserException ex) {
                            JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        ptra.optionsEnableShortcut2();
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
