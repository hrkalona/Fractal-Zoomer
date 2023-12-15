/*
 * Copyright (C) 2020 hrkalona
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
import fractalzoomer.parser.ParserException;
import fractalzoomer.utils.ColorSpaceConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona
 */
public class InTrueColorDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public InTrueColorDialog(MainWindow ptr, Settings s) {

        super(ptr);

        ptra = ptr;

        setTitle("In True Coloring Mode");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        final JCheckBox true_color = new JCheckBox("True Coloring");
        true_color.setSelected(s.fns.tcs.trueColorIn);
        true_color.setFocusable(false);

        ButtonGroup true_color_group = new ButtonGroup();
        final JRadioButton presetButton = new JRadioButton("Preset");
        presetButton.setFocusable(false);

        presetButton.setToolTipText("Uses a preset true coloring algorithm.");

        final JRadioButton userDefinedButton = new JRadioButton("User Defined");
        userDefinedButton.setToolTipText("Uses a user defined true coloring algorithm.");
        userDefinedButton.setFocusable(false);

        true_color_group.add(presetButton);
        true_color_group.add(userDefinedButton);

        final JComboBox<String> true_color_presets_opt = new JComboBox<>(Constants.trueColorModes);
        true_color_presets_opt.setSelectedIndex(s.fns.tcs.trueColorInPreset);
        true_color_presets_opt.setFocusable(false);
        true_color_presets_opt.setToolTipText("Sets the true coloring mode preset.");

        JPanel mode = new JPanel();

        mode.add(presetButton);
        mode.add(true_color_presets_opt);
        mode.add(new JLabel(" "));
        mode.add(userDefinedButton);

        JTextField field_c1 = new JTextField(MainWindow.runsOnWindows ? 50 : 45);//48
        field_c1.setText(s.fns.tcs.inTcComponent1);

        JTextField field_c2 = new JTextField(MainWindow.runsOnWindows ? 50 : 45);//48
        field_c2.setText(s.fns.tcs.inTcComponent2);

        JTextField field_c3 = new JTextField(MainWindow.runsOnWindows ? 50 : 45);//48
        field_c3.setText(s.fns.tcs.inTcComponent3);

        JPanel c1_panel = new JPanel();
        c1_panel.setLayout(new FlowLayout());
        JLabel c1Label = new JLabel();
        c1_panel.add(c1Label);
        c1_panel.add(field_c1);

        JPanel c2_panel = new JPanel();
        c2_panel.setLayout(new FlowLayout());
        JLabel c2Label = new JLabel();
        c2_panel.add(c2Label);
        c2_panel.add(field_c2);

        JPanel c3_panel = new JPanel();
        c3_panel.setLayout(new FlowLayout());
        JLabel c3Label = new JLabel();
        c3_panel.add(c3Label);
        c3_panel.add(field_c3);

        setLabels(c1Label, c2Label, c3Label, s.fns.tcs.inTcColorSpace);

        final JComboBox<String> color_space_opt = new JComboBox<>(Constants.trueColorSpaces);
        color_space_opt.setSelectedIndex(s.fns.tcs.inTcColorSpace);
        color_space_opt.setFocusable(false);
        color_space_opt.setToolTipText("Sets the true coloring mode color space.");

        JLabel infoLabel = new JLabel("Set the color values. Only the real component of the complex number will be used.");
        JLabel valueLabel = new JLabel("The color values must be scaled between 0 and 1.");

        c2_panel.setVisible(s.fns.tcs.inTcColorSpace != ColorSpaceConverter.DIRECT && s.fns.tcs.inTcColorSpace != ColorSpaceConverter.PALETTE && s.fns.tcs.inTcColorSpace != ColorSpaceConverter.GRADIENT);
        c3_panel.setVisible(s.fns.tcs.inTcColorSpace != ColorSpaceConverter.DIRECT && s.fns.tcs.inTcColorSpace != ColorSpaceConverter.PALETTE && s.fns.tcs.inTcColorSpace != ColorSpaceConverter.GRADIENT);
        valueLabel.setVisible(s.fns.tcs.inTcColorSpace != ColorSpaceConverter.DIRECT && s.fns.tcs.inTcColorSpace != ColorSpaceConverter.PALETTE && s.fns.tcs.inTcColorSpace != ColorSpaceConverter.GRADIENT);

