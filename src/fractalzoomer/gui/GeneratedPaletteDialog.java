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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class GeneratedPaletteDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public GeneratedPaletteDialog(MainWindow ptr, Settings s, boolean outcoloring) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Generated Palette");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());


        JTextField generated_palette_restart_field = new JTextField();
        generated_palette_restart_field.setText("" + (outcoloring ? s.gps.restartGeneratedOutColoringPaletteAt : s.gps.restartGeneratedInColoringPaletteAt));

        final JCheckBox enable_generated_palette = new JCheckBox("Generated Palette");
        enable_generated_palette.setSelected(outcoloring ? s.gps.useGeneratedPaletteOutColoring : s.gps.useGeneratedPaletteInColoring);
        enable_generated_palette.setFocusable(false);

        final JComboBox<String> generated_palettes_combon = new JComboBox<>(Constants.generatedPalettes);
        generated_palettes_combon.setSelectedIndex(outcoloring ? s.gps.generatedPaletteOutColoringId : s.gps.generatedPaletteInColoringId);
        generated_palettes_combon.setFocusable(false);
        generated_palettes_combon.setToolTipText("Sets the generated palette algorithm.");


        Object[] message = {
            " ",
                enable_generated_palette,
                " ",
                "Set the generated palette algorithm.",
                "Generated Palette algorithm:", generated_palettes_combon,
            " ",
            "Set value at which the palette will cycle back.",
            "Cycle Palette:", generated_palette_restart_field,
            " "};

        optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            int temp2 = Integer.parseInt(generated_palette_restart_field.getText());

                            if (temp2 < 0) {
                                JOptionPane.showMessageDialog(ptra, "The generated palette cycle value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 > 2100000000) {
                                JOptionPane.showMessageDialog(ptra, "The generated palette cycle value must be lower than 2100000001.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }


                            if(outcoloring) {
                                s.gps.useGeneratedPaletteOutColoring = enable_generated_palette.isSelected();
                                s.gps.restartGeneratedOutColoringPaletteAt = temp2;
                                s.gps.generatedPaletteOutColoringId = generated_palettes_combon.getSelectedIndex();
                            }
                            else {
                                s.gps.useGeneratedPaletteInColoring = enable_generated_palette.isSelected();
                                s.gps.restartGeneratedInColoringPaletteAt = temp2;
                                s.gps.generatedPaletteInColoringId = generated_palettes_combon.getSelectedIndex();
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setGeneratedPalettePost();
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
