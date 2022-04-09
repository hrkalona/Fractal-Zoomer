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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static fractalzoomer.main.Constants.color_interp_str;

/**
 *
 * @author hrkalona2
 */
public class SmoothingDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public SmoothingDialog(MainWindow ptr, Settings s) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Smoothing");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        final JCheckBox enable_smoothing = new JCheckBox("Smoothing");
        enable_smoothing.setSelected(s.fns.smoothing);
        enable_smoothing.setFocusable(false);

        String[] escaping_algorithm_str = {"Algorithm 1", "Algorithm 2"};

        JComboBox escaping_alg_combo = new JComboBox(escaping_algorithm_str);
        escaping_alg_combo.setSelectedIndex(s.fns.escaping_smooth_algorithm);
        escaping_alg_combo.setFocusable(false);
        escaping_alg_combo.setToolTipText("Sets the smooting algorithm for escaping functions.");

        String[] converging_algorithm_str = {"Algorithm 1", "Algorithm 2"};

        JComboBox converging_alg_combo = new JComboBox(converging_algorithm_str);
        converging_alg_combo.setSelectedIndex(s.fns.converging_smooth_algorithm);
        converging_alg_combo.setFocusable(false);
        converging_alg_combo.setToolTipText("Sets the smooting algorithm for converging functions.");

        final JComboBox combo_box_color_interp = new JComboBox(color_interp_str);
        combo_box_color_interp.setSelectedIndex(s.color_smoothing_method);
        combo_box_color_interp.setFocusable(false);
        combo_box_color_interp.setToolTipText("Sets the color interpolation method.");

        if (s.ds.domain_coloring) {
            escaping_alg_combo.setEnabled(false);
            converging_alg_combo.setEnabled(false);
        }

        if (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1) {
            enable_smoothing.setEnabled(false);
        }

        Object[] message = {
            " ",
            enable_smoothing,
            " ",
            "Set the smoothing algorithm for escaping and converging functions.",
            "Escaping:", escaping_alg_combo,
            "Converging:", converging_alg_combo,
            " ",
            "Set the color interpolation method.",
            combo_box_color_interp,
            " ",};

        optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                        dispose();
                        return;
                    }

                    try {
                        s.fns.smoothing = enable_smoothing.isSelected();
                        s.fns.escaping_smooth_algorithm = escaping_alg_combo.getSelectedIndex();
                        s.fns.converging_smooth_algorithm = converging_alg_combo.getSelectedIndex();
                        s.color_smoothing_method = combo_box_color_interp.getSelectedIndex();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                    ptra.setSmoothingPost();
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