        color_space_opt.addActionListener(e -> {
            setLabels(c1Label, c2Label, c3Label, color_space_opt.getSelectedIndex());
            c2_panel.setVisible(color_space_opt.getSelectedIndex() != ColorSpaceConverter.DIRECT && color_space_opt.getSelectedIndex() != ColorSpaceConverter.PALETTE && color_space_opt.getSelectedIndex() != ColorSpaceConverter.GRADIENT);
            c3_panel.setVisible(color_space_opt.getSelectedIndex() != ColorSpaceConverter.DIRECT && color_space_opt.getSelectedIndex() != ColorSpaceConverter.PALETTE && color_space_opt.getSelectedIndex() != ColorSpaceConverter.GRADIENT);
            valueLabel.setVisible(color_space_opt.getSelectedIndex() != ColorSpaceConverter.DIRECT && color_space_opt.getSelectedIndex() != ColorSpaceConverter.PALETTE && color_space_opt.getSelectedIndex() != ColorSpaceConverter.GRADIENT);
            pack();
        });

        JPanel color_space_panel = new JPanel();
        JLabel color_space_label = new JLabel("Color Space: ");
        color_space_panel.add(color_space_label);
        color_space_panel.add(color_space_opt);

        Object[] labels3 = ptra.createUserFormulaLabels("z, c, s, c0, pixel, p, pp, n, maxn, bail, cbail, center, size, sizei, v1 - v30, point, stat, trap");

        if (s.fns.tcs.trueColorInMode == 0) {
            presetButton.setSelected(true);
        } else {
            userDefinedButton.setSelected(true);
        }

        true_color_presets_opt.setEnabled(s.fns.tcs.trueColorInMode == 0);
        
        infoLabel.setEnabled(s.fns.tcs.trueColorInMode == 1);
        valueLabel.setEnabled(s.fns.tcs.trueColorInMode == 1);
        color_space_opt.setEnabled(s.fns.tcs.trueColorInMode == 1);
        color_space_label.setEnabled(s.fns.tcs.trueColorInMode == 1);

        c1Label.setEnabled(s.fns.tcs.trueColorInMode == 1);
        c2Label.setEnabled(s.fns.tcs.trueColorInMode == 1);
        c3Label.setEnabled(s.fns.tcs.trueColorInMode == 1);

        field_c1.setEnabled(s.fns.tcs.trueColorInMode == 1);
        field_c2.setEnabled(s.fns.tcs.trueColorInMode == 1);
        field_c3.setEnabled(s.fns.tcs.trueColorInMode == 1);

        for (Object obj : labels3) {
            if (obj instanceof JPanel) {

                Component[] comp = ((JPanel) obj).getComponents();
                for (Component componet : comp) {
                    componet.setEnabled(s.fns.tcs.trueColorInMode == 1);
                }
            } else if (obj instanceof JLabel) {
                ((JLabel) obj).setEnabled(s.fns.tcs.trueColorInMode == 1);
            }
        }

        userDefinedButton.addActionListener(e -> {
            true_color_presets_opt.setEnabled(!userDefinedButton.isSelected());
            infoLabel.setEnabled(userDefinedButton.isSelected());
            valueLabel.setEnabled(userDefinedButton.isSelected());

            color_space_opt.setEnabled(userDefinedButton.isSelected());
            color_space_label.setEnabled(userDefinedButton.isSelected());

            c1Label.setEnabled(userDefinedButton.isSelected());
            c2Label.setEnabled(userDefinedButton.isSelected());
            c3Label.setEnabled(userDefinedButton.isSelected());

            field_c1.setEnabled(userDefinedButton.isSelected());
            field_c2.setEnabled(userDefinedButton.isSelected());
            field_c3.setEnabled(userDefinedButton.isSelected());

            for (Object obj : labels3) {
                if (obj instanceof JPanel) {
                    Component[] comp = ((JPanel) obj).getComponents();
                    for (Component componet : comp) {
                        componet.setEnabled(userDefinedButton.isSelected());
                    }
                } else if (obj instanceof JLabel) {
                    ((JLabel) obj).setEnabled(userDefinedButton.isSelected());
                }
            }
        });

        presetButton.addActionListener(e -> {
            true_color_presets_opt.setEnabled(presetButton.isSelected());
            infoLabel.setEnabled(!presetButton.isSelected());
            valueLabel.setEnabled(!presetButton.isSelected());

            color_space_opt.setEnabled(!presetButton.isSelected());
            color_space_label.setEnabled(!presetButton.isSelected());

            c1Label.setEnabled(!presetButton.isSelected());
            c2Label.setEnabled(!presetButton.isSelected());
            c3Label.setEnabled(!presetButton.isSelected());

            field_c1.setEnabled(!presetButton.isSelected());
            field_c2.setEnabled(!presetButton.isSelected());
            field_c3.setEnabled(!presetButton.isSelected());

            for (Object obj : labels3) {
                if (obj instanceof JPanel) {
                    Component[] comp = ((JPanel) obj).getComponents();
                    for (Component componet : comp) {
                        componet.setEnabled(!presetButton.isSelected());
                    }
                } else if (obj instanceof JLabel) {
                    ((JLabel) obj).setEnabled(!presetButton.isSelected());
                }
            }
        });

