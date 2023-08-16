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

import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class VariablesDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    private final JScrollPane scrollPane;

    public VariablesDialog(MainWindow ptr, Settings s) {

        super(ptr);
        
        ptra = ptr;

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(700, 700));

        setTitle("User Formula Variables");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JPanel[] var_panels = new JPanel[s.fns.variable_re.length];
        JTextField[] variable_re = new JTextField[var_panels.length];
        JTextField[] variable_im = new JTextField[var_panels.length];

        for (int k = 0; k < var_panels.length; k++) {
            var_panels[k] = new JPanel();
            var_panels[k].setLayout(new FlowLayout());

            variable_re[k] = new JTextField(20);
            variable_re[k].setText("" + s.fns.variable_re[k]);

            variable_im[k] = new JTextField(20);
            variable_im[k].setText("" + s.fns.variable_im[k]);

            var_panels[k].add(new JLabel("v" + (k + 1)));
            var_panels[k].add(new JLabel(" Re: "));
            var_panels[k].add(variable_re[k]);
            var_panels[k].add(new JLabel(" Im: "));
            var_panels[k].add(variable_im[k]);
        }



        Object[] poly_poly = {
            " ",
            "Insert the variable values.",
            " ",
            var_panels[0],
            var_panels[1],
            var_panels[2],
            var_panels[3],
            var_panels[4],
            var_panels[5],
            var_panels[6],
            var_panels[7],
            var_panels[8],
            var_panels[9],
            var_panels[10],
                var_panels[11],
                var_panels[12],
                var_panels[13],
                var_panels[14],
                var_panels[15],
                var_panels[16],
                var_panels[17],
                var_panels[18],
                var_panels[19],
                var_panels[20],
                var_panels[21],
                var_panels[22],
                var_panels[23],
                var_panels[24],
                var_panels[25],
                var_panels[26],
                var_panels[27],
                var_panels[28],
                var_panels[29],
            " ",};

        optionPane = new JOptionPane(poly_poly, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            double[] temp_coef = new double[variable_re.length];
                            double[] temp_coef_im = new double[variable_im.length];

                            for (int k = 0; k < variable_re.length; k++) {
                                temp_coef[k] = Double.parseDouble(variable_re[k].getText());
                                temp_coef_im[k] = Double.parseDouble(variable_im[k].getText());
                            }

                            s.fns.variable_re = temp_coef;
                            s.fns.variable_im = temp_coef_im;


                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptr.redraw();

                    }
                });

        //Make this dialog display it.
        scrollPane.setViewportView(optionPane);
        setContentPane(scrollPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

}
