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
public class PaletteGradientMergingDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public PaletteGradientMergingDialog(MainWindow ptr, Settings s) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Palette/Gradient Merging");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        JTextField palette_blend_factor_field = new JTextField();
        palette_blend_factor_field.setText("" + s.pbs.gradient_intensity);

        JTextField palette_offset_field = new JTextField();
        palette_offset_field.setText("" + s.pbs.gradient_offset);

        final JCheckBox enable_blend_palette = new JCheckBox("Palette/Gradient Merging");
        enable_blend_palette.setSelected(s.pbs.palette_gradient_merge);
        enable_blend_palette.setFocusable(false);

        JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.pbs.palette_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        final JComboBox merging_method_combo = new JComboBox(Constants.colorMethod);
        merging_method_combo.setSelectedIndex(s.pbs.merging_type);
        merging_method_combo.setFocusable(false);
        merging_method_combo.setToolTipText("Sets the palette/gradient merging method.");

        merging_method_combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color_blend_opt.setEnabled(merging_method_combo.getSelectedIndex() == 3);
            }
        });

        color_blend_opt.setEnabled(s.pbs.merging_type == 3);

        Object[] message = {
            " ",
            enable_blend_palette,
            " ",
            "Set the gradient intensity.",
            "Gradient Intensity:", palette_blend_factor_field,
            " ",
            "Set the gradient offset.",
            "Gradient Offset:", palette_offset_field,
            " ",
            "Set the palette/gradient merging method.",
            "Palette/Gradient Merging method:", merging_method_combo,
            " ",
            "Set the color blending percentage.",
            "Color Blending:", color_blend_opt,
            " "};

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
                        double temp = Double.parseDouble(palette_blend_factor_field.getText());
                        int temp2 = Integer.parseInt(palette_offset_field.getText());

                        if (temp < 0) {
                            JOptionPane.showMessageDialog(ptra, "The gradient intensity must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (temp2 < 0) {
                            JOptionPane.showMessageDialog(ptra, "The gradient offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        s.pbs.gradient_intensity = temp;
                        s.pbs.palette_gradient_merge = enable_blend_palette.isSelected();
                        s.pbs.merging_type = merging_method_combo.getSelectedIndex();
                        s.pbs.palette_blending = color_blend_opt.getValue() / 100.0;
                        s.pbs.gradient_offset = temp2;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                    ptra.setPaletteGradientMergingPost();
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