        Object[] message3 = {
            true_color,
            mode,
            " ",
            labels3,
            " ",           
            infoLabel,
            valueLabel,
            " ",
            color_space_panel,
            c1_panel,
            c2_panel,
            c3_panel};

        optionPane = new JOptionPane(message3, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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

                            if (userDefinedButton.isSelected()) {
                                s.parser.parse(field_c1.getText());

                                if (s.parser.foundR()) {
                                    JOptionPane.showMessageDialog(ptra, "The variable: r cannot be used in the formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                if (s.isConvergingType()) {
                                    if (s.parser.foundBail()) {
                                        JOptionPane.showMessageDialog(ptra, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                } else if (s.parser.foundCbail()) {
                                    JOptionPane.showMessageDialog(ptra, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                                if (color_space_opt.getSelectedIndex() != ColorSpaceConverter.DIRECT && color_space_opt.getSelectedIndex() != ColorSpaceConverter.PALETTE && color_space_opt.getSelectedIndex() != ColorSpaceConverter.GRADIENT) {
                                    s.parser.parse(field_c2.getText());

                                    if (s.parser.foundR()) {
                                        JOptionPane.showMessageDialog(ptra, "The variable: r cannot be used in the formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }

                                    if (s.isConvergingType()) {
                                        if (s.parser.foundBail()) {
                                            JOptionPane.showMessageDialog(ptra, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                            return;
                                        }
                                    } else if (s.parser.foundCbail()) {
                                        JOptionPane.showMessageDialog(ptra, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }

                                    s.parser.parse(field_c3.getText());

                                    if (s.parser.foundR()) {
                                        JOptionPane.showMessageDialog(ptra, "The variable: r cannot be used in the formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }

                                    if (s.isConvergingType()) {
                                        if (s.parser.foundBail()) {
                                            JOptionPane.showMessageDialog(ptra, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                            return;
                                        }
                                    } else if (s.parser.foundCbail()) {
                                        JOptionPane.showMessageDialog(ptra, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }

                            s.fns.tcs.trueColorIn = true_color.isSelected();

                            if (presetButton.isSelected()) {
                                s.fns.tcs.trueColorInMode = 0;
                                s.fns.tcs.trueColorInPreset = true_color_presets_opt.getSelectedIndex();
                            } else {
                                s.fns.tcs.trueColorInMode = 1;

                                s.fns.tcs.inTcColorSpace = color_space_opt.getSelectedIndex();

                                s.fns.tcs.inTcComponent1 = field_c1.getText();

                                if (s.fns.tcs.inTcColorSpace != ColorSpaceConverter.DIRECT && s.fns.tcs.inTcColorSpace != ColorSpaceConverter.PALETTE && s.fns.tcs.inTcColorSpace != ColorSpaceConverter.GRADIENT) {
                                    s.fns.tcs.inTcComponent2 = field_c2.getText();
                                    s.fns.tcs.inTcComponent3 = field_c3.getText();
                                }
                            }
                        } catch (ParserException ex) {
                            JOptionPane.showMessageDialog(ptra, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setInTrueColorModePost();
                    }
                });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

    private void setLabels(JLabel l1, JLabel l2, JLabel l3, int space) {

        switch (space) {
            case ColorSpaceConverter.RGB:
                l1.setText("R =");
                l2.setText("G =");
                l3.setText("B =");
                break;
            case ColorSpaceConverter.XYZ:
                l1.setText("X =");
                l2.setText("Y =");
                l3.setText("Z =");
                break;
            case ColorSpaceConverter.HSB:
                l1.setText("H =");
                l2.setText("S =");
                l3.setText("B =");
                break;
            case ColorSpaceConverter.HWB:
                l1.setText("H =");
                l2.setText("W =");
                l3.setText("B =");
                break;
            case ColorSpaceConverter.HSL:
            case ColorSpaceConverter.HSL_uv:
                l1.setText("H =");
                l2.setText("S =");
                l3.setText("L =");
                break;
            case ColorSpaceConverter.RYB:
                l1.setText("R =");
                l2.setText("Y =");
                l3.setText("B =");
                break;
            case ColorSpaceConverter.LAB:
                l1.setText("L =");
                l2.setText("A =");
                l3.setText("B =");
                break;
            case ColorSpaceConverter.LCH_ab:
            case ColorSpaceConverter.LCH_uv:
            case ColorSpaceConverter.LCH_oklab:
                l1.setText("L =");
                l2.setText("C =");
                l3.setText("H =");
                break;
            case ColorSpaceConverter.DIRECT:
                l1.setText("Direct =");
                break;
            case ColorSpaceConverter.GRADIENT:
                l1.setText("Gradient =");
                break;
            case ColorSpaceConverter.PALETTE:
                l1.setText("Palette =");
                break;
        }
    }

}
