/*
 * Copyright (C) 2019 hrkalona
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

import fractalzoomer.fractal_options.orbit_traps.ImageOrbitTrap;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;

public class OrbitTrapsFrame extends JFrame {
	private static final long serialVersionUID = -4097447483434039100L;
	private MainWindow ptra2;
    private JFrame this_frame;
    private JTextField trap_norm_field;
    private JComboBox orbit_traps_combo;
    private JComboBox lines_function_combo;
    private JTextField trap_width_field;
    private JTextField trap_length_field;
    private JSlider blend_opt;
    private JComboBox color_method_combo;
    private JButton load_image_button;
    private JTextField trap_threshold_field;
    private JTextField trap_intensity_field;
    private JPanel color_options_panel;
    private BufferedImage image;

    public OrbitTrapsFrame(MainWindow ptra, OrbitTrapSettings ots) {

        super();
        ptra2 = ptra;
        this_frame = this;

        ptra2.setEnabled(false);
        int color_window_width = 700;
        int color_window_height = 525;
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
        options_panel.setPreferredSize(new Dimension(600, 380));
        options_panel.setBackground(MainWindow.bg_color);
        options_panel.setLayout(new FlowLayout());

        final JCheckBox orbit_traps_opt = new JCheckBox("Orbit Traps");
        orbit_traps_opt.setFocusable(false);
        orbit_traps_opt.setToolTipText("Applies a coloring effect when an orbit gets trapped under a specific condition.");
        orbit_traps_opt.setBackground(MainWindow.bg_color);
        orbit_traps_opt.setSelected(ots.useTraps);

        ComponentTitledBorder options_border = new ComponentTitledBorder(orbit_traps_opt, options_panel, BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), this_frame);
        options_border.setChangeListener();

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


        load_image_button = new JButton();
        load_image_button.setIcon(getIcon("/fractalzoomer/icons/load_image.png"));
        load_image_button.setFocusable(false);
        load_image_button.setToolTipText("Loads an image to be used as a pattern, with the Image based Trap");
        load_image_button.setPreferredSize(new Dimension(30, 30));

        load_image_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPatternImage(this_frame);
            }
        });

        trap_norm_field = new JTextField(10);
        trap_norm_field.setText("" + ots.trapNorm);
        
        lines_function_combo = new JComboBox(MainWindow.orbitTrapLineTypes);
        lines_function_combo.setSelectedIndex(ots.lineType);
        lines_function_combo.setFocusable(false);
        lines_function_combo.setToolTipText("Sets the function which is applied to the lines.");

        JPanel p1 = new JPanel();
        p1.setBackground(MainWindow.bg_color);

        p1.add(new JLabel("Shape: "));
        p1.add(orbit_traps_combo);
        p1.add(load_image_button);
        p1.add(new JLabel("  Norm: "));
        p1.add(trap_norm_field);
        p1.add(new JLabel("  Lines: "));
        p1.add(lines_function_combo);

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

        trap_length_field = new JTextField(10);
        trap_length_field.setText("" + ots.trapLength);

        trap_width_field = new JTextField(10);
        trap_width_field.setText("" + ots.trapWidth);
        
        trap_threshold_field = new JTextField(10);
        trap_threshold_field.setText("" + ots.trapMaxDistance);
        
        trap_intensity_field = new JTextField(10);
        trap_intensity_field.setText("" + ots.trapIntensity);

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
        
        color_method_combo = new JComboBox(Constants.colorMethod);
        color_method_combo.setSelectedIndex(ots.trapColorMethod);
        color_method_combo.setFocusable(false);
        color_method_combo.setToolTipText("Sets the color method.");
        
        color_method_combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blend_opt.setEnabled(color_method_combo.getSelectedIndex() == 3);
            }         
        });
      
        JPanel p4 = new JPanel();
        p4.setBackground(MainWindow.bg_color);

        p4.add(new JLabel("Color Method: "));
        p4.add(color_method_combo);
        p4.add(new JLabel("  Trap Blending: "));
        p4.add(blend_opt);
        
        final JCheckBox include_escaped_opt = new JCheckBox("Include Escaped");
        include_escaped_opt.setFocusable(false);
        include_escaped_opt.setToolTipText("Includes the escaped points into the trap application.");
        include_escaped_opt.setBackground(MainWindow.bg_color);
        include_escaped_opt.setSelected(ots.trapIncludeEscaped);
        
        final JCheckBox include_notescaped_opt = new JCheckBox("Include Not Escaped");
        include_notescaped_opt.setFocusable(false);
        include_notescaped_opt.setToolTipText("Includes the not escaped points into the trap application.");
        include_notescaped_opt.setBackground(MainWindow.bg_color);
        include_notescaped_opt.setSelected(ots.trapIncludeNotEscaped);
        
        JPanel p5 = new JPanel();
        p5.setBackground(MainWindow.bg_color);
        
        p5.add(new JLabel("Intensity: "));
        p5.add(trap_intensity_field);
        p5.add(include_escaped_opt);
        p5.add(include_notescaped_opt);
        
        final JLabel trap1_color_label = new JLabel();

        trap1_color_label.setPreferredSize(new Dimension(22, 22));
        trap1_color_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        trap1_color_label.setBackground(ots.trapColor1);
        trap1_color_label.setOpaque(true);
        trap1_color_label.setToolTipText("Changes the trap color.");

        trap1_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!trap1_color_label.isEnabled()) {
                    return;
                }

                new ColorChooserFrame(this_frame, "Trap Color", trap1_color_label, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
        
        final JLabel trap2_color_label = new JLabel();

        trap2_color_label.setPreferredSize(new Dimension(22, 22));
        trap2_color_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        trap2_color_label.setBackground(ots.trapColor2);
        trap2_color_label.setOpaque(true);
        trap2_color_label.setToolTipText("Changes the trap color.");

        trap2_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!trap2_color_label.isEnabled()) {
                    return;
                }

                new ColorChooserFrame(this_frame, "Trap Color", trap2_color_label, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
        
        final JLabel trap3_color_label = new JLabel();

        trap3_color_label.setPreferredSize(new Dimension(22, 22));
        trap3_color_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        trap3_color_label.setBackground(ots.trapColor3);
        trap3_color_label.setOpaque(true);
        trap3_color_label.setToolTipText("Changes the trap color.");

        trap3_color_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!trap3_color_label.isEnabled()) {
                    return;
                }

                new ColorChooserFrame(this_frame, "Trap Color", trap3_color_label, -1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
        
        JSlider interpolation_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        interpolation_opt.setValue((int) (100 * ots.trapColorInterpolation));
        interpolation_opt.setBackground(MainWindow.bg_color);
        interpolation_opt.setMajorTickSpacing(25);
        interpolation_opt.setMinorTickSpacing(1);
        interpolation_opt.setToolTipText("Sets the trap color interpolation percentage.");
        interpolation_opt.setFocusable(false);
        interpolation_opt.setPaintLabels(true);
        
        JPanel p6 = new JPanel();
        p6.setBackground(MainWindow.bg_color);
        
        p6.add(new JLabel("Trap 1: "));
        p6.add(trap1_color_label);
        p6.add(new JLabel("  Trap 2: "));
        p6.add(trap2_color_label);
        p6.add(new JLabel("  Trap 3: "));
        p6.add(trap3_color_label);
        p6.add(new JLabel("  Interpolation: "));
        p6.add(interpolation_opt);
        
        JPanel p7 = new JPanel();
        p7.setBackground(MainWindow.bg_color);
        
        JComboBox colors = new JComboBox(new String[] {"Per Trap", "Random", "Hue/Arg HSB", "Hue/Arg LCH"});
        colors.setFocusable(false);
        colors.setSelectedIndex(ots.trapColorFillingMethod);
        colors.setToolTipText("Sets the trap color filling method.");

        p7.add(new JLabel("Color Filling Method: "));
        p7.add(colors);
              
        color_options_panel = new JPanel();
        color_options_panel.setPreferredSize(new Dimension(580, 160));
        color_options_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Color Options", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        color_options_panel.setLayout(new GridLayout(3, 1));
        color_options_panel.setBackground(MainWindow.bg_color);
        
        JPanel trap_options_panel = new JPanel();
        trap_options_panel.setPreferredSize(new Dimension(580, 170));
        trap_options_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Trap Options", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        trap_options_panel.setLayout(new GridLayout(4, 1));
        trap_options_panel.setBackground(MainWindow.bg_color);
        
        color_options_panel.add(p4);
        color_options_panel.add(p7);
        color_options_panel.add(p6);

        trap_options_panel.add(p1);
        trap_options_panel.add(p2);
        trap_options_panel.add(p3);
        trap_options_panel.add(p5);
        
        options_panel.add(trap_options_panel);
        options_panel.add(color_options_panel);

        JButton ok = new JButton("Ok");
        ok.setFocusable(false);
        
        getRootPane().setDefaultButton( ok );

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                double temp, temp2, temp3, temp4, temp5, temp6, temp7;
                try {
                    temp = Double.parseDouble(trap_norm_field.getText());
                    temp2 = Double.parseDouble(real_textfield.getText());
                    temp3 = Double.parseDouble(imaginary_textfield.getText());
                    temp4 = Double.parseDouble(trap_length_field.getText());
                    temp5 = Double.parseDouble(trap_width_field.getText());
                    temp6 = Double.parseDouble(trap_threshold_field.getText());
                    temp7 = Double.parseDouble(trap_intensity_field.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this_frame, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (temp4 < 0) {
                    JOptionPane.showMessageDialog(this_frame, "Trap Length must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (temp5 < 0) {
                    JOptionPane.showMessageDialog(this_frame, "Trap Width must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (temp6 < 0) {
                    JOptionPane.showMessageDialog(this_frame, "Trap Max Distance must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (temp7 < 0) {
                    JOptionPane.showMessageDialog(this_frame, "Trap Intesity must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ots.trapType = orbit_traps_combo.getSelectedIndex();
                ots.useTraps = orbit_traps_opt.isSelected();
                ots.trapLength = temp4;
                ots.trapWidth = temp5;
                ots.trapMaxDistance = temp6;
                ots.trapIntensity = temp7;
                ots.trapNorm = temp;
                ots.trapPoint[0] = temp2;
                ots.trapPoint[1] = temp3;
                ots.trapBlending = blend_opt.getValue() / 100.0;
                ots.lineType = lines_function_combo.getSelectedIndex();
                ots.trapColorMethod = color_method_combo.getSelectedIndex();
                ots.trapColor1 = trap1_color_label.getBackground();
                ots.trapColor2 = trap2_color_label.getBackground();
                ots.trapColor3 = trap3_color_label.getBackground();
                ots.trapColorInterpolation = interpolation_opt.getValue() / 100.0;
                ots.trapIncludeEscaped = include_escaped_opt.isSelected();
                ots.trapIncludeNotEscaped = include_notescaped_opt.isSelected();
                ots.trapColorFillingMethod = colors.getSelectedIndex();

                if(image != null) {
                    ots.trapImage = image;
                    ImageOrbitTrap.image = ots.trapImage;
                }

                ptra2.setOrbitTrapSettings();
                ptra2.setEnabled(true);
                dispose();

            }
        });

        JButton close = new JButton("Cancel");
        close.setFocusable(false);
        close.addActionListener(new ActionListener() {

            @Override
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
        round_panel.setPreferredSize(new Dimension(630, 440));
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

        if(index != MainWindow.IMAGE_TRAP) {
            trap_threshold_field.setEnabled(true);
            trap_intensity_field.setEnabled(true);
            setComponentState(color_options_panel ,true);
        }
        else {
            trap_threshold_field.setEnabled(false);
            trap_intensity_field.setEnabled(false);
            setComponentState(color_options_panel ,false);
        }

        if (index == MainWindow.POINT_N_NORM_TRAP || index == MainWindow.N_NORM_TRAP || index == MainWindow.N_NORM_CROSS_TRAP || index == MainWindow.N_NORM_POINT_TRAP || index == MainWindow.N_NORM_POINT_N_NORM_TRAP || index == MainWindow.GOLDEN_RATIO_SPIRAL_POINT_N_NORM_TRAP || index == MainWindow.GOLDEN_RATIO_SPIRAL_N_NORM_TRAP || index == MainWindow.STALKS_POINT_N_NORM_TRAP || index == MainWindow.STALKS_N_NORM_TRAP) {
            trap_norm_field.setEnabled(true);
        } else {
            trap_norm_field.setEnabled(false);
        }

        if (index == MainWindow.POINT_RHOMBUS_TRAP || index == MainWindow.POINT_SQUARE_TRAP || index == MainWindow.POINT_TRAP || index == MainWindow.POINT_N_NORM_TRAP) {
            trap_width_field.setEnabled(false);
        } else {
            trap_width_field.setEnabled(true);
        }
        
        if (index == MainWindow.GOLDEN_RATIO_SPIRAL_TRAP || index == MainWindow.STALKS_TRAP) {
            trap_length_field.setEnabled(false);
        } else {
            trap_length_field.setEnabled(true);
        }
        
        if(index == MainWindow.CROSS_TRAP || index ==  MainWindow.RE_TRAP || index ==  MainWindow.IM_TRAP || index ==  MainWindow.CIRCLE_CROSS_TRAP || index ==  MainWindow.SQUARE_CROSS_TRAP || index ==  MainWindow.RHOMBUS_CROSS_TRAP || index ==  MainWindow.N_NORM_CROSS_TRAP || index == MainWindow.GOLDEN_RATIO_SPIRAL_CROSS_TRAP || index == MainWindow.STALKS_CROSS_TRAP) {
            lines_function_combo.setEnabled(true);
        }
        else {
            lines_function_combo.setEnabled(false);
        }
        
        blend_opt.setEnabled(color_method_combo.getSelectedIndex() == 3 && index != MainWindow.IMAGE_TRAP);

        load_image_button.setEnabled(index == MainWindow.IMAGE_TRAP);

    }

    private void loadPatternImage(Component parent) {

        JFileChooser file_chooser = new JFileChooser(".");

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        FileFilter imageFilter = new FileNameExtensionFilter(
                "Image Files", ImageIO.getReaderFileSuffixes());

        file_chooser.addChoosableFileFilter(imageFilter);

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
                file_chooser.setSelectedFile(new File(file_name));
            }
        });

        int returnVal = file_chooser.showDialog(parent, "Load Pattern Image");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = file_chooser.getSelectedFile().getPath();

            if(path != null && !path.isEmpty()) {
                try {
                    image = ImageIO.read(new File(path));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this_frame, "Error while loading the " + path + " file.", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void setComponentState(Component component, boolean state) {
        component.setEnabled(state);
        if (component instanceof JComponent) {
            JComponent a = (JComponent) component;
            Component comp[] = a.getComponents();
            for (int i = 0; i < comp.length; i++) {
                setComponentState(comp[i], state);
            }
        }
    }

}
