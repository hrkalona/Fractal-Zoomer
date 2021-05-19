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
import fractalzoomer.parser.ParserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;

/**
 *
 * @author hrkalona2
 */
public class UserFormulaCoupledDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public UserFormulaCoupledDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("User Formula Coupled");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JTextField field_formula_coupled = new JTextField(50);
        field_formula_coupled.setText(s.fns.user_formula_coupled[0]);

        JTextField field_formula_coupled2 = new JTextField(50);
        field_formula_coupled2.setText(s.fns.user_formula_coupled[1]);

        JTextField field_formula_coupled3 = new JTextField(50);
        field_formula_coupled3.setText(s.fns.user_formula_coupled[2]);

        JPanel formula_panel_coupled = new JPanel();

        formula_panel_coupled.add(new JLabel("z ="));
        formula_panel_coupled.add(field_formula_coupled);

        JPanel formula_panel_coupled2 = new JPanel();

        formula_panel_coupled2.add(new JLabel("z2 ="));
        formula_panel_coupled2.add(field_formula_coupled2);

        JPanel formula_panel_coupled3 = new JPanel();

        formula_panel_coupled3.add(new JLabel("z2(0) ="));
        formula_panel_coupled3.add(field_formula_coupled3);

        JLabel bail5 = new JLabel("Bailout Technique:");
        bail5.setFont(new Font("Arial", Font.BOLD, 11));

        String[] method5 = {"Escaping Algorithm", "Converging Algorithm"};

        JComboBox method5_choice = new JComboBox(method5);
        method5_choice.setSelectedIndex(s.fns.bail_technique);
        method5_choice.setToolTipText("Selects the bailout technique.");
        method5_choice.setFocusable(false);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(410, 185));
        tabbedPane.setFocusable(false);

        JPanel formula_panel3 = new JPanel();
        JPanel coupling_options_panel = new JPanel();

        tabbedPane.addTab("Formula", formula_panel3);
        tabbedPane.addTab("Coupling", coupling_options_panel);

        JSlider coupling_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.fns.coupling * 100));
        coupling_slid.setPreferredSize(new Dimension(360, 45));
        coupling_slid.setFocusable(false);
        coupling_slid.setToolTipText("Sets the percentange of coupling.");
        coupling_slid.setPaintLabels(true);

        Hashtable<Integer, JLabel> table3 = new Hashtable<Integer, JLabel>();
        table3.put(0, new JLabel("0.0"));
        table3.put(25, new JLabel("0.25"));
        table3.put(50, new JLabel("0.5"));
        table3.put(75, new JLabel("0.75"));
        table3.put(100, new JLabel("1.0"));
        coupling_slid.setLabelTable(table3);

        JPanel coupling_panel = new JPanel();
        coupling_panel.add(new JLabel("Coupling: "));
        coupling_panel.add(coupling_slid);

        final JTextField field_amplitude = new JTextField(8);
        field_amplitude.setText("" + s.fns.coupling_amplitude);

        final JTextField field_frequency = new JTextField(8);
        field_frequency.setText("" + s.fns.coupling_frequency);

        final JTextField field_seed = new JTextField(8);
        field_seed.setText("" + s.fns.coupling_seed);

        String[] coupling_method_str = {"Simple", "Cosine", "Random"};

        final JComboBox coupling_method_choice = new JComboBox(coupling_method_str);
        coupling_method_choice.setSelectedIndex(s.fns.coupling_method);
        coupling_method_choice.setToolTipText("Selects the coupling method.");
        coupling_method_choice.setFocusable(false);

        JPanel coupling_panel2 = new JPanel();

        coupling_panel2.setPreferredSize(new Dimension(400, 40));
        coupling_panel2.setLayout(new GridLayout(2, 4));
        coupling_panel2.add(new JLabel("Method:", SwingConstants.HORIZONTAL));
        coupling_panel2.add(new JLabel("Amplitude:", SwingConstants.HORIZONTAL));
        coupling_panel2.add(new JLabel("Frequency:", SwingConstants.HORIZONTAL));
        coupling_panel2.add(new JLabel("Seed:", SwingConstants.HORIZONTAL));
        coupling_panel2.add(coupling_method_choice);
        coupling_panel2.add(field_amplitude);
        coupling_panel2.add(field_frequency);
        coupling_panel2.add(field_seed);

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        p1.setPreferredSize(new Dimension(400, 18));
        p1.add(new JLabel("Insert your formulas, that will be evaluated in every iteration."));

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        p2.setPreferredSize(new Dimension(400, 18));
        p2.add(new JLabel("Insert the initial value for the second formula."));

        formula_panel3.add(p1);
        formula_panel3.add(formula_panel_coupled);
        formula_panel3.add(formula_panel_coupled2);
        formula_panel3.add(p2);
        formula_panel3.add(formula_panel_coupled3);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.setPreferredSize(new Dimension(400, 18));
        p3.add(new JLabel("Select the coupling options between the formulas."));

        coupling_options_panel.add(p3);
        coupling_options_panel.add(coupling_panel2);
        coupling_options_panel.add(coupling_panel);

        if (s.fns.coupling_method == 0) {
            field_seed.setEnabled(false);
            field_frequency.setEnabled(false);
            field_amplitude.setEnabled(false);
        } else if (s.fns.coupling_method == 1) {
            field_seed.setEnabled(false);
            field_frequency.setEnabled(true);
            field_amplitude.setEnabled(true);
        } else {
            field_seed.setEnabled(true);
            field_frequency.setEnabled(false);
            field_amplitude.setEnabled(true);
        }

        coupling_method_choice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (coupling_method_choice.getSelectedIndex() == 0) {
                    field_seed.setEnabled(false);
                    field_frequency.setEnabled(false);
                    field_amplitude.setEnabled(false);
                } else if (coupling_method_choice.getSelectedIndex() == 1) {
                    field_seed.setEnabled(false);
                    field_frequency.setEnabled(true);
                    field_amplitude.setEnabled(true);
                } else {
                    field_seed.setEnabled(true);
                    field_frequency.setEnabled(false);
                    field_amplitude.setEnabled(true);
                }
            }
        });

        Object[] labels5 = ptra.createUserFormulaLabels("z, c, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

        Object[] message5 = {
            labels5,
            bail5,
            method5_choice,
            " ",
            tabbedPane,};

        optionPane = new JOptionPane(message5, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        s.parser.parse(field_formula_coupled.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        boolean temp_bool = s.parser.foundC();

                        s.parser.parse(field_formula_coupled2.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: bail, cbail, r, stat, trap cannot be used in the z2 formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.parser.parse(field_formula_coupled3.getText());

                        if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: z, n, s, p, pp, bail, cbail, r, stat, trap cannot be used in the z2(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        double temp_amp, temp_freq;
                        int temp_seed;
                        try {
                            temp_amp = Double.parseDouble(field_amplitude.getText());
                            temp_freq = Double.parseDouble(field_frequency.getText());
                            temp_seed = Integer.parseInt(field_seed.getText());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.fns.user_formula_coupled[0] = field_formula_coupled.getText();
                        s.fns.user_formula_coupled[1] = field_formula_coupled2.getText();
                        s.fns.user_formula_coupled[2] = field_formula_coupled3.getText();
                        s.userFormulaHasC = temp_bool;
                        s.fns.bail_technique = method5_choice.getSelectedIndex();
                        s.fns.coupling = coupling_slid.getValue() / 100.0;
                        s.fns.coupling_amplitude = temp_amp;
                        s.fns.coupling_seed = temp_seed;
                        s.fns.coupling_frequency = temp_freq;
                        s.fns.coupling_method = coupling_method_choice.getSelectedIndex();

                        ptra.setUserFormulaOptions(true);
                    } catch (ParserException ex) {
                        JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ptra.optionsEnableShortcut();
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
