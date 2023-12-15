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
import fractalzoomer.parser.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hrkalona2
 */
public class MagneticPendulumDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public MagneticPendulumDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType, boolean wasMagnetPatakiType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Magnetic Pendulum");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JPanel magnet_p1 = new JPanel();
        magnet_p1.setLayout(new FlowLayout());

        magnet_p1.add(new JLabel("Gravity Re: "));
        MyJSpinner gravity_re = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.gravity[0], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        magnet_p1.add(gravity_re);

        magnet_p1.add(new JLabel(" Im: "));
        MyJSpinner gravity_im = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.gravity[1], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        magnet_p1.add(gravity_im);

        magnet_p1.add(new JLabel(" Friction Re: "));
        MyJSpinner friction_re = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.friction[0], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        magnet_p1.add(friction_re);

        magnet_p1.add(new JLabel(" Im: "));
        MyJSpinner friction_im = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.friction[1], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        magnet_p1.add(friction_im);

        JPanel magnet_p2 = new JPanel();
        magnet_p2.setLayout(new FlowLayout());

        magnet_p2.add(new JLabel("Pendulum Re: "));
        MyJSpinner pendulum_re = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.pendulum[0], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        magnet_p2.add(pendulum_re);

        magnet_p2.add(new JLabel(" Im: "));
        MyJSpinner pendulum_im = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.pendulum[1], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        magnet_p2.add(pendulum_im);

        magnet_p2.add(new JLabel(" Height: "));
        MyJSpinner pendulum_height = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.height, -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        magnet_p2.add(pendulum_height);

        magnet_p2.add(new JLabel(" Stepsize Re: "));
        MyJSpinner pendulum_stepsize_re = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.stepsize, -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        magnet_p2.add(pendulum_stepsize_re);
        
        magnet_p2.add(new JLabel(" Im: "));
        MyJSpinner pendulum_stepsize_im = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.stepsize_im, -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        magnet_p2.add(pendulum_stepsize_im);

        JPanel[] magnet_panels = new JPanel[s.fns.mps.magnetLocation.length];
        MyJSpinner[] magnet_re = new MyJSpinner[magnet_panels.length];
        MyJSpinner[] magnet_im = new MyJSpinner[magnet_panels.length];
        MyJSpinner[] magnet_strength_re = new MyJSpinner[magnet_panels.length];
        MyJSpinner[] magnet_strength_im = new MyJSpinner[magnet_panels.length];

        for (int k = 0; k < magnet_panels.length; k++) {
            magnet_panels[k] = new JPanel();
            magnet_panels[k].setLayout(new FlowLayout());

            magnet_panels[k].add(new JLabel("[Magnet " + String.format("%02d", (k + 1)) + "] "));
            magnet_panels[k].add(new JLabel("Strength Re: "));
            magnet_strength_re[k] = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.magnetStrength[k][0], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
            magnet_panels[k].add(magnet_strength_re[k]);

            magnet_panels[k].add(new JLabel(" Im: "));
            magnet_strength_im[k] = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.magnetStrength[k][1], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
            magnet_panels[k].add(magnet_strength_im[k]);

            magnet_panels[k].add(new JLabel(" Location Re: "));
            magnet_re[k] = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.magnetLocation[k][0], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
            magnet_panels[k].add(magnet_re[k]);

            magnet_panels[k].add(new JLabel(" Im: "));
            magnet_im[k] = new MyJSpinner(10, new SpinnerNumberModel(s.fns.mps.magnetLocation[k][1], -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
            magnet_panels[k].add(magnet_im[k]);
        }
        
        List<String> variables = new ArrayList<>();
        
        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            variables.add("v" + (i + 1));
        }
        
        String[] variablesArr = new String[variables.size()];
        variablesArr = variables.toArray(variablesArr);

        JComboBox<String> variable_choice = new JComboBox<>(variablesArr);
        variable_choice.setSelectedIndex(s.fns.mps.magnetPendVariableId);
        variable_choice.setToolTipText("Exposes the magnetic pendulum accumulated length to the selected variable.");
        variable_choice.setFocusable(false);
        
        JPanel variable_panel = new JPanel();
        variable_panel.add(new JLabel("Expose accumulated length to variable: "));
        variable_panel.add(variable_choice);

        Object[] magnets = {
            " ",
            "Insert the magnetic pendulum simulation parameters",
            magnet_p1,
            magnet_p2,
            magnet_panels[0],
            magnet_panels[1],
            magnet_panels[2],
            magnet_panels[3],
            magnet_panels[4],
            magnet_panels[5],
            magnet_panels[6],
            magnet_panels[7],
            magnet_panels[8],
            magnet_panels[9],
            variable_panel,
            " "};

        optionPane = new JOptionPane(magnets, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            double[][] temp_magnet = new double[magnet_panels.length][2];
                            double[][] temp_magnet_strength = new double[magnet_panels.length][2];
                            double[] temp_gravity = new double[2];
                            double[] temp_friction = new double[2];
                            double[] temp_pendulum = new double[2];

                            for (int k = 0; k < magnet_panels.length; k++) {
                                temp_magnet[k][0] = Double.parseDouble(magnet_re[k].getText());
                                temp_magnet[k][1] = Double.parseDouble(magnet_im[k].getText());
                                temp_magnet_strength[k][0] = Double.parseDouble(magnet_strength_re[k].getText());
                                temp_magnet_strength[k][1] = Double.parseDouble(magnet_strength_im[k].getText());
                            }

                            temp_gravity[0] = Double.parseDouble(gravity_re.getText());
                            temp_gravity[1] = Double.parseDouble(gravity_im.getText());

                            temp_friction[0] = Double.parseDouble(friction_re.getText());
                            temp_friction[1] = Double.parseDouble(friction_im.getText());

                            temp_pendulum[0] = Double.parseDouble(pendulum_re.getText());
                            temp_pendulum[1] = Double.parseDouble(pendulum_im.getText());
                            double temp_height = Double.parseDouble(pendulum_height.getText());
                            double temp_stepsize = Double.parseDouble(pendulum_stepsize_re.getText());
                            double temp_stepsize_im = Double.parseDouble(pendulum_stepsize_im.getText());

                            s.fns.mps.magnetLocation = temp_magnet;
                            s.fns.mps.magnetStrength = temp_magnet_strength;
                            s.fns.mps.gravity = temp_gravity;
                            s.fns.mps.friction = temp_friction;
                            s.fns.mps.pendulum = temp_pendulum;
                            s.fns.mps.height = temp_height;
                            s.fns.mps.stepsize = temp_stepsize;
                            s.fns.mps.stepsize_im = temp_stepsize_im;
                            s.fns.mps.magnetPendVariableId = variable_choice.getSelectedIndex();
                        } catch (Exception ex) {
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
