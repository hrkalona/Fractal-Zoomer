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

import fractalzoomer.core.TaskDraw;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 *
 * @author hrkalona2
 */
public class HueGeneratedPalettes extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public HueGeneratedPalettes(MainWindow ptr, Settings s) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Hue Generated Palettes");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField sat = new JTextField(10);
        sat.setText("" + s.hsb_constant_s);
        JTextField bright = new JTextField(10);
        bright.setText("" + s.hsb_constant_b);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Saturation:"));
        panel.add(new JLabel("Brightness:"));

        JPanel p12 = new JPanel();
        p12.add(sat);
        JPanel p13 = new JPanel();
        p13.add(bright);
        panel.add(p12);
        panel.add(p13);


        JTextField lightnessab = new JTextField(10);
        lightnessab.setText("" + s.lchab_constant_l);
        JTextField chromaab = new JTextField(10);
        chromaab.setText("" + s.lchab_constant_c);

        JPanel panel2 = new JPanel(new GridLayout(2, 2));
        panel2.add(new JLabel("Lightness:"));
        panel2.add(new JLabel("Chroma:"));

        JPanel p121 = new JPanel();
        p121.add(lightnessab);
        JPanel p131 = new JPanel();
        p131.add(chromaab);
        panel2.add(p121);
        panel2.add(p131);


        JTextField lightnessuv = new JTextField(10);
        lightnessuv.setText("" + s.lchuv_constant_l);
        JTextField chromauv = new JTextField(10);
        chromauv.setText("" + s.lchuv_constant_c);

        JPanel panel3 = new JPanel(new GridLayout(2, 2));
        panel3.add(new JLabel("Lightness:"));
        panel3.add(new JLabel("Chroma:"));

        JPanel p1211 = new JPanel();
        p1211.add(lightnessuv);
        JPanel p1311 = new JPanel();
        p1311.add(chromauv);
        panel3.add(p1211);
        panel3.add(p1311);

        Object[] message = {
            " ",
                "Set the rest of the color space parameters for every\ncoloring algorithm that uses the following hue generated palettes.",
                " ",
            "HSB Color Space:",
            panel,
                " ",
                "LCH_ab Color Space:",
                panel2,
                " ",
                "LCH_uv Color Space:",
                panel3,
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

                        float saturation, brightness;
                        double lightness1, chroma1;
                        double lightness2, chroma2;
                        try {
                            saturation = Float.parseFloat(sat.getText());
                            brightness = Float.parseFloat(bright.getText());
                            lightness1 = Double.parseDouble(lightnessab.getText());
                            chroma1 = Double.parseDouble(chromaab.getText());
                            lightness2 = Double.parseDouble(lightnessuv.getText());
                            chroma2 = Double.parseDouble(chromauv.getText());

                            if(saturation < 0 || saturation > 1) {
                                JOptionPane.showMessageDialog(ptra, "Saturation be in the range [0, 1].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(brightness < 0 || brightness > 1) {
                                JOptionPane.showMessageDialog(ptra, "Brightness be in the range [0, 1].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(lightness1 < 0 || lightness1 > 100) {
                                JOptionPane.showMessageDialog(ptra, "Lightness (ab) be in the range [0, 100].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(chroma1 < 0 || chroma1 > 133.81) {
                                JOptionPane.showMessageDialog(ptra, "Chroma (ab) be in the range [0, 133.81].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(lightness2 < 0 || lightness2 > 100) {
                                JOptionPane.showMessageDialog(ptra, "Lightness (uv) be in the range [0, 100].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(chroma2 < 0 || chroma2 > 179.08) {
                                JOptionPane.showMessageDialog(ptra, "Chroma (uv) be in the range [0, 179.08].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }


                            TaskDraw.HSB_CONSTANT_B = s.hsb_constant_b = brightness;
                            TaskDraw.HSB_CONSTANT_S = s.hsb_constant_s = saturation;
                            TaskDraw.LCHab_CONSTANT_L = s.lchab_constant_l = lightness1;
                            TaskDraw.LCHab_CONSTANT_C = s.lchab_constant_c = chroma1;
                            TaskDraw.LCHuv_CONSTANT_L = s.lchuv_constant_l = lightness2;
                            TaskDraw.LCHuv_CONSTANT_C = s.lchuv_constant_c = chroma2;

                            TaskDraw.setAlgorithmColors();

                            ptr.updateColorPalettesMenu();
                            ptr.updateColors(false);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

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
