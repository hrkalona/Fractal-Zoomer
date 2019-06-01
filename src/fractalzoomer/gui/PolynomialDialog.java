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

import static fractalzoomer.main.Constants.BAIRSTOWPOLY;
import static fractalzoomer.main.Constants.DURAND_KERNERPOLY;
import static fractalzoomer.main.Constants.MANDELPOLY;
import static fractalzoomer.main.Constants.NEWTON_HINESPOLY;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

/**
 *
 * @author hrkalona2
 */
public class PolynomialDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public PolynomialDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType) {

        super();
        
        ptra = ptr;

        setTitle("Polynomial coefficients");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JLabel polynomial = new JLabel();
        polynomial.setIcon(getIcon("/fractalzoomer/icons/polynomial.png"));

        JPanel[] poly_panels = new JPanel[s.fns.coefficients.length];
        JTextField[] poly_re = new JTextField[poly_panels.length];
        JTextField[] poly_im = new JTextField[poly_panels.length];

        for (int k = 0; k < poly_panels.length; k++) {
            poly_panels[k] = new JPanel();
            poly_panels[k].setLayout(new FlowLayout());

            JLabel poly_label_k = new JLabel();
            poly_label_k.setIcon(getIcon("/fractalzoomer/icons/a" + (poly_panels.length - 1 - k) + ".png"));
            poly_re[k] = new JTextField(30);
            poly_re[k].setText("" + s.fns.coefficients[k]);

            poly_im[k] = new JTextField(30);
            poly_im[k].setText("" + s.fns.coefficients_im[k]);

            poly_panels[k].add(poly_label_k);
            poly_panels[k].add(new JLabel(" Re: "));
            poly_panels[k].add(poly_re[k]);

            if (s.fns.function != BAIRSTOWPOLY) {
                poly_panels[k].add(new JLabel(" Im: "));
                poly_panels[k].add(poly_im[k]);
            }
        }

        JPanel init_val_panel = new JPanel();
        JTextField init_val_real = new JTextField(30);
        init_val_real.setText("" + s.fns.durand_kerner_init_val[0]);
        JTextField init_val_imag = new JTextField(30);
        init_val_imag.setText("" + s.fns.durand_kerner_init_val[1]);
        init_val_panel.setLayout(new FlowLayout());
        init_val_panel.add(new JLabel("Durand/Kerner Initial Value,  Re: "));
        init_val_panel.add(init_val_real);
        init_val_panel.add(new JLabel(" Im: "));
        init_val_panel.add(init_val_imag);
        
        
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

        Object[] poly_poly = {
            " ",
            "Enter the coefficients of the polynomial,",
            polynomial,
            " ",
            poly_panels[0],
            poly_panels[1],
            poly_panels[2],
            poly_panels[3],
            poly_panels[4],
            poly_panels[5],
            poly_panels[6],
            poly_panels[7],
            poly_panels[8],
            poly_panels[9],
            poly_panels[10],
            " ", s.fns.function == DURAND_KERNERPOLY ? init_val_panel : "",
            s.fns.function == NEWTON_HINESPOLY ? k_panel : ""};

        optionPane = new JOptionPane(poly_poly, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        double[] temp_coef = new double[11];
                        double[] temp_coef_im = new double[11];

                        for (int k = 0; k < poly_re.length; k++) {
                            temp_coef[k] = Double.parseDouble(poly_re[k].getText());
                            temp_coef_im[k] = Double.parseDouble(poly_im[k].getText());
                        }

                        double temp_re = Double.parseDouble(init_val_real.getText());
                        double temp_im = Double.parseDouble(init_val_imag.getText());
                        
                        double temp_re2 = Double.parseDouble(k_real.getText());
                        double temp_im2 = Double.parseDouble(k_imag.getText());

                        boolean non_zero = false;
                        for (int l = 0; l < s.fns.coefficients.length; l++) {
                            if (temp_coef[l] != 0 || temp_coef_im[l] != 0) {
                                non_zero = true;
                                break;
                            }
                        }

                        if (!non_zero) {
                            JOptionPane.showMessageDialog(ptra, "At least one coefficient must be non zero!", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        for (int l = 0; l < s.fns.coefficients.length; l++) {
                            s.fns.coefficients[l] = temp_coef[l] == 0.0 ? 0.0 : temp_coef[l];
                        }

                        for (int l = 0; l < s.fns.coefficients_im.length; l++) {
                            s.fns.coefficients_im[l] = temp_coef_im[l] == 0.0 ? 0.0 : temp_coef_im[l];
                        }

                        s.createPoly();

                        if (s.fns.function == DURAND_KERNERPOLY) {
                            s.fns.durand_kerner_init_val[0] = temp_re;
                            s.fns.durand_kerner_init_val[1] = temp_im;
                        }
                        
                        if(s.fns.function == NEWTON_HINESPOLY) {
                            s.fns.newton_hines_k[0] = temp_re2;
                            s.fns.newton_hines_k[1] = temp_im2;
                        }

                        if (s.fns.function == MANDELPOLY) {
                            ptra.optionsEnableShortcut();
                        } else {
                            ptra.optionsEnableShortcut2();
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

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
