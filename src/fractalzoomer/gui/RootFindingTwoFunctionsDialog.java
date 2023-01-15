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

import fractalzoomer.core.Derivative;
import fractalzoomer.main.Constants;
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
public class RootFindingTwoFunctionsDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public RootFindingTwoFunctionsDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType, boolean wasMagnetPatakiType) {

        super(ptr);
        
        ptra = ptr;

        setTitle(FractalFunctionsMenu.functionNames[s.fns.function]);
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        String f1 = s.fns.user_fz_formula;
        String f2 = s.fns.user_dfz_formula;
        
        if (s.fns.function != oldSelected) {
            f1 = "z^3 - 1";
            f2 = "3*z^2";
        }

        JTextField field_fz_formula = new JTextField(50);
        field_fz_formula.setText(f1);

        JTextField field_dfz_formula = new JTextField(50);
        field_dfz_formula.setText(f2);

        JPanel formula_fz_panel = new JPanel();

        formula_fz_panel.add(new JLabel("f(z) ="));
        formula_fz_panel.add(field_fz_formula);

        JPanel formula_dfz_panel = new JPanel();

        formula_dfz_panel.add(new JLabel("f'(z) ="));
        formula_dfz_panel.add(field_dfz_formula);
        
        JPanel k_panel = new JPanel();
        JTextField k_real = new JTextField(30);
        k_real.setText("" + s.fns.newton_hines_k[0]);
        JTextField k_imag = new JTextField(30);
        k_imag.setText("" + s.fns.newton_hines_k[1]);
        k_panel.setLayout(new FlowLayout());
        k_panel.add(new JLabel("Newton-Hines k,  Re: "));
        k_panel.add(k_real);
        k_panel.add(new JLabel(" Im: "));
        k_panel.add(k_imag);

        JLabel imagelabel4 = new JLabel();
        
        if (s.fns.function == Constants.NEWTONFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("newton.png"));
        } else if (s.fns.function == Constants.NEWTON_HINESFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("newton_hines.png"));
        }
        else if(s.fns.function == Constants.TRAUB_OSTROWSKIFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("traub_ostrowski.png"));
        }
        else if(s.fns.function == Constants.STIRLINGFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("stirling.png"));
        }
        else if(s.fns.function == Constants.MIDPOINTFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("midpoint.png"));
        }
        else if(s.fns.function == Constants.JARATTFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("jaratt.png"));
        }
        else if(s.fns.function == Constants.JARATT2FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("jaratt2.png"));
        }
        else if(s.fns.function == Constants.THIRDORDERNEWTONFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("third_order_newton.png"));
        }
        else if(s.fns.function == Constants.WEERAKOON_FERNANDOFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("weerakoon_fernando.png"));
        }
        else if(s.fns.function == Constants.CONTRA_HARMONIC_NEWTONFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("contra_harmonic_newton.png"));
        }
        else if(s.fns.function == Constants.CHUN_HAMFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("chun_ham.png"));
        }
        else if(s.fns.function == Constants.CHUN_KIMFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("chun_kim.png"));
        }
        else if(s.fns.function == Constants.EZZATI_SALEKI2FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("ezzati_saleki2.png"));
        }
        else if(s.fns.function == Constants.HOMEIER1FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("homeier1.png"));
        }
        else if(s.fns.function == Constants.CHANGBUM_CHUN1FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("changbum_chun1.png"));
        }
        else if(s.fns.function == Constants.CHANGBUM_CHUN2FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("changbum_chun2.png"));
        }
        else if(s.fns.function == Constants.KING3FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("king3.png"));
        }
        else if(s.fns.function == Constants.HOMEIER2FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("homeier2.png"));
        }
        else if(s.fns.function == Constants.KIM_CHUNFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("kim_chun.png"));
        }
        else if(s.fns.function == Constants.KOU_LI_WANG1FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("kou_li_wang1.png"));
        }
        else if(s.fns.function == Constants.MAHESHWERIFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("maheshweri.png"));
        }
        else if(s.fns.function == Constants.RAFIULLAH1FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("rafiullah1.png"));
        }
        else if(s.fns.function == Constants.CHANGBUM_CHUN3FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("changbum_chun3.png"));
        }
        else if(s.fns.function == Constants.EZZATI_SALEKI1FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("ezzati_saleki1.png"));
        }
        else if(s.fns.function == Constants.FENGFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("feng.png"));
        }
        else if(s.fns.function == Constants.KING1FORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("king1.png"));
        }
        else if(s.fns.function == Constants.NOOR_GUPTAFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("noor_gupta.png"));
        }
        else if(s.fns.function == Constants.HARMONIC_SIMPSON_NEWTONFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("harmonic_simpson_newton.png"));
        }
        else if(s.fns.function == Constants.NEDZHIBOVFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("nedzhibov.png"));
        }
        else if(s.fns.function == Constants.SIMPSON_NEWTONFORMULA) {
            imagelabel4.setIcon(MainWindow.getIcon("simpson_newton.png"));
        }

        JPanel imagepanel4 = new JPanel();
        imagepanel4.add(imagelabel4);
        
        JPanel derivativePanel = new JPanel();
        
        JComboBox<String> derivative_choice = new JComboBox<>(Constants.derivativeMethod);
        derivative_choice.setSelectedIndex(s.fns.derivative_method);
        derivative_choice.setToolTipText("Selects the derivative method.");
        derivative_choice.setFocusable(false);
        
        derivativePanel.add(new JLabel("Derivative: "));
        derivativePanel.add(derivative_choice);
        
        formula_dfz_panel.setVisible(s.fns.derivative_method == Derivative.DISABLED);
        
        derivative_choice.addActionListener(e -> {
            formula_dfz_panel.setVisible(derivative_choice.getSelectedIndex() == Derivative.DISABLED);
            pack();
        });

        Object[] labels4 = ptra.createUserFormulaLabels("z, s, pixel, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

        Object[] message4 = {
            labels4,
            " ",
            imagepanel4,
            " ",
            derivativePanel,
            "Insert the function and its derivative (if required).",
            formula_fz_panel,
            formula_dfz_panel,
            s.fns.function == Constants.NEWTON_HINESFORMULA ? " " : "",
            s.fns.function == Constants.NEWTON_HINESFORMULA ? k_panel : ""};

        optionPane = new JOptionPane(message4, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            double temp_re2 = Double.parseDouble(k_real.getText());
                            double temp_im2 = Double.parseDouble(k_imag.getText());

                            s.parser.parse(field_fz_formula.getText());
                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC0() || s.parser.foundC() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: c, c0, bail, cbail, r, stat, trap cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.parser.parse(field_dfz_formula.getText());

                            if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC() || s.parser.foundC0() ||  s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                                JOptionPane.showMessageDialog(ptra, "The variables: c, c0, bail, cbail, r, stat, trap cannot be used in the f'(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.fns.user_fz_formula = field_fz_formula.getText();
                            s.fns.user_dfz_formula = field_dfz_formula.getText();

                            s.fns.derivative_method = derivative_choice.getSelectedIndex();
                            Derivative.DERIVATIVE_METHOD = s.fns.derivative_method;

                            if(s.fns.function == Constants.NEWTON_HINESFORMULA) {
                                s.fns.newton_hines_k[0] = temp_re2;
                                s.fns.newton_hines_k[1] = temp_im2;
                            }
                        } catch (ParserException ex) {
                            JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
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
