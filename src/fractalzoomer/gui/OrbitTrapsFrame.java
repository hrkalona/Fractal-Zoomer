/*
 * Copyright (C) 2018 hrkalona
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
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class OrbitTrapsFrame extends JFrame {

    private MainWindow ptra2;
    private JFrame this_frame;
    private JTextField trap_norm_field;
    private JComboBox orbit_traps_combo;
    private JTextField trap_width_field;
    private JCheckBox useSpecialColorAsBg_check;
    private JSlider blend_opt;

    public OrbitTrapsFrame(MainWindow ptra, OrbitTrapSettings ots) {

        super();
        ptra2 = ptra;
        this_frame = this;

        ptra2.setEnabled(false);
        int color_window_width = 700;
        int color_window_height = 335;
        setTitle("Orbit Traps");
        setSize(color_window_width, color_window_height);
        setIconImage(getIcon("/fractalzoomer/icons/orbit_traps.png").getImage());
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (color_window_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (color_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        JPanel options_panel = new JPanel();
        options_panel.setPreferredSize(new Dimension(600, 190));
        options_panel.setBackground(MainWindow.bg_color);
        options_panel.setLayout(new FlowLayout());

        final JCheckBox orbit_traps_opt = new JCheckBox("Orbit Traps");
        orbit_traps_opt.setFocusable(false);
        orbit_traps_opt.setToolTipText("Applies a coloring effect when an orbit gets trapped under a specific condition.");
        orbit_traps_opt.setBackground(MainWindow.bg_color);
        orbit_traps_opt.setSelected(ots.useTraps);

        ComponentTitledBorder options_border = new ComponentTitledBorder(orbit_traps_opt, options_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        options_border.setCheckBoxListener();

        options_panel.setBorder(options_border);

        orbit_traps_combo = new JComboBox(MainWindow.orbitTrapsNames);
        orbit_traps_combo.setSelectedIndex(ots.trapType);
        orbit_traps_combo.setFocusable(false);
        orbit_traps_combo.setToolTipText("Sets the orbit shape.");

        orbit_traps_combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggled(true);
            }

        });

        trap_norm_field = new JTextField(10);
        trap_norm_field.addAncestorListener(new RequestFocusListener());
        trap_norm_field.setText("" + ots.trapNorm);

        JPanel p1 = new JPanel();
        p1.setBackground(MainWindow.bg_color);

        p1.add(new JLabel("Shape: "));
        p1.add(orbit_traps_combo);
        p1.add(new JLabel("  Norm: "));
        p1.add(trap_norm_field);

        final JTextField real_textfield = new JTextField(15);
        real_textfield.setText("" + ots.trapPoint[0]);

        final JTextField imaginary_textfield = new JTextField(15);
        imaginary_textfield.setText("" + ots.trapPoint[1]);

        JPanel p2 = new JPanel();
        p2.setBackground(MainWindow.bg_color);

        p2.add(new JLabel("Center Real: "));
        p2.add(real_textfield);
        p2.add(new JLabel("  Center Imaginary: "));
        p2.add(imaginary_textfield);

        final JTextField trap_length_field = new JTextField(10);
        trap_length_field.setText("" + ots.trapLength);

        trap_width_field = new JTextField(10);
        trap_width_field.setText("" + ots.trapWidth);
        
        final JTextField trap_threshold_field = new JTextField(10);
        trap_threshold_field.setText("" + ots.trapMaxDistance);

        JPanel p3 = new JPanel();
        p3.setBackground(MainWindow.bg_color);

        p3.add(new JLabel("Length: "));
        p3.add(trap_length_field);
        p3.add(new JLabel("  Width: "));
        p3.add(trap_width_field);
        p3.add(new JLabel("  Max Distance: "));
        p3.add(trap_threshold_field);

        blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        blend_opt.setValue((int) (100 * ots.trapBlending));
        blend_opt.setBackground(MainWindow.bg_color);
        blend_opt.setMajorTickSpacing(25);
        blend_opt.setMinorTickSpacing(1);
        blend_opt.setToolTipText("Sets the trap blending percentage.");
        blend_opt.setFocusable(false);
        blend_opt.setPaintLabels(true);

        useSpecialColorAsBg_check = new JCheckBox("Use Special Color as Background");
        useSpecialColorAsBg_check.setSelected(ots.trapUseSpecialColor);
        useSpecialColorAsBg_check.setBackground(MainWindow.bg_color);
        useSpecialColorAsBg_check.setFocusable(false);
        useSpecialColorAsBg_check.setToolTipText("Sets the special color to be the background color.");

        useSpecialColorAsBg_check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (useSpecialColorAsBg_check.isSelected()) {
                    blend_opt.setEnabled(false);
                } else {
                    blend_opt.setEnabled(true);
                }
            }

        });
        JPanel p4 = new JPanel();
        p4.setBackground(MainWindow.bg_color);

        p4.add(new JLabel("Trap Blending: "));
        p4.add(blend_opt);
        p4.add(useSpecialColorAsBg_check);

        options_panel.add(p1);
        options_panel.add(p2);
        options_panel.add(p3);
        options_panel.add(p4);

        JButton ok = new JButton("Ok");
        ok.setFocusable(false);

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                double temp, temp2, temp3, temp4, temp5, temp6;
                try {
                    temp = Double.parseDouble(trap_norm_field.getText());
                    temp2 = Double.parseDouble(real_textfield.getText());
                    temp3 = Double.parseDouble(imaginary_textfield.getText());
                    temp4 = Double.parseDouble(trap_length_field.getText());
                    temp5 = Double.parseDouble(trap_width_field.getText());
                    temp6 = Double.parseDouble(trap_threshold_field.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this_frame, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (temp4 <= 0) {
                    JOptionPane.showMessageDialog(this_frame, "Trap Length must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (temp5 <= 0) {
                    JOptionPane.showMessageDialog(this_frame, "Trap Width must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (temp6 <= 0) {
                    JOptionPane.showMessageDialog(this_frame, "Max Distance must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ots.trapType = orbit_traps_combo.getSelectedIndex();
                ots.useTraps = orbit_traps_opt.isSelected();
                ots.trapLength = temp4;
                ots.trapWidth = temp5;
                ots.trapMaxDistance = temp6;
                ots.trapNorm = temp;
                ots.trapPoint[0] = temp2;
                ots.trapPoint[1] = temp3;
                ots.trapBlending = blend_opt.getValue() / 100.0;
                ots.trapUseSpecialColor = useSpecialColorAsBg_check.isSelected();

                ptra2.setOrbitTrapSettings();
                ptra2.setEnabled(true);
                dispose();

            }
        });

        JButton close = new JButton("Cancel");
        close.setFocusable(false);
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptra2.setEnabled(true);
                dispose();

            }
        });

        options_border.setState(orbit_traps_opt.isSelected());
        toggled(orbit_traps_opt.isSelected());

        JPanel buttons = new JPanel();
        buttons.setBackground(MainWindow.bg_color);

        buttons.add(ok);
        buttons.add(close);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(630, 250));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(options_panel, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 1;

        round_panel.add(buttons, con);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new GridBagLayout());
        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;
        main_panel.add(round_panel, con);

        JScrollPane scrollPane = new JScrollPane(main_panel);
        add(scrollPane);

        setVisible(true);
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

    public void toggled(boolean toggled) {

        if (!toggled) {
            return;
        }

        int index = orbit_traps_combo.getSelectedIndex();

        if (index == MainWindow.POINT_N_NORM_TRAP || index == MainWindow.N_NORM_TRAP || index == MainWindow.N_NORM_CROSS_TRAP || index == MainWindow.N_NORM_POINT_TRAP || index == MainWindow.N_NORM_POINT_N_NORM_TRAP) {
            trap_norm_field.setEnabled(true);
        } else {
            trap_norm_field.setEnabled(false);
        }

        if (index == MainWindow.POINT_RHOMBUS_TRAP || index == MainWindow.POINT_SQUARE_TRAP || index == MainWindow.POINT_TRAP || index == MainWindow.POINT_N_NORM_TRAP) {
            trap_width_field.setEnabled(false);
        } else {
            trap_width_field.setEnabled(true);
        }

        if (useSpecialColorAsBg_check.isSelected()) {
            blend_opt.setEnabled(false);
        } else {
            blend_opt.setEnabled(true);
        }

    }

}
