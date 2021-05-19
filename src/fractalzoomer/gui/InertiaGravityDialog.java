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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author hrkalona2
 */
public class InertiaGravityDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public InertiaGravityDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Modified Inertia/Gravity");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());

        p1.add(new JLabel("Inertia Contribution Re: "));
        JTextField inertia_contribution_re = new JTextField(10);
        inertia_contribution_re.setText("" + s.fns.igs.inertia_contribution[0]);
        p1.add(inertia_contribution_re);

        p1.add(new JLabel(" Im: "));
        JTextField inertia_contribution_im = new JTextField(10);
        inertia_contribution_im.setText("" + s.fns.igs.inertia_contribution[1]);
        p1.add(inertia_contribution_im);

        p1.add(new JLabel(" Initial Inertia Re: "));
        JTextField init_inertia_re = new JTextField(10);
        init_inertia_re.setText("" + s.fns.igs.initial_inertia[0]);
        p1.add(init_inertia_re);

        p1.add(new JLabel(" Im: "));
        JTextField init_inertia_im = new JTextField(10);
        init_inertia_im.setText("" + s.fns.igs.initial_inertia[1]);
        p1.add(init_inertia_im);
        
        JComboBox pull_choice = new JComboBox(MainWindow.inertiaGravityPullFunction);
        pull_choice.setSelectedIndex(s.fns.igs.pull_scaling_function);
        pull_choice.setToolTipText("Sets the pull scaling function.");
        pull_choice.setFocusable(false);
        
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        
        p2.add(new JLabel("Scaling Function: "));
        p2.add(pull_choice);
        
        p2.add(new JLabel(" Exponent: "));
        JTextField exponent = new JTextField(10);
        exponent.setText("" + s.fns.igs.inertia_exponent);
        p2.add(exponent);
        
        p2.add(new JLabel(" Timestep Re: "));
        JTextField timestep_re = new JTextField(10);
        timestep_re.setText("" + s.fns.igs.time_step[0]);
        p2.add(timestep_re);

        p2.add(new JLabel(" Im: "));
        JTextField timestep_im = new JTextField(10);
        timestep_im.setText("" + s.fns.igs.time_step[1]);
        p2.add(timestep_im);
        
        exponent.setEnabled(s.fns.igs.pull_scaling_function == MainWindow.PULL_EXP);
        
        pull_choice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exponent.setEnabled(pull_choice.getSelectedIndex() == MainWindow.PULL_EXP);
            }
            
        });

        JPanel[] inertia_panels = new JPanel[s.fns.igs.bodyLocation.length];
        JTextField[] body_re = new JTextField[inertia_panels.length];
        JTextField[] body_im = new JTextField[inertia_panels.length];
        JTextField[] body_gravity_re = new JTextField[inertia_panels.length];
        JTextField[] body_gravity_im = new JTextField[inertia_panels.length];

        for (int k = 0; k < inertia_panels.length; k++) {
            inertia_panels[k] = new JPanel();
            inertia_panels[k].setLayout(new FlowLayout());

            inertia_panels[k].add(new JLabel("[Body " + String.format("%02d", (k + 1)) + "] "));
            inertia_panels[k].add(new JLabel("Strength Re: "));
            body_gravity_re[k] = new JTextField(10);
            body_gravity_re[k].setText("" + s.fns.igs.bodyGravity[k][0]);
            inertia_panels[k].add(body_gravity_re[k]);

            inertia_panels[k].add(new JLabel(" Im: "));
            body_gravity_im[k] = new JTextField(10);
            body_gravity_im[k].setText("" + s.fns.igs.bodyGravity[k][1]);
            inertia_panels[k].add(body_gravity_im[k]);

            inertia_panels[k].add(new JLabel(" Location Re: "));
            body_re[k] = new JTextField(10);
            body_re[k].setText("" + s.fns.igs.bodyLocation[k][0]);
            inertia_panels[k].add(body_re[k]);

            inertia_panels[k].add(new JLabel(" Im: "));
            body_im[k] = new JTextField(10);
            body_im[k].setText("" + s.fns.igs.bodyLocation[k][1]);
            inertia_panels[k].add(body_im[k]);
        }
        

        Object[] magnets = {
            " ",
            "Enter the modified inertia/gravity simulation parameters",
            p1,
            p2,
            inertia_panels[0],
            inertia_panels[1],
            inertia_panels[2],
            inertia_panels[3],
            inertia_panels[4],
            inertia_panels[5],
            inertia_panels[6],
            inertia_panels[7],
            inertia_panels[8],
            inertia_panels[9],
            " "};

        optionPane = new JOptionPane(magnets, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        double[][] temp_bodies = new double[inertia_panels.length][2];
                        double[][] temp_bodies_gravity = new double[inertia_panels.length][2];
                        double[] temp_timestep = new double[2];
                        double[] temp_inertia_contribution = new double[2];
                        double[] temp_init_inertia = new double[2];

                        for (int k = 0; k < inertia_panels.length; k++) {
                            temp_bodies[k][0] = Double.parseDouble(body_re[k].getText());
                            temp_bodies[k][1] = Double.parseDouble(body_im[k].getText());
                            temp_bodies_gravity[k][0] = Double.parseDouble(body_gravity_re[k].getText());
                            temp_bodies_gravity[k][1] = Double.parseDouble(body_gravity_im[k].getText());
                        }
                        
                        temp_inertia_contribution[0] = Double.parseDouble(inertia_contribution_re.getText());
                        temp_inertia_contribution[1] = Double.parseDouble(inertia_contribution_im.getText());

                        temp_init_inertia[0] = Double.parseDouble(init_inertia_re.getText());
                        temp_init_inertia[1] = Double.parseDouble(init_inertia_im.getText());
                        
                        temp_timestep[0] = Double.parseDouble(timestep_re.getText());
                        temp_timestep[1] = Double.parseDouble(timestep_im.getText());
                       
                        double temp_exponent = Double.parseDouble(exponent.getText());

                        s.fns.igs.bodyLocation = temp_bodies;
                        s.fns.igs.bodyGravity = temp_bodies_gravity;
                        s.fns.igs.inertia_contribution = temp_inertia_contribution;
                        s.fns.igs.initial_inertia = temp_init_inertia;
                        s.fns.igs.pull_scaling_function = pull_choice.getSelectedIndex();
                        s.fns.igs.inertia_exponent = temp_exponent;
                        s.fns.igs.time_step = temp_timestep;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ptra.setInertiaGravitySierpinskiPost();
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
