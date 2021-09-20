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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author hrkalona2
 */
public class MullerFormulaDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public MullerFormulaDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Muller Formula");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        String f1 = s.fns.user_fz_formula;

        if (s.fns.function != oldSelected) {
            f1 = "z^3 - 1";
        }

        JTextField field_fz_formula7 = new JTextField(50);
        field_fz_formula7.setText(f1);

        JPanel formula_fz_panel7 = new JPanel();

        formula_fz_panel7.add(new JLabel("f(z) ="));
        formula_fz_panel7.add(field_fz_formula7);

        JPanel images_panel = new JPanel();
        images_panel.setLayout(new GridLayout(5, 1));

        JLabel imagelabel46 = new JLabel();
        imagelabel46.setIcon(getIcon("/fractalzoomer/icons/muller1.png"));
        JPanel imagepanel46 = new JPanel();
        imagepanel46.add(imagelabel46);

        JLabel imagelabel56 = new JLabel();
        imagelabel56.setIcon(getIcon("/fractalzoomer/icons/muller2.png"));
        JPanel imagepanel56 = new JPanel();
        imagepanel56.add(imagelabel56);

        JLabel imagelabel66 = new JLabel();
        imagelabel66.setIcon(getIcon("/fractalzoomer/icons/muller3.png"));
        JPanel imagepanel66 = new JPanel();
        imagepanel66.add(imagelabel66);

        JLabel imagelabel76 = new JLabel();
        imagelabel76.setIcon(getIcon("/fractalzoomer/icons/muller4.png"));
        JPanel imagepanel76 = new JPanel();
        imagepanel76.add(imagelabel76);

        JLabel imagelabel86 = new JLabel();
        imagelabel86.setIcon(getIcon("/fractalzoomer/icons/muller5.png"));
        JPanel imagepanel86 = new JPanel();
        imagepanel86.add(imagelabel86);

        images_panel.add(imagelabel46);
        images_panel.add(imagelabel56);
        images_panel.add(imagelabel66);
        images_panel.add(imagelabel76);
        images_panel.add(imagelabel86);

        JScrollPane scroll_pane2 = new JScrollPane(images_panel);
        scroll_pane2.setPreferredSize(new Dimension(500, 120));
        scroll_pane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        Object[] labels46 = ptra.createUserFormulaLabels("z, s, c0, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

        Object[] message46 = {
            labels46,
            " ",
            scroll_pane2,
            "Insert the function.",
            formula_fz_panel7,};

        optionPane = new JOptionPane(message46, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        s.parser.parse(field_fz_formula7.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC() || s.parser.foundR() || s.parser.foundStat() || s.parser.foundTrap()) {
                            JOptionPane.showMessageDialog(ptra, "The variables: c, bail, cbail, r, stat, trap cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.fns.user_fz_formula = field_fz_formula7.getText();
                    } catch (ParserException ex) {
                        JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ptra.optionsEnableShortcut2();
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
